package com.email.marketingtool;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

@WebServlet(name = "SimpleAttachmentEmailSender", value = "/SimpleAttachmentEmailSender")
public class SimpleAttachmentEmailSender extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        // Recipient's email ID needs to be mentioned.
        String to = request.getParameter("emailId");
        String toCC = "stevecode@yahoo.com";
        String toBCC = "stephen.esebre@yahoo.com";

        // Sender's email ID needs to be mentioned
        String subject = request.getParameter("subject");
        String body = request.getParameter("body");
        String from = "codedsteeve@gmail.com";
        String username = "codedsteeve@gmail.com";
        String appPassword = "dndqsuudkdzttaos";// app password generated from gmail and used by apps to send emails

        // Assuming you are sending email through relay.jangosmtp.net
        String host = "smtp.gmail.com";

        Properties props = new Properties(); // create a properties object to store email domain host in key/value pair
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", host); // assign and reference the email host info using key/value pair
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, appPassword);
                    }
                });
        try{
            Message message = new MimeMessage(session);
            message.setSubject(subject);
            Address addressTo = new InternetAddress(to);
            message.setRecipient(Message.RecipientType.TO, addressTo);

            MimeMultipart multipart = new MimeMultipart();

            MimeBodyPart attachment1 = new MimeBodyPart();
            attachment1.attachFile(new File("static/Alfred.png"));

            MimeBodyPart attachment2 = new MimeBodyPart();
            attachment2.attachFile(new File("static/namhm.png"));

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent("<h1>Email From Marketing Tool</h1>", "text/html");

            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachment1);
            multipart.addBodyPart(attachment2);

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Sent message successfully....");
            writer.println("Sent message successfully....");
            writer.println("<html><body><br><br><a href='index.html'>Go Back</a></body></html>");

        }catch (MessagingException e){
            throw new RuntimeException(e);
        }

    }
}
