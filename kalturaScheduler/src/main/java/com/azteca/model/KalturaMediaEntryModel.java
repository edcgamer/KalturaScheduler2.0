/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azteca.model;

import com.kaltura.client.types.KalturaMediaEntry;
import java.util.Date;

/**
 *
 * @author Santa
 */
public class KalturaMediaEntryModel {
    private KalturaMediaEntry kalturaMediaEntry;
    private Integer tamanio;
    private Integer duration;
    private Date fechaCreacion;
    private Long tiempoVisto;
    private String entry;
    private String name;
    private String category;
    private String categoryName;

    public KalturaMediaEntryModel(String entry, String name, String category, String categoryName) {
        this.entry = entry;
        this.name = name;
        this.category = category;
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public KalturaMediaEntryModel(String entry, String name, String category) {
        this.entry = entry;
        this.name = name;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public KalturaMediaEntryModel(String entry, Long tiempoVisto) {
        this.entry = entry;
        this.tiempoVisto = tiempoVisto;
    }
    /**
     * @return the kalturaMediaEntry
     */
    public KalturaMediaEntry getKalturaMediaEntry() {
        return kalturaMediaEntry;
    }

    /**
     * @param kalturaMediaEntry the kalturaMediaEntry to set
     */
    public void setKalturaMediaEntry(KalturaMediaEntry kalturaMediaEntry) {
        this.kalturaMediaEntry = kalturaMediaEntry;
    }

    /**
     * @return the tamanio
     */
    public Integer getTamanio() {
        return tamanio;
    }

    public KalturaMediaEntryModel(String entry, String name) {
        this.entry = entry;
        this.name = name;
    }

    public KalturaMediaEntryModel() {
    }

    public KalturaMediaEntryModel(KalturaMediaEntry kalturaMediaEntry, Integer tamanio) {
        this.kalturaMediaEntry = kalturaMediaEntry;
        this.tamanio = tamanio;
    }
    
    public KalturaMediaEntryModel(KalturaMediaEntry kalturaMediaEntry, Integer tamanio,Integer duration) {
        this.kalturaMediaEntry = kalturaMediaEntry;
        this.tamanio = tamanio;
        this.duration= duration;
    }

    public KalturaMediaEntryModel(KalturaMediaEntry kalturaMediaEntry, Integer tamanio, Integer duration, Date fechaCreacion, Long tiempoVisto, String entry, String name) {
        this.kalturaMediaEntry = kalturaMediaEntry;
        this.tamanio = tamanio;
        this.duration = duration;
        this.fechaCreacion = fechaCreacion;
        this.tiempoVisto = tiempoVisto;
        this.entry = entry;
        this.name = name;
    }

    public KalturaMediaEntryModel(KalturaMediaEntry kalturaMediaEntry, Integer tamanio, Integer duration, Date fechaCreacion, String name) {
        this.kalturaMediaEntry = kalturaMediaEntry;
        this.tamanio = tamanio;
        this.duration = duration;
        this.fechaCreacion = fechaCreacion;
        this.name = name;
    }
    
    public KalturaMediaEntryModel(KalturaMediaEntry kalturaMediaEntry, Integer tamanio,Integer duration,Date fechaCreacion) {
        this.kalturaMediaEntry = kalturaMediaEntry;
        this.tamanio = tamanio;
        this.duration= duration;
        this.fechaCreacion=fechaCreacion;
    }

    /**
     * @param tamanio the tamanio to set
     */
    public void setTamanio(Integer tamanio) {
        this.tamanio = tamanio;
    }

    /**
     * @return the duration
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the tiempoVisto
     */
    public Long getTiempoVisto() {
        return tiempoVisto;
    }

    /**
     * @param tiempoVisto the tiempoVisto to set
     */
    public void setTiempoVisto(Long tiempoVisto) {
        this.tiempoVisto = tiempoVisto;
    }

    /**
     * @return the entry
     */
    public String getEntry() {
        return entry;
    }

    /**
     * @param entry the entry to set
     */
    public void setEntry(String entry) {
        this.entry = entry;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
}
