/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azteca.job;

import com.azteca.model.EntryJobModel;
import com.azteca.model.KalturaMediaEntryModel;
import com.azteca.persistence.entities.KalturaCuenta;
import com.azteca.persistence.entities.KalturaEntry;
import com.azteca.persistence.entities.KalturaFabrica;
import com.azteca.persistence.entities.KalturaPrograma;
import com.azteca.persistence.entities.SistemaReporte;
import com.azteca.persistence.entities.SistemaFecha;
import com.azteca.quartz.SchedulerFactory;
import com.azteca.service.ConsultasBDService;
import com.azteca.service.ConsultasKalturaService;
import com.azteca.utils.FechaParser;
import com.azteca.utils.KalturaObjectsParser;
import com.azteca.utils.MailService;
import com.azteca.utils.TimeParser;
import com.kaltura.client.enums.KalturaMediaType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.quartz.JobExecutionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *
 * @author Santa
 */
public class WeekConsumeByPartnerJob extends QuartzJobBean {

    private ApplicationContext applicationContext;
    private EntryJobModel entryJobModel;

    @Override
    protected void executeInternal(JobExecutionContext arg0) {
        try {
            System.out.println("se inicio el job weekConsume" + arg0.getJobDetail().getKey().getName());
            this.entryJobModel = (EntryJobModel) arg0.getJobDetail().getJobDataMap().get("entryJobModel");
            if (this.entryJobModel.getFecha() == null) {
                this.entryJobModel.setBanderaFecha(false);
            }
            if (this.entryJobModel.getPrograma() == null) {
                this.entryJobModel.setBanderaPrograma(false);
            }
            if (this.entryJobModel.isClon()) {
                arg0.getScheduler().deleteJob(arg0.getJobDetail().getKey());
                System.out.println("se borro el hilo temporal");
            }
            Date fechaInicio = new Date();
            this.applicationContext = new ClassPathXmlApplicationContext("Spring-Quartz.xml");
            ConsultasBDService rep = this.applicationContext.getBean("scheduler", ConsultasBDService.class);
            KalturaCuenta kc = rep.findCuentasByNombre(this.entryJobModel.getCuenta()).get(0);
            System.out.println("--------------------" + kc.getNombre());
            ConsultasKalturaService cks = new ConsultasKalturaService(kc.getUsuario(),
                    kc.getAdmin(), Integer.parseInt(kc.getPartner()), KalturaMediaType.VIDEO);

            for (SistemaFecha sf : this.entryJobModel.getFechas()) {
                if (this.entryJobModel.isBanderaFecha() && sf.equals(this.entryJobModel.getFecha())) {
                    this.entryJobModel.setBanderaFecha(false);
                }
                if (!this.entryJobModel.isBanderaFecha()) {
                    if (!this.entryJobModel.isBanderaPrograma()) {
                        this.entryJobModel.setPrograma("");
                    }
                    this.entryJobModel.setFecha(sf);
                    for (KalturaFabrica kf : rep.findKalturaFabricaByNombreAndUnidad(this.entryJobModel.getFabrica(),rep.findKalturaUnidadByNombre(this.entryJobModel.getUnidad()).get(0))) {
//                    for (KalturaFabrica kf : rep.findKalturaFabricaByNombre(this.entryJobModel.getFabrica())) {
                        if (true) {
                            System.out.println(kf.getNombre() + "  categoria");
                            for (KalturaPrograma kp : kf.getListaProgramas()) {
                                System.out.println("Programa :" + kp.getNombre());
                                if (this.entryJobModel.isBanderaPrograma() && kp.getNombre().equalsIgnoreCase(this.entryJobModel.getPrograma())) {
                                    this.entryJobModel.setBanderaPrograma(false);
                                }
                                if (!this.entryJobModel.isBanderaPrograma()) {
                                    this.entryJobModel.setPrograma(kp.getNombre());
                                    int valor = 0;
                                    List<String> cadenasGuardadas = new ArrayList<>();
                                    System.out.println(rep.findentrysByFechaAndPrograma(new Date(1230768900001L), sf.getFechaFinal(), kp).size()+" "+kp.getListraEntry().size());
                                    System.out.println("cantidad de entrys pro programa:"+kp.getListraEntry().size()+" vamos en el offset:"+entryJobModel.getOffset());
                                    List<KalturaEntry> listaEntrys=rep.findentrysByFechaAndPrograma(new Date(1230768900001L), sf.getFechaFinal(), kp);
//                                    listaEntrys=kp.getListraEntry();
                                    for (; entryJobModel.getOffset() < listaEntrys.size(); entryJobModel.setOffset(entryJobModel.getOffset()+500)) {
                                        System.out.println("offset:"+entryJobModel.getOffset());
                                        if (entryJobModel.getOffset() + 500 > listaEntrys.size()) {
                                            valor = listaEntrys.size();
                                        } else {
                                            valor = entryJobModel.getOffset() + 500;
                                        }
                                        System.out.println(kp.getId() + " " + kp.getNombre() + " " + kp.getIdKaltura());
                                        List<KalturaEntry> materialesTemp = listaEntrys.subList(entryJobModel.getOffset(), valor);
                                        String cadena = KalturaObjectsParser.generaStringDataMaterials(listaEntrys.subList(entryJobModel.getOffset(), valor));
                                        List<KalturaMediaEntryModel> listamodelTmp = cks.consultaMinutosVistosWPager(cadena, sf);

                                        for (KalturaMediaEntryModel kmem : listamodelTmp) {
                                            List<KalturaEntry> catMats = rep.findKalturaEntryByentryId(kmem.getEntry());
                                            KalturaEntry catMat = null;
                                            if (!catMats.isEmpty()) {
                                                catMat = catMats.get(0);
                                                System.out.println(catMat.getEntryId() + " " + catMat.getNombre());
                                            }

                                            if (rep.findSistemaReporteByNombreAndFechaCorte(kmem.getEntry(), new Date()).isEmpty() && catMat != null) {
                                                cadenasGuardadas.add(kmem.getEntry());
                                                rep.saveReporte(new SistemaReporte(catMat.getNombre(), kmem.getEntry(), catMat.getTamanio(),
                                                        Long.parseLong(catMat.getDuracion().toString()), catMat.getFechaCreacion(), new Date(), kmem.getTiempoVisto(),
                                                        catMat.getKalturaPrograma().getNombre(), kf.getNombre(), kf.getKalturaUnidad().getNombre()));
                                            } else {
                                                System.out.println("Ya hay referencia del entry------------- " + kmem.getEntry());
                                            }

                                        }
                                        for (KalturaEntry caMa : materialesTemp) {
                                            if (cadenasGuardadas.indexOf(caMa.getEntryId()) < 0) {
                                                if (rep.findSistemaReporteByNombreAndFechaCorte(caMa.getEntryId(), new Date()).isEmpty()) {
                                                    System.out.println("se guarda Entry sin ver");
                                                    System.out.println(caMa.getEntryId() + "--------------");
                                                    rep.saveReporte(new SistemaReporte(caMa.getNombre(), caMa.getEntryId(), caMa.getTamanio(),
                                                            Long.parseLong(caMa.getDuracion().toString()), caMa.getFechaCreacion(), new Date(), 0L,
                                                            caMa.getKalturaPrograma().getNombre(), kf.getNombre(), kf.getKalturaUnidad().getNombre()));
                                                }
                                                else{
                                                    System.out.println("Ya hay referencia del entry------------- " + caMa.getEntryId());
                                                }
                                            }
                                        }
                                    }
                                    this.entryJobModel.setOffset(0);
                                }
                            }
                        }
                    }
                }
            }

            System.out.println("Se termino correctamente el job :" + arg0.getJobDetail().getKey().getName() + " Le tomo la cantidad de tiempo  :" + TimeParser.tiempoTranscurrido(fechaInicio, new Date()));
            MailService mailService = new MailService(this.applicationContext);
            mailService.mandaCorreo("todo se guardo bien",this.entryJobModel.getFabrica()+" en la cuenta "+this.entryJobModel.getCuenta());
        } catch (Exception ex) {
            System.out.println("mori cuenta:"+entryJobModel.getCuenta()+" fabrica:"+entryJobModel.getFabrica()+" programa:"+entryJobModel.getPrograma());
            ex.printStackTrace();
            this.entryJobModel.setClon(true);
            this.entryJobModel.setBanderaFecha(true);
            this.entryJobModel.setBanderaPrograma(true);
            this.entryJobModel.setNombre(this.entryJobModel.getNombre() + new Date().getTime());
            Date date= new Date();
            this.entryJobModel.setNombre(entryJobModel.getNombre()+date.toString());
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 1);
            this.entryJobModel.setCron(FechaParser.dateFilter(calendar.getTime()));
            SchedulerFactory schedulerFactory = new SchedulerFactory();
            schedulerFactory.setScheduler(arg0.getScheduler());
            schedulerFactory.creaWeekConsumeJob(this.entryJobModel, 0);
            MailService mailService = new MailService(this.applicationContext);
            mailService.mandaCorreo("Hubo un error al guardar los nuevos entrys de kaltura con la cuenta "
                    + this.entryJobModel.getCuenta() + " segmento " + this.entryJobModel.getSegmento() + " offset "
                    + this.entryJobModel.getOffset() + " " + ex, "Error al insertar los nuevos entrys");
        }
    }
}
