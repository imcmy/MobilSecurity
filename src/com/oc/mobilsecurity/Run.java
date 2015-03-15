package com.oc.mobilsecurity;

import java.util.StringTokenizer;
import android.content.Context;
import android.util.Log;
import com.oc.mobilsecurity.contact.Client;
import com.oc.mobilsecurity.data.UserData;

/**
 * 
 * @author uyiqgyy 交互方法
 */
public class Run {

	// private HardwareDao hardwareDao;
	private Context context;
	private UserData userData = UserData.getInstance();

	public Run(Context con) {
		context = con;
		userData.getMoblileInformation().setContext(con);
	}

	/**
	 * 注册 传参顺序依次是
	 * 
	 * @param uacot
	 *            账号
	 * @param upswd
	 *            密码
	 * @param ucard
	 *            身份证
	 * @param uname
	 *            姓名
	 * @param umobi
	 *            手机号
	 * @param umail
	 *            邮箱号
	 * 
	 * @return 成功或失败
	 */
	public boolean regist(String uacot, String upswd, String ucard,
			String uname, String umobi, String umail) {

		String message = PreferenceValue.ACTIONTYPEREGISTER + "*" + uacot + "*"
				+ upswd + "*" + ucard + "*" + uname + "*" + umobi + "*" + umail;
		if (!update(PreferenceValue.ACTIONTYPEREGISTER, message)) {
			return false;
		}

		message = PreferenceValue.ACTIONTYPEUPDATEHARD + "*" + uacot + "*"
				+ userData.getMoblileInformation().getUHard() + "*" + userData.getMoblileInformation().getDeviceName();
		if (!update(PreferenceValue.ACTIONTYPEUPDATEHARD, message)) {
			return false;
		}

		return true;
	}

	/**
	 * 找回 账号密码 要求传入二维码信息 输出一串String,以*号隔开（可以商量）
	 */
	public boolean findcap(String qr) {

		return update(PreferenceValue.ACTIONTYPEFINDCAP,
				PreferenceValue.ACTIONTYPEFINDCAP + "*" + qr + "*"
						+ userData.getMoblileInformation().getUHard());
	}

	public boolean findQR(String qr) {

		return update(PreferenceValue.ACTIONTYPEFINDQR,
				PreferenceValue.ACTIONTYPEFINDQR + "*" + qr + "*"
						+ userData.getMoblileInformation().getUHard());
	}

	public boolean bindMobil(String qr) {

		return update(PreferenceValue.ACTIONTYPEBINDMOBIL,
				PreferenceValue.ACTIONTYPEBINDMOBIL + "*"
						+ userData.getUserInformation().getId() + "*" + qr
						+ "*" + userData.getMoblileInformation().getUHard()
						+ "*" + userData.getMoblileInformation().getDeviceName());
	}

	public boolean bindPC(String qr) {

		return update(PreferenceValue.ACTIONTYPEBINDPC,
				PreferenceValue.ACTIONTYPEBINDPC + "*"
						+ userData.getUserInformation().getId() + "*" + qr
						+ "*" + userData.getMoblileInformation().getUHard());
	}

	public boolean unbindMobil(String name) {

		return update(PreferenceValue.ACTIONTYPEUNBINDMOBIL,
				PreferenceValue.ACTIONTYPEUNBINDMOBIL + "*"
						+ userData.getUserInformation().getId() + "*" + name);
	}
	
	public boolean unbindPC(String name) {

		return update(PreferenceValue.ACTIONTYPEUNBINDPC,
				PreferenceValue.ACTIONTYPEUNBINDPC + "*"
						+ userData.getUserInformation().getId() + "*" + name);
	}

	/**
	 * 修改用户名和密码
	 * 
	 * @param uacot
	 * @param upswd
	 * @return
	 */
	public boolean changeAccount(String uacot) {

		String message = PreferenceValue.ACTIONVERIFY + "*"
				+ userData.getUserInformation().getId() + "*"
				+ userData.getMoblileInformation().getUHard();
		if (!update(PreferenceValue.ACTIONVERIFY, message)) {
			return false;
		}

		message = PreferenceValue.MODIFYTYPEUSERNAME + "*"
				+ userData.getUserInformation().getId() + "*" + uacot;
		if (!update(PreferenceValue.MODIFYTYPEUSERNAME, message)) {
			return false;
		}

		userData.getUserInformation().setUAcot(uacot);
		return true;
	}

