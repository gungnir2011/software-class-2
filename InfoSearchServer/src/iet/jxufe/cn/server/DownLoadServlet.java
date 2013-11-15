package iet.jxufe.cn.server;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownLoadServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();		
		String path = getServletContext().getRealPath("/").trim();
		String result="";		
		File file=new File(path);		
		try{
			File[] files=file.listFiles(fileFiler);
			if(files==null||files.length==0){
				result="noFile";
			}else{
				for(int i=0;i<files.length;i++){
					result+=files[i].getName()+"*";
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		out.println(result);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
	FileFilter fileFiler=new FileFilter() {		
		public boolean accept(File pathname) {
			if (pathname.isFile())//只列出文件
				return true;
			else
				return false;
		}
	};
}

	
