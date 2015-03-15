package com.oc.mobilsecurity;

import java.util.ArrayList;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class RegisterActivity extends Activity {

	private Button back;
	private Button Ok;

	private TextView userName;
	private TextView passwd;
	private TextView realName;
	private TextView id;
	private TextView mobile;
	private TextView mail;

	private String uname;
	private String upasswd;
	private String urname;
	private String uid;
	private String umobile;
	private String umail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_register);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titlebar_ok);

		userName = (TextView) findViewById(R.id.registerUserNameEditText);
		passwd = (TextView) findViewById(R.id.registerPasswordEditText);
		realName = (TextView) findViewById(R.id.registerRealNameText);
		id = (TextView) findViewById(R.id.registerIDText);
		mobile = (TextView) findViewById(R.id.registerMobileText);
		mail = (TextView) findViewById(R.id.registerMailText);

		back = (Button) findViewById(R.id.TitleBarBack);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = getIntent();
				setResult(RESULT_CANCELED, intent);
				finish();
			}
		});

		Ok = (Button) findViewById(R.id.TitleBarOk);
		Ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				attemptRegister();
			}
		});
	}

	private void attemptRegister() {

		userName.setError(null);
		passwd.setError(null);
		realName.setError(null);
		id.setError(null);
		mobile.setError(null);
		mail.setError(null);

		boolean cancel = false;
		View focusView = null;

		uname = userName.getText().toString();
		upasswd = passwd.getText().toString();
		uid = id.getText().toString();
		urname = realName.getText().toString();
		umobile = mobile.getText().toString();
		umail = mail.getText().toString();
		
		// Check for a valid mail.
		Pattern pattern = Pattern
				.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
		if (TextUtils.isEmpty(umail)) {
			mail.setError(getString(R.string.error_field_required));
			focusView = mail;
			cancel = true;
		} else if (!pattern.matcher(umail).matches()) {
			mail.setError(getString(R.string.error_invalid_mail));
			focusView = mail;
			cancel = true;
		}

		// Check for a valid mobile.
		pattern = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		if (TextUtils.isEmpty(umobile)) {
			mobile.setError(getString(R.string.error_field_required));
			focusView = mobile;
			cancel = true;
		} else if (!pattern.matcher(umobile).matches()) {
			mobile.setError(getString(R.string.error_invalid_mobile));
			focusView = mobile;
			cancel = true;
		}

		// Check for a valid id.
		if (TextUtils.isEmpty(uid)) {
			id.setError(getString(R.string.error_field_required));
			focusView = id;
			cancel = true;
		} else if (uid.length() != PreferenceValue.IDCARDLENGTH) {
			id.setError(getString(R.string.error_invalid_id));
			focusView = id;
			cancel = true;
		}

		// Check for a valid real name.
		if (TextUtils.isEmpty(urname)) {
			realName.setError(getString(R.string.error_field_required));
			focusView = realName;
			cancel = true;
		} else if (urname.length() < PreferenceValue.NAMESHORTEST) {
			realName.setError(getString(R.string.error_invalid_name));
			focusView = realName;
			cancel = true;
		}

		// Check for a valid password.
		if (TextUtils.isEmpty(upasswd)) {
			passwd.setError(getString(R.string.error_field_required));
			focusView = passwd;
			cancel = true;
		} else if (upasswd.length() < PreferenceValue.PASSWORDSHORTEST) {
			passwd.setError(getString(R.string.error_invalid_password));
			focusView = passwd;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(uname)) {
			userName.setError(getString(R.string.error_field_required));
			focusView = userName;
			cancel = true;
		} else if (uname.length() < PreferenceValue.USERNAMESHORTEST) {
			userName.setError(getString(R.string.error_invalid_username));
			focusView = userName;
			cancel = true;
		}

		if (cancel) {
			focusView.requestFocus();
		} else {
			ArrayList<String> list = new ArrayList<String>();
			list.add(uname);
			list.add(upasswd);
			list.add(uid);
			list.add(urname);
			list.add(umobile);
			list.add(umail);

			Intent intent = getIntent();
			Bundle bundle = new Bundle();
			bundle.putStringArrayList("registerInfo", list);
			intent.putExtras(bundle);
			setResult(RESULT_OK, intent);

			finish();
		}
	}
}
