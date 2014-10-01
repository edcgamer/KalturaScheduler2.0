/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azteca.utils;

import java.util.Date;

/**
 *
 * @author Santa
 */
public class TimeParser {
    
    public static String tiempoTranscurrido(Date fechaInicio,Date fechaFin){
        long diff = fechaFin.getTime() - fechaInicio.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays + " dias "+ +diffHours + " horas " + diffMinutes + " minutos " +diffSeconds+" segundos ";
    }
    
}
