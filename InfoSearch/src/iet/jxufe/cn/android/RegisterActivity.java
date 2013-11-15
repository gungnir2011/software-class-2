package iet.jxufe.cn.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	private EditText name, psd, repsd;
	private Button reset, register;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		name = (EditText) findViewById(R.id.name);
		psd = (EditText) findViewById(R.id.psd);
		repsd = (EditText) findViewById(R.id.repsd);
		reset = (Button) findViewById(R.id.reset);
		register = (Button) findViewById(R.id.register);
		reset.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				name.setText("");
				psd.setText("");
				repsd.setText("");
			}
		});
		register.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String checkResult = checkInfo();
				if (checkResult != null) {
					Builder builder = new AlertDialog.Builder(
							RegisterActivity.this);
					builder.setTitle("出错提示");
					builder.setMessage(checkResult);
					builder.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									psd.setText("");
									repsd.setText("");
								}
							});
					builder.create().show();
				} else {
					final Handler myHandler = new Handler() {
						public void handleMessage(Message msg) {
							boolean b = (Boolean) msg.obj;
							System.out.println("b=" + b);
							if (b) {
								Intent intent = new Intent(
										RegisterActivity.this,
										LoginActivity.class);
								startActivity(intent);
								Toast.makeText(RegisterActivity.this, "注册成功",
										1000).show();
								finish();
							} else {
								Toast.makeText(RegisterActivity.this, "注册失败",
										1000).show();
							}
						}
					};
					new Thread() {
						public void run() {
							boolean b = false;
							RegisterToServer registerToServer = new RegisterToServer();
							String response = registerToServer.doGet(name
									.getText().toString(), psd.getText()
									.toString());
							if ("true".equals(response)) {
								b = true;
							} else {
								b = false;
							}
							Message message = new Message();
							message.obj = b;
							myHandler.sendMessage(message);
						}
					}.start();
				}
			}
		});
	}

	public String checkInfo() {
		if (name.getText().toString() == null
				|| name.getText().toString().equals("")) {
			return "用户名不能为空";
		}
		if (psd.getText().toString().trim().length() < 3
				|| psd.getText().toString().trim().length() > 15) {
			return "密码位数应该3~15之间";
		}
		if (!psd.getText().toString().equals(repsd.getText().toString())) {
			return "两次输入的密码不一致";
		}
		return null;
	}
}
