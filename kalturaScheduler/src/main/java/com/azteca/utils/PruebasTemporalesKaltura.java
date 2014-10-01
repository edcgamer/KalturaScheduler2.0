/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azteca.utils;

import com.azteca.service.ConsultasKalturaService;
import com.azteca.service.ConsultasBDService;
import com.azteca.model.KalturaMediaEntryModel;
import com.azteca.persistence.entities.KalturaCuenta;
import com.azteca.persistence.entities.KalturaMaterial;
import com.azteca.persistence.entities.Reporte;
import com.kaltura.client.KalturaApiException;
import com.kaltura.client.enums.KalturaMediaType;
import java.util.Date;
import java.util.List;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Santa
 */
public class PruebasTemporalesKaltura {

    private int i;
    private ConsultasBDService rep;
    private KalturaCuenta kc;
    private ConsultasKalturaService ckes;
    private ApplicationContext applicationContext;
    private int consultas;

    public void testGetTable(int offset, ConsultasBDService rep, ApplicationContext applicationContext) {
//        consultas = offset;
//        if (this.rep == null) {
//            this.rep = rep;
//        }
//        if (this.applicationContext == null) {
//            this.applicationContext = applicationContext;
//        }
//        try {
//            this.ckes = new ConsultasKalturaService();
//            for (KalturaCuenta kcss : this.rep.findAllCuentas()) {
//                if (!kcss.getNombre().equals("noticias")) {
//                    this.kc = kcss;
//                    this.i = offset;
//                    if (this.kc.getPartner() != 0) {
//                        subtestGetTable(this.ckes, this.kc, offset, rep, applicationContext);
//                    }
//                }
//            }
//        } catch (Exception ex) {
//
//            try {
//                MailService mailService = new MailService(this.applicationContext);
//                System.out.println(ex);
//                mailService.mandaCorreo(ex.toString(), "algo paso loco");
//            } catch (Exception e) {
//                System.out.println(ex);
//            }
//        }
    }

    private void subtestGetTable(ConsultasKalturaService ckes, KalturaCuenta kc, int offset, ConsultasBDService rep, ApplicationContext applicationContext) throws KalturaApiException {
//        try {
//            this.i = offset;
//            if (this.applicationContext == null) {
//                this.applicationContext = applicationContext;
//            }
//            if (this.rep == null) {
//                this.rep = rep;
//            }
//            System.out.println(kc.getNombre());
//            ckes = new ConsultasKalturaService(kc.getUser(), kc.getAdmin(), kc.getPartner(), KalturaMediaType.VIDEO);
//            Date fecha = new Date();
//            int valor = 0;
//            for (; this.i < (kc.getKalturaMaterials().size()) + 1; this.i += 500) {
//                if (this.i + 500 > kc.getKalturaMaterials().size()) {
//                    valor = kc.getKalturaMaterials().size();
//                    System.out.println("entre");
//                } else {
//                    valor = this.i + 500;
//                }
//                for (KalturaMediaEntryModel kmem : ckes.consultaMinutosVistosWPager(KalturaObjectsParser.generaStringData(kc.getKalturaMaterials().subList(this.i, valor)))) {
//                    List<KalturaMaterial> km = rep.findTotalEntrysByEntryId(kmem.getEntry());
//                    if (km.size() > 0 && rep.findReportByEntry(kmem.getEntry()).isEmpty()) {
//                        rep.saveReporte(new Reporte(kmem.getEntry(), kmem.getTiempoVisto() + "", km.get(0).getTamanio(), kc.getPartner()));
//                        System.out.println(kmem.getEntry() + " " + kmem.getTiempoVisto() + " " + km.get(0).getTamanio() + " " + kc.getPartner());
//                    } else {
//                        System.out.println("no existe referencia de " + kmem.getEntry());
//                    }
//                }
//                this.consultas += 500;
//                System.out.println("consultas --------------" + consultas);
//            }
//            System.out.println(TimeParser.tiempoTranscurrido(fecha, new Date()) + "de la cuenta " + kc.getNombre());
//        } catch (Exception ex) {
//            try {
//                System.out.println(ex);
////                MailService mailService = new MailService(this.applicationContext);
////                mailService.mandaCorreo(ex.toString(), "algo paso loco");
//                PruebasTemporalesKaltura ptk = new PruebasTemporalesKaltura();
//
////                subtestGetTable(this.ckes, this.kc, this.i, rep, this.applicationContext);
//                testGetTable(this.consultas, this.rep, this.applicationContext);
//            } catch (Exception exs) {
//                 testGetTable(this.consultas, this.rep, this.applicationContext);
//            }
//        }
    }

}
