package com.oc.mobilsecurity.personinfo;

import com.oc.mobilsecurity.MainActivity;
import com.oc.mobilsecurity.PreferenceValue;
import com.oc.mobilsecurity.R;
import com.oc.mobilsecurity.Run;
import com.oc.mobilsecurity.data.UserData;
import com.oc.mobilsecurity.qr.CaptureActivity;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PersonInfoFragment extends Fragment {

	private Run run;
	private Activity mActivity;
	private String scanResult;
	private TextView Name;
	private TextView Device;
	private TextView LastLogin;

	private BindTask bTask = null;
	private ProgressDialog pd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		MainActivity.FragmentNo = 2;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.personinfo, null);
		Button lookButton = (Button) view.findViewById(R.id.personLook);
		Button bindButton = (Button) view.findViewById(R.id.personBind);
		Button unbindButton = (Button) view.findViewById(R.id.personUnbind);
		Button customFileButton = (Button) view
				.findViewById(R.id.personSettingfile);

		Name = (TextView) view.findViewById(R.id.frag_app_name);
		Name.setText(UserData.getInstance().getUserInformation().getUName());

		Device = (TextView) view.findViewById(R.id.frag_app_device);
		Device.setText(UserData.getInstance().getMoblileInformation()
				.getDeviceCount()
				+ " 台设备");

		LastLogin = (TextView) view.findViewById(R.id.frag_app_log);
		LastLogin.setText(UserData.getInstance().getApplicationData()
				.getLastLogin());

		run = new Run(mActivity);

		lookButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Activity mainActivity = (MainActivity) getActivity();
				Intent intent = new Intent(mainActivity, PersonInfoLook.class);
				startActivityForResult(intent, PreferenceValue.FRAGPERSONLOOK);
			}
		});

		bindButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Activity mainActivity = (MainActivity) getActivity();
				Intent intent = new Intent(mainActivity, CaptureActivity.class);
				startActivityForResult(intent, PreferenceValue.FRAGPERSONBIND);
			}
		});

		unbindButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Activity mainActivity = (MainActivity) getActivity();
				Intent intent = new Intent(mainActivity, PersonInfoUnbind.class);
				startActivityForResult(intent, PreferenceValue.FRAGPERSONUNBIND);
			}
		});

		customFileButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Activity mainActivity = (MainActivity) getActivity();
				Intent intent = new Intent(mainActivity, PersonInfoFile.class);
				startActivityForResult(intent, PreferenceValue.FRAGPERSONFILE);
			}
		});

		return view;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case PreferenceValue.FRAGAPPLOG:
		case PreferenceValue.FRAGAPPACCO:
			break;
		case PreferenceValue.FRAGPERSONUNBIND:
			Device.setText(UserData.getInstance().getMoblileInformation()
					.getDeviceCount()
					+ " 台设备");
			break;
		case PreferenceValue.FRAGPERSONBIND:
			switch (resultCode) {
			case Activity.RESULT_OK:
				Bundle bundle = data.getExtras();
				scanResult = bundle.getString("scanResult");

				if (bTask != null) {
					return;
				}
				showProgress(true);
				bTask = new BindTask();
				bTask.execute((Void) null);
				break;
			case Activity.RESULT_CANCELED:
			default:
				break;
			}
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void showProgress(final boolean show) {
		if (show) {
			pd = ProgressDialog.show((MainActivity) getActivity(), "绑定",
					"绑定中，请稍后……");
		} else {
			pd.dismiss();
		}
	}

	private class BindTask extends AsyncTask<Void, Void, Boolean> {

		private boolean ifPC = false;

		@Override
		protected Boolean doInBackground(Void... params) {
			if (scanResult.startsWith("bindMobil:")) {
				return run.bindMobil(scanResult);
			} else if (scanResult.startsWith("bindPC:")) {
				ifPC = true;
				return run.bindPC(scanResult);
			} else {
				return false;
			}
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			bTask = null;
			showProgress(false);

			if (success) {
				if (ifPC) {
					Toast.makeText((MainActivity) getActivity(),
							"绑定成功，请点击屏幕上的按钮！", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText((MainActivity) getActivity(), "绑定成功",
							Toast.LENGTH_SHORT).show();
					String DeviceName = UserData.getInstance()
							.getMoblileInformation().getDeviceName();
					UserData.getInstance().getMoblileInformation()
							.addMobile(DeviceName);
				}
			} else {
				Toast.makeText((MainActivity) getActivity(), "绑定失败",
						Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onCancelled() {
			bTask = null;
			showProgress(false);
		}
	}
}