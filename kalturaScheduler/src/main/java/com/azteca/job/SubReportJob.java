/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azteca.job;

import com.azteca.model.EntryJobModel;
import com.azteca.persistence.entities.KalturaCuenta;
import com.azteca.persistence.entities.KalturaEntry;
import com.azteca.persistence.entities.KalturaFabrica;
import com.azteca.persistence.entities.KalturaPrograma;
import com.azteca.persistence.entities.KalturaUnidad;
import com.azteca.persistence.entities.SistemaReporte;
import com.azteca.persistence.entities.SistemaSubReporte;
import com.azteca.service.ConsultasBDService;
import com.azteca.utils.TimeParser;
import java.util.Date;
import java.util.List;
import org.quartz.JobExecutionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *
 * @author Santa
 */
public class SubReportJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext arg0) {
        try {
            int novistos = (int) arg0.getJobDetail().getJobDataMap().get("noVistos");
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("Spring-Quartz.xml");
            ConsultasBDService rep = applicationContext.getBean("scheduler", ConsultasBDService.class);
            System.out.println("Generando Subreporte Programa "+novistos);
            Date fechaInicial = new Date();
            double tamanioPro = 0.0;
            double tamanioFab = 0.0;
            double tamanioUni = 0.0;
            double tamanioCue = 0.0;
            Long duracionPro = 0L;
            Long duracionFab = 0L;
            Long duracionUni = 0L;
            Long duracionCue = 0L;
            Long tiempoVistoPro = 0L;
            Long tiempoVistoFab = 0L;
            Long tiempoVistoUni = 0L;
            Long tiempoVistoCue = 0L;
            Long totalEntrysPro=0L;
            Long totalEntrysFab=0L;
            Long totalEntrysUni=0L;
            Long totalEntrysCue=0L;
            SistemaReporte sisRep;
            for (KalturaCuenta kalCue : rep.findAllCuentas()) {
                for (KalturaUnidad kalUni : kalCue.getListaUnidad()) {
                    for (KalturaFabrica kalFab : kalUni.getListaFabricas()) {
                        for (KalturaPrograma KalPro : kalFab.getListaProgramas()) {
                            for (SistemaReporte kalEnt : rep.findByFechaCorteAndParameters(new Date(), kalUni.getNombre(), kalFab.getNombre(), KalPro.getNombre())) {
//                                List<SistemaReporte> sisRepListTmp = rep.findReportByEntry(kalEnt.getEntryId());
//                                    sisRep = sisRepListTmp.get(0);
                                if ((novistos == 1 && kalEnt.getTiempoVisto() == 0)||novistos == 0) {
                                    tamanioPro += (((kalEnt.getTamanio() * 1.0) / 1024) / 1024);
                                    duracionPro += (long) kalEnt.getDuracion();
                                    tiempoVistoPro += (long) kalEnt.getTiempoVisto();
                                    totalEntrysPro++;
                                    System.out.println("entre");
                                }
                                else{
                                    System.out.println("no entre");
                                }
                            }
                            tamanioFab += tamanioPro;
                            duracionFab += duracionPro;
                            tiempoVistoFab += tiempoVistoPro;
                            rep.saveSistemaSubReporte(new SistemaSubReporte(KalPro.getNombre(),
                                    tamanioPro, tiempoVistoPro, duracionPro, "programa", new Date(), kalCue.getNombre(),totalEntrysPro,kalUni.getNombre(),kalFab.getNombre(),novistos));
                            System.out.println("Guarde programa tam:"+tamanioPro);
                            totalEntrysFab+= totalEntrysPro;
                            totalEntrysPro=0L;
                            tamanioPro = 0;
                            duracionPro = 0L;
                            tiempoVistoPro = 0L;
                        }
                        tamanioUni += tamanioFab;
                        duracionUni += duracionFab;
                        tiempoVistoUni += tiempoVistoFab;
                        rep.saveSistemaSubReporte(new SistemaSubReporte(kalFab.getNombre(),
                                tamanioFab, tiempoVistoFab, duracionFab, "fabrica", new Date(), kalCue.getNombre(),totalEntrysFab,kalUni.getNombre(),novistos));
                        System.out.println("Guarde Fabrica tam:"+tamanioFab);
                        totalEntrysUni += totalEntrysFab;
                        totalEntrysFab = 0L;
                        tamanioFab = 0;
                        duracionFab = 0L;
                        tiempoVistoFab = 0L;
                    }
                    tamanioCue += tamanioUni;
                    duracionCue += duracionUni;
                    tiempoVistoCue += tiempoVistoUni;
                    rep.saveSistemaSubReporte(new SistemaSubReporte(kalUni.getNombre(),
                            tamanioUni, tiempoVistoUni, duracionUni, "unidad", new Date(), kalCue.getNombre(),totalEntrysUni,novistos));
                    System.out.println("Guarde Unidad tam:"+tamanioUni);
                    totalEntrysCue += totalEntrysUni;
                    totalEntrysUni = 0L;
                    tamanioUni = 0;
                    duracionUni = 0L;
                    tiempoVistoUni = 0L;
                }
                rep.saveSistemaSubReporte(new SistemaSubReporte(kalCue.getNombre(),
                        tamanioCue, tiempoVistoCue, duracionCue, "cuenta", new Date(), kalCue.getNombre(),totalEntrysCue,novistos));
                System.out.println("Guarde cuenta tam:"+tamanioCue);
                totalEntrysCue=0L;
                tamanioCue=0;
                duracionCue=0L;
                tiempoVistoCue=0L;
            }
            System.out.println("Termine en "+TimeParser.tiempoTranscurrido(fechaInicial, new Date()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
