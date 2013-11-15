package iet.jxufe.cn.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UploadServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		String file = new String(req.getParameter("file")
				.getBytes("iso-8859-1"), "UTF-8");
		String content = new String(req.getParameter("content").getBytes(
				"iso-8859-1"), "UTF-8");
		String path= getServletContext().getRealPath("/").trim();
		System.out.println(path);
		out.println(createFile(file,content,path));
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);	
	}
	public boolean createFile(String file,String content,String path){
		boolean flag=false;
		try{
			File newFile=new File(path,file);
			System.out.println("file="+file+"content="+content+"path="+path);
			if(!newFile.exists()){
				newFile.createNewFile();
			}
			FileOutputStream fos=new FileOutputStream(newFile);
			fos.write(content.getBytes());
			flag=true;
			if(fos!=null){
				fos.close();
			}			
		}catch(Exception ex){
			ex.printStackTrace();
			flag=false;
		}
		
		return flag;
		
	}
}
