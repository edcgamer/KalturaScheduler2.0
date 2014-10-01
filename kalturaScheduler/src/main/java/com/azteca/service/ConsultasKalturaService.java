/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azteca.service;

import com.azteca.model.KalturaMediaEntryModel;
import com.azteca.persistence.entities.KalturaEntry;
import com.azteca.persistence.entities.KalturaPrograma;
import com.azteca.persistence.entities.SistemaFecha;
import com.azteca.utils.FechaParser;
import com.azteca.utils.KalturaObjectsParser;
import com.azteca.utils.TimeParser;
import com.kaltura.client.KalturaApiException;
import com.kaltura.client.KalturaClient;
import com.kaltura.client.KalturaConfiguration;
import com.kaltura.client.enums.KalturaMediaType;
import com.kaltura.client.enums.KalturaReportType;
import com.kaltura.client.enums.KalturaSessionType;
import com.kaltura.client.services.KalturaMediaService;
import com.kaltura.client.types.KalturaCategory;
import com.kaltura.client.types.KalturaCategoryEntry;
import com.kaltura.client.types.KalturaCategoryEntryFilter;
import com.kaltura.client.types.KalturaCategoryEntryListResponse;
import com.kaltura.client.types.KalturaCategoryFilter;
import com.kaltura.client.types.KalturaCategoryListResponse;
import com.kaltura.client.types.KalturaFilterPager;
import com.kaltura.client.types.KalturaFlavorAsset;
import com.kaltura.client.types.KalturaMediaEntry;
import com.kaltura.client.types.KalturaMediaEntryFilter;
import com.kaltura.client.types.KalturaMediaListResponse;
import com.kaltura.client.types.KalturaReportInputFilter;
import com.kaltura.client.types.KalturaReportTable;
import com.kaltura.client.types.KalturaReportTotal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Santa
 */
public class ConsultasKalturaService {

    private String userSecret;
    private String adminSecret;
    private Integer partnerId;
    private KalturaMediaType mediaType;
    private KalturaClient client;
    private KalturaMediaService mediaService;
    private KalturaMediaEntryFilter filter;
    private KalturaCategoryFilter filtersCategory;

    public ConsultasKalturaService(String userSecret, String adminSecret, Integer partnerId, KalturaMediaType mediaType) throws KalturaApiException {
        this.userSecret = userSecret;
        this.adminSecret = adminSecret;
        this.partnerId = partnerId;
        this.mediaType = mediaType;

        fabricaConexionKaltura();
    }

//    Generadora de conexion
    private void fabricaConexionKaltura() throws KalturaApiException {

        this.client = getKalturaClient(this.partnerId, this.adminSecret, true);
        this.mediaService = client.getMediaService();
        this.filter = new KalturaMediaEntryFilter();
    }

    public int obtnerTotalEntrysPorCategoria(String idMatch, String idNotMatch, int fechainicio, int fechaFinal) throws KalturaApiException {
        KalturaMediaEntryFilter filters = new KalturaMediaEntryFilter();
        filters.mediaTypeEqual = mediaType;
        if (!idNotMatch.equals("")) {
            filters.categoriesIdsNotContains = idNotMatch;
        }
        if (!idMatch.equals("")) {
            filters.categoriesIdsMatchOr = idMatch;
        }
        if (fechainicio != 0) {
            filters.createdAtGreaterThanOrEqual = fechainicio;
        }
        if (fechaFinal != 0) {
            filters.createdAtLessThanOrEqual = fechaFinal;
        }
        KalturaMediaService mediaServices = client.getMediaService();
//        System.out.println(mediaServices.count(filters));
        return mediaService.count(filters);
    }

