package iet.jxufe.cn.android;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class FileSelect extends Activity {
	ListView listFile;
	TextView showPath;
	File parentFile;// ��¼��ǰ�ĸ�Ŀ¼
	File[] files;// ��¼��ǰ·���������ļ����ļ�����
//	FileFilter fileFilter = new FileFilter() {// �ļ�������
//		public boolean accept(File pathname) {
//			if (pathname.isDirectory() || pathname.getName().endsWith(".txt"))// ֻ�г��ļ��к�.txt�ļ�
//				return true;
//			else
//				return false;
//		}
//	};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selector);
		listFile = (ListView) findViewById(R.id.listFile);// ��ȡ�г�ȫ���ļ���ListView
		showPath = (TextView) findViewById(R.id.showPath);
		init();// �ڴ洢�������һЩ�ļ�
		parentFile = new File("/mnt/sdcard/");// ��ȡϵͳ��SD����Ŀ¼
		files = parentFile.listFiles();
		System.out.println(files);
		inflateListFile();// ʹ�õ�ǰĿ¼�������ļ����listFile
		listFile.setOnItemClickListener(new OnItemClickListener() {// ΪlistFile���б���ĵ����¼��󶨼�����
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// �û������.txt�ļ�
				if (files[position].isFile()) {

					Intent intent = new Intent();
					intent.putExtra("filePath",
							files[position].getAbsolutePath());
					setResult(0, intent);
					finish();

				} else {// �û�������ļ���
					File[] tmp = files[position].listFiles();
					if (tmp == null || tmp.length == 0) {
						Toast.makeText(FileSelect.this, "����������Ҫ����ļ���",
								Toast.LENGTH_LONG).show();
					} else {// ��ȡ�û�����б����Ӧ���ļ�����Ϊ���ļ���
						parentFile = files[position];
						files = tmp;// ���浱ǰ�ĸ��ļ����������ļ�
						inflateListFile();// �ٴθ���listFile
					}
				}
			}
		});
		// ��ȡ��һ��Ŀ¼�İ�ť
		Button parentBn = (Button) findViewById(R.id.parentBn);
		parentBn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					if (!parentFile.getCanonicalPath().equals("/mnt/sdcard")) {
						// ��ȡ��һ��Ŀ¼
						parentFile = parentFile.getParentFile();
						// �г���ǰĿ¼�������ļ�
						files = parentFile.listFiles();
						// �ٴθ���listFile
						inflateListFile();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
	}
	
	private void inflateListFile() {// ���߷���������ļ��б�
		// ����һ��List���ϣ�List���ϵ�Ԫ����Map
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < files.length; i++) {
			Map<String, Object> item = new HashMap<String, Object>();			
			if (files[i].isDirectory()) {// �����ǰFile���ļ��У�ʹ��folderͼ�꣬����ʹ��fileͼ��
				item.put("icon", R.drawable.folder);
			} else {
				item.put("icon", R.drawable.file);
			}
			item.put("fileName", files[i].getName());
			// ���List��
			listItems.add(item);
		}
		// ����һ��SimpleAdapter
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
				R.layout.line, new String[] { "icon", "fileName" }, new int[] {
						R.id.icon, R.id.file_name });
		// ΪlistFile����Adapter
		listFile.setAdapter(simpleAdapter);
		try {
			showPath.setText(parentFile.getCanonicalPath());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void init() {// ��ģ�����ϵĴ洢�����г�ʼ�������������ļ�
		try {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				File sdCardDir = Environment.getExternalStorageDirectory();
				for (int i = 0; i < 5; i++) {
					File destFile = new File(sdCardDir.getCanonicalPath()
							+ File.separator + "gao" + i + ".txt");
					RandomAccessFile raf = new RandomAccessFile(destFile, "rw");
					//raf.seek(destFile.length());
					raf.write(("gao" + i + ".txt,Hello!\r\n").getBytes());
					raf.close();

				}//�����ļ�
//				for (int i = 0; i < 5; i++) {
//					File destFile = new File(sdCardDir.getCanonicalPath()
//							+ File.separator + "cheng" + i);
//					if (!destFile.exists()) {
//						destFile.mkdir();
//					}
//				}//�����ļ���
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