	public boolean changePasswd(String upswd) {

		String message = PreferenceValue.ACTIONVERIFY + "*"
				+ userData.getUserInformation().getId() + "*"
				+ userData.getMoblileInformation().getUHard();
		if (!update(PreferenceValue.ACTIONVERIFY, message)) {
			return false;
		}

		message = PreferenceValue.MODIFYTYPEPASSWORD + "*"
				+ userData.getUserInformation().getId() + "*" + upswd;
		if (!update(PreferenceValue.MODIFYTYPEPASSWORD, message)) {
			return false;
		}

		userData.getUserInformation().setUPswd(upswd);
		return true;
	}

	public boolean changeMail(String umail) {

		String message = PreferenceValue.MODIFYTYPEEMAIL + "*"
				+ userData.getUserInformation().getId() + "*" + umail;
		if (!update(PreferenceValue.MODIFYTYPEEMAIL, message)) {
			return false;
		}

		userData.getUserInformation().setUMail(umail);
		return true;
	}

	public boolean changeFile(String ufile) {
		return false;
	}

	public boolean login(String uacot, String upswd, String ulgqr) {

		return update(PreferenceValue.ACTIONTYPELOGIN,
				PreferenceValue.ACTIONTYPELOGIN + "*" + uacot + "*" + upswd
						+ "*" + ulgqr);
	}

	public String lookdaliy() {

		String result = null;
		return result;
	}

	public boolean pay() {

		return update(PreferenceValue.ACTIONVERIFY,
				PreferenceValue.ACTIONVERIFY + "*"
						+ userData.getUserInformation().getId() + "*"
						+ userData.getMoblileInformation().getUHard());
	}

	private boolean update(int actionType, String message) {

		Client client = new Client();
		client.init(context);
		String result = client.process(message);

		switch (actionType) {
		case PreferenceValue.ACTIONTYPELOGIN: // Login
			if (result.equals("null") || result.equals("error")
					|| result.equals("false"))
				return false;
			else {
				StringTokenizer st = new StringTokenizer(result, "*");
				st.nextToken();
				long id = Long.parseLong(st.nextToken());
				String userAccount = st.nextToken();
				String userPassword = st.nextToken();
				String userCard = st.nextToken();
				String userName = st.nextToken();
				String userMobi = st.nextToken();
				String userMail = st.nextToken();
				int Mcount = Integer.parseInt(st.nextToken());
				for (int i=0; i<Mcount; i++) {
					userData.getMoblileInformation().addMobile(st.nextToken());
				}
				int Pcount = Integer.parseInt(st.nextToken());
				for (int i=0; i<Pcount; i++) {
					userData.getMoblileInformation().addPC(st.nextToken());
				}
				userData.getUserInformation().setAll(id, userAccount,
						userPassword, userCard, userName, userMobi, userMail);
				return true;
			}
		case PreferenceValue.ACTIONTYPEFINDCAP: // Find Account and Passwd
			if (result.equals("null") || result.equals("error")
					|| result.equals("false"))
				return false;
			else {
				Log.v("TAG", result);
				StringTokenizer st = new StringTokenizer(result, "*");
				userData.getUserInformation().setUAcot(st.nextToken());
				userData.getUserInformation().setUPswd(st.nextToken());
				return true;
			}
		case PreferenceValue.ACTIONTYPEFINDQR:
		case PreferenceValue.ACTIONTYPEREGISTER:
		case PreferenceValue.ACTIONVERIFY:
		case PreferenceValue.ACTIONTYPEBINDMOBIL:
		case PreferenceValue.ACTIONTYPEBINDPC:
		case PreferenceValue.ACTIONTYPEUNBINDMOBIL:
		case PreferenceValue.ACTIONTYPEUNBINDPC:
		case PreferenceValue.ACTIONTYPEUPDATEHARD:
		case PreferenceValue.ACTIONTYPEUPDATESOFT:
		case PreferenceValue.MODIFYTYPEUSERNAME:
		case PreferenceValue.MODIFYTYPEPASSWORD:
		case PreferenceValue.MODIFYTYPEEMAIL:
			Log.v("TAG", result);
			if (result.equals("null") || result.equals("error")
					|| result.equals("false"))
				return false;
			else
				return true;
		default:
			return false;
		}
	}
}
