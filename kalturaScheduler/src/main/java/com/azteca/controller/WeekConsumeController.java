/*
 * Copyright 2014 Santa.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.azteca.controller;

import com.azteca.model.KalturaMediaEntryModel;

import com.azteca.persistence.entities.KalturaCuenta;
import com.azteca.persistence.entities.KalturaMaterial;
import com.azteca.quartz.SchedulerFactory;
import com.azteca.service.ConsultasBDService;
import com.azteca.service.ConsultasKalturaService;
import com.azteca.utils.KalturaObjectsParser;
import com.azteca.utils.MailService;
import com.azteca.utils.TimeParser;
import com.kaltura.client.KalturaApiException;
import com.kaltura.client.enums.KalturaMediaType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Santa
 */
public class WeekConsumeController {

    private ApplicationContext applicationContext;
    private KalturaCuenta cuenta;
    private String nombreFabricaBandera;
    private String nombreProgramaBandera;
    private JobDetail jobDetail;
    private Scheduler scheduler;
    private int tablas;
    private int offset;

    public void consultaEntrysSemanaPorCuenta(String nombre, String nombrePrograma, String nombreFabrica, boolean clon, int tablas) {
//        try {
//            MailService mailService = new MailService(this.applicationContext);
//            mailService.mandaCorreo("me inicie", "se inicio");
//            this.tablas = tablas;
//            System.out.println("haciendo entrys");
//            this.nombreFabricaBandera = nombreFabrica;
//            this.nombreProgramaBandera = nombrePrograma;
//            boolean banderaFabricaActivo = clon;
//            boolean banderaProgramaActivo = clon;
//            if (nombreProgramaBandera.equals("")) {
//                banderaProgramaActivo = false;
//            }
//            if (nombreFabricaBandera.equals("")) {
//                banderaFabricaActivo = false;
//            }
//            ConsultasBDService rep = this.applicationContext.getBean("scheduler", ConsultasBDService.class);
//            ConsultasKalturaService ckes;
//            int valor = 0;
//            System.out.println(nombre + "-------------" + nombreFabricaBandera + " " + nombreProgramaBandera + "   " + rep.findCuentasByNombre(nombre).size());
//            for (KalturaCuenta cts : rep.findCuentasByNombre(nombre)) {
//                System.out.println(cts.getNombre() + " " + cts.getCatFabricas().size());
//                ckes = new ConsultasKalturaService(cts.getUser(), cts.getAdmin(), cts.getPartner(), KalturaMediaType.VIDEO);
//                this.cuenta = cts;
//                for (CatFabrica catFab : cts.getCatFabricas()) {
//                    if(catFab.getNombre().equals("Sin categoria")){
//                    System.out.println(banderaFabricaActivo + " " + this.nombreFabricaBandera.equals(catFab.getNombre()) + " " + catFab.getNombre());
//                    if (banderaFabricaActivo && this.nombreFabricaBandera.equalsIgnoreCase(catFab.getNombre())) {
//                        System.out.println("entre");
//                        banderaFabricaActivo = false;
//                    }
//                    if (!banderaFabricaActivo) {
//                        if (!banderaProgramaActivo) {
//                            this.nombreProgramaBandera = "";
//                        }
//                        this.nombreFabricaBandera = catFab.getNombre();
//                        System.out.println("fabrica " + catFab.getNombre() + " " + catFab.getId());
//                        for (CatProgramas catPro : catFab.getCatProgramas()) {
//                            if (banderaProgramaActivo && this.nombreProgramaBandera.equals(catPro.getNombre())) {
//                                banderaProgramaActivo = false;
//                            }
//                            if (!banderaProgramaActivo) {
//                                this.nombreProgramaBandera = catPro.getNombre();
//                                System.out.println("programa " + catPro.getNombre() + " " + catPro.getId());
//                                for (int i = 53500; i < catPro.getCatMateriales().size(); i += 500) {
//                                    if (i + 500 > catPro.getCatMateriales().size()) {
//                                        valor = catPro.getCatMateriales().size();
//                                    } else {
//                                        valor = i + 500;
//                                    }
//                                    List<CatMaterial> materialesTemp = catPro.getCatMateriales().subList(i, valor);
//                                    String cadena = KalturaObjectsParser.generaStringDataMaterials(catPro.getCatMateriales().subList(i, valor));
//                                    List<KalturaMediaEntryModel> listamodelTmp = ckes.consultaMinutosVistosWPager(cadena);
//                                    List<String> cadenasGuardadas = new ArrayList<>();
//                                    int cantidad = 0;
//                                    for (KalturaMediaEntryModel kmem : listamodelTmp) {
//                                        if (rep.findCatReporteByEntryId(kmem.getEntry()).isEmpty()) {
//                                            cantidad++;
//                                            cadenasGuardadas.add(kmem.getEntry());
//                                            CatMaterial catMat = rep.findCatMaterialByname(kmem.getEntry()).get(0);
//                                            rep.saceCatReporteHist(new CatReporteHist(kmem.getEntry(), catFab.getNombre(), catPro.getNombre(), catMat.getNombre(), kmem.getTiempoVisto() + "", catMat.getTamanio() + "", catMat.getDuration() + "", new Date(), catMat.getFechaCreacion()));
//                                            System.out.println(kmem.getEntry() + " " + kmem.getTiempoVisto());
//                                            rep.saveCatReporte(new CatReporte(kmem.getEntry(), catFab.getNombre(), catPro.getNombre(), catMat.getNombre(), kmem.getTiempoVisto() + "", catMat.getTamanio() + "", catMat.getDuration() + "", new Date(), catMat.getFechaCreacion()));
//                                        } else {
//                                            System.out.println("Ya hay referencia del entry------------- " + kmem.getEntry());
//                                        }
//
//                                    }
//                                    for (CatMaterial caMa : materialesTemp) {
//                                        if (cadenasGuardadas.indexOf(caMa.getEntryId()) < 0) {
//                                            if (rep.findCatReporteByEntryId(caMa.getEntryId()).isEmpty()) {
//                                                cantidad++;
//                                                System.out.println("se guarda Entry sin ver");
//                                                System.out.println(caMa.getEntryId() + "--------------");
//                                                rep.saceCatReporteHist(new CatReporteHist(caMa.getEntryId(), catFab.getNombre(), caMa.getCatProgramas().getNombre(), caMa.getNombre(), "0.0", caMa.getTamanio() + "", caMa.getDuration() + "", new Date(), caMa.getFechaCreacion()));
//                                                rep.saveCatReporte(new CatReporte(caMa.getEntryId(), catFab.getNombre(), caMa.getCatProgramas().getNombre(), caMa.getNombre(), "0.0", caMa.getTamanio() + "", caMa.getDuration() + "", new Date(), caMa.getFechaCreacion()));
//                                            }
//                                        }
//                                    }
//                                    System.out.println("guardados " + cantidad + "de " + materialesTemp.size());
//                                }
//                            }
//                            mailService = new MailService(this.applicationContext);
//                            mailService.mandaCorreo( "se termino chido el "+catFab.getNombre() ,"Una fabrica correcta");
//                        }
//                    }
//                }
//                }
//            }
//        } catch (Exception ex) {
//            System.out.println(ex);
//            Date date = new Date();
//            System.out.println("cuenta " + jobDetail.getKey().getName());
//            System.out.println("El proceso se murio y se clono");
//            System.out.println(this.nombreFabricaBandera + " " + this.nombreProgramaBandera);
//            SchedulerFactory schedulerFactory = new SchedulerFactory();
//            schedulerFactory.setScheduler(this.scheduler);
//            Calendar calendar = GregorianCalendar.getInstance();
//            calendar.setTime(date);
//            calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 1);
//            schedulerFactory.creaWeekConsumeJob(0, this.cuenta, true, "clon", calendar, this.nombreFabricaBandera, this.nombreProgramaBandera, tablas);
//            MailService mailService = new MailService(this.applicationContext);
//            mailService.mandaCorreo("No termino correctamente " + "cuenta " + this.jobDetail.getKey().getName(), " NO Se termino correctamente entrys semanales " + this.jobDetail.getKey().getName());
//        }
    }

