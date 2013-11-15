package iet.jxufe.cn.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button fileUpload, download, infoQuery;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fileUpload = (Button) findViewById(R.id.fileUpload);
		download = (Button) findViewById(R.id.downLoad);
		infoQuery = (Button) findViewById(R.id.infoQuery);
		infoQuery.setOnClickListener(myListener);
		download.setOnClickListener(myListener);
		fileUpload.setOnClickListener(myListener);
	}

	private OnClickListener myListener = new OnClickListener() {
		public void onClick(View v) {
			Intent intent=null;
			switch (v.getId()) {
			case R.id.fileUpload:
				intent=new Intent(MainActivity.this,UpLoadFileActivity.class);
				startActivity(intent);	
				break;
			case R.id.downLoad:
				intent=new Intent(MainActivity.this,DownLoadActivity.class);
				startActivity(intent);
				break;
			case R.id.infoQuery:
				intent=new Intent(MainActivity.this,InfoQueryActivity.class);
				startActivity(intent);
				break;

			default:
				break;
			}
		}
	};
}
