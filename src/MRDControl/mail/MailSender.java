/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MRDControl.mail;

import MRDControl.Config;
import MRDControl.report.ReportGenerator;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author armando
 */
public class MailSender implements Runnable {

    private Session session;
    private String user;
    private String password;
    private ReportGenerator reportGenerator;


    public MailSender() {
        this.user = MailConfig.propiedades.getProperty("mail.username");
        this.password = MailConfig.propiedades.getProperty("mail.password");
        Properties props = new Properties();
        props.put("mail.smtp.host", MailConfig.propiedades.getProperty("mail.host"));
        props.put("mail.smtp.port", MailConfig.propiedades.getProperty("mail.port"));
        props.put("mail.smtp.user", user);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        this.session = Session.getDefaultInstance(props);
        this.reportGenerator = new ReportGenerator();
    }

    public void sendMail() throws MessagingException {
        String filePath = reportGenerator.generateReport();
        File file = new File(filePath);
        String to = Config.propiedades.get("correo").toString();
        List<String> tos = Arrays.asList(to.split(","));

        MimeMessage message = new MimeMessage(session);
        message.addRecipients(Message.RecipientType.TO, to);
        message.setSubject("Reporte de ocupación de habitaciones");
        message.setText("Adjunto reporte ocupacion habitaciones");

        MimeBodyPart mailBody = new MimeBodyPart();
        mailBody.setText("Adjunto reporte ocupacion habitaciones");

        MimeBodyPart attachment = new MimeBodyPart();

        try {
            attachment.attachFile(file);
        } catch (IOException ex) {
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
        }

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mailBody);
        multipart.addBodyPart(attachment);

        message.setContent(multipart);
        message.setFrom(new InternetAddress(user));

        Transport transport = this.session.getTransport("smtp");
        transport.connect(user, password);

        Address[] toAddresses = new Address[tos.size()];

        for(int i = 0;i< toAddresses.length ; i ++){
            toAddresses[i] = new InternetAddress(tos.get(i));
        }

        transport.sendMessage(message, toAddresses);
    }

    @Override
    public void run() {
        System.out.println("Enviando Reporte");
        try {
            sendMail();
            System.out.println("Reporte enviado");
        } catch (MessagingException ex) {
            System.out.println("No se pudo enviar reporte");
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