    public void consultaEntrysSemanaAntigua(int tablas, int offset, boolean clon, String nombre) {
        this.offset = offset;
//        try {
//            System.out.println("se inicio el job " + nombre);
//            ConsultasKalturaService cks;
//            ConsultasBDService rep = this.applicationContext.getBean("scheduler", ConsultasBDService.class);
////            KalturaMaterialPrograma wxpj;
//            Date fechaInicio = new Date();
//            if (clon) {
//                this.scheduler.deleteJob(this.jobDetail.getKey());
//                System.out.println("se borro el hilo temporal");
//            }
//            for (KalturaCuenta cts : rep.findCuentasByNombre(nombre)) {
//                this.cuenta = cts;
//                cks = new ConsultasKalturaService(cts.getUser(), cts.getAdmin(), cts.getPartner(), KalturaMediaType.VIDEO);
////                for (KalturaMaterial te : cts.getKalturaMaterials().subList(this.offset, cts.getKalturaMaterials().size())) {
//                    System.out.println(cks.consultaMinutosVistos(te.getEntryId()) + " " + cts.getNombre() + " " + te.getEntryId());
////                    wxpj = new KalturaMaterialPrograma();
//                    wxpj.setMinutesViewed(cks.consultaMinutosVistos(te.getEntryId()));
//                    wxpj.setFechaConsulta(new Date());
//                    wxpj.setEntryId(te);
//                    rep.saveWeekConsume(wxpj);
//                    offset++;
//                }
//                System.out.println(JavaVirtualMemoryController.msgComplete());
//            }
//            Date fechaFin = new Date();
////            MailService mailService = new MailService(this.applicationContext);
////            mailService.mandaCorreo("Se termino correctamente el job :" + arg0.getJobDetail().getKey().getName() + " Le tomo la cantidad de tiempo  :" + TimeParser.tiempoTranscurrido(fechaInicio, fechaFin), "Se termino correctamente entrys semanales " + arg0.getJobDetail().getKey().getName());
//            System.out.println("Se termino correctamente el job :" + jobDetail.getKey().getName() + " Le tomo la cantidad de tiempo  :" + TimeParser.tiempoTranscurrido(fechaInicio, fechaFin));
//        } catch (BeansException | KalturaApiException | SchedulerException ex) {
//            System.out.println(ex);
//            Date date = new Date();
//            System.out.println("cuenta " + jobDetail.getKey().getName() + " offset " + this.offset);
//            System.out.println("El proceso se murio y se clono");
//            SchedulerFactory schedulerFactory = new SchedulerFactory();
//            schedulerFactory.setScheduler(scheduler);
//            Calendar calendar = GregorianCalendar.getInstance();
//            calendar.setTime(date);
//            calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 10);
//            schedulerFactory.creaWeekConsumeJob(this.offset, this.cuenta, true, "clon", calendar, this.nombreFabricaBandera, this.nombreProgramaBandera, tablas);
//            MailService mailService = new MailService(this.applicationContext);
//            mailService.mandaCorreo("No termino correctamente " + "cuenta " + jobDetail.getKey().getName() + " offset " + this.offset, " NO Se termino correctamente entrys semanales " + this.jobDetail.getKey().getName());
//        }

    }

    public WeekConsumeController(ApplicationContext applicationContext, KalturaCuenta cuenta, String nombreFabricaBandera, String nombreProgramaBandera, JobDetail jobDetail, Scheduler scheduler) {
        this.applicationContext = applicationContext;
        this.cuenta = cuenta;
        this.nombreFabricaBandera = nombreFabricaBandera;
        this.nombreProgramaBandera = nombreProgramaBandera;
        this.jobDetail = jobDetail;
        this.scheduler = scheduler;
    }

    public WeekConsumeController(ApplicationContext applicationContext, KalturaCuenta cuenta, String nombreFabricaBandera, String nombreProgramaBandera) {
        this.applicationContext = applicationContext;
        this.cuenta = cuenta;
        this.nombreFabricaBandera = nombreFabricaBandera;
        this.nombreProgramaBandera = nombreProgramaBandera;
    }

    public WeekConsumeController() {
    }

}
