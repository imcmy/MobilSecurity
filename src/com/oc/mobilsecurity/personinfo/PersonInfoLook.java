package com.oc.mobilsecurity.personinfo;

import com.oc.mobilsecurity.*;
import com.oc.mobilsecurity.data.UserData;
import com.oc.mobilsecurity.decorator.CustomButton;
import com.oc.mobilsecurity.decorator.CustomButtonArrow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PersonInfoLook extends Activity {

	private Button back;

	private View mEditFormView;
	private View mEditStatusView;
	private TextView mEditStatusMessageView;

	private ModifyTask mModifyTask = null;
	private int type = 0;

	// TODO: Delete
	private String username;
	private String passwd;
	private String email;
	private String realname;
	private String ID;
	private String mobile;

	private Button userHeaderButton;
	private CustomButtonArrow userNameButton;
	private CustomButtonArrow emailButton;
	private Button passwordButton;
	private CustomButton realNameButton;
	private CustomButton IDButton;
	private CustomButton mobileButton;

	private Run run = new Run(this);
	private UserData userData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.personinfo_look);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titlebar_back);

		userData = UserData.getInstance();
		username = userData.getUserInformation().getUAcot();
		passwd = userData.getUserInformation().getUPswd();
		email = userData.getUserInformation().getUMail();
		realname = userData.getUserInformation().getUName();
		ID = userData.getUserInformation().getUCard();
		mobile = userData.getUserInformation().getUMobi();

		final Bundle bundle = new Bundle();
		final Intent intent = new Intent(PersonInfoLook.this,
				PersonInfoEdit.class);

		mEditFormView = findViewById(R.id.edit_form);
		mEditStatusView = findViewById(R.id.edit_status);
		mEditStatusMessageView = (TextView) findViewById(R.id.edit_status_message);

		userHeaderButton = (Button) findViewById(R.id.personinfoHeader);
		userHeaderButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bundle.putString("hint", "******");
				intent.putExtras(bundle);
				startActivityForResult(intent, PreferenceValue.MODIFYTYPEHEADER);
			}
		});

		userNameButton = (CustomButtonArrow) findViewById(R.id.personinfoUsername);
		userNameButton.setTitleText(getResources().getString(
				R.string.editUsername));
		userNameButton.setContentText(username);
		userNameButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bundle.putString("hint", username);
				bundle.putInt("type", PreferenceValue.MODIFYTYPEUSERNAME);
				intent.putExtras(bundle);
				startActivityForResult(intent,
						PreferenceValue.MODIFYTYPEUSERNAME);
			}
		});

		emailButton = (CustomButtonArrow) findViewById(R.id.personinfoEmail);
		emailButton.setTitleText(getResources().getString(R.string.editEmail));
		emailButton.setContentText(email);
		emailButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bundle.putString("hint", email);
				bundle.putInt("type", PreferenceValue.MODIFYTYPEEMAIL);
				intent.putExtras(bundle);
				startActivityForResult(intent, PreferenceValue.MODIFYTYPEEMAIL);
			}
		});

		passwordButton = (Button) findViewById(R.id.personinfoPassword);
		passwordButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				bundle.putString("hint", "");
				bundle.putInt("type", PreferenceValue.MODIFYTYPEPASSWORD);
				intent.putExtras(bundle);
				startActivityForResult(intent,
						PreferenceValue.MODIFYTYPEPASSWORD);
			}
		});

		realNameButton = (CustomButton) findViewById(R.id.personinfoName);
		realNameButton
				.setTitleText(getResources().getString(R.string.RealName));
		realNameButton.setContentText(realname);

		IDButton = (CustomButton) findViewById(R.id.personinfoID);
		IDButton.setTitleText(getResources().getString(R.string.ID));
		IDButton.setContentText(ID);

		mobileButton = (CustomButton) findViewById(R.id.personinfoMobile);
		mobileButton.setTitleText(getResources().getString(R.string.Mobile));
		mobileButton.setContentText(mobile);

		back = (Button) findViewById(R.id.TitleBarBack);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mEditStatusView.setVisibility(View.VISIBLE);
			mEditStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mEditStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mEditFormView.setVisibility(View.VISIBLE);
			mEditFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mEditFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mEditStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mEditFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Deal with the result from login, find back and register.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case PreferenceValue.MODIFYTYPEUSERNAME:
			switch (resultCode) {
			case RESULT_OK:
				type = PreferenceValue.MODIFYTYPEUSERNAME;
				Bundle bundle = data.getExtras();
				username = bundle.getString("info");
				if (mModifyTask != null) {
					return;
				}
				mEditStatusMessageView.setText(R.string.edit_progress);
				showProgress(true);
				mModifyTask = new ModifyTask();
				mModifyTask.execute((Void) null);
				break;
			case RESULT_CANCELED:
				Toast.makeText(getBaseContext(), "取消修改", Toast.LENGTH_SHORT)
						.show();
				break;
			}
			break;
		case PreferenceValue.MODIFYTYPEPASSWORD:
			switch (resultCode) {
			case RESULT_OK:
				type = PreferenceValue.MODIFYTYPEPASSWORD;
				Bundle bundle = data.getExtras();
				passwd = bundle.getString("info");
				if (mModifyTask != null) {
					return;
				}
				mEditStatusMessageView.setText(R.string.edit_progress);
				showProgress(true);
				mModifyTask = new ModifyTask();
				mModifyTask.execute((Void) null);
				break;
			case RESULT_CANCELED:
				Toast.makeText(getBaseContext(), "取消修改", Toast.LENGTH_SHORT)
						.show();
				break;
			}
			break;
		case PreferenceValue.MODIFYTYPEEMAIL:
			switch (resultCode) {
			case RESULT_OK:
				type = PreferenceValue.MODIFYTYPEEMAIL;
				Bundle bundle = data.getExtras();
				email = bundle.getString("info");
				if (mModifyTask != null) {
					return;
				}
				mEditStatusMessageView.setText(R.string.edit_progress);
				showProgress(true);
				mModifyTask = new ModifyTask();
				mModifyTask.execute((Void) null);
				break;
			case RESULT_CANCELED:
				Toast.makeText(getBaseContext(), "取消修改", Toast.LENGTH_SHORT)
						.show();
				break;
			}
			break;
		}
	}

	private class ModifyTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			boolean result = false;
			switch (type) {
			case PreferenceValue.MODIFYTYPEUSERNAME:
				result = run.changeAccount(username);
				break;
			case PreferenceValue.MODIFYTYPEPASSWORD:
				result = run.changePasswd(passwd);
				break;
			case PreferenceValue.MODIFYTYPEEMAIL:
				result = run.changeMail(email);
				break;
			}
			return result;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mModifyTask = null;
			showProgress(false);

			if (success) {
				switch (type) {
				case PreferenceValue.MODIFYTYPEUSERNAME:
					userNameButton.setContentText(username);
					break;
				case PreferenceValue.MODIFYTYPEPASSWORD:
					break;
				case PreferenceValue.MODIFYTYPEEMAIL:
					emailButton.setContentText(email);
					break;
				}
				Toast.makeText(getBaseContext(), R.string.edit_success,
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getBaseContext(), R.string.edit_failure,
						Toast.LENGTH_SHORT).show();
			}
			
			username = userData.getUserInformation().getUAcot();
			email = userData.getUserInformation().getUMail();
		}

		@Override
		protected void onCancelled() {
			mModifyTask = null;
			showProgress(false);
		}
	}
}
