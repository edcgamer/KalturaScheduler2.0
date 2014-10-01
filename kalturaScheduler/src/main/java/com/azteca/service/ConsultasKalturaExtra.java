/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azteca.service;

import com.azteca.model.KalturaMediaEntryModel;
import com.azteca.persistence.entities.KalturaCuenta;
import com.azteca.persistence.entities.KalturaMaterial;
import com.azteca.utils.MailService;
import com.kaltura.client.KalturaApiException;
import com.kaltura.client.enums.KalturaMediaType;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailException;

/**
 *
 * @author Santa
 */
public class ConsultasKalturaExtra {

    private int contador = 0;
    private ConsultasBDService rep;
    private ApplicationContext applicationContext;

//    reconstruye el material que existe en la tabla kalturamaterialconsultaEntrySemana
    public void reconstruyeKalturaMaterial(ConsultasBDService rep, int offset, ApplicationContext applicationContext) {
//        int cont = 0;
//        try {
//            this.applicationContext = applicationContext;
//            System.out.println("empezare hacer todo " + offset);
//            if (this.rep == null) {
//                this.rep = rep;
//            }
//            this.contador = offset;
//            KalturaCuenta kc = rep.findAllCuentas().get(0);
//            ConsultasKalturaService cs = new ConsultasKalturaService(kc.getUser(), kc.getAdmin(), kc.getPartner(), KalturaMediaType.VIDEO);
//            for (KalturaMaterial km : kc.getKalturaMaterials().subList(offset, kc.getKalturaMaterials().size())) {
//                if (km.getDuration() == 5) {
//                    System.out.println("entre " + km.getEntryId());
//                    for (KalturaMediaEntryModel kmem : cs.consultaEntryReconstruccionTablaKalturaMaterial(10, 0, km.getEntryId())) {
//                        System.out.println("duracion nueva " + kmem.getDuration());
//
//                        km.setFechaCreacion(kmem.getFechaCreacion());
//                        km.setDuration(kmem.getDuration());
//                        rep.saveTotalEntrys(km);
//                        cont++;
//                        if (cont % 1000 == 0) {
//                            MailService ms = new MailService(this.applicationContext);
//                            ms.mandaCorreo("Se hicieron 1000 entrys", "1000 entrys");
//                        }
//                    }
//                    this.contador++;
//                    System.out.println(contador);
//                }
//            }
//        } catch (Exception ex) {
//            MailService ms = new MailService(this.applicationContext);
//            System.out.println(ex);
//            ms.mandaCorreo("Se murio el proceso de reindexado", ex.toString());
//            System.out.println("ehh muerto y me quede en el indice " + this.contador);
//            if (ex.toString().contains("not found")) {
//                this.contador++;
//            }
//            this.reconstruyeKalturaMaterial(rep, this.contador, this.applicationContext);
//        }

    }

