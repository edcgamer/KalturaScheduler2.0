/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azteca.job;

import com.azteca.persistence.entities.KalturaCuenta;
import com.azteca.service.ConsultasBDService;
import com.azteca.utils.CronParser;
import com.azteca.utils.FechaParser;
import com.azteca.utils.MailService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *
 * @author Santa
 */
public class AdminPartnersJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext arg0) {
//        System.out.println(" haciendo job Admin de partners");
//        try {
//            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("Spring-Quartz.xml");
//            ConsultasBDService rep = applicationContext.getBean("scheduler", ConsultasBDService.class);
//            for (JobKey jobKey : arg0.getScheduler().getJobKeys(GroupMatcher.jobGroupEquals("cuenta"))) {
//                List<KalturaCuenta> cuentas = rep.findCuentasByNombre(jobKey.getName());
//                for (Trigger triggerKey : arg0.getScheduler().getTriggersOfJob(jobKey)) {
//                    for (KalturaCuenta cts : cuentas) {
//                        if (!cts.getCron().equals(FechaParser.dateFilter(triggerKey.getNextFireTime()))) {
//                            if (CronParser.validaCron(cts.getCron())) {
//                                String cronTemp = CronParser.eliminaEspacios(cts.getCron());
//                                if (!cronTemp.equals(cts.getCron())) {
//                                    System.out.println("son diferentes y se guardara el cambio");
//                                    cts.setCron(cronTemp);
//                                    rep.saveCuentas(cts);
//                                }
//                                System.out.println(FechaParser.dateFilter(triggerKey.getNextFireTime()));
//                                System.out.println("el job " + jobKey.getName() + " se va a reprogramar al nuevo cron " + cts.getCron());
//                                Trigger trigger = TriggerBuilder
//                                        .newTrigger()
//                                        .withIdentity(triggerKey.getKey().getName(), triggerKey.getKey().getGroup())
//                                        .withSchedule(
//                                        CronScheduleBuilder.cronSchedule(cts.getCron()))
//                                        .build();
//                                arg0.getScheduler().rescheduleJob(triggerKey.getKey(), trigger);
//                                MailService mailService = new MailService(applicationContext);
//                                mailService.mandaCorreo("se realizo  un cambio  en el cron de " + cts.getNombre() + " correctamente :" + cts.getCron(), "Alerta admin partners");
//                            } else {
//                                System.out.println("No se guardo El cron de la base de datos , por lo que se regresara a su valor normal");
//                                cts.setCron(FechaParser.dateFilter(triggerKey.getNextFireTime()));
//                                rep.saveCuentas(cts);
//                                MailService mailService = new MailService(applicationContext);
//                                mailService.mandaCorreo("La modificacion de la base de datos fue incorrecta el cron no esta formado correctamente y se regreso a su valor normal", "Alerta admin partners mal formacion cron");
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (BeansException | SchedulerException | MailException ex) {
//            Logger.getLogger(AdminPartners.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
