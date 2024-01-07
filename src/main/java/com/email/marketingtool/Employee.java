package com.email.marketingtool;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Employee", value = "/Employee")
public class Employee extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user = (String)session.getAttribute("username");
        PrintWriter write = response.getWriter();
        write.println(" <html>\n" +
                "<head>\n" +
                "    <title>ABC Email Marketing</title>\n" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<div id=\"head\" style=\"text-align:center\"><h1><span style=\"color:red\">A</span><span style=\"color:yellow\">B</span><span style=\"color:pink\">C</span> - <span style=\"color:white\">Email Marketing Tool</span> </h1></div>\n" +
                "<nav id=\"navi\"><span class=\"m2\"><a href=\"email.html\">Send Email</a></span>&nbsp;&nbsp;&nbsp;<span class=\"m3\"><a href=\"email-attachment.html\">Attachment Email</a></span>&nbsp;&nbsp;&nbsp;<span class=\"m4\"><a href=\"simple-email-attachment.html\">Simple Attachment Email</a></span></nav>\n" +
                "<div id=\"main-page\">\n" +
                "    Hello " + user +
                "</div>\n" + "br><br><a href='index.html'>Go Back</a>" +
                "\n" +
                "</body>\n" +
                "</html>");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
