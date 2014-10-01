/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.azteca.service;



import com.azteca.persistence.entities.ComscoreConsumo;
import com.azteca.persistence.entities.ComscoreFabrica;
import com.azteca.persistence.entities.ComscorePrograma;
import com.azteca.persistence.entities.ComscoreSitio;
import com.azteca.persistence.entities.KalturaCuenta;
import com.azteca.persistence.entities.KalturaEntry;
import com.azteca.persistence.entities.KalturaFabrica;
import com.azteca.persistence.entities.KalturaMaterial;
import com.azteca.persistence.entities.KalturaPrograma;
import com.azteca.persistence.entities.KalturaUnidad;
import com.azteca.persistence.entities.Reporte;
import com.azteca.persistence.entities.SistemaFecha;
import com.azteca.persistence.entities.SistemaProceso;
import com.azteca.persistence.entities.SistemaReporte;
import com.azteca.persistence.entities.SistemaSubReporte;
import com.azteca.persistence.repository.ComscoreConsumoRepository;
import com.azteca.persistence.repository.ComscoreFabricaRepository;
import com.azteca.persistence.repository.ComscoreProgramaRepository;
import com.azteca.persistence.repository.ComscoreSitioRepository;
import com.azteca.persistence.repository.KalturaCuentaRepository;
import com.azteca.persistence.repository.KalturaEntryRepository;
import com.azteca.persistence.repository.KalturaFabricaRepository;
import com.azteca.persistence.repository.KalturaMaterialRepository;
import com.azteca.persistence.repository.KalturaProgramaRepository;
import com.azteca.persistence.repository.KalturaUnidadRepository;
import com.azteca.persistence.repository.SistemaFechaRepository;
import com.azteca.persistence.repository.SistemaProcesoRepository;
import com.azteca.persistence.repository.SistemaReporteRepository;
import com.azteca.persistence.repository.SistemaSubReporteRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 * @author Santa
 */
public class ConsultasBDService {

    @Autowired
    private KalturaEntryRepository kalturaEntryRepository;
    
    @Autowired
    private KalturaMaterialRepository kalturaMaterialRepository;

    @Autowired
    private KalturaCuentaRepository kalturaCuentaRepository;
    
    @Autowired
    private KalturaProgramaRepository kalturaProgramaRepository;
    
    @Autowired
    private KalturaFabricaRepository kalturaFabricaRepository;
    
    @Autowired
    private KalturaUnidadRepository kalturaUnidadRepository;
     
    @Autowired
    private  SistemaReporteRepository reporteRepository;
    
    @Autowired
    private  SistemaProcesoRepository sistemaProcesoRepository;
    
    @Autowired ComscoreSitioRepository comscoreSitioRepository; 
        
    @Autowired
    private SistemaFechaRepository sistemaFechaRepository;
        
    @Autowired
    private SistemaSubReporteRepository sistemaSubReporteRepository;
    
    @Autowired
    private ComscoreFabricaRepository comscoreFabricaRepository;
    
    @Autowired
    private ComscoreProgramaRepository comscoreProgramaRepository;
    
    @Autowired
    private ComscoreConsumoRepository comscoreConsumoRepository;
    
    public List<ComscoreConsumo> findComscoreComsumobyNombreAndFechaCorte(String nombre,Date fecha){
        return comscoreConsumoRepository.findByNombreAndFechaCorte(nombre, fecha);
    }
    
    public List<ComscorePrograma> findComscoreProgramaByNombre(String nombre){
        return comscoreProgramaRepository.findByNombre(nombre);
    }
    
    public List<ComscoreSitio> findComscoreSitioByNombre(String nombre){
        return comscoreSitioRepository.findByNombre(nombre);
    }
    
    public void saveComscoreConsumo(ComscoreConsumo cc){
        comscoreConsumoRepository.save(cc);
    }
    
    public List<ComscoreFabrica> findComscoreFabricaByNombre(String nombre,ComscoreSitio cf){
        return comscoreFabricaRepository.findByNombreAndComscoreSitio(nombre,cf);
    }
    
    public void saveComscorePrograma(ComscorePrograma cp){
        comscoreProgramaRepository.save(cp);
    }
    
    public List<ComscoreSitio> findAllComscoreSitio(){
        return comscoreSitioRepository.findAll();
    }
    
    public void saveComscoreSitio(ComscoreSitio cs){
        comscoreSitioRepository.save(cs);
    }
    
    public long entrysPorFabrica(int fabrica){
        return kalturaFabricaRepository.findEntrysByFabrica(fabrica);
    }
    
    public List<KalturaUnidad> findKalturaUnidadByID(int id){
       return kalturaUnidadRepository.findByIdKaltura(id);
    }
    
    public List<KalturaFabrica> findKalturaFabricaByID(int id){
       return kalturaFabricaRepository.findByIdKaltura(id);
    }
    
    public List<KalturaPrograma> findKalturaProgramaByID(int id){
       return kalturaProgramaRepository.findByIdKaltura(id);
    }
    
