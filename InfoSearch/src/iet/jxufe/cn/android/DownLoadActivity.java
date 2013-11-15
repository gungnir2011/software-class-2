package iet.jxufe.cn.android;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class DownLoadActivity extends Activity {
	ListView listFile;
	private String result;
	private boolean b=false;
	private String[] files;
	private String[] content;
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_down);
		listFile = (ListView) findViewById(R.id.listFile);
		final Handler myHandler=new Handler(){					
			public void handleMessage(Message msg) {
				if(msg.what==0x11){
					result=(String)msg.obj;					
					if("noFile".equals(result)){
						Toast.makeText(DownLoadActivity.this, "不存在可下载的文件", 1000).show();					
					}else{						
						files=result.split("\\*");						
						List<Map<String,Object>> items=new ArrayList<Map<String,Object>>();
						for(int i=0;i<files.length;i++){
							Map<String,Object> item=new HashMap<String,Object>();
							item.put("icon", R.drawable.file);
							item.put("name",files[i]);
							items.add(item);
						}
						SimpleAdapter simpleAdapter = new SimpleAdapter(DownLoadActivity.this, items,
								R.layout.line, new String[] { "icon", "name" }, new int[] {
										R.id.icon, R.id.file_name });
						listFile.setAdapter(simpleAdapter);
					}
				}else if(msg.what==0x12){
					result=(String)msg.obj;
					System.out.println(result);
					content=result.split("\\*");
					Builder myBuilder = new Builder(DownLoadActivity.this);
					final EditText fileName = new EditText(DownLoadActivity.this);
					fileName.setText(content[0]);
					myBuilder.setTitle("请输入保存的文件名");
					myBuilder.setIcon(R.drawable.ic_launcher);
					myBuilder.setView(fileName);
					myBuilder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int which) {
							try{
								File sdCardDir = Environment.getExternalStorageDirectory();
								File destFile = new File(sdCardDir.getCanonicalPath()
										+ File.separator + "download");
								if (!destFile.exists()) {
									destFile.mkdir();									
								}
								File saveFile = new File(destFile.getPath(),fileName.getText().toString());
								FileOutputStream fos=new FileOutputStream(saveFile);
								fos.write(content[1].getBytes());
								if(fos!=null){
									fos.close();
								}
							}catch(Exception ex){
								ex.printStackTrace();
							}							
						}
					});
					myBuilder.show();
				}
				
			}
		};
		new Thread(){
			public void run() {						
				DownloadToServer downloadToServer = new DownloadToServer();
				result=downloadToServer.doGet();								
				Message message=new Message();
				message.obj=result;
				message.what=0x11;
				myHandler.sendMessage(message);
			}
		}.start();
		
		listFile.setOnItemClickListener(new OnItemClickListener() {// 为listFile的列表项的单击事件绑定监听器
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final String file=files[position];
				new Thread(){
					public void run() {						
						DownloadToServer downloadToServer = new DownloadToServer();
						result=downloadToServer.doGet(file);								
						Message message=new Message();
						message.obj=result;
						message.what=0x12;
						myHandler.sendMessage(message);
					}
				}.start();				
			}
		});
	}
}
