/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azteca.job;

import com.azteca.model.CategoryJobModel;
import com.azteca.persistence.entities.KalturaCuenta;
import com.azteca.persistence.entities.KalturaFabrica;
import com.azteca.persistence.entities.KalturaPrograma;
import com.azteca.persistence.entities.KalturaUnidad;
import com.azteca.service.ConsultasBDService;
import com.azteca.service.ConsultasKalturaService;
import com.azteca.utils.TimeParser;
import com.kaltura.client.enums.KalturaMediaType;
import com.kaltura.client.types.KalturaCategory;
import java.util.Date;
import org.quartz.JobExecutionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *
 * @author Santa
 */
public class AdminCategoryJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext arg0) {
        try {
            Date fechaIni = new Date();
            System.out.println("Se inicia el Admin Category Job");
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("Spring-Quartz.xml");
            ConsultasBDService rep = applicationContext.getBean("scheduler", ConsultasBDService.class);
            CategoryJobModel categoryJobModel = (CategoryJobModel) arg0.getJobDetail().getJobDataMap().get("categoryJobModel");
            for (KalturaCuenta kc : categoryJobModel.getCuentas()) {
               System.out.println("cuenta " + kc.getNombre());
//                if (kc.getNombre().equals("Azteca")) {
               if (true) {
                    ConsultasKalturaService cks = new ConsultasKalturaService(kc.getUsuario(), kc.getAdmin(), Integer.parseInt(kc.getPartner()), KalturaMediaType.VIDEO);
                    for (KalturaCategory kctn1 : cks.consultaNivelCategoria(0, null)) {
                        System.out.println("unidad " + kctn1.name);
                        if (rep.findKalturaUnidadByID(kctn1.id).isEmpty()) {
                            rep.saveKalturaUnidad(new KalturaUnidad(kctn1.name, kctn1.id, kc));
                            System.out.println(kctn1.name + " guardada");
                        } else {
                            System.out.println("ya existia la unidad " + kctn1.name);
                        }
//                        KalturaUnidad kalturaUnidad= rep.findKalturaUnidadByNombre(kctn1.name).get(0);
                        for (KalturaCategory kctn2 : cks.consultaNivelCategoria(1, kctn1.id)) {
                            System.out.println("fabrica " + kctn2.name);
                            if (rep.findKalturaFabricaById(kctn2.id).isEmpty()) {
                                rep.saveKalturaFabrica(new KalturaFabrica(kctn2.name, kctn2.id, rep.findKalturaUnidadByNombre(kctn1.name).get(0)));
                                System.out.println(kctn2.name + " guardada");
                            } else {
                                System.out.println("ya existia la fabrica " + kctn2.name);
                            }
//                            KalturaFabrica kalturaFabrica= rep.findKalturaFabricaById(kctn2.id).get(0);
//                            System.out.println(kalturaFabrica.getNombre()+"------");
                            for (KalturaCategory kctn3 : cks.consultaNivelCategoria(2, kctn2.id)) {
                                System.out.println("programa " + kctn3.name);
                                if (rep.findKalturaProgramaByID(kctn3.id).isEmpty()) {
                                    rep.saveKalturaPrograma(new KalturaPrograma(kctn3.name, kctn3.id, rep.findKalturaFabricaByNombre(kctn2.name).get(0)));
                                    System.out.println(kctn3.name + " guardada");
                                } else {
                                    System.out.println("ya existia el programa " + kctn3.name);
                                }
                            }
                        }
                    }
                    System.out.println(TimeParser.tiempoTranscurrido(fechaIni, new Date()));
                }
            }
            System.out.println("Se termino correctamente la cargada de categorias");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
