/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MRDControl.report;

import MRDControl.Config;
import MRDControl.ConfigurarPuerto;
import MRDControl.mail.MailSender;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author armando
 */
public class SendReportScheduler {

    public void schedule() {
        String hourString = Config.propiedades.getProperty("hora", "7:00 AM");
        int hour;
        int minute;

        try {
            SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat date24Format = new SimpleDateFormat("HH");
            hour = Integer.parseInt(date24Format.format(date12Format.parse(hourString)));
            minute = Integer.parseInt(new SimpleDateFormat("mm").format(date12Format.parse(hourString)));
        } catch (ParseException ex) {
            hour = 7;
            minute = 0;
            Logger.getLogger(ConfigurarPuerto.class.getName()).log(Level.SEVERE, null, ex);
        }

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Santo_Domingo"));
        ZonedDateTime nextRun = now.withHour(hour).withMinute(minute).withSecond(0);
        System.out.println("Next Run: "+ nextRun.toString());
        //ZonedDateTime nextRun = now.withHour(12).withMinute(57).withSecond(0);
        if (now.compareTo(nextRun) > 0) {
            nextRun = nextRun.plusDays(1);
        }

        Duration duration = Duration.between(now, nextRun);
        long initalDelay = duration.getSeconds();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new MailSender(),
                initalDelay,
                TimeUnit.DAYS.toSeconds(1),
                TimeUnit.SECONDS);
    }

}
