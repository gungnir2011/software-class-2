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
	File parentFile;// 记录当前的父目录
	File[] files;// 记录当前路径下所有文件的文件数组
//	FileFilter fileFilter = new FileFilter() {// 文件过滤器
//		public boolean accept(File pathname) {
//			if (pathname.isDirectory() || pathname.getName().endsWith(".txt"))// 只列出文件夹和.txt文件
//				return true;
//			else
//				return false;
//		}
//	};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selector);
		listFile = (ListView) findViewById(R.id.listFile);// 获取列出全部文件的ListView
		showPath = (TextView) findViewById(R.id.showPath);
		init();// 在存储卡中添加一些文件
		parentFile = new File("/mnt/sdcard/");// 获取系统的SD卡的目录
		files = parentFile.listFiles();
		System.out.println(files);
		inflateListFile();// 使用当前目录下所有文件填充listFile
		listFile.setOnItemClickListener(new OnItemClickListener() {// 为listFile的列表项的单击事件绑定监听器
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 用户点击了.txt文件
				if (files[position].isFile()) {

					Intent intent = new Intent();
					intent.putExtra("filePath",
							files[position].getAbsolutePath());
					setResult(0, intent);
					finish();

				} else {// 用户点击了文件夹
					File[] tmp = files[position].listFiles();
					if (tmp == null || tmp.length == 0) {
						Toast.makeText(FileSelect.this, "不包含符合要求的文件！",
								Toast.LENGTH_LONG).show();
					} else {// 获取用户点击列表项对应的文件夹作为父文件夹
						parentFile = files[position];
						files = tmp;// 保存当前的父文件夹下所有文件
						inflateListFile();// 再次更新listFile
					}
				}
			}
		});
		// 获取上一级目录的按钮
		Button parentBn = (Button) findViewById(R.id.parentBn);
		parentBn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					if (!parentFile.getCanonicalPath().equals("/mnt/sdcard")) {
						// 获取上一级目录
						parentFile = parentFile.getParentFile();
						// 列出当前目录下所有文件
						files = parentFile.listFiles();
						// 再次更新listFile
						inflateListFile();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
	}
	
	private void inflateListFile() {// 工具方法，填充文件列表
		// 创建一个List集合，List集合的元素是Map
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < files.length; i++) {
			Map<String, Object> item = new HashMap<String, Object>();			
			if (files[i].isDirectory()) {// 如果当前File是文件夹，使用folder图标，否则使用file图标
				item.put("icon", R.drawable.folder);
			} else {
				item.put("icon", R.drawable.file);
			}
			item.put("fileName", files[i].getName());
			// 添加List项
			listItems.add(item);
		}
		// 创建一个SimpleAdapter
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
				R.layout.line, new String[] { "icon", "fileName" }, new int[] {
						R.id.icon, R.id.file_name });
		// 为listFile设置Adapter
		listFile.setAdapter(simpleAdapter);
		try {
			showPath.setText(parentFile.getCanonicalPath());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void init() {// 对模拟器上的存储卡进行初始化，创建几个文件
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

				}//创建文件
//				for (int i = 0; i < 5; i++) {
//					File destFile = new File(sdCardDir.getCanonicalPath()
//							+ File.separator + "cheng" + i);
//					if (!destFile.exists()) {
//						destFile.mkdir();
//					}
//				}//创建文件夹
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
