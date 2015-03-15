package com.oc.mobilsecurity;

import java.util.ArrayList;

import com.oc.mobilsecurity.R;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.oc.mobilsecurity.PreferenceValue;
import com.oc.mobilsecurity.data.UserData;
import com.oc.mobilsecurity.qr.*;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;
	private UserFindbackTask mFindbackTask = null;
	private UserRegisterTask mRegisterTask = null;

	// Values for user name and password at the time of the login attempt.
	private String mUserName;
	private String mPassword;
	private String scanResult;
	private ArrayList<String> list;

	// UI references.
	private EditText mUserNameView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	private TextView findbackTextView;
	private TextView registerTextView;
	private long mExitTime = 0;

	// Data Class
	private Run run = new Run(this);
	private UserData userData = UserData.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		userData.getMoblileInformation().initMobileInfo();

		// Set up the login form.
		mUserNameView = (EditText) findViewById(R.id.LoginName);
		mUserNameView.setText(mUserName);
		mPasswordView = (EditText) findViewById(R.id.LoginPassword);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.passwordLogin
								|| id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.edit_status_message);
		findViewById(R.id.LoginButton).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});

		findbackTextView = (TextView) findViewById(R.id.FindbackLink);
		registerTextView = (TextView) findViewById(R.id.RegisterLink);
		findbackTextView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		registerTextView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		findbackTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				attemptFindback();
			}
		});

		registerTextView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				attemptRegister();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * Attempts to sign in the account specified by the login form. If there are
	 * form errors (invalid email, missing fields, etc.), the errors are
	 * presented and no actual login attempt is made.
	 */
	private void attemptLogin() {
		
		// Reset errors.
		mUserNameView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mUserName = mUserNameView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < PreferenceValue.PASSWORDSHORTEST) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mUserName)) {
			mUserNameView.setError(getString(R.string.error_field_required));
			focusView = mUserNameView;
			cancel = true;
		} else if (mUserName.length() < PreferenceValue.USERNAMESHORTEST) {
			mUserNameView.setError(getString(R.string.error_invalid_username));
			focusView = mUserNameView;
			cancel = true;
		}

		if (cancel) {
			focusView.requestFocus();
		} else {
			Intent loginIntent = new Intent(LoginActivity.this,
					CaptureActivity.class);
			startActivityForResult(loginIntent, PreferenceValue.ACTIONTYPELOGIN);
		}
	}

	/**
	 * Attempts to find back the information
	 */
	private void attemptFindback() {
		Intent findbackIntent = new Intent(this, CaptureActivity.class);
		startActivityForResult(findbackIntent,
				PreferenceValue.ACTIONTYPEFINDBACK);
	}

	/**
	 * Attempts to register user
	 */
	private void attemptRegister() {
		Intent registerIntent = new Intent(this, RegisterActivity.class);
		startActivityForResult(registerIntent,
				PreferenceValue.ACTIONTYPEREGISTER);
	}

	/**
	 * Deal with the result from login, find back and register.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case PreferenceValue.ACTIONTYPELOGIN:
			switch (resultCode) {
			case RESULT_OK:
				Bundle bundle = data.getExtras();
				scanResult = bundle.getString("scanResult");

				if (mAuthTask != null) {
					return;
				}
				mLoginStatusMessageView
						.setText(R.string.login_progress_signing_in);
				showProgress(true);
				mAuthTask = new UserLoginTask();
				mAuthTask.execute((Void) null);
				break;
			case RESULT_CANCELED:
				break;
			default:
				break;
			}
			break;
		case PreferenceValue.ACTIONTYPEFINDBACK:
			switch (resultCode) {
			case RESULT_OK:
				Bundle bundle = data.getExtras();
				scanResult = bundle.getString("scanResult");

				if (mFindbackTask != null) {
					return;
				}
				mLoginStatusMessageView
						.setText(R.string.login_progress_signing_in);
				showProgress(true);
				mFindbackTask = new UserFindbackTask();
				mFindbackTask.execute((Void) null);
				break;
			case RESULT_CANCELED:
				break;
			default:
				break;
			}
			break;
		case PreferenceValue.ACTIONTYPEREGISTER:
			switch (resultCode) {
			case RESULT_OK:
				Bundle bundle = data.getExtras();
				list = bundle.getStringArrayList("registerInfo");

				if (mRegisterTask != null) {
					return;
				}
				mLoginStatusMessageView
						.setText(R.string.login_progress_register);
				showProgress(true);
				mRegisterTask = new UserRegisterTask();
				mRegisterTask.execute((Void) null);
				break;
			case RESULT_CANCELED:
			default:
				break;
			}
		default:
			break;
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login task used to authenticate the user.
	 */
	private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			boolean result = run.login(mUserName, mPassword, scanResult);
			return result;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				UserData.getInstance().getUserInformation().setLoginQR(scanResult);
				Intent intent = new Intent(getBaseContext(), MainActivity.class);
				startActivity(intent);
				finish();
			} else {
				Toast.makeText(getBaseContext(),
						R.string.error_incorrect_password, Toast.LENGTH_SHORT)
						.show();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}

	/**
	 * Represents an asynchronous find back task used to authenticate the user.
	 */
	private class UserFindbackTask extends AsyncTask<Void, Void, Boolean> {

		private int actionType = 0;

		@Override
		protected Boolean doInBackground(Void... params) {
			if (scanResult.startsWith("findCAP:")) {
				actionType = PreferenceValue.ACTIONTYPEFINDCAP;
				return run.findcap(scanResult);
			} else if (scanResult.startsWith("findQR:")) {
				actionType = PreferenceValue.ACTIONTYPEFINDQR;
				return run.findQR(scanResult);
			} else if (scanResult.startsWith("quicklog:")) {
				actionType = PreferenceValue.ACTIONTYPEQUICK;
				return false;
			} else {
				return false;
			}
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mFindbackTask = null;
			showProgress(false);

			switch (actionType) {
			case PreferenceValue.ACTIONTYPEFINDCAP:
				if (success) {
					Intent intent = new Intent(getBaseContext(),
							ShowActivity.class);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(getBaseContext(), "找回失败！",
							Toast.LENGTH_SHORT).show();
				}
				break;
			case PreferenceValue.ACTIONTYPEFINDQR:
				if (success) {
					Toast.makeText(getBaseContext(), "找回成功，请查收邮件！",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getBaseContext(), "找回失败", Toast.LENGTH_SHORT)
							.show();
				}
				break;
			case PreferenceValue.ACTIONTYPEQUICK:
				if (success) {
					Intent intent = new Intent(getBaseContext(),
							MainActivity.class);
					startActivity(intent);
					finish();
				} else {
					Toast.makeText(getBaseContext(), "登录失败！",
							Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				Toast.makeText(getBaseContext(), "无法识别二维码", Toast.LENGTH_SHORT)
						.show();
			}
		}

		@Override
		protected void onCancelled() {
			mFindbackTask = null;
			showProgress(false);
		}
	}

	/**
	 * Represents an asynchronous registration task used to authenticate the
	 * user.
	 */
	private class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {

			boolean result = run.regist(list.get(0), list.get(1), list.get(2),
					list.get(3), list.get(4), list.get(5));

			return result;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mRegisterTask = null;
			showProgress(false);

			if (success) {
				Toast.makeText(getBaseContext(), "注册成功，请登录邮箱打印下载二维码！",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getBaseContext(), "注册失败！", Toast.LENGTH_SHORT)
						.show();
			}
		}

		@Override
		protected void onCancelled() {
			mRegisterTask = null;
			showProgress(false);
		}

	}
}
