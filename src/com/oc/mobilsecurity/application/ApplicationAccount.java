package com.oc.mobilsecurity.application;

import java.util.ArrayList;
import java.util.List;

import com.oc.mobilsecurity.R;
import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ApplicationAccount extends ExpandableListActivity {

	List<String> group;
	List<List<String>> child;
	ContactsInfoAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.application_account);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titlebar_back);
		
		initializeData();  
        getExpandableListView().setAdapter(new ContactsInfoAdapter());  
        getExpandableListView().setCacheColorHint(0);
	}

	/**
	 * 初始化组、子列表数据
	 */
	private void initializeData() {
		group = new ArrayList<String>();
		child = new ArrayList<List<String>>();

		addInfo("Outlook", new String[] { "imcmy@outlook.com" });
		addInfo("GMail", new String[] { "imcmy912@gmail.com" });
	}

	/**
	 * 模拟给组、子列表添加数据
	 * 
	 * @param g
	 *            -group
	 * @param c
	 *            -child
	 */
	private void addInfo(String g, String[] c) {
		group.add(g);
		List<String> childitem = new ArrayList<String>();
		for (int i = 0; i < c.length; i++) {
			childitem.add(c[i]);
		}
		child.add(childitem);
	}

	class ContactsInfoAdapter extends BaseExpandableListAdapter {

		// -----------------Child----------------//
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return child.get(groupPosition).get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return child.get(groupPosition).size();
		}

		// ----------------Group----------------//
		@Override
		public Object getGroup(int groupPosition) {
			return group.get(groupPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public int getGroupCount() {
			return group.size();
		}

		// 创建组/子视图
		public TextView getGenericView(String s) {
			// Layout parameters for the ExpandableListView
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT, 40);

			TextView text = new TextView(ApplicationAccount.this);
			text.setLayoutParams(lp);
			// Center the text vertically
			text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
			// Set the text starting position
			text.setPadding(36, 0, 0, 0);

			text.setText(s);
			return text;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
				ViewGroup arg4) {
			String string = child.get(arg0).get(arg1);
			return getGenericView(string);
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			String string = group.get(groupPosition);
			return getGenericView(string);
		}

	}
}
