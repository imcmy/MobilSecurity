package com.oc.mobilsecurity;

public final class PreferenceValue {
	
	/*
	 * LENGTH
	 */
	public static final int USERNAMESHORTEST = 5;
	public static final int USERNAMELONGEST = 15;
	
	//  TODO: Change to 8
	public static final int PASSWORDSHORTEST = 6;
	public static final int PASSWORDLONGEST= 20;
	public static final int IDCARDLENGTH = 18;
	public static final int NAMESHORTEST = 4;
	public static final int NAMELONGEST = 30;
	public static final int MOBILELENGTH = 11;
	public static final int EMAILLONGEST = 80;
	
	/*
	 * ACTION TYPE
	 * Define the type of action.
	 * 0 is waiting for ....
	 */
	public static final int ACTIONTYPEERROR = -1;
	public static final int ACTIONTYPELOGIN = 1;
	public static final int ACTIONTYPEFINDBACK = 2;
	public static final int ACTIONTYPEFINDCAP = 3;
	public static final int ACTIONTYPEFINDQR = 4;
	public static final int ACTIONTYPEBINDMOBIL = 5;
	public static final int ACTIONTYPEBINDPC = 6;
	public static final int ACTIONTYPEUNBINDMOBIL = 7;
	public static final int ACTIONTYPEUNBINDPC = 17;
	public static final int ACTIONTYPEQUICK = 8;
	public static final int ACTIONTYPEREGISTER = 9;
	public static final int ACTIONTYPEUPDATEHARD = 10;
	public static final int ACTIONTYPEUPDATESOFT = 11;
	public static final int ACTIONVERIFY = 12;
	
	/*
	 * MODIFY TYPE
	 * Define the type of modifying.
	 * 0 is waiting for ....
	 */
	public static final int MODIFYTYPEHEADER = 13;
	public static final int MODIFYTYPEUSERNAME = 14;
	public static final int MODIFYTYPEPASSWORD = 15;
	public static final int MODIFYTYPEEMAIL = 16;
	
	/*
	 * Menu
	 */
	public static final String menuApplication = "我的应用";
	public static final String menuPersoninfo = "个人信息";
	public static final String menuSetting = "软件设置";
	public static final String menuAbout = "关于软件";
	
	/*
	 * Fragment Action
	 */
	public static final int FRAGAPPLOG = 1;
	public static final int FRAGAPPACCO = 2;
	public static final int FRAGPERSONLOOK = 3;
	public static final int FRAGPERSONBIND = 4;
	public static final int FRAGPERSONUNBIND = 5;
	public static final int FRAGPERSONFILE = 6;
	
}