    public List<KalturaMediaEntryModel> obtnerEntrysPorCategoria(String idMatch, String idNotMatch, int fechainicio, int fechaFinal, int page, int howmany) throws KalturaApiException {
        KalturaMediaEntryFilter filters = new KalturaMediaEntryFilter();
        KalturaFilterPager kfp = new KalturaFilterPager();
        List<KalturaMediaEntryModel> listaEntrys = new ArrayList<>();
        if (page != 0) {
            kfp.pageIndex = page;
        }
        if (howmany != 0) {
            kfp.pageSize = howmany;
        }
        filters.mediaTypeEqual = mediaType;
        if (!idNotMatch.equals("")) {
            filters.categoriesIdsNotContains = idNotMatch;
        }
        if (!idMatch.equals("")) {
            filters.categoriesIdsMatchOr = idMatch;
        }
        if (fechainicio != 0) {
            filters.createdAtGreaterThanOrEqual = fechainicio;
        }
        if (fechaFinal != 0) {
            filters.createdAtLessThanOrEqual = fechaFinal;
        }
        KalturaMediaService mediaServices = client.getMediaService();
        KalturaMediaEntryModel kmm;
//        System.out.println(mediaServices.count(filters));
        for (KalturaMediaEntry kme : mediaServices.list(filters, kfp).objects) {
//            System.out.println(kme.categoriesIds+" ad***");
//            if (kme.categoriesIds != null) {
//                if (kme.categoriesIds.equals("") || kme.categoriesIds.equals("2130322") || kme.categoriesIds.equals("2130342")) {
                    kmm = new KalturaMediaEntryModel(kme.id, kme.name, kme.categories, kme.categories);
                    kmm.setFechaCreacion(new Date(kme.createdAt * 1000L));
//                   for(KalturaMediaEntryModel kmem: consultaMinutosVistosWPager(kme.id)){
//                       kmm.setTiempoVisto(kmem.getTiempoVisto());
//                   }
                    listaEntrys.add(kmm);
//                }
//            }else {
//                kmm = new KalturaMediaEntryModel(kme.id, kme.name, "", kme.categories);
//                listaEntrys.add(kmm);
//            }
        }
        return listaEntrys;
    }

    public List<KalturaCategory> consultaNivelCategoria(int nivel, Integer padre) throws KalturaApiException {
        KalturaCategoryFilter filtersCategory = new KalturaCategoryFilter();
        if (padre != null) {
            filtersCategory.parentIdEqual = padre;
        }
        filtersCategory.depthEqual = nivel;
        filtersCategory.createdAtLessThanOrEqual = Integer.valueOf(new Date().getTime() / 1000 + "");;
        KalturaCategoryListResponse kcs = client.getCategoryService().list(filtersCategory);
        return kcs.objects;
    }

    public int entriesPorSemana(Integer padre,SistemaFecha sf) throws KalturaApiException {
        this.filter.mediaTypeEqual=KalturaMediaType.VIDEO;
       if(sf.getFechaInicio()!=null&&sf.getFechaFinal()!=null){
        this.filter.createdAtGreaterThanOrEqual=Integer.valueOf(sf.getFechaInicio().getTime()/1000+"");
        this.filter.createdAtLessThanOrEqual=Integer.valueOf(sf.getFechaFinal().getTime()/1000+"");
       }else{
            if (sf.getTiempo() != null) {
                System.out.println("consulta por dias :"+sf.getTiempo());
                this.filter.createdAtGreaterThanOrEqual = Integer.parseInt((new Date().getTime() - 86400000 * sf.getTiempo()) / 1000 + "");
                this.filter.createdAtLessThanOrEqual = Integer.valueOf(new Date().getTime() / 1000 + "");
            }
        }
       this.filter.categoriesIdsMatchAnd = padre + "";
        return mediaService.count(this.filter);
    }

