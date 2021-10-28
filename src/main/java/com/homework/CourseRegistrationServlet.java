package com.homework;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CourseRegistrationServlet")
public class CourseRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String courseID = (String) request.getParameter("CourseID");
		String semester = (String) request.getParameter("Semester");
		System.out.println(courseID);
		System.out.println(semester);
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/NJIT", "root", "password");
			Statement stmt = con.createStatement();
			String sqlQuery = "select * from Courses where course_id = '"+courseID+"' and sem = '"+semester+"' ;";
			ResultSet rs = stmt.executeQuery(sqlQuery);
			StringBuilder builder = new StringBuilder();
			if(!rs.next()) {
				System.out.println("The course is not offered");
				builder.append("The course is not offered");
			}else {
				while (rs.next()) {
					System.out.println("You are registered in " + rs.getString("course_name") + " for " + semester);
					builder.append("You are registered in ");
					builder.append(rs.getString("course_name"));
					builder.append(" for ");
					builder.append(rs.getString("sem"));
				}
			}
			
			PrintWriter writer = response.getWriter();
			writer.write("<!DOCTYPE html>\n"
					+ "<html>\n"
					+ "<head>\n"
					+ "<meta charset=\"UTF-8\">\n"
					+ "<title>Course Details</title>\n"
					+ "</head>\n"
					+ "<body>\n"
					+ "	<h2>Course Details</h2>\n"
					+ "	<hr>\n"
					+ builder
					+ "</body>\n"
					+ "</html>");
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
