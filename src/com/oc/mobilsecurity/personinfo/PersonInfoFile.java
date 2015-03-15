package com.oc.mobilsecurity.personinfo;

import com.oc.mobilsecurity.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class PersonInfoFile extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.personinfo_file);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titlebar_back);

		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		try {
			startActivityForResult(Intent.createChooser(intent, "请选择文件"), 1);
		} catch (android.content.ActivityNotFoundException e) {
			Toast.makeText(this, "请安装文件管理器", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();
			String path = uri.getPath();
			TextView test = (TextView) findViewById(R.id.testFile);
			test.setText(path);
			
			//TODO: calculate MD5
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
