/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azteca.job;


import com.azteca.model.EntryJobModel;
import com.azteca.persistence.entities.ComscoreSitio;
import com.azteca.persistence.entities.KalturaCuenta;
import com.azteca.persistence.entities.SistemaFecha;
import com.azteca.quartz.SchedulerFactory;
import com.azteca.service.ComscoreConsultaService;
import com.azteca.service.ConsultasBDService;
import com.azteca.utils.MailService;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.quartz.JobExecutionContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *
 * @author Santa
 */
public class ComscoreJob extends QuartzJobBean {

    private ApplicationContext applicationContext;
    private EntryJobModel entryJobModel;

    @Override
    protected void executeInternal(JobExecutionContext arg0) {

        try {
            this.applicationContext = new ClassPathXmlApplicationContext("Spring-Quartz.xml");
            ConsultasBDService rep = this.applicationContext.getBean("scheduler", ConsultasBDService.class);
            System.out.println("haciendo el job de comscore");
            this.entryJobModel = (EntryJobModel) arg0.getJobDetail().getJobDataMap().get("comscoreJobModel");
            if (entryJobModel.isClon()) {
                arg0.getScheduler().deleteJob(arg0.getJobDetail().getKey());
                System.out.println("se borro el hilo temporal");
            }
            for (SistemaFecha sf : entryJobModel.getFechas()) {
                for (ComscoreSitio comscoreSitio : rep.findAllComscoreSitio()) {
                    ComscoreConsultaService.consultaURL(ComscoreConsultaService.generaURL(comscoreSitio, sf.getFechaInicio() ,sf.getFechaFinal()), comscoreSitio,rep);
                }
            }
//                List<ComscoreConsumoSemanal> listacwc=ComscoreConsultaService.consultaURL(ComscoreConsultaService.generaURL(comscore, new Date(new Date().getTime() - (86400000 * 8)), new Date()), comscore);
//                for (ComscoreConsumoSemanal cwc : listacwc.subList(offset2, listacwc.size())) {
//                    rep.saveComscoreWeekConsumeFabrica(cwc);
//                    offset2++;
//                }
//                offset++;
//            }
//            MailService mailService = new MailService(this.applicationContext);
//            mailService.mandaCorreo("Se inserto todo correctamente en comscore", "Se inserto todo correctamente en comscore");
//        
        } catch (Exception ex) {
            System.out.println(ex);
            Date date = new Date();
//            System.out.println("cuenta " + arg0.getJobDetail().getKey().getName() + " offset " + this.offset + " offset2 " + this.offset2);
//            System.out.println("El proceso se murio y se clono");
//            SchedulerFactory schedulerFactory = new SchedulerFactory();
//            schedulerFactory.setScheduler(arg0.getScheduler());
//            Calendar calendar = GregorianCalendar.getInstance();
//            calendar.setTime(date);
//            calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 2);
//            schedulerFactory.creaAdminComscore(this.cuenta, this.offset, this.offset2, true, calendar);
//            MailService mailService = new MailService(this.applicationContext);
//            mailService.mandaCorreo("Se murio el proceso de comscore en la url " + this.offset + " en el elemento " + this.offset2, "Se murio el proceso comscore");
        }
    }
}