    public void saveSistemaSubReporte(SistemaSubReporte reporte){
        sistemaSubReporteRepository.save(reporte);
    }
    
    public List<SistemaReporte> findByFechaCorteAndParameters(Date fecha,String unidad,String fabrica,String programa){
       return  reporteRepository.findReportByFechaCorteNivelProgramaSque(fecha, unidad, fabrica, programa);
    }
    
    public List<SistemaReporte> findSistemaReporteByFechaCorte(Date fehcaCorte){
        return reporteRepository.findByFechaCorte(fehcaCorte);
    }
    
    public List<SistemaReporte> findSistemaReporteByNombreAndFechaCorte(String entry,Date fecha){
        return reporteRepository.findByEntryIdAndFechaCorte(entry, fecha);
    }
    
    public List<KalturaEntry> findKalturaEntryByNombre(String nombre){
        return kalturaEntryRepository.findByNombre(nombre);
    }
    
    public List<KalturaEntry> findKalturaEntryByentryId(String nombre){
        return kalturaEntryRepository.findByEntryId(nombre);
    }
    
    public void saveKalturaEntry(KalturaEntry kalturaEntry){
        kalturaEntryRepository.save(kalturaEntry);
    }
    
    public void saveKalturaEntrys(List<KalturaEntry> kalturaEntrys){
        kalturaEntryRepository.save(kalturaEntrys);
    }
    
    public void saveKalturaFabrica(KalturaFabrica kalturaFabrica){
        kalturaFabricaRepository.save(kalturaFabrica);
    }
    
    public void saveKalturaPrograma(KalturaPrograma kalturaPrograma){
        kalturaProgramaRepository.save(kalturaPrograma);
    }
    
    public void saveKalturaUnidad(KalturaUnidad KalturaUnidad){
        kalturaUnidadRepository.save(KalturaUnidad);
    }
    
    public List<KalturaFabrica> findKalturaFabricaByNombre(String nombre){
        return kalturaFabricaRepository.findByNombre(nombre);
    }
    
    public List<KalturaFabrica> findKalturaFabricaById(Integer id){
        return kalturaFabricaRepository.findByIdKaltura(id);
    }
    
    public List<KalturaFabrica> findKalturaFabricaByNombreAndUnidad(String nombre,KalturaUnidad ku){
        return kalturaFabricaRepository.findByNombreAndKalturaUnidad(nombre,ku);
    }
    
    public List<KalturaPrograma> findKalturaProgramaByNombre(String nombre){
        return kalturaProgramaRepository.findByNombre(nombre);
    }
    
    public List<KalturaPrograma> findKalturaProgramaByNombreAndFabrica(String nombre,KalturaFabrica kf){
        return kalturaProgramaRepository.findByNombreAndKalturaFabrica(nombre,kf);
    }
    
    public List<KalturaUnidad> findKalturaUnidadByNombre(String nombre){
        return kalturaUnidadRepository.findByNombre(nombre);
    }
    
    public void saveTotalEntrys(KalturaMaterial totalEntrys){
        this.kalturaMaterialRepository.save(totalEntrys);
    }
    
    public List<SistemaFecha> findAllSistemaFecha(){
        return sistemaFechaRepository.findAll();
    }
    
    public  List<SistemaProceso> findAllSistemaProceso(){
        return sistemaProcesoRepository.findAll();
    }
   
    public void saveCuentas(KalturaCuenta kalturaCuenta){
        this.kalturaCuentaRepository.save(kalturaCuenta);
    }
    
    public void saveReporte(SistemaReporte reporte){
        reporteRepository.save(reporte);
    }

    public List<SistemaReporte> findReportByEntry(String entry){
        return reporteRepository.findByEntryId(entry);
    }
    
   
    
    public List<KalturaMaterial> findTotalEntrysByEntryId(String id){
        return this.kalturaMaterialRepository.findByEntryId(id);
    }
    
    public List<KalturaCuenta> findCuentasByNombre(String cuenta){
        return this.kalturaCuentaRepository.findByNombre(cuenta);
    }
    
    public List<KalturaEntry> findentrysByFechaAndPrograma(Date fechaIni,Date fechaFinal,KalturaPrograma kp){
        return kalturaEntryRepository.findCortesByFecha(fechaIni, fechaFinal, kp);
    }
    
    
    public List<KalturaMaterial> findAllTotalEntrys(){
        return kalturaMaterialRepository.findAll();
    }
    
//    public List<KalturaMaterialPrograma> findWeekConsumeByEntryId(String id){
//       return kalturaMaterialProgramaRepository.findByEntryId(id);
//    }
    
    public List<KalturaCuenta> findAllCuentas(){
        return kalturaCuentaRepository.findAll();
    }
    
    public List<KalturaCuenta> findCuentasByPartner(Integer partner){
        return kalturaCuentaRepository.findBypartner(partner);
    }
    
    public void init( ) {
     
     }
}
