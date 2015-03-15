package com.oc.mobilsecurity.application;

import java.util.ArrayList;
import java.util.HashMap;

import com.oc.mobilsecurity.R;
import com.oc.mobilsecurity.data.UserData;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ApplicationLog extends Activity {

	private Button back;
	private ListView list;
	private String[] titles = UserData.getInstance().getApplicationData().titles;
	private String[] texts = UserData.getInstance().getApplicationData().texts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.application_log);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titlebar_back);

		list = (ListView) findViewById(R.id.logList);
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < titles.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("actionTitle", titles[i]);
			map.put("actionTime", texts[i]);
			listItem.add(map);
		}
		// 生成适配器的Item和动态数组对应的元素
		SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem,
				R.layout.list_log, new String[] { "actionTitle",
						"actionTime" }, new int[] { R.id.actionTitle,
						R.id.actionTime });
		list.setAdapter(listItemAdapter);

		back = (Button) findViewById(R.id.TitleBarBack);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