    /**
     * Create a new KalturaClient object with an initiated session.
     *
     * @param partnerId Partner's ID number
     * @param secret Secret string (either user's or admin's, depending on
     * 'isAdmin')
     * @param isAdmin Should client be an admin or a normal user ?
     *
     * @return A configured client that you can use to call API services
     *
     * @throws KalturaApiException
     */
    private KalturaClient getKalturaClient(int partnerId, String secret, boolean isAdmin) throws KalturaApiException {
        // set a new configuration object
        KalturaConfiguration config = new KalturaConfiguration();
        config.setPartnerId(partnerId);
        config.setEndpoint("http://www.kaltura.com");

        KalturaClient client = new KalturaClient(config);

        // start a new session (admin/user) and recieve the ks (kaltura session) string
        String userId = "user's name";
        KalturaSessionType sessionType = (isAdmin ? KalturaSessionType.ADMIN : KalturaSessionType.USER);
        String ks = client.getSessionService().start(secret, userId, sessionType);

        // set the kaltura client to use the recieved ks as default for all future operations
        client.setSessionId(ks);

        return client;
    }

//    devuelve los minutos vistos de cada entry
    public String consultaMinutosVistos(String idEntry) throws KalturaApiException {
        KalturaReportType reportType = KalturaReportType.TOP_CONTENT;
        KalturaReportInputFilter reportInputFilter = new KalturaReportInputFilter();
        Date fechaInicio = new Date();
        Date fechaFin = new Date(fechaInicio.getTime() - (86400000 * 8));
        reportInputFilter.fromDay = FechaParser.dateToReportFilter(fechaFin);
        reportInputFilter.toDay = FechaParser.dateToReportFilter(fechaInicio);
        KalturaReportTotal result = this.client.getReportService().getTotal(reportType, reportInputFilter, idEntry);
        String min_vistos = KalturaObjectsParser.parseCadena(result.data);
        if (min_vistos.equals("")) {
            min_vistos = "0";
        } else if (min_vistos.equals("0.000")) {
            min_vistos = "0";
        }
        return min_vistos;
    }

//    consulta minutos vistos por paquetes de 500
    public List<KalturaMediaEntryModel> consultaMinutosVistosWPager(String idEntry,SistemaFecha sf) throws KalturaApiException {
        Date fechaInicio = new Date();
        System.out.println(idEntry);
        KalturaReportType reportType = KalturaReportType.TOP_CONTENT;
        KalturaReportInputFilter reportInputFilter = new KalturaReportInputFilter();
        if(sf.getFechaInicio()!=null&&sf.getFechaFinal()!=null){
//            reportInputFilter.fromDay = (sf.getFechaInicio().getTime()/1000)+"";
//            reportInputFilter.toDay = (sf.getFechaFinal().getTime()/1000)+"";
            reportInputFilter.fromDay =FechaParser.dateToReportFilter(sf.getFechaInicio());
            reportInputFilter.toDay =FechaParser.dateToReportFilter(sf.getFechaFinal());
//            reportInputFilter.fromDay=20140701+"";
//            reportInputFilter.toDay=20140731+"";
        }else {
            reportInputFilter.fromDay =FechaParser.dateToReportFilter(new Date((new Date().getTime() - 86400000 * sf.getTiempo())));
            reportInputFilter.toDay =FechaParser.dateToReportFilter(new Date((new Date().getTime()  * sf.getTiempo())));
//            reportInputFilter.fromDay = ((new Date().getTime() - 86400000 * sf.getTiempo()) / 1000) + "";
//            reportInputFilter.toDay = ((new Date().getTime()) / 1000) + "";
        }

        KalturaFilterPager pager = new KalturaFilterPager();
        pager.pageIndex = 1;
        pager.pageSize = 500;
        KalturaReportTable result = this.client.getReportService().getTable(reportType, reportInputFilter, pager, null, idEntry);
//        System.out.println(result.header);
//        System.out.println(result.data);
//        System.out.println(TimeParser.tiempoTranscurrido(fechaInicio, new Date()));
        List<KalturaMediaEntryModel> error = new ArrayList<>();
        if (result.data == null) {
            return error;
        } else {
            return KalturaObjectsParser.parseGetTable(result.data);
        }
    }

//    obtiene los entrys de cada semana sin offset
    public List<KalturaMediaEntryModel> consultaEntrySemanaSinOffser(int howMany) throws KalturaApiException {
        KalturaFilterPager pager = new KalturaFilterPager();
        this.filter.createdAtGreaterThanOrEqual = Integer.parseInt((new Date().getTime() - 86400000 * 16) / 1000 + "");
        this.filter.createdAtLessThanOrEqual = Integer.valueOf(new Date().getTime() / 1000 + "");
        this.filter.mediaTypeEqual = this.mediaType;
        System.out.println(this.filter.createdAtGreaterThanOrEqual + " " + this.filter.createdAtLessThanOrEqual);
        KalturaMediaListResponse listResponse = mediaService.list(this.filter);

        List<KalturaMediaEntryModel> listaTotal = new ArrayList<>();
        List<KalturaMediaEntryModel> subLista;
        System.out.println(listResponse.totalCount);

        for (int u = 1; u <= (listResponse.totalCount / 500) + 1; u++) {
            subLista = new ArrayList<>();

            pager.pageIndex = u;
            pager.pageSize = howMany;

            this.mediaService = this.client.getMediaService();

            // execute the list action of the mediaService object to recieve the list of entries
            listResponse = this.mediaService.list(this.filter, pager);

            for (KalturaMediaEntry kme : listResponse.objects) {
                System.out.println(kme.id + " " + kme.duration);
                List list = this.client.getFlavorAssetService().getByEntryId(kme.id);
                int tamaño = list.size();
                KalturaFlavorAsset as[] = new KalturaFlavorAsset[tamaño];
                list.toArray(as);
                int suma = 0;
                for (KalturaFlavorAsset a : as) {
                    suma += a.size;
                }
                subLista.add(new KalturaMediaEntryModel(kme, suma, kme.duration));
            }
            listaTotal.addAll(subLista);
        }
        return listaTotal;
    }

//    obtiene el promedio de pesos de los entrys ubicados entre las fechas asignadas
    public double consultaEntryPromediomesPesosPorDia(int howMany, int fechaIni, int fechaFin) throws KalturaApiException {

        KalturaFilterPager pager = new KalturaFilterPager();
        Date date = new Date();
        this.filter.createdAtGreaterThanOrEqual = fechaIni;
        this.filter.createdAtLessThanOrEqual = fechaFin;
        this.filter.mediaTypeEqual = this.mediaType;
        System.out.println(this.filter.createdAtGreaterThanOrEqual + " " + this.filter.createdAtLessThanOrEqual);
        KalturaMediaListResponse listResponse = mediaService.list(this.filter);

        List<KalturaMediaEntryModel> listaTotal = new ArrayList<>();
        List<KalturaMediaEntryModel> subLista;
        System.out.println(listResponse.totalCount);
        int cont = 0;
        int cont2 = 0;
        for (int u = 1; u <= (listResponse.totalCount / 500) + 1; u++) {
            subLista = new ArrayList<>();

            pager.pageIndex = u;
            pager.pageSize = howMany;

            this.mediaService = this.client.getMediaService();

            // execute the list action of the mediaService object to recieve the list of entries
            listResponse = this.mediaService.list(this.filter, pager);

            for (KalturaMediaEntry kme : listResponse.objects) {

                List list = this.client.getFlavorAssetService().getByEntryId(kme.id);
                int tamaño = list.size();
                KalturaFlavorAsset as[] = new KalturaFlavorAsset[tamaño];
                list.toArray(as);
                int suma = 0;
                System.out.println(kme.name);
                System.out.println(list.size() + "+++");
                for (KalturaFlavorAsset a : as) {
                    suma += a.size;
                }
                System.out.println(suma);
                cont += suma;
                cont2 += kme.duration;

            }

        }
        System.out.println("promedio duracion =" + (cont2 / listResponse.totalCount));
        System.out.println(listResponse.totalCount + " " + cont);
        return cont / listResponse.totalCount;
    }

