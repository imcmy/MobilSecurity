package com.oc.mobilsecurity;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.oc.mobilsecurity.application.ApplicationFragment;
import com.oc.mobilsecurity.data.UserData;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends SlidingActivity {

	public static int FragmentNo = 0;
	private long mExitTime = 0;
	private ImageButton home;

	private SlidingMenu slidingMenu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.fragment_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titlebar_main);

		setBehindContentView(R.layout.fragment_menu);
		FragmentTransaction fragmentTransaction = getFragmentManager()
				.beginTransaction();
		fragmentTransaction.replace(R.id.menu, new MenuFragment());
		fragmentTransaction.replace(R.id.main, new ApplicationFragment());
		fragmentTransaction.commit();

		slidingMenu = getSlidingMenu();
		slidingMenu.setShadowWidth(10);
		slidingMenu.setShadowDrawable(R.drawable.shadow);
		slidingMenu.setBehindOffset(250);
		slidingMenu.setFadeDegree(0.35f);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		home = (ImageButton) findViewById(R.id.home);
		home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				toggle();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (slidingMenu.isMenuShowing()) {
				toggle();
			} else if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else {
				UserData.getInstance().clearAll();
				finish();
			}
			return true;
		case KeyEvent.KEYCODE_MENU:
			toggle();
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
