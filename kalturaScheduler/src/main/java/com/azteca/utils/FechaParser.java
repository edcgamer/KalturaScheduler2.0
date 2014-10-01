/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azteca.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Santa
 */
public class FechaParser {
    
    public static String parseDiaSemana(Calendar calendar){
//        if(valor){
            calendar.set(Calendar.DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK)-1);
//        }
        if(calendar.get(Calendar.DAY_OF_WEEK)==0)
            return "SUN";
        else if(calendar.get(Calendar.DAY_OF_WEEK)==1)
            return "MON";
        else if(calendar.get(Calendar.DAY_OF_WEEK)==2)
            return "TUE";
        else if(calendar.get(Calendar.DAY_OF_WEEK)==3)
            return "WED";
        else if(calendar.get(Calendar.DAY_OF_WEEK)==4)
            return "THU";
        else if(calendar.get(Calendar.DAY_OF_WEEK)==5)
            return "FRI";
        else if(calendar.get(Calendar.DAY_OF_WEEK)==6)
            return "MON";
        else if(calendar.get(Calendar.DAY_OF_WEEK)==7)
            return "SAT";
        else
            return "";
        
    }
    
    public static String dateToReportFilter(Date fecha){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.YEAR)+""+valoresMenoresDiez(calendar.get(Calendar.MONTH)+1)+""+valoresMenoresDiez(calendar.get(Calendar.DAY_OF_MONTH))+"";
        
    }
    
    public static String dateFilter(Date fecha){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.SECOND)+" "+calendar.get(Calendar.MINUTE)+" "+calendar.get(Calendar.HOUR_OF_DAY)+" ? * "+parseDiaSemana(calendar);
    }
    
    public static String modificaCron(String cron,int valor){
        String []cad2=cron.split(" ");
        int numero=(Integer.parseInt(cad2[1])+valor);
        int numero2=(Integer.parseInt(cad2[2]));
        int j=0;
        while(true) {
            if(numero>=((j+1)*60)){
            j++;
            }else{
                break;
            }
        }
        
        numero=numero-(j*60);
        numero2=numero2+j;
        cad2[1]= numero+"";
        
        if(numero2>=24){
            
            numero2=numero2-24;
            if(cad2[5].equals("SUN")){
                cad2[5]="MON";
            }
            else if(cad2[5].equals("MON")){
                cad2[5]="TUE";
            }
            else if(cad2[5].equals("TUE")){
                cad2[5]="WED";
            }
            else if(cad2[5].equals("WED")){
                cad2[5]="THU";
            }
            else if(cad2[5].equals("THU")){
                cad2[5]="FRI";
            }
            else if(cad2[5].equals("FRI")){
                cad2[5]="SAT";
            }
            else if(cad2[5].equals("SAT")){
                cad2[5]="SUN";
            }
            
        }
        cad2[2]= numero2+"";
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<cad2.length;i++){
            sb.append(cad2[i]).append(" ");
        }
        return sb.toString();
    }
    
    private static String valoresMenoresDiez(int valor){
        if(valor<10){
            return "0"+String.valueOf(valor);
        }else{
            return String.valueOf(valor);
        }
    }
    
    
}
