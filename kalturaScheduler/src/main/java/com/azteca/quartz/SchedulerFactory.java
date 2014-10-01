/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azteca.quartz;

import com.azteca.job.AdminCategoryJob;
import com.azteca.job.AdminEntrysJob;
import com.azteca.job.ComscoreJob;
import com.azteca.job.SubReportJob;
import com.azteca.job.WeekConsumeByPartnerJob;
import com.azteca.model.CategoryJobModel;
import com.azteca.model.EntryJobModel;
import com.azteca.persistence.entities.KalturaCuenta;
import com.azteca.persistence.entities.KalturaFabrica;
import com.azteca.persistence.entities.KalturaUnidad;
import com.azteca.persistence.entities.SistemaConsulta;
import com.azteca.persistence.entities.SistemaProceso;
import com.azteca.service.ConsultasBDService;
import com.azteca.utils.FechaParser;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author SANTA
 */
public class SchedulerFactory {

    private Scheduler scheduler;

    public SchedulerFactory() {
    }

    public void fabricaScheduler(ConsultasBDService rep) {
        System.out.println(new Date());
        for (SistemaProceso sp : rep.findAllSistemaProceso()) {
            for (SistemaConsulta sc : sp.getListaConsulta()) {
                int valor;
                switch (sc.getTipo()) {
                    case 1:
                        valor = 0;
//                        if (!sp.getListaFecha().isEmpty()) {
//                            System.out.println(sp.getListaFecha().get(0).getFechaInicio().getTime() / 1000 + " " + sp.getListaFecha().get(0).getFechaFinal().getTime() / 1000);
//                        }

                        for (KalturaCuenta kc : sp.getListaCuenta()) {
                            for (KalturaUnidad ku : kc.getListaUnidad()) {
//                                if(ku.getNombre().equals("UN_AZTECA"))
                                for (KalturaFabrica kf : ku.getListaFabricas()) {
                                    System.out.println(kf.getNombre());
//                                    if(kf.getNombre().equals("FABRICA_SIN_CATEGORIA"))
                                    creaAdminEntrysJob(new EntryJobModel(kf.getNombre(), ku.getNombre(),
                                            kc.getNombre(), sp.getCron(), sp.getListaFecha(), sp.getListaConsulta(),
                                            sp.getListaCuenta(), 1, 50, false, null, kc.getNombre() + "_" + kf.getNombre() + "_adminentry"), valor);
//                                    if (((rep.entrysPorFabrica(kf.getId()) / 500) * 2) == 0) {
                                        valor += 2;
//                                        System.out.println("----------" + 2);
//                                    } else {
//                                        valor += ((rep.entrysPorFabrica(kf.getId()) / 500) * 2);
//                                        System.out.println("----------" + (rep.entrysPorFabrica(kf.getId()) / 500) * 2);
//                                    }
                                }
                            }
                        }
                        break;
                    case 2:
                        valor = 0;

                        for (KalturaCuenta kc : sp.getListaCuenta()) {
//                            if (kc.getNombre().equals("Azteca")) {
                                for (KalturaUnidad ku : kc.getListaUnidad()) {
                                    for (KalturaFabrica kf : ku.getListaFabricas()) {
                                        System.out.println(kf.getNombre() + " " + kf.getIdKaltura());
                                        System.out.println(rep.entrysPorFabrica(kf.getId()));
//                                    if (!kf.getNombre().equals("FABRICA_DEPORTES_COMPRADOS") && !kf.getNombre().equals("FABRICA_DEPORTES_DEPORTES")
//                                            && !kf.getNombre().equals("FABRICA_DEPORTES_SIN FABRICA") && !kf.getNombre().equals("FABRICA_AZTECA_AMERICA_ESPECT¿?CULOS")
//                                            && !kf.getNombre().equals("FABRICA_AZTECA_AMERICA_SIN FABRICA") && !kf.getNombre().equals("FABRICA_AZTECA_AMERICA_TELENOVELAS")
//                                            && !kf.getNombre().equals("FABRICA_AZTECA_AMERICA_NOTICIAS") && !kf.getNombre().equals("FABRICA_AZTECA_AMERICA_BARRA DE OPINIÓN")
//                                            && !kf.getNombre().equals("FABRICA_SIN_CATEGOR¿?A") && !kf.getNombre().equals("FABRICA_AZTECA_AMERICA_DEPORTES")
//                                            && !kf.getNombre().equals("FABRICA_AZTECA_OTROS INTERNET") && !kf.getNombre().equals("FABRICA_AZTECA_SIN FABRICA")) {
                                        if (true) {
                                            creaWeekConsumeJob(new EntryJobModel(kf.getNombre(), ku.getNombre(),
                                                    kc.getNombre(), sp.getCron(), sp.getListaFecha(), sp.getListaConsulta(),
                                                    sp.getListaCuenta(), 0, 50, false, null, kc.getNombre() + "_" + kf.getNombre() + "_entryConsumo"), valor);
//                                        if (sc.getSistemaProceso().getId().equals(2)) {
//                                            valor += 15;
//                                        } else if (sc.getSistemaProceso().getId().equals(5)) {
//                                            valor += 10;
//                                        } else if (sc.getSistemaProceso().getId().equals(6)) {
//                                            valor += 2;
//                                        }
                                            if (((rep.entrysPorFabrica(kf.getId()) / 500) * 2) == 0) {
                                                valor += 2;
                                                System.out.println("----------" + 2);
                                            } else {
                                                valor += ((rep.entrysPorFabrica(kf.getId()) / 500) * 2);
                                                System.out.println("----------" + (rep.entrysPorFabrica(kf.getId()) / 500) * 2);
                                            }
                                        }
                                    }
                                }
//                            }
                        }
                        break;
                    case 3:
                        System.out.println("entree");
                        creaAdminCategory(new CategoryJobModel(sp.getCron(), sp.getListaFecha(), sp.getListaConsulta(),
                                sp.getListaCuenta()));
                        break;
                    case 4:
                        creaSubReportJob(sp, 0);
//                        creaSubReportJob(sp,1);
                        break;
                    case 5:
                        creaConsumeComscore(new EntryJobModel("", "",
                                                    "", sp.getCron(), sp.getListaFecha(), sp.getListaConsulta(),
                                                    sp.getListaCuenta(), 0, 50, false, null, "comscoreConsumo"));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void creaAdminCategory(CategoryJobModel categoryJobModel) {
        try {
            System.out.println("creando admin category job****************************************************** ");
            JobKey jobKey = new JobKey("adminCategory", "admin");
            JobDetail job = JobBuilder.newJob(AdminCategoryJob.class)
                    .withIdentity(jobKey).build();
            job.getJobDataMap().put("categoryJobModel", categoryJobModel);
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("Trigger_adminCategory", "admin")
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule(categoryJobModel.getCron()))
                    .build();
            System.out.println(categoryJobModel.getCron());
            this.getScheduler().scheduleJob(job, trigger);
        } catch (SchedulerException ex) {
            Logger.getLogger(SchedulerFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void creaConsumeComscore(EntryJobModel entryJobModel) {
        try {
            System.out.println("creando Comscore consume job****************************************************** ");
            JobKey jobKey = new JobKey(entryJobModel.getNombre(), "admin");
            JobDetail job = JobBuilder.newJob(ComscoreJob.class)
                    .withIdentity(jobKey).build();
            job.getJobDataMap().put("comscoreJobModel", entryJobModel);
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(entryJobModel+"Trigger_comscoreconsume", "admin")
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule(entryJobModel.getCron()))
                    .build();
            System.out.println(entryJobModel.getCron());
            this.getScheduler().scheduleJob(job, trigger);
        } catch (SchedulerException ex) {
            Logger.getLogger(SchedulerFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void creaSubReportJob(SistemaProceso sisPro, int novistos) {
        try {
            System.out.println("creando subReporte job ");
            JobKey jobKey = new JobKey("subReporte  " + novistos, "admin");
            JobDetail job = JobBuilder.newJob(SubReportJob.class)
                    .withIdentity(jobKey).build();
            job.getJobDataMap().put("noVistos", novistos);
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("Trigger_subReporte" + novistos, "admin")
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule(sisPro.getCron()))
                    .build();
            System.out.println(sisPro.getCron());
            this.getScheduler().scheduleJob(job, trigger);
        } catch (SchedulerException ex) {
            Logger.getLogger(SchedulerFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void creaAdminPartners() {
//        try {
//            System.out.println("creando admin job del partner " );
//            JobKey jobKey = new JobKey("adminPartner", "admin");
//            JobDetail job = JobBuilder.newJob(AdminPartners.class)
//                    .withIdentity(jobKey).build();
//            Trigger trigger = TriggerBuilder
//                    .newTrigger()
//                    .withIdentity("Trigger_adnminPartners", "admin")
//                    .withSchedule(
//                    CronScheduleBuilder.cronSchedule("0 */10 * * * ?"))
//                    .build();
//            System.out.println("0 */10 * * * ?");
//            this.getScheduler().scheduleJob(job, trigger);
//        } catch (SchedulerException  ex) {
//            Logger.getLogger(SchedulerFactory.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void creaAdminComscore(KalturaCuenta cuenta, int offset, int offset2, boolean clon, Calendar calendar) {
//        try {
//            String cron;
//            if(clon){
//                cron=FechaParser.dateFilter(calendar.getTime());
//            }else{
//                cron=cuenta.getCron();
//            }
//            System.out.println("creando admin job de comscore " );
//            JobKey jobKey = new JobKey(cuenta.getNombre(), "cuenta");
//            JobDetail job = JobBuilder.newJob(ComscoreJob.class)
//                    .withIdentity(jobKey).build();
//            job.getJobDataMap().put("offset", offset);
//            job.getJobDataMap().put("offset2", offset2);
//            job.getJobDataMap().put("clon", clon);
//            job.getJobDataMap().put("cuenta", cuenta);
//            Trigger trigger = TriggerBuilder
//                    .newTrigger()
//                    .withIdentity("Trigger_"+cuenta.getNombre(), "cuenta")
//                    .withSchedule(
//                    CronScheduleBuilder.cronSchedule(cron))
//                    .build();
//            System.out.println(cuenta.getCron());
//            this.getScheduler().scheduleJob(job, trigger);
//            
//        } catch (Exception ex) {
//            Logger.getLogger(SchedulerFactory.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void creaWeekConsumeJob(EntryJobModel entryJobModel, int valor) {
        try {
            System.out.println("creando weekConsume job del partner " + entryJobModel.getCuenta() + " " + entryJobModel.getFabrica());
            JobKey jobKey = new JobKey(entryJobModel.getNombre(), "cuenta");
            JobDetail job = JobBuilder.newJob(WeekConsumeByPartnerJob.class)
                    .withIdentity(jobKey).build();
            job.getJobDataMap().put("entryJobModel", entryJobModel);
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(entryJobModel.getNombre() + "_Trigger_entrys_consumo", "adminentry")
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule(FechaParser.modificaCron(entryJobModel.getCron(), valor)))
                    .build();
            System.out.println(FechaParser.modificaCron(entryJobModel.getCron(), valor));
            this.getScheduler().scheduleJob(job, trigger);
        } catch (SchedulerException ex) {
            Logger.getLogger(SchedulerFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void creaAdminEntrysJob(EntryJobModel entryJobModel, int valor) {
        try {
            System.out.println("creando adminEntry job " + entryJobModel.getFabrica());
            JobKey jobKey = new JobKey(entryJobModel.getNombre(), "cuenta");
            JobDetail job = JobBuilder.newJob(AdminEntrysJob.class)
                    .withIdentity(jobKey).build();
            job.getJobDataMap().put("entryJobModel", entryJobModel);
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(entryJobModel.getNombre() + "_Trigger_entrys", "adminentry")
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule(FechaParser.modificaCron(entryJobModel.getCron(), valor)))
                    .build();
            this.getScheduler().scheduleJob(job, trigger);
            System.out.println(FechaParser.modificaCron(entryJobModel.getCron(), valor));
        } catch (SchedulerException ex) {
            Logger.getLogger(SchedulerFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startScheduler() {
        try {
            this.getScheduler().start();
        } catch (SchedulerException ex) {
            Logger.getLogger(SchedulerFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void scheduleInit() {
        try {
            this.setScheduler(new StdSchedulerFactory().getScheduler());
        } catch (SchedulerException ex) {
            Logger.getLogger(SchedulerFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the scheduler
     */
    public Scheduler getScheduler() {
        return scheduler;
    }

    /**
     * @param scheduler the scheduler to set
     */
    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
