package com.Registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/*
 * Servlet implementation class RegistrationServlet
 * 
 */
@WebServlet("/register")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	//	PrintWriter out=response.getWriter();  // checking server is working and checking the details
	//	out.print("working");
		
	    String uname = request.getParameter("name");
	    String uemail = request.getParameter("email");
	    String upwd = request.getParameter("pass");
	    String umobile = request.getParameter("contact");
	    RequestDispatcher dispatcher = null;
	    Connection con = null;

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/registerlogin?useSSL=false", "root", "K0A8v1i9@");

	        PreparedStatement pst = con.prepareStatement("INSERT INTO login(uname, uemail,upwd, umobile) VALUES (?, ?, ?, ?)");
	        pst.setString(1, uname);
	        pst.setString(2, uemail);
	        pst.setString(3, upwd);
	        pst.setString(4, umobile);

	        int rowCount = pst.executeUpdate();

	        dispatcher = request.getRequestDispatcher("registration.jsp");
	        if (rowCount > 0) {
	            request.setAttribute("status", "success");
	        } else {
	            request.setAttribute("status", "failed");
	        }
	        dispatcher.forward(request, response);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	        	if(con !=null) {
	                con.close();
	        	} 
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
    }
}