package iet.jxufe.cn.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.database.CursorJoiner.Result;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class InfoQueryActivity extends Activity {
	private ListView result;
	private Button bookQuery, gradeQuery;
	private EditText keyword;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_query);
		result = (ListView) findViewById(R.id.result);
		bookQuery = (Button) findViewById(R.id.search);
		keyword = (EditText) findViewById(R.id.key);
		bookQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final Handler myHandler = new Handler() {
					public void handleMessage(Message msg) {
						String resultStr = (String) msg.obj;
						System.out.println("resultStr=" + resultStr);
						String[] items = resultStr.split("\\*");
						int j = items.length / 3;
						ArrayList<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();
						for (int m = 0; m < j; m++) {
							Map<String, Object> item = new HashMap<String, Object>();
							item.put("ques_id", items[m * 3]);
							item.put("question", items[m * 3 + 1]);
							item.put("answer", items[m * 3 + 2]);
			
							itemList.add(item);
						}
						SimpleAdapter adapter = new SimpleAdapter(
								InfoQueryActivity.this, itemList,
								R.layout.activity_book_list, new String[] {
										"ques_id", "question", "answer"},
								new int[] { R.id.ques_id, R.id.question,
										R.id.answer });

						result.setAdapter(adapter);
					}
				};
				new Thread() {
					public void run() {
						InfoToServer infoToServer = new InfoToServer();
						String response = infoToServer.doGet(keyword.getText()
								.toString(), "");
						Message message = new Message();
						message.obj = response;
						myHandler.sendMessage(message);
					}
				}.start();
			}
		});
		/*gradeQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final Handler myHandler = new Handler() {
					public void handleMessage(Message msg) {
						String resultStr = (String) msg.obj;
						String[] items = resultStr.split("\\*");
						int j = items.length / 5;
						ArrayList<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();
						for (int m = 0; m < j; m++) {
							Map<String, Object> item = new HashMap<String, Object>();
							item.put("grade_id", items[m * 5]);
							item.put("num", items[m * 5 + 1]);
							item.put("course_name", items[m * 5 + 2]);
							item.put("credit", items[m * 5 + 3]);
							item.put("score", items[m * 5 + 4]);
							itemList.add(item);
						}
						SimpleAdapter adapter = new SimpleAdapter(
								InfoQueryActivity.this, itemList,
								R.layout.activity_grade_list, new String[] {
										"grade_id", "num", "course_name",
										"credit", "score" },
								new int[] { R.id.grade_id,R.id.num,R.id.course_name,R.id.credit,R.id.score});

						result.setAdapter(adapter);
					}
				};
				new Thread() {
					public void run() {
						InfoToServer infoToServer = new InfoToServer();
						String response = infoToServer.doGet("",keyword.getText()
								.toString());
						Message message = new Message();
						message.obj = response;
						myHandler.sendMessage(message);
					}
				}.start();
			}
		});*/
	}
}
