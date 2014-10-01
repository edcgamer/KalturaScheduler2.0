/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.azteca.model;

import com.azteca.persistence.entities.KalturaCuenta;
import com.azteca.persistence.entities.SistemaConsulta;
import com.azteca.persistence.entities.SistemaFecha;
import java.util.List;

/**
 *
 * @author Santa
 */
public class CategoryJobModel {
    
    String Cron;
    private List<SistemaFecha> fechas;
    private List<SistemaConsulta> consultas;
    private List<KalturaCuenta> cuentas;

    public CategoryJobModel(String Cron, List<SistemaFecha> fechas, List<SistemaConsulta> consultas, List<KalturaCuenta> cuentas) {
        this.Cron = Cron;
        this.fechas = fechas;
        this.consultas = consultas;
        this.cuentas = cuentas;
    }

    
    
    public CategoryJobModel() {
    }

    public List<KalturaCuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<KalturaCuenta> cuentas) {
        this.cuentas = cuentas;
    }

    
    public String getCron() {
        return Cron;
    }

    public void setCron(String Cron) {
        this.Cron = Cron;
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
