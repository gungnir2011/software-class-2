package iet.jxufe.cn.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QueryServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("gbk");
		resp.setContentType("text/html;charset=GBK");
		PrintWriter pw = resp.getWriter();
		String book = new String(req.getParameter("book")
				.getBytes("iso-8859-1"), "utf-8");
		String grade = new String(req.getParameter("grade").getBytes(
				"iso-8859-1"), "utf-8");
		DBHandler dbh = new DBHandler();
		if (book != null && !"".equals(book)) {// ≤È—ØÕº È
			System.out.println("book=" + book);
			pw.println(dbh.query(
				"select * from qanda where question like ?",
				new String[] { "%" + book + "%" }));
		} else if (grade != null && !"".equals(grade)) {
			System.out.println("grade=" + grade);
			pw.println(dbh.query(
				"select * from grade where num like ? or course_name like ?",
				new String[] { "%" + grade + "%", "%" + grade + "%" }));
		}
	}
}
