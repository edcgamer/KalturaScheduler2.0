/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.azteca.service;


import com.azteca.persistence.entities.ComscoreConsumo;
import com.azteca.persistence.entities.ComscoreFabrica;
import com.azteca.persistence.entities.ComscorePrograma;
import com.azteca.persistence.entities.ComscoreSitio;
import com.azteca.utils.Encrypt;
import com.azteca.utils.FechaParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author Santa
 */
public class ComscoreConsultaService {

    private static String USER_AGENT = "Mozilla/5.0";

    public static List<ComscoreConsumo> consultaURL(String url, ComscoreSitio comscore,ConsultasBDService rep) throws MalformedURLException, IOException {
        System.out.println(url);
        URL obj = new URL(url);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.65.0.43", 80));
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection(proxy);
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String[] pb = response.toString().replaceAll("\\|\\|", "\\|").split("\\|");
        List<String> lista = Arrays.asList(pb);
        lista = lista.subList(4, lista.size());
        ComscoreConsumo comscoreWeekConsume;
        List<ComscoreConsumo> listaComscore = new ArrayList<>();

        for (int contador = 0; contador < lista.size(); contador += 3) {
            DecimalFormat df = new DecimalFormat("###.##");
            
//            comscoreWeekConsume = new ComscoreConsumo(lista.get(contador), Integer.parseInt(lista.get(contador + 1)), Double.parseDouble(df.format(Double.parseDouble(lista.get(contador + 2)))), new Date());
              ComscoreFabrica comsFab=rep.findComscoreFabricaByNombre("Sin Fabrica",comscore).get(0);
            if (!lista.get(contador).equals("/")&&rep.findComscoreComsumobyNombreAndFechaCorte(lista.get(contador), new Date()).isEmpty()) {
                System.out.println("guardando :"+lista.get(contador) + " " + Double.parseDouble(df.format(Double.parseDouble(lista.get(contador + 2)))) + " " + Integer.parseInt(lista.get(contador + 1)));
                if (rep.findComscoreProgramaByNombre(lista.get(contador)).isEmpty()) {
                    rep.saveComscorePrograma(new ComscorePrograma(lista.get(contador), comsFab));
                }
                rep.saveComscoreConsumo(new ComscoreConsumo(lista.get(contador), Double.parseDouble(lista.get(contador + 1)), 0.0, new Date(), comscore.getNombre(), comsFab.getNombre()));
                comscoreWeekConsume = new ComscoreConsumo();
                listaComscore.add(comscoreWeekConsume);
            }
        }
        return listaComscore;
    }

    public static String generaURL(ComscoreSitio comscore, Date fechaIni, Date fechaFin) throws Exception {
//
        String subUrl = "client=tv-azteca&user=" + comscore.getUsuario() + "&password=" + Encrypt.decrypt(comscore.getContrasena()) + "&itemid=7&site="+comscore.getReferencia()+"&startdate=" + FechaParser.dateToReportFilter(fechaIni) + "&enddate=" + FechaParser.dateToReportFilter(fechaFin);
        String url = "https://dax-rest.comscore.com/v1/reportitems.csv?";
        return url + subUrl;
    }
}
