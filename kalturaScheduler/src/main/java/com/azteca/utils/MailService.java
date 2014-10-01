/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azteca.utils;

import com.azteca.quartz.SchedulerFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 *
 * @author Santa
 */
public class MailService {
    SimpleMailMessage simpleMailMessage;
    JavaMailSender mailSender;

    public MailService(ApplicationContext applicationContext) {
        this.simpleMailMessage = applicationContext.getBean("simpleMailMessage", SimpleMailMessage.class);
        this.mailSender = applicationContext.getBean("mailSender", JavaMailSender.class);
    }
    
    public  void mandaCorreo(String msg, String header) throws MailException{
        this.simpleMailMessage.setFrom("eramoss@tvazteca.com.mx");
        this.simpleMailMessage.setTo("edcgamer@hotmail.com");
        this.simpleMailMessage.setSubject(header);
        this.simpleMailMessage.setText(msg);
        this.mailSender.send(simpleMailMessage);
    }
    
}
