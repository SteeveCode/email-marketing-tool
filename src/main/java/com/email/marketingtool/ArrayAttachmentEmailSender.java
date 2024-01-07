package com.email.marketingtool;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

@WebServlet(name = "ArrayAttachmentEmailSender", value = "/ArrayAttachmentEmailSender")
public class ArrayAttachmentEmailSender extends HttpServlet {
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
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");


        // Get the Session object.
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, appPassword);
                    }
                });
        try{
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            message.setSubject(subject);
            Address addressTo = new InternetAddress(to);
            Address CC = new InternetAddress(toCC);
            Address BCC = new InternetAddress(toBCC);
            message.setRecipient(Message.RecipientType.TO, addressTo);
            message.addRecipient(Message.RecipientType.CC, CC);
            message.addRecipient(Message.RecipientType.BCC, BCC);

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // File paths to attach
            String[] filePaths = {
                    "C:\\Users\\SteveCode\\Documents\\Web Projects\\Learn\\Java Servlets\\email-marketing-tool\\static\\Alfred.png",
                    "C:\\Users\\SteveCode\\Documents\\Web Projects\\Learn\\Java Servlets\\email-marketing-tool\\static\\namhm.png",
                    "C:\\Users\\SteveCode\\Documents\\Web Projects\\Learn\\Java Servlets\\email-marketing-tool\\static\\coffee.jpg"};

            // Attach files
            for (String filePath : filePaths) {
                MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                File file = new File(filePath);
                attachmentBodyPart.attachFile(file);
                multipart.addBodyPart(attachmentBodyPart);
            }

            MimeBodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setContent(body, "text/html");

            multipart.addBodyPart(messageBodyPart);

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