    public Integer totalEntrys() throws KalturaApiException {
        Date date = new Date();
        this.filter.mediaTypeEqual = this.mediaType;
        KalturaMediaListResponse listResponse = mediaService.list(this.filter);
        return listResponse.totalCount;
    }

//    metodo para vr los nuevos entrys de la semana
    public List<KalturaEntry> consultaEntrySemana(int howMany, int u, String cat,SistemaFecha sf,KalturaPrograma kp) throws KalturaApiException {
         this.filter.mediaTypeEqual=KalturaMediaType.VIDEO;
        if (sf.getFechaInicio() != null && sf.getFechaFinal() != null) {
            
            this.filter.createdAtGreaterThanOrEqual = Integer.valueOf(sf.getFechaInicio().getTime() / 1000 + "");
            this.filter.createdAtLessThanOrEqual = Integer.valueOf(sf.getFechaFinal().getTime() / 1000 + "");
        } else {
            if (sf.getTiempo() != null) {
                this.filter.createdAtGreaterThanOrEqual = Integer.parseInt((new Date().getTime() - 86400000 * sf.getTiempo()) / 1000 + "");
                this.filter.createdAtLessThanOrEqual = Integer.valueOf(new Date().getTime() / 1000 + "");
            }
        }
        this.filter.categoriesIdsMatchAnd = cat;
        KalturaFilterPager pager = new KalturaFilterPager();
        pager.pageIndex = u;
        pager.pageSize = howMany;
        List<KalturaMediaEntryModel> listaTotal = new ArrayList<>();
        List<KalturaMediaEntryModel> subLista;
        List<KalturaEntry> listaEntrys= new ArrayList();
        subLista = new ArrayList<>();
        this.mediaService = this.client.getMediaService();
        KalturaMediaListResponse listResponse = mediaService.list(this.filter, pager);
        System.out.println("tamaño de la respuesta: " + listResponse.objects.size());
        for (KalturaMediaEntry kme : listResponse.objects) {
            List list = this.client.getFlavorAssetService().getByEntryId(kme.id);
            int tamaño = list.size();
            KalturaFlavorAsset as[] = new KalturaFlavorAsset[tamaño];
            list.toArray(as);
            int suma = 0;
            for (KalturaFlavorAsset a : as) {
                suma += a.size;
            }
            listaEntrys.add(new KalturaEntry(kme.id,kme.name,new Date(kme.createdAt * 1000L),suma,kme.duration,kp));
            subLista.add(new KalturaMediaEntryModel(kme, suma, kme.duration, new Date(kme.createdAt * 1000L), kme.name));
        }
        listaTotal.addAll(subLista);
//        }
        return listaEntrys;
    }

//    busca por entry el sus datos para recounstruir la tbla de kaltura
    public List<KalturaMediaEntryModel> consultaEntryReconstruccionTablaKalturaMaterial(int howMany, int u, String entry) throws KalturaApiException {
        KalturaFilterPager pager = new KalturaFilterPager();
        KalturaMediaListResponse listResponse = mediaService.list(this.filter);
        this.filter.idEqual = entry;
        List<KalturaMediaEntryModel> listaTotal = new ArrayList<>();
        List<KalturaMediaEntryModel> subLista;
        subLista = new ArrayList<>();
        pager.pageIndex = u;
        pager.pageSize = howMany;
        this.mediaService = this.client.getMediaService();
        listResponse = this.mediaService.list(this.filter, pager);
        if (listResponse != null) {
            System.out.println("el numero de respuestas es " + listResponse.objects.size());
            for (KalturaMediaEntry kme : listResponse.objects) {
                System.out.println(kme.id + " " + kme.duration + " " + new Date(kme.createdAt * 1000L));
                List list = this.client.getFlavorAssetService().getByEntryId(kme.id);
                int tamaño = list.size();
                KalturaFlavorAsset as[] = new KalturaFlavorAsset[tamaño];
                list.toArray(as);
                int suma = 0;
                for (KalturaFlavorAsset a : as) {
                    suma += a.size;
                }
                subLista.add(new KalturaMediaEntryModel(kme, suma, kme.duration, new Date(kme.createdAt * 1000L)));
            }
            listaTotal.addAll(subLista);
        }
        return listaTotal;
    }

    public ConsultasKalturaService() {
    }
}
