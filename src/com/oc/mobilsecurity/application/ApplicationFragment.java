package com.oc.mobilsecurity.application;

import java.util.ArrayList;
import java.util.HashMap;

import com.oc.mobilsecurity.MainActivity;
import com.oc.mobilsecurity.PreferenceValue;
import com.oc.mobilsecurity.R;
import com.oc.mobilsecurity.Run;
import com.oc.mobilsecurity.data.UserData;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ApplicationFragment extends Fragment {

	private ListView list;
	private Activity mActivity;
	private Run run;
	private ProgressDialog pd;
	private TextView Name;
	private TextView Device;
	private TextView LastLogin;

	private PayTask payTask = null;

	private String[] titles = { "��־", "�˺Ź���", "֧������", "��Ӹ���" };
	private String[] texts = { "�鿴������ʲô", "��������˺�����", "�������������֧��",
			"�����Ӧ�õȴ�����" };
	int[] resIds = { R.drawable.yellow_circle, R.drawable.green_circle,
			R.drawable.orange_circle, R.drawable.purple_circle };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		MainActivity.FragmentNo = 1;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.application, null);
		
		Name = (TextView) view.findViewById(R.id.frag_app_name);
		Name.setText(UserData.getInstance().getUserInformation().getUName());
		
		Device = (TextView) view.findViewById(R.id.frag_app_device);
		Device.setText(UserData.getInstance().getMoblileInformation().getDeviceCount()
				+ " ̨�豸");
		
		LastLogin = (TextView) view.findViewById(R.id.frag_app_log);
		LastLogin.setText(UserData.getInstance().getApplicationData().getLastLogin());
		
		run = new Run(mActivity);

		list = (ListView) view.findViewById(R.id.application_list);
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 4; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("appListItemImage", resIds[i]);
			map.put("appListItemTitle", titles[i]);
			map.put("appListItemText", texts[i]);
			listItem.add(map);
		}
		// ������������Item�Ͷ�̬�����Ӧ��Ԫ��
		SimpleAdapter listItemAdapter = new SimpleAdapter(getActivity()
				.getBaseContext(), listItem, R.layout.list_application,
				new String[] { "appListItemImage", "appListItemTitle",
						"appListItemText" }, new int[] { R.id.appListItemImage,
						R.id.actionTitle, R.id.actionTime });

		// ��Ӳ�����ʾ
		list.setAdapter(listItemAdapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				switch (arg2) {
				case 0:
					intent.setClass((MainActivity) getActivity(),
							ApplicationLog.class);
					startActivityForResult(intent, PreferenceValue.FRAGAPPLOG);
					break;
				case 1:
					intent.setClass((MainActivity) getActivity(),
							ApplicationAccount.class);
					startActivityForResult(intent, PreferenceValue.FRAGAPPACCO);
					break;
				case 2:
					if (payTask != null) {
						return;
					}
					showProgress(true);
					payTask = new PayTask();
					payTask.execute((Void) null);
					break;
				case 3:
					Toast.makeText((MainActivity) getActivity(), "�����ڴ���", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		});

		return view;
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	private void showProgress(final boolean show) {
		if (show) {
			pd = ProgressDialog.show((MainActivity) getActivity(), "֧��",
					"��֤�У����Ժ󡭡�");
		} else {
			pd.dismiss();
		}
	}

	/**
	 * Represents an asynchronous login task used to authenticate the user.
	 */
	public class PayTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			Log.v("TAG", "PAY!");
			return run.pay();
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			Log.v("TAG", "PAY!PAY!");
			payTask = null;
			showProgress(false);

			if (success) {
				Toast.makeText((MainActivity) getActivity(), "֧��ͨ����",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText((MainActivity) getActivity(), "֧��ʧ�ܣ�",
						Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onCancelled() {
			payTask = null;
			showProgress(false);
		}
	}
}
