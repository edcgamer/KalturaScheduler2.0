package com.azteca.utils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Marco
 */
public class Methods {
    public String cadena (String cadena) {
         String[] textElements=new String[2];
        if (cadena.equals(",,,,,")){
        
            textElements[1]="0";
        
        }else{
        
        String totalText = cadena;
         textElements=totalText.split(",");
        //System.out.println(textElements[1]);
        }
        return textElements[1];
    }
}
