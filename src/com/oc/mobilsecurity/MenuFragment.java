package com.oc.mobilsecurity;

import java.util.ArrayList;
import java.util.HashMap;

import com.oc.mobilsecurity.about.AboutFragment;
import com.oc.mobilsecurity.application.ApplicationFragment;
import com.oc.mobilsecurity.data.UserData;
import com.oc.mobilsecurity.personinfo.*;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MenuFragment extends Fragment {

	private ListView list;
	private ArrayList<HashMap<String, Object>> listItem;
	private Button exitButton;
	private UserData userData = UserData.getInstance();

	private String[] titles = { 
			PreferenceValue.menuApplication,
			PreferenceValue.menuPersoninfo, 
			PreferenceValue.menuSetting,
			PreferenceValue.menuAbout };
	int resIds = R.drawable.blue_circle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// inflater the layout
		View view = inflater.inflate(R.layout.menu, null);
		list = (ListView) view.findViewById(R.id.menu_list);
		listItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 4; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("menuListItemTitle", titles[i]);
			map.put("menuListItemImage", resIds);
			listItem.add(map);
		}
		// 生成适配器的Item和动态数组对应的元素
		SimpleAdapter listItemAdapter = new SimpleAdapter(
				getActivity().getBaseContext(), 
				listItem,
				R.layout.list_menu, 
				new String[] { "menuListItemImage", "menuListItemTitle" },
				new int[] { R.id.menuListItemImage, R.id.menuListItemTitle });

		// 添加并且显示
		list.setAdapter(listItemAdapter);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				FragmentTransaction fragmentTransaction = ((MainActivity) getActivity())
						.getFragmentManager().beginTransaction();
				if (listItem.get(arg2).containsValue(PreferenceValue.menuApplication)) {
					if (MainActivity.FragmentNo != 1) {
						fragmentTransaction.replace(R.id.main,
								new ApplicationFragment());
						fragmentTransaction.commit();
					}
				} else if (listItem.get(arg2).containsValue(PreferenceValue.menuPersoninfo)) {
					if (MainActivity.FragmentNo != 2) {
						fragmentTransaction.replace(R.id.main,
								new PersonInfoFragment());
						fragmentTransaction.commit();
					}
				} else if (listItem.get(arg2).containsValue(PreferenceValue.menuSetting)) {
					if (MainActivity.FragmentNo != 3) {

					}
				} else {
					if (MainActivity.FragmentNo != 4) {
						fragmentTransaction.replace(R.id.main,
								new AboutFragment());
						fragmentTransaction.commit();
					}
				}
				((MainActivity) getActivity()).getSlidingMenu().toggle();
			}
		});

		exitButton = (Button) view.findViewById(R.id.exitButton);
		exitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				userData.clearAll();
				((MainActivity) getActivity()).finish();
			}
		});
		return view;
	}
}