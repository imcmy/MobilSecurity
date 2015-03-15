package com.oc.mobilsecurity.data;

import android.content.Context;
import com.oc.mobilsecurity.data.ApplicationData;
import com.oc.mobilsecurity.data.MoblileInformation;
import com.oc.mobilsecurity.data.UserInformation;

public class UserData {
	
	private UserInformation user = UserInformation.getInstance();
	private ApplicationData application = ApplicationData.getInstance();
	private MoblileInformation mobile = MoblileInformation.getInstance();

	private static UserData instance = null;

	private UserData() {
	}

	public static UserData getInstance() {
		if (instance == null) {
			instance = new UserData();
		}
		return instance;
	}

	public void setContext(Context con) {
		mobile.setContext(con);
	}

	public UserInformation getUserInformation() {
		return user;
	}

	public ApplicationData getApplicationData() {
		return application;
	}

	public MoblileInformation getMoblileInformation() {
		return mobile;
	}

	public void clearAll() {
		user.delete();
		application.delete();
		mobile.delete();
	}
}
