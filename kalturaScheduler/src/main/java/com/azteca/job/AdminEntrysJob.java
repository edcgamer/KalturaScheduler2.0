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
import com.azteca.persistence.entities.SistemaFecha;
import com.azteca.quartz.SchedulerFactory;
import com.azteca.service.ConsultasBDService;
import com.azteca.service.ConsultasKalturaService;
import com.azteca.utils.FechaParser;
import com.azteca.utils.MailService;
import com.azteca.utils.TimeParser;
import com.kaltura.client.enums.KalturaMediaType;
import com.kaltura.client.types.KalturaCategory;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.JobExecutionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *
 * @author Santa
 */
public class AdminEntrysJob extends QuartzJobBean {

    private ApplicationContext applicationContext;
    private EntryJobModel entryJobModel;

    @Override
    protected void executeInternal(JobExecutionContext arg0) {
        try {
            this.entryJobModel = (EntryJobModel) arg0.getJobDetail().getJobDataMap().get("entryJobModel");
            if (entryJobModel.getFecha() == null) {
                this.entryJobModel.setBanderaFecha(false);
            }
            if (entryJobModel.getPrograma() == null) {
                this.entryJobModel.setBanderaPrograma(false);
            }
            this.applicationContext = new ClassPathXmlApplicationContext("Spring-Quartz.xml");
            ConsultasBDService rep = this.applicationContext.getBean("scheduler", ConsultasBDService.class);
            KalturaCuenta kc = rep.findCuentasByNombre(this.entryJobModel.getCuenta()).get(0);
            System.out.println("--------------------" + kc.getNombre()+" "+kc.getUsuario()+" "+kc.getAdmin()+" "+kc.getPartner());
            ConsultasKalturaService cks = new ConsultasKalturaService(kc.getUsuario(),
                    kc.getAdmin(), Integer.parseInt(kc.getPartner()), KalturaMediaType.VIDEO);
            if (this.entryJobModel.isClon()) {
                arg0.getScheduler().deleteJob(arg0.getJobDetail().getKey());
                System.out.println("se borro el hilo temporal");
                System.out.println(arg0.getScheduler().checkExists(arg0.getJobDetail().getKey()));
            }
            Date fechaIni = new Date();
            for (SistemaFecha sf : this.entryJobModel.getFechas()) {
                System.out.println(sf.getFechaInicio()+" "+sf.getFechaFinal());
                if (this.entryJobModel.isBanderaFecha() && sf.equals(this.entryJobModel.getFecha())) {
                    this.entryJobModel.setBanderaFecha(false);
                    System.out.println("reiniciando la busqueda en la fecha:" + this.entryJobModel.getFecha().getFechaInicio()+" "+entryJobModel.getPrograma());
                }
                if (!this.entryJobModel.isBanderaFecha()) {
                    System.out.println("consultas "+arg0.getJobDetail().getKey()+" "+sf.getFechaInicio()+" "+sf.getFechaFinal());
                    if (!this.entryJobModel.isBanderaPrograma()) {
                        this.entryJobModel.setPrograma("");
                    }
                    this.entryJobModel.setFecha(sf);
                    for (KalturaFabrica kf : rep.findKalturaFabricaByNombreAndUnidad(this.entryJobModel.getFabrica(),rep.findKalturaUnidadByNombre(entryJobModel.getUnidad()).get(0))) {
                        System.out.println("Fabrica "+kf.getNombre() + "  categoria "+kf.getListaProgramas().size());
//                        if (!kf.getNombre().equals("FABRICA_BARRA DE OPINION")&&!kf.getNombre().equals("FABRICA_SIN FABRICA")) {
//                        if(kf.getNombre().equals("FABRICA_SIN_CATEGORIA")){
                            if(true){
                            System.out.println("Se haran la captura de nuevos entrys "+kf.getNombre());
                            System.out.println("--------------");
                            if(!kf.getListaProgramas().isEmpty())
                                System.out.println("no toy vacio");
                            List<KalturaPrograma> lista=kf.getListaProgramas();
                            for (KalturaPrograma kp :lista) {
                                System.out.println("**********************");
                                System.out.println("programa:" +kp.getNombre());
                                if (this.entryJobModel.isBanderaPrograma() && kp.getNombre().equalsIgnoreCase(this.entryJobModel.getPrograma())) {
                                    this.entryJobModel.setBanderaPrograma(false);
                                    System.out.println("reiniciando la busqueda en el programa:" + this.entryJobModel.getPrograma());
                                }
                                if (!this.entryJobModel.isBanderaPrograma()) {
                                    this.entryJobModel.setPrograma(kp.getNombre());
                                    System.out.println(kp.getId() + " " + kp.getNombre() + " " + kp.getIdKaltura());
                                    System.out.println(cks.entriesPorSemana(kp.getIdKaltura(), sf) + " ======por semana en el programa " + kp.getNombre());
                                    int contador=0;
                                    for (; this.entryJobModel.getOffset() <= (cks.entriesPorSemana(kp.getIdKaltura(), sf) / this.entryJobModel.getSegmento()) + 1;
                                            this.entryJobModel.setOffset(this.entryJobModel.getOffset() + 1)) {
                                        for (KalturaEntry kmt : cks.consultaEntrySemana(this.entryJobModel.getSegmento(),
                                                this.entryJobModel.getOffset(), kp.getIdKaltura() + "", sf, kp)) {
                                            if (rep.findKalturaEntryByentryId(kmt.getEntryId()).isEmpty()) {
                                                rep.saveKalturaEntry(kmt);
                                                System.out.println("se guardo el entry " + kmt.getEntryId());
                                            } else {
                                                System.out.println("no se puede guardar el entry " + kmt.getEntryId());
                                            }
                                            contador++;
                                        }
                                        System.out.println("llevamos la cantidad de "+contador);
                                    }
                                    this.entryJobModel.setOffset(1);
                                }
                            }
                        }
                    }
                }
            }

//            boolean clon = (boolean) arg0.getJobDetail().getJobDataMap().get("clon");
//            this.segmento = (Integer) arg0.getJobDetail().getJobDataMap().get("segmento");
//            this.offsetDummy = (int) arg0.getJobDetail().getJobDataMap().get("offset");
//            this.cuentaDummy = (KalturaCuenta) arg0.getJobDetail().getJobDataMap().get("cuenta");
//            this.nombreFabricaBandera = (String) arg0.getJobDetail().getJobDataMap().get("nombreFabricaBandera");
//            this.nombreProgramaBandera = (String) arg0.getJobDetail().getJobDataMap().get("nombreProgramaBandera");
//            boolean banderaClonActivo = clon;
//            boolean banderaFabricaActivo = (boolean) arg0.getJobDetail().getJobDataMap().get("banderaFabrica");
//            boolean banderaProgramaActivo = clon;
//            Date fechaInicio= new Date();
//            int totalEntrys=0;
//            if(nombreProgramaBandera.equals("")){
//                banderaProgramaActivo=false;
//            }
//            if(nombreFabricaBandera.equals("")){
//                banderaFabricaActivo=false;
//            }
//            this.applicationContext = new ClassPathXmlApplicationContext("Spring-Quartz.xml");
//            ConsultasBDService rep = this.applicationContext.getBean("scheduler", ConsultasBDService.class);
//            ConsultasKalturaService cks;
//            System.out.println("haciendo el admin entrys");
//            int noGuardados = 0;
//            int totalRegistros = 0;
//            if (clon) {
//                arg0.getScheduler().deleteJob(arg0.getJobDetail().getKey());
//                System.out.println("se borro el hilo temporal");
//                System.out.println(arg0.getScheduler().checkExists(arg0.getJobDetail().getKey()));
//            }
//            for (KalturaCuenta cts : rep.findAllCuentas()) {
//                System.out.println("--------------------------------------------------------------");
//                if (!cts.getPartner().equals(0)) {
//                    if (banderaClonActivo && cts.getNombre().equalsIgnoreCase(this.cuentaDummy.getNombre())) {
//                        banderaClonActivo = false;
//                    }
//                    if (!banderaClonActivo) { 
//                        System.out.println("Se haran las consultas de la cuenta " + cts.getNombre() + " con el offset ");
//                        this.cuentaDummy = cts;
//                        if (cts.getNombre().equalsIgnoreCase("capitulos_Vod")) {
//                            System.out.println(cts.getNombre());
//                            cks = new ConsultasKalturaService(cts.getUser(), cts.getAdmin(), cts.getPartner(), KalturaMediaType.VIDEO);
//                            for (KalturaCategory kc : cks.consultaNivelCategoria(0, null)) {
//                                System.out.println("");
////                                if(true){
//                                    if(kc.name.equals("Sin categoria")){
//                                System.out.println(kc.name + "  fabrica bandera :" + this.nombreFabricaBandera +" fabrica id"+kc.id);
//                                if (banderaFabricaActivo&&this.nombreFabricaBandera.equals(kc.name)) {
//                                    banderaFabricaActivo = false;
//                                }
//                                if (!banderaFabricaActivo) {
//                                    if(!banderaProgramaActivo){
//                                        this.nombreProgramaBandera = "";
//                                    }
//                                    this.nombreFabricaBandera = kc.name;
//                                    fechaInicio = new Date();
//                                    if (rep.findCatFabByNombre(kc.name).isEmpty()) {
////                                        rep.saveCatFabrica(new CatFabrica(kc.name,kc.id+"", cts));
//                                    } else {
//                                        System.out.println("fabrica " + kc.name + " ya existia----");
//                                    }
//                                    for (KalturaCategory kcc : cks.consultaNivelCategoria(1, kc.id)) {
//                                        System.out.println("Programa :"+kcc.name +"programa bandera"+this.nombreProgramaBandera);
//                                        if(banderaProgramaActivo&&nombreProgramaBandera.equals(kcc.name)){
//                                            banderaProgramaActivo=false;
//                                        }
//                                        if (!banderaProgramaActivo) {
//                                            nombreProgramaBandera=kcc.name;
//                                            if (rep.findCatProgByNombre(kcc.name).isEmpty()) {
//                                                rep.saveCatPrograma(new CatProgramas(kcc.name,kc.id+"", rep.findCatFabByNombre(kc.name).get(0)));
//                                            } else {
//                                                System.out.println("produccion " + kcc.name + " ya existia----");
//                                            }
//                                            System.out.println(cks.entriesPorSemana(kcc.id) + "===por semana");
//                                            for (; this.offsetDummy <= (cks.entriesPorSemana(kcc.id)/ this.segmento) + 1; this.offsetDummy++) {
//                                                for (KalturaMediaEntryModel kmt : cks.consultaEntrySemana(this.segmento, this.offsetDummy, kcc.id + "")) {
//                                                    
//                                                    totalRegistros++;
//                                                    System.out.println("Entry id :" + kmt.getKalturaMediaEntry().id);
//                                                    if (rep.findCatMaterialByname(kmt.getKalturaMediaEntry().id).isEmpty()) {
//                                                        rep.saveCatMaterial(new CatMaterial(kmt.getFechaCreacion(), kmt.getName(), kmt.getKalturaMediaEntry().id, kmt.getTamanio(), kmt.getDuration(), rep.findCatProgByNombre(kcc.name).get(0)));
//                                                        totalEntrys++;
//                                                    } else {
//                                                        noGuardados++;
//                                                        System.out.println("--------ya existia el entry" + kmt.getKalturaMediaEntry().name + " con el id " + kmt.getKalturaMediaEntry().id + "------");
//                                                    }
//                                                }
//                                                System.out.println("------------se acabo el segmento "+this.offsetDummy+"--------");
//                                            }
//                                            
//                                            this.offsetDummy=1;
//                                            System.out.println("Se guardaron " + (totalRegistros - noGuardados) + " de un total de " + totalRegistros + "");
//                                            System.out.println(totalEntrys);
//                                            noGuardados = 0;
//                                            totalRegistros = 0;
//                                        }
//                                    }
//                                }
//                                System.out.println("Tiempo que le tomo a una fabrica realizarse" + TimeParser.tiempoTranscurrido(fechaInicio, new Date()));
//                            }
//                            }
//                        }
//                    }
//                    Date fechaActual = new Date();
//                    MailService mailService = new MailService(this.applicationContext);
//                    mailService.mandaCorreo("se realizo Correctamente la indexada de nuevos entrys id iniciado " + cts.getNombre() + " " + fechaActual, "Inyeccion nuevos entrys");
//                }
//            }
            MailService mailService = new MailService(this.applicationContext);
            mailService.mandaCorreo("se guardo bien la fabrica  "+this.entryJobModel.getFabrica()+"  cuenta "+this.entryJobModel.getCuenta(),"se guardo correctamente una fabrica cuenta "+this.entryJobModel.getCuenta());
            System.out.println("termino correctamente " + this.entryJobModel.getFabrica() + " " + this.entryJobModel.getCuenta() + " y me tomo:" + TimeParser.tiempoTranscurrido(fechaIni, new Date()));
        } catch (Exception ex) {
            System.out.println("mori cuenta:" + entryJobModel.getCuenta() + " unidad:" + entryJobModel.getUnidad() +" fabrica:"+this.entryJobModel.getFabrica()+ " fecha:" + entryJobModel.getFecha().getFechaInicio() + " programa:" + entryJobModel.getPrograma());
            ex.printStackTrace();
            this.entryJobModel.setClon(true);
            this.entryJobModel.setBanderaPrograma(true);
            this.entryJobModel.setBanderaFecha(true);
            this.entryJobModel.setNombre(this.entryJobModel.getNombre() + new Date().getTime());
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 1);
            this.entryJobModel.setCron(FechaParser.dateFilter(calendar.getTime()));
            SchedulerFactory schedulerFactory = new SchedulerFactory();
            schedulerFactory.setScheduler(arg0.getScheduler());
            schedulerFactory.creaAdminEntrysJob(this.entryJobModel, 0);
            MailService mailService = new MailService(this.applicationContext);
            mailService.mandaCorreo("Hubo un error al guardar los nuevos entrys de kaltura con la cuenta "
                    + this.entryJobModel.getCuenta() +" fabrica :"+this.entryJobModel.getFabrica()+  " segmento " + this.entryJobModel.getSegmento() + " offset "
                    + this.entryJobModel.getOffset() + " " + ex, "Error al insertar los nuevos entrys");
        }
//            Logger.getLogger(SchedulerFactory.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("fabrica " + this.nombreFabricaBandera + " "+this.nombreProgramaBandera+" cuenta " + this.cuentaDummy.getNombre() + " segmento " + this.segmento + " offset " + this.offsetDummy);
//            System.out.println("El proceso se murio y se clono");
//            SchedulerFactory schedulerFactory = new SchedulerFactory();
//            schedulerFactory.setScheduler(arg0.getScheduler());
//            Date date = new Date();
//            Calendar calendar = GregorianCalendar.getInstance();
//            calendar.setTime(date);
//            calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 1);
//            schedulerFactory.creaAdminEntrysJob("AdminEntrysJob" + date.getTime() + "", this.nombreFabricaBandera, this.nombreProgramaBandera, true, true, this.segmento, this.cuentaDummy, this.offsetDummy, FechaParser.dateFilter(calendar.getTime()));
//           
//        }
    }
}
