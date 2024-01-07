package com.email.marketingtool;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "AddEmployees", value = "/AddEmployees")
public class AddEmployees extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        int empId = Integer.parseInt(request.getParameter("e1"));
        String empName = request.getParameter("e2");
        String empLoginId = request.getParameter("e3");
        String empPassword = request.getParameter("e4");

        String jdbcUrl = "jdbc:mysql://localhost:3306/email?serverTimeZone=u&useSSL=false";
        String username = "sergey";
        String password = "sergey";

        PrintWriter writer = response.getWriter();


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(jdbcUrl,username,password);
            PreparedStatement pst = con.prepareStatement("insert into emprecord values (?,?,?,?)");
            pst.setInt(1, empId);
            pst.setString(2, empName);
            pst.setString(3, empLoginId);
            pst.setString(4, empPassword);
            int rowCount = pst.executeUpdate();
            writer.println("Data Added Successfully");
            Thread.sleep(2000);
            response.sendRedirect("index.html");
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
