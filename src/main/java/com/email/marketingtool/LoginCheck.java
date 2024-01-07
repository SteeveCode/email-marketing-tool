package com.email.marketingtool;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "LoginCheck", value = "/LoginCheck")
public class LoginCheck extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/email?serverTimeZone=u&useSSL=false";
        String username = "sergey";
        String password = "sergey";

        String empLoginId = request.getParameter("n1");
        String empPassword = request.getParameter("p1");

        PrintWriter writer = response.getWriter();
        if(empLoginId.equals("admin@abc.com") && empPassword.equals("123")){
            response.sendRedirect("admin.html");
        } else{
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(jdbcUrl,username,password);
                PreparedStatement pst = con.prepareStatement("select login, password, empname from emprecord");
                ResultSet rs = pst.executeQuery();
                while (rs.next()){
                    if(rs.getString(1).equals(empLoginId) && rs.getString(2).equals(empPassword)){
                        String ename = rs.getString(3);
                        HttpSession session = request.getSession();
                        session.setAttribute("username", ename);
                        response.sendRedirect("Employee");
                    }else {
                        writer.println("Login Invalid");
                    }
                }

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
