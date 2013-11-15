package iet.jxufe.cn.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class DownloadToServer {
	String urlAddress = "http://192.168.1.113:8080/login/DownLoadServlet";// �������˵�ַ
	public String doGet() {// ��Get��ʽ��������				
		HttpGet httpGet = new HttpGet(urlAddress);
		HttpClient hc = new DefaultHttpClient();		
		try {
			HttpResponse ht = hc.execute(httpGet); // ���ͻ���һ����Ӧ
			System.out.println("ht=" + ht);
			HttpEntity he = ht.getEntity(); // ����
			InputStream is = he.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"GBK"));
			String response = "";
			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				response = response + readLine;
			}
			is.close();
			return response;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return "exception";
		} catch (IOException e) {
			e.printStackTrace();
			return "exception";
		}
	}
	
	public String doGet(String file) {// ��Get��ʽ��������
		String url = "http://192.168.1.113:8080/login/DownFile";
		String getUrl = url+ "?file=" + file;// ƴ�ӵ�ַ		
		HttpGet httpGet = new HttpGet(getUrl);
		HttpClient hc = new DefaultHttpClient();		
		try {
			HttpResponse ht = hc.execute(httpGet); // ���ͻ���һ����Ӧ			
			HttpEntity he = ht.getEntity(); // ����
			InputStream is = he.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,"GBK"));
			String response = "";
			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				response = response + readLine;
			}
			is.close();
			return response;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return "exception";
		} catch (IOException e) {
			e.printStackTrace();
			return "exception";
		}
	}
}
