package com.oc.mobilsecurity.personinfo;

import java.util.ArrayList;
import java.util.HashMap;
import com.oc.mobilsecurity.R;
import com.oc.mobilsecurity.Run;
import com.oc.mobilsecurity.data.UserData;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PersonInfoUnbind extends Activity {

	private Button back;
	private ListView mobile;
	private ListView PC;

	private ProgressDialog pd;
	private int type = 0;
	private String deviceName;

	private String[] mobileNames = UserData.getInstance()
			.getMoblileInformation().getMobileNames();
	private String[] PCNames = UserData.getInstance().getMoblileInformation()
			.getPCNames();
	private String[] unbind = { "解绑" };

	private Run run = new Run(this);
	private UnBindTask unbindTask = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.personinfo_unbind);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titlebar_back);

		mobile = (ListView) findViewById(R.id.unbindMobile);
		ArrayList<HashMap<String, Object>> listItemMobile = getListItem(
				"mobileName", mobileNames);
		ListViewAdapter listViewAdapterMobile = new ListViewAdapter(this,
				listItemMobile, "mobileName");
		mobile.setAdapter(listViewAdapterMobile);
		mobile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (unbindTask != null) {
					return;
				}
				type = 1;
				deviceName = mobileNames[arg2];
				showProgress(true);
				unbindTask = new UnBindTask();
				unbindTask.execute(mobileNames[arg2]);
			}
		});

		PC = (ListView) findViewById(R.id.unbindPC);
		ArrayList<HashMap<String, Object>> listItemPC = getListItem("PCName",
				PCNames);
		ListViewAdapter listViewAdapterPC = new ListViewAdapter(this,
				listItemPC, "PCName");
		PC.setAdapter(listViewAdapterPC);
		PC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (unbindTask != null) {
					return;
				}
				type = 2;
				deviceName = PCNames[arg2];
				showProgress(true);
				unbindTask = new UnBindTask();
				unbindTask.execute(PCNames[arg2]);
			}
		});

		back = (Button) findViewById(R.id.TitleBarBack);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private ArrayList<HashMap<String, Object>> getListItem(String tag,
			String[] device) {
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < device.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put(tag, device[i]);
			map.put("unbind", unbind[0]);
			listItem.add(map);
		}

		return listItem;
	}

	private class ListViewAdapter extends BaseAdapter {
		@SuppressWarnings("unused")
		private Context context;
		private ArrayList<HashMap<String, Object>> listItems;
		private LayoutInflater listContainer;
		private String tag;

		public final class ListItemView {
			public TextView deviceName;
			public TextView unbind;
		}

		public ListViewAdapter(Context context,
				ArrayList<HashMap<String, Object>> listItems, String tag) {
			this.context = context;
			listContainer = LayoutInflater.from(context);
			this.listItems = listItems;
			this.tag = tag;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			ListItemView listItemView = null;
			if (arg1 == null) {
				listItemView = new ListItemView();
				arg1 = listContainer.inflate(R.layout.button_arrow, null);

				listItemView.deviceName = (TextView) arg1
						.findViewById(R.id.customButtonText1);
				listItemView.unbind = (TextView) arg1
						.findViewById(R.id.customButtonText2);

				arg1.setTag(listItemView);
			} else {
				listItemView = (ListItemView) arg1.getTag();
			}

			listItemView.deviceName.setText((String) listItems.get(arg0).get(
					tag));
			listItemView.unbind.setText("解绑");

			return arg1;
		}

		@Override
		public int getCount() {
			return listItems.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
	}

	private void showProgress(final boolean show) {
		if (show) {
			pd = ProgressDialog.show(this, "解绑", "解绑中，请稍后……");
		} else {
			pd.dismiss();
		}
	}
	
	private void delArray() {
		switch (type) {
		case 1:
			for (int i=0; i<mobileNames.length; i++) {
				if (mobileNames[i].equals(deviceName)) {
					for (int j=i; j<mobileNames.length-1; j++) {
						mobileNames[j] = mobileNames[j+1];
					}
					mobileNames[mobileNames.length-1] = "";
					break;
				}
			}
			break;
		case 2:
			for (int i=0; i<PCNames.length; i++) {
				if (PCNames[i].equals(deviceName)) {
					for (int j=i; j<PCNames.length-1; j++) {
						PCNames[j] = PCNames[j+1];
					}
					PCNames[PCNames.length-1] = "";
					break;
				}
			}
		}
	}

	private class UnBindTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			boolean result = false;
			switch (type) {
			case 1:
				result = run.unbindMobil(params[0]);
				break;
			case 2:
				result = run.unbindPC(params[0]);
				break;
			}
			
			return result;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			unbindTask = null;
			showProgress(false);

			if (success) {
				switch (type) {
				case 1:
					UserData.getInstance().getMoblileInformation()
							.delMobile(deviceName);
					mobileNames = UserData.getInstance().getMoblileInformation()
							.getMobileNames();
					delArray();
					ListViewAdapter mAdapter = (ListViewAdapter)mobile.getAdapter();
		            mAdapter.notifyDataSetChanged();
					break;
				case 2:
					UserData.getInstance().getMoblileInformation()
							.delPC(deviceName);
					PCNames = UserData.getInstance().getMoblileInformation()
							.getPCNames();
					delArray();
					ListViewAdapter pAdapter = (ListViewAdapter)PC.getAdapter();
		            pAdapter.notifyDataSetChanged();
					break;
				}
				Toast.makeText(getBaseContext(), "解绑成功", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(getBaseContext(), "解绑失败", Toast.LENGTH_SHORT)
						.show();
			}
		}

		@Override
		protected void onCancelled() {
			unbindTask = null;
			showProgress(false);
		}
	}
}