    public void consultaEntrysSemana(String admin, String user, int partner) {
        try {
            ConsultasKalturaService ckss = new ConsultasKalturaService(user, admin, partner, KalturaMediaType.VIDEO);

            for (int u = 0; u <= 15; u++) {
                System.out.println(u);
//                ckss.consultaEntrySemana(500, u);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        MailService ms = new MailService(applicationContext);
        ms.mandaCorreo("hola ed", "test");
    }
    
    public void obtenerentrysConCategoria(ConsultasBDService rep, int offset, ApplicationContext applicationContext){
//        try {
//            this.applicationContext = applicationContext;
//            System.out.println("empezare hacer todo " + offset);
//            if (this.rep == null) {
//                this.rep = rep;
//            }
//            this.contador = offset;
//            KalturaCuenta kc = rep.findAllCuentas().get(0);
//
//            ConsultasKalturaService cs = new ConsultasKalturaService(kc.getUser(), kc.getAdmin(), kc.getPartner(), KalturaMediaType.VIDEO);
//            System.out.println(cs.obtnerTotalEntrysPorCategoria("5980342,20794872,20794962,20794982,20794992,20795012,20795042,20795062,20795092,20795122,20795142,20795152,20795212,20795232,20795242,20795252,20795262,20795302,20795312,20795322,20795332,20795342,20795352,20795362,20795372,20795382,20795432,20795442,20795522,20795542,20795582,20795592,20795652,20795662,20795682,20795692,20795712,20795752,20795782,20795792,20795812,20795852,20795892,20795912,20795962,20795992,20796032,20796042,20794882,20795022,20795052,20795072,20795102,20795112,20795132,20795172,20795202,20795292,20795392,20795412,20795452,20795502,20795622,20795772,20795802,20795842,20795862,20795872,20795932,20795942,20795952,20796062,20794892,20795222,20795882,20794902,20795002,20795082,20795192,20795272,20795282,20795402,20795492,20795532,20795612,20795732,20795832,20796002,20796012,20794912,20795162,20795422,20795462,20795472,20795602,20795742,20795822,20795972,20795982,20796022,20794922,20795632,20795642,20794932,20795552,20794942,20795512,20795562,20795572,20795702,20795762,20794952,20794972,20795032,20795182,20795482,20795672,20795722,20795902,20795922,20796052,20926442,21461012,21477382,21472042", "", 1395273600,1400544000 )/500);
//            for(int u=this.contador;u<=(cs.obtnerTotalEntrysPorCategoria("5980342,20794872,20794962,20794982,20794992,20795012,20795042,20795062,20795092,20795122,20795142,20795152,20795212,20795232,20795242,20795252,20795262,20795302,20795312,20795322,20795332,20795342,20795352,20795362,20795372,20795382,20795432,20795442,20795522,20795542,20795582,20795592,20795652,20795662,20795682,20795692,20795712,20795752,20795782,20795792,20795812,20795852,20795892,20795912,20795962,20795992,20796032,20796042,20794882,20795022,20795052,20795072,20795102,20795112,20795132,20795172,20795202,20795292,20795392,20795412,20795452,20795502,20795622,20795772,20795802,20795842,20795862,20795872,20795932,20795942,20795952,20796062,20794892,20795222,20795882,20794902,20795002,20795082,20795192,20795272,20795282,20795402,20795492,20795532,20795612,20795732,20795832,20796002,20796012,20794912,20795162,20795422,20795462,20795472,20795602,20795742,20795822,20795972,20795982,20796022,20794922,20795632,20795642,20794932,20795552,20794942,20795512,20795562,20795572,20795702,20795762,20794952,20794972,20795032,20795182,20795482,20795672,20795722,20795902,20795922,20796052,20926442,21461012,21477382,21472042", "", 1395273600, 1400544000)/500)+8;u++){
//                for(KalturaMediaEntryModel kmem:cs.obtnerEntrysPorCategoria("5980342,20794872,20794962,20794982,20794992,20795012,20795042,20795062,20795092,20795122,20795142,20795152,20795212,20795232,20795242,20795252,20795262,20795302,20795312,20795322,20795332,20795342,20795352,20795362,20795372,20795382,20795432,20795442,20795522,20795542,20795582,20795592,20795652,20795662,20795682,20795692,20795712,20795752,20795782,20795792,20795812,20795852,20795892,20795912,20795962,20795992,20796032,20796042,20794882,20795022,20795052,20795072,20795102,20795112,20795132,20795172,20795202,20795292,20795392,20795412,20795452,20795502,20795622,20795772,20795802,20795842,20795862,20795872,20795932,20795942,20795952,20796062,20794892,20795222,20795882,20794902,20795002,20795082,20795192,20795272,20795282,20795402,20795492,20795532,20795612,20795732,20795832,20796002,20796012,20794912,20795162,20795422,20795462,20795472,20795602,20795742,20795822,20795972,20795982,20796022,20794922,20795632,20795642,20794932,20795552,20794942,20795512,20795562,20795572,20795702,20795762,20794952,20794972,20795032,20795182,20795482,20795672,20795722,20795902,20795922,20796052,20926442,21461012,21477382,21472042", "", 1395273600,1400544000 , u, 500)){
////                    if(rep.findCatMaterialSinFabricaByName(kmem.getName()).isEmpty()){
//                    
//                       rep.saveCatMaterialConFabrica(new CatMaterialConFabrica(kmem.getEntry(),kmem.getName(),kmem.getFechaCreacion().toString(),new Date().toString(),"11",kmem.getCategory()));
////                    }
//                    System.out.println(kmem.getEntry()+" "+kmem.getName()+" "+kmem.getCategory()+" "+kmem.getCategoryName()+" "+kmem.getFechaCreacion()+" "+kmem.getTiempoVisto());
//                }
//                this.contador++;
//            }
//        } catch (Exception ex) {
//            System.out.println(ex);
//            System.out.println("ehh muerto y me quede en el indice " + this.contador);
//            if (ex.toString().contains("not found")) {
//                this.contador++;
//            }
//            this.obtenerEntrysSinCategoria(rep, this.contador, this.applicationContext);
//        }
    }
    

    public void obtenerEntrysSinCategoria(ConsultasBDService rep, int offset, ApplicationContext applicationContext) {
//        try {
//            this.applicationContext = applicationContext;
//            System.out.println("empezare hacer todo " + offset);
//            if (this.rep == null) {
//                this.rep = rep;
//            }
//            this.contador = offset;
//            KalturaCuenta kc = rep.findAllCuentas().get(0);
//
//            ConsultasKalturaService cs = new ConsultasKalturaService(kc.getUser(), kc.getAdmin(), kc.getPartner(), KalturaMediaType.VIDEO);
//            System.out.println(cs.obtnerTotalEntrysPorCategoria("", "5980342,20794872,20794962,20794982,20794992,20795012,20795042,20795062,20795092,20795122,20795142,20795152,20795212,20795232,20795242,20795252,20795262,20795302,20795312,20795322,20795332,20795342,20795352,20795362,20795372,20795382,20795432,20795442,20795522,20795542,20795582,20795592,20795652,20795662,20795682,20795692,20795712,20795752,20795782,20795792,20795812,20795852,20795892,20795912,20795962,20795992,20796032,20796042,20794882,20795022,20795052,20795072,20795102,20795112,20795132,20795172,20795202,20795292,20795392,20795412,20795452,20795502,20795622,20795772,20795802,20795842,20795862,20795872,20795932,20795942,20795952,20796062,20794892,20795222,20795882,20794902,20795002,20795082,20795192,20795272,20795282,20795402,20795492,20795532,20795612,20795732,20795832,20796002,20796012,20794912,20795162,20795422,20795462,20795472,20795602,20795742,20795822,20795972,20795982,20796022,20794922,20795632,20795642,20794932,20795552,20794942,20795512,20795562,20795572,20795702,20795762,20794952,20794972,20795032,20795182,20795482,20795672,20795722,20795902,20795922,20796052,20926442,21461012,21477382,21472042", 1400544000,1405728000 )/500);
//            for(int u=this.contador;u<=(cs.obtnerTotalEntrysPorCategoria("", "5980342,20794872,20794962,20794982,20794992,20795012,20795042,20795062,20795092,20795122,20795142,20795152,20795212,20795232,20795242,20795252,20795262,20795302,20795312,20795322,20795332,20795342,20795352,20795362,20795372,20795382,20795432,20795442,20795522,20795542,20795582,20795592,20795652,20795662,20795682,20795692,20795712,20795752,20795782,20795792,20795812,20795852,20795892,20795912,20795962,20795992,20796032,20796042,20794882,20795022,20795052,20795072,20795102,20795112,20795132,20795172,20795202,20795292,20795392,20795412,20795452,20795502,20795622,20795772,20795802,20795842,20795862,20795872,20795932,20795942,20795952,20796062,20794892,20795222,20795882,20794902,20795002,20795082,20795192,20795272,20795282,20795402,20795492,20795532,20795612,20795732,20795832,20796002,20796012,20794912,20795162,20795422,20795462,20795472,20795602,20795742,20795822,20795972,20795982,20796022,20794922,20795632,20795642,20794932,20795552,20794942,20795512,20795562,20795572,20795702,20795762,20794952,20794972,20795032,20795182,20795482,20795672,20795722,20795902,20795922,20796052,20926442,21461012,21477382,21472042", 1400544000, 1405728000)/500)+8;u++){
//                for(KalturaMediaEntryModel kmem:cs.obtnerEntrysPorCategoria("", "5980342,20794872,20794962,20794982,20794992,20795012,20795042,20795062,20795092,20795122,20795142,20795152,20795212,20795232,20795242,20795252,20795262,20795302,20795312,20795322,20795332,20795342,20795352,20795362,20795372,20795382,20795432,20795442,20795522,20795542,20795582,20795592,20795652,20795662,20795682,20795692,20795712,20795752,20795782,20795792,20795812,20795852,20795892,20795912,20795962,20795992,20796032,20796042,20794882,20795022,20795052,20795072,20795102,20795112,20795132,20795172,20795202,20795292,20795392,20795412,20795452,20795502,20795622,20795772,20795802,20795842,20795862,20795872,20795932,20795942,20795952,20796062,20794892,20795222,20795882,20794902,20795002,20795082,20795192,20795272,20795282,20795402,20795492,20795532,20795612,20795732,20795832,20796002,20796012,20794912,20795162,20795422,20795462,20795472,20795602,20795742,20795822,20795972,20795982,20796022,20794922,20795632,20795642,20794932,20795552,20794942,20795512,20795562,20795572,20795702,20795762,20794952,20794972,20795032,20795182,20795482,20795672,20795722,20795902,20795922,20796052,20926442,21461012,21477382,21472042", 1400544000,1405728000 , u, 500)){
////                    if(rep.findCatMaterialSinFabricaByName(kmem.getName()).isEmpty()){
//                       rep.saveCatMaterialSinFabrica(new CatMaterialSinFabrica(kmem.getEntry(),kmem.getName(),kmem.getFechaCreacion(),new Date(),"12"));
////                    }
//                    System.out.println(kmem.getEntry()+" "+kmem.getName()+" "+kmem.getCategory()+" "+kmem.getCategoryName()+" "+kmem.getFechaCreacion()+" "+kmem.getTiempoVisto());
//                }
//                this.contador++;
//            }
//        } catch (Exception ex) {
//            System.out.println(ex);
//            System.out.println("ehh muerto y me quede en el indice " + this.contador);
//            if (ex.toString().contains("not found")) {
//                this.contador++;
//            }
//            this.obtenerEntrysSinCategoria(rep, this.contador, this.applicationContext);
//        }
    }
    
}
