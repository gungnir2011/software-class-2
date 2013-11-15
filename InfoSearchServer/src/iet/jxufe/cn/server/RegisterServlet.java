package iet.jxufe.cn.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String name = request.getParameter("username");
		String psd = request.getParameter("password");
		DBHandler dbh = new DBHandler();		
		boolean b = dbh.insert("insert into user(user_name,user_psd)values(?,?)",
				new String[] { name, psd });
		System.out.println(name+":"+" "+psd+":");
		out.print(b);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
