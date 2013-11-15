package iet.jxufe.cn.android;

import java.io.File;
import java.io.FileInputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpLoadFileActivity extends Activity {
	private Button upload, select;
	private EditText fileName, content;
	private boolean b=false;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload);
		upload = (Button) findViewById(R.id.upload);
		select = (Button) findViewById(R.id.select);
		fileName = (EditText) findViewById(R.id.file_path);
		content = (EditText) findViewById(R.id.content);
		select.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(UpLoadFileActivity.this,
						FileSelect.class);
				startActivityForResult(intent, 0);
			}
		});
		upload.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final Handler myHandler=new Handler(){					
					public void handleMessage(Message msg) {
						b=(Boolean)msg.obj;						
						if(b){							
							Toast.makeText(UpLoadFileActivity.this, "文件上传成功！", 1000).show();
						}else{
							Toast.makeText(UpLoadFileActivity.this, "文件上传失败", 1000).show();
						}
					}
				};
				new Thread(){
					public void run() {						
						UploadToServer uploadToServer = new UploadToServer();
						String fileNameStr=fileName.getText().toString();
						String file=fileNameStr.substring(fileNameStr.lastIndexOf("/")+1);
						System.out.println(file);
						String response = uploadToServer.doPost(file
								, content.getText().toString());		
						if ("true".equals(response)) {
							b=true;
						} else {
							b=false;
						}						
						Message message=new Message();
						message.obj=b;
						myHandler.sendMessage(message);
					}
				}.start();				
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String filePath = data.getStringExtra("filePath");
		fileName.setText(filePath);
		content.setText(readFile(filePath));
	}

	public String readFile(String fileName) {
		StringBuilder sb = new StringBuilder();
		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[64];
			int hasRead;
			while ((hasRead = fis.read(buffer)) != -1) {
				sb.append(new String(buffer, 0, hasRead));
			}
			if (fis != null) {
				fis.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sb.toString();
	}
}
