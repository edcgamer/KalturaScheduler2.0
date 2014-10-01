package com.azteca.quartz;

import com.azteca.persistence.entities.KalturaCuenta;
import com.azteca.persistence.entities.KalturaUnidad;
import com.azteca.persistence.entities.SistemaConsulta;
import com.azteca.persistence.entities.SistemaFecha;
import com.azteca.persistence.entities.SistemaProceso;
import com.azteca.service.ConsultasBDService;
import com.azteca.service.ConsultasKalturaExtra;
import com.azteca.service.ConsultasKalturaService;
import com.azteca.utils.PruebasTemporalesKaltura;
import com.azteca.utils.TimeParser;
import com.kaltura.client.enums.KalturaMediaType;
import java.util.Date;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SchedulerTrigger {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("Spring-Quartz.xml");
        ConsultasBDService rep = applicationContext.getBean("scheduler", ConsultasBDService.class);
        SchedulerFactory schedulerFact = new SchedulerFactory();
        int tablas;
        int valor;
        if (args.length > 1) {
            tablas = Integer.valueOf(args[1]);
        } else {
            tablas = 0;
        }
        if (args.length == 0) {
            System.out.println("ERROR!");
            return;
        } else {
            try {
                valor = Integer.valueOf(args[0]);
            } catch (NumberFormatException ex) {
                System.out.println("argumentos no validos correr el programa con las opcion necesaria");
                return;
            }
        }
        ConsultasKalturaExtra cke = new ConsultasKalturaExtra();
        switch (valor) {
            case 0:
                schedulerFact.scheduleInit();
                schedulerFact.startScheduler();
                schedulerFact.fabricaScheduler(rep);
                
            break;

            default:
                System.out.println("no se mando a llamar nada");
                break;
        }
    }
}
