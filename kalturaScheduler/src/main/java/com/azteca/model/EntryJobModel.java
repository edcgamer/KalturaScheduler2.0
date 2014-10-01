/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.azteca.model;

import com.azteca.persistence.entities.KalturaCuenta;
import com.azteca.persistence.entities.SistemaConsulta;
import com.azteca.persistence.entities.SistemaFecha;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Santa
 */
public class EntryJobModel {
    private String programa;
    private String fabrica;
    private String unidad;
    private String cuenta;
    private String cron;
    private String nombre;
    private List<SistemaFecha> fechas;
    private List<SistemaConsulta> consultas;
    private List<KalturaCuenta> cuentas;
    private Integer offset;
    private Integer segmento;
    private boolean clon;
    private boolean banderaFecha;
    private boolean banderaPrograma;
    private boolean banderaOffset;
    private SistemaFecha fecha;

    public SistemaFecha getFecha() {
        return fecha;
    }

    public void setFecha(SistemaFecha fecha) {
        this.fecha = fecha;
    }

    public Integer getSegmento() {
        return segmento;
    }

    public void setSegmento(Integer segmento) {
        this.segmento = segmento;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public boolean isClon() {
        return clon;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    
    public void setClon(boolean clon) {
        this.clon = clon;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    

    public EntryJobModel() {
    }

    public EntryJobModel(String fabrica, String unidad, String cuenta, String cron, List<SistemaFecha> fechas, List<SistemaConsulta> consultas, List<KalturaCuenta> cuentas, Integer offset, Integer segmento,boolean clon,SistemaFecha fecha,String nombre) {
        this.fabrica = fabrica;
        this.unidad = unidad;
        this.cuenta = cuenta;
        this.cron = cron;
        this.fechas = fechas;
        this.consultas = consultas;
        this.cuentas = cuentas;
        this.offset = offset;
        this.segmento = segmento;
        this.clon=clon;
        this.fecha=fecha;
        this.banderaFecha=clon;
        this.banderaPrograma=clon;
        this.nombre= nombre;
        this.banderaOffset=clon;
    }

    public boolean isBanderaFecha() {
        return banderaFecha;
    }

    public void setBanderaFecha(boolean banderaFecha) {
        this.banderaFecha = banderaFecha;
    }

    public boolean isBanderaPrograma() {
        return banderaPrograma;
    }

    public void setBanderaPrograma(boolean banderaPrograma) {
        this.banderaPrograma = banderaPrograma;
    }

    public boolean isBanderaOffset() {
        return banderaOffset;
    }

    public void setBanderaOffset(boolean banderaOffset) {
        this.banderaOffset = banderaOffset;
    }

   
    
    
    public List<KalturaCuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<KalturaCuenta> cuentas) {
        this.cuentas = cuentas;
    }

  
    public String getFabrica() {
        return fabrica;
    }

    public void setFabrica(String fabrica) {
        this.fabrica = fabrica;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public List<SistemaFecha> getFechas() {
        return fechas;
    }

    public void setFechas(List<SistemaFecha> fechas) {
        this.fechas = fechas;
    }

    public List<SistemaConsulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<SistemaConsulta> consultas) {
        this.consultas = consultas;
    }
    
    
}
