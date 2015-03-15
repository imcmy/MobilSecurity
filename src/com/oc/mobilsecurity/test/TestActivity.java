package com.oc.mobilsecurity.test;

import com.oc.mobilsecurity.R;
import com.oc.mobilsecurity.R.layout;
import com.oc.mobilsecurity.Run;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestActivity extends Activity {
	
	Run run = new Run(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setText("bindMobil");
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setText("bindPC");
		Button button3 = (Button) findViewById(R.id.button3);
		Button button4 = (Button) findViewById(R.id.button4);
		Button button5 = (Button) findViewById(R.id.button5);
		Button button6 = (Button) findViewById(R.id.button6);
		Button button7 = (Button) findViewById(R.id.button7);
		Button button8 = (Button) findViewById(R.id.button8);
		Button button9 = (Button) findViewById(R.id.button9);
		
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//run.bindMobil();
			}
		});
	}
}
