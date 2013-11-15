package iet.jxufe.cn.android;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private EditText name,password;
	private Button login,reset,register;	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = (EditText) findViewById(R.id.name);
		password = (EditText) findViewById(R.id.psd);
		login = (Button) findViewById(R.id.login);
		reset = (Button) findViewById(R.id.reset);
		register=(Button)findViewById(R.id.register);
		login.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final Handler myHandler=new Handler(){					
					public void handleMessage(Message msg) {
						boolean b=(Boolean)msg.obj;
						System.out.println("b="+b);
						//boolean b=checkUser(name.getText().toString(),password.getText().toString());
						if(b){
							Intent intent=new Intent(LoginActivity.this,InfoQueryActivity.class);
							Bundle bundle=new Bundle();
							bundle.putString("name", name.getText().toString());
							bundle.putString("psd", password.getText().toString());
							intent.putExtras(bundle);
							startActivity(intent);
							finish();
							Toast.makeText(LoginActivity.this, "µÇÂ¼³É¹¦", 1000).show();
						}else{
							Toast.makeText(LoginActivity.this, "µÇÂ¼Ê§°Ü", 1000).show();
						}
					}
				};
				new Thread(){
					public void run() {
						boolean b=checkUser(name.getText().toString(),password.getText().toString());						
						Message message=new Message();
						message.obj=b;
						myHandler.sendMessage(message);
					}
				}.start();				
			}
		});
		reset.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				name.setText("");
				password.setText("");
			}
		});
		register.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	public boolean checkUser(String name, String psd) {		
		LoginToServer loginToServer = new LoginToServer();
		String response = loginToServer.doGet(name, psd);		
		if ("true".equals(response)) {
			return true;
		} else {
			return false;
		}
	}
}
