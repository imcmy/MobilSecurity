package com.oc.mobilsecurity.personinfo;

import java.util.regex.Pattern;

import com.oc.mobilsecurity.PreferenceValue;
import com.oc.mobilsecurity.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class PersonInfoEdit extends Activity {

	private TextView edit;
	private Button back;
	private Button Ok;

	private int type = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.personinfo_edit);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titlebar_ok);

		edit = (TextView) findViewById(R.id.Edit);
		Bundle bundle = getIntent().getExtras();
		edit.setHint(bundle.getString("hint"));
		type = bundle.getInt("type");

		Log.v("TAG", type + "");

		switch (type) {
		case PreferenceValue.MODIFYTYPEUSERNAME:
			edit.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
			edit.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
					PreferenceValue.USERNAMELONGEST) });
			break;
		case PreferenceValue.MODIFYTYPEPASSWORD:
			edit.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_PASSWORD);
			edit.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
					PreferenceValue.PASSWORDLONGEST) });
			break;
		case PreferenceValue.MODIFYTYPEEMAIL:
			edit.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
			edit.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
					PreferenceValue.EMAILLONGEST) });
			break;
		}

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
				attemptFinish();
			}
		});
	}

	private void attemptFinish() {

		edit.setError(null);
		String info = edit.getText().toString();

		boolean cancel = false;
		View focusView = null;

		switch (type) {
		case PreferenceValue.MODIFYTYPEUSERNAME:
			if (TextUtils.isEmpty(info)) {
				edit.setError(getString(R.string.error_field_required));
				focusView = edit;
				cancel = true;
			} else if (info.length() < PreferenceValue.USERNAMESHORTEST) {
				edit.setError(getString(R.string.error_invalid_username));
				focusView = edit;
				cancel = true;
			}
			break;
		case PreferenceValue.MODIFYTYPEPASSWORD:
			if (TextUtils.isEmpty(info)) {
				edit.setError(getString(R.string.error_field_required));
				focusView = edit;
				cancel = true;
			} else if (info.length() < PreferenceValue.PASSWORDSHORTEST) {
				edit.setError(getString(R.string.error_invalid_password));
				focusView = edit;
				cancel = true;
			}
			break;
		case PreferenceValue.MODIFYTYPEEMAIL:
			Pattern pattern = Pattern
					.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
			if (TextUtils.isEmpty(info)) {
				Log.v("TAG", "mail1");
				edit.setError(getString(R.string.error_field_required));
				focusView = edit;
				cancel = true;
			} else if (!pattern.matcher(info).matches()) {
				Log.v("TAG", "mail2");
				edit.setError(getString(R.string.error_invalid_mail));
				focusView = edit;
				cancel = true;
			}
			break;
		}

		if (cancel) {
			focusView.requestFocus();
		} else {
			Intent intent = getIntent();
			Bundle bundle = new Bundle();
			bundle.putString("info", info);
			intent.putExtras(bundle);
			setResult(RESULT_OK, intent);
			finish();
		}
	}
}
