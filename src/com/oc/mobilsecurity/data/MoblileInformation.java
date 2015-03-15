package com.oc.mobilsecurity.data;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;

import com.oc.mobilsecurity.check.FetchData;
import com.oc.mobilsecurity.datamanager.Encryption;
import com.oc.mobilsecurity.datamanager.HardwareDao;
import com.oc.mobilsecurity.datamanager.SoftwareDao;

import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.*;
import android.util.Log;

public class MoblileInformation {

	private HardwareDao hardwareDao;
	private SoftwareDao softwareDao;
	private Context context;

	private String USER_SOFT = null; // 软件信息
	private String USER_HARD = null; // 硬件信息
	private String USER_UKEY = null; // 数字证书
	private String DEVICENAME;

	private ArrayList<String> mobileNames;
	private ArrayList<String> PCNames;

	private static MoblileInformation instance;

	private MoblileInformation() {
		mobileNames = new ArrayList<String>();
		PCNames = new ArrayList<String>();
	}
	
	public void initMobileInfo() {
		getUHardInfo();
		getUSoftInfo();
	}

	public static MoblileInformation getInstance() {
		if (instance == null) {
			instance = new MoblileInformation();
		}
		return instance;
	}

	public void setContext(Context con) {
		if (con == null) {
			Log.v("TAG", "Mob NULL");
		}
		context = con;
	}
	
	public String getDeviceName() {
		return DEVICENAME;
	}

	public void getUHardInfo() {

		String IMEI = ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
		String ANDROIDID = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);
		String MODEL = android.os.Build.MODEL;
		String MAC = ((WifiManager) context
				.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo()
				.getMacAddress();
		String CPU = FetchData.fetch_cpu_info();
		// String info = IMEI + ANDROIDID + MODEL + MAC + CPU;
		String info = IMEI + ANDROIDID + MODEL + MAC;
		DEVICENAME = MODEL;

		USER_HARD = Encryption.getMD5(info);
		hardwareDao = new HardwareDao(context);
		try {
			hardwareDao.save(USER_HARD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getUSoftInfo() {
		
		int ANDROIDVER = android.os.Build.VERSION.SDK_INT;
		String VER = android.os.Build.VERSION.RELEASE;
		String KERNEL = FetchData.fetch_kernel_version();
		String BASEBAND = FetchData.fetch_baseband_version();
		String info = ANDROIDVER + VER + KERNEL + BASEBAND;
		
		USER_SOFT = Encryption.getMD5(info);
		softwareDao = new SoftwareDao(context);
		try {
			softwareDao.save(USER_SOFT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete() {
		instance = null;
	}

	public void setUSoft(String usoft) {
		this.USER_SOFT = usoft;
	}

	public void setUHard(String uhard) {
		this.USER_HARD = uhard;
	}

	public void setUkey(String ukey) {
		this.USER_UKEY = ukey;
	}

	public String getUSoft() {
		return USER_SOFT;
	}

	public String getUHard() {
		return USER_HARD;
	}

	public String getUkey() {
		return USER_UKEY;
	}

	public void setMobileNames(String[] mob) {
		mobileNames = (ArrayList<String>) Arrays.asList(mob);
	}

	public void setPCNames(String[] pc) {
		PCNames = (ArrayList<String>) Arrays.asList(pc);
	}

	public void addMobile(String mob) {
		mobileNames.add(mob);
	}

	public void addPC(String pc) {
		PCNames.add(pc);
	}

	public void delMobile(String mob) {
		mobileNames.remove(mob);
	}

	public void delPC(String pc) {
		PCNames.remove(pc);
	}
	
	public ArrayList<String> getMobileNamesList() {
		return mobileNames;
	}
	
	public ArrayList<String> getPCNamesList() {
		return PCNames;
	}

	public String[] getMobileNames() {
		String[] array = new String[mobileNames.size()];
		for (int i = 0; i < mobileNames.size(); i++) {
			array[i] = mobileNames.get(i);
		}
		return array;
	}

	public String[] getPCNames() {
		String[] array = new String[PCNames.size()];
		for (int i = 0; i < PCNames.size(); i++) {
			array[i] = PCNames.get(i);
		}
		return array;
	}
	
	public int getDeviceCount() {
		return mobileNames.size() + PCNames.size();
	}
}
