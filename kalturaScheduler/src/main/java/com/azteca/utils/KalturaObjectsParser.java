/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azteca.utils;

import com.azteca.model.KalturaMediaEntryModel;
import com.azteca.persistence.entities.KalturaEntry;
import com.azteca.persistence.entities.KalturaMaterial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Santa
 */
public class KalturaObjectsParser {

    
    //    parsea cadena de obtencion de mintos vistos por entry
    
    public static String parseCadena(String cadena) {
        String[] textElements = new String[2];
        if (cadena.equals(",,,,,")) {
            textElements[1] = "0";
        } else {
            String totalText = cadena;
            textElements = totalText.split(",");
        }
        return textElements[1];
    }
    
    public static List<KalturaMediaEntryModel> parseGetTable(String data) {
        data=data.replaceAll(";", ",");
        List<String> items = Arrays.asList(data.split(","));
        List<KalturaMediaEntryModel> datos = new ArrayList<>();
        if (items.size() > 0) {
            for (int i = 0; i < items.size(); i+=8) {
                Long valor;
                if(items.get(i + 3).equals(""))
                    valor=0L;
                else{
                    valor = (long)Double.parseDouble(items.get(i + 3));
                }
                    
                datos.add(new KalturaMediaEntryModel(items.get(i), valor));
            }
        }
        return datos;
    }
    
    
    public static String generaStringData(List<KalturaEntry> entrys){
        StringBuilder sb = new StringBuilder();
        System.out.println(entrys.size());
        for (KalturaEntry km : entrys) {
            sb.append(",").append(km.getEntryId());
        }
        return sb.toString().substring(1, sb.toString().length()-1);
    }
    
    public static String generaStringDataMaterials(List<KalturaEntry> entrys){
        StringBuilder sb = new StringBuilder();
        System.out.println(entrys.size());
        for (KalturaEntry km : entrys) {
            sb.append(",").append(km.getEntryId());
        }
        return sb.toString().substring(1, sb.toString().length());
    }

}
