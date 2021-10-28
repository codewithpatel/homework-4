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

@WebServlet("/CourseSearchServlet")
public class CourseSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String semester = (String) request.getParameter("semester");
		System.out.println(semester);
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/NJIT", "root", "password");
			Statement stmt = con.createStatement();
			String sql = "select * from Courses where sem = '"+semester+"' ";
			ResultSet rs = stmt.executeQuery(sql);
			StringBuilder builder = new StringBuilder();
			boolean hasValue = false;
			while (rs.next()) {
				hasValue = true;
				System.out.println(rs.getString(1) + "		" + rs.getString(2) + "		" + rs.getString(3));
				builder.append(
						"<p>" + rs.getString(1) + "		" + rs.getString(2) + "		" + rs.getString(3) + "<p>");

			}
			
			if(!hasValue) {
				builder.append("<p style='color: red;'>No Course Found</p>");
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
