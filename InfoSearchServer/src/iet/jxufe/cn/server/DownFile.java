package iet.jxufe.cn.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownFile extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		String path = getServletContext().getRealPath("/").trim();
		String fileName = new String(req.getParameter("file")
				.getBytes("iso-8859-1"), "UTF-8");
		File file=new File(path,fileName);
		String result=fileName+"*";
		try{
			FileInputStream fis=new FileInputStream(file);			
			byte[] buffer=new byte[64];
			int hasRead=0;
			while((hasRead=fis.read(buffer))!=-1){
				result+=new String(buffer,0,hasRead);
			}
			if(fis!=null){
				fis.close();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		System.out.println(result);
		out.print(result);		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
