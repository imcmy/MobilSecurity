package com.oc.mobilsecurity;

import com.oc.mobilsecurity.data.UserData;
import com.oc.mobilsecurity.decorator.CustomButton;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class ShowActivity extends Activity {
	
	private Button back;
	private CustomButton nameButton;
	private CustomButton passwdButton;
	
	private UserData userData = UserData.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_show);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titlebar_back);
		
		nameButton = (CustomButton) findViewById(R.id.showName);
		nameButton.setTitleText("”√ªß√˚");
		nameButton.setContentText(userData.getUserInformation().getUAcot());

		passwdButton = (CustomButton) findViewById(R.id.showPasswd);
		passwdButton.setTitleText("√‹¬Î");
		passwdButton.setContentText(userData.getUserInformation().getUPswd());
		
		back = (Button) findViewById(R.id.TitleBarBack);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(),
						LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}
