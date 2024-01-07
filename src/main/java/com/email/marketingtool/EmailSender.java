package com.email.marketingtool;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

@WebServlet(name = "EmailSender", value = "/EmailSender")
public class EmailSender extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        // Recipient's email ID needs to be mentioned.
        String to = request.getParameter("emailId");

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
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.trust", host);


        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, appPassword);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session); // MIME supports not only ASCII, but BLOB, Audio, Attachments, Images, Video etc

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from)); // typecast sender address to InternetAddress type

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(body);

            // Send message
            Transport.send(message); // used to establish connection with the configured host and then transport data

            System.out.println("Sent message successfully....");
            writer.println("Sent message successfully....");
            writer.println("<html><body><br><br><a href='index.html'>Go Back</a></body></html>");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
