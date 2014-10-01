/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azteca.utils;

import java.util.Arrays;

/**
 *
 * @author Santa
 */
public class CronParser {
    public static boolean validaCron(String cron) {
        String cadTemp = cron.replaceAll("\\s++", " ");
        if (!cron.equals(cadTemp)) {
            cron = cadTemp;
        }
        String[] cadenas = cron.split(" ");
        if (Arrays.asList(cadenas).size() != 6) {
            return false;
        }
        return true;
    }
    
    public static String eliminaEspacios(String cron){
        return cron.replaceAll("\\s++", " ");
    }
}
