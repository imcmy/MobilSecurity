package com.oc.mobilsecurity.data;

public class UserInformation {
	private long ID; // Ψһ��ʾ�������ذ�׿���²���Ҫά������ʱд��
	private String USER_ACOT = null; // �û���
	private String USER_PSWD = null; // ����
	private String USER_CARD = null; // ���֤
	private String USER_NAME = null; // ����
	private String USER_MOBI = null; // �ֻ���
	private String USER_MAIL = null; // ����
	
	private String LOGINQR = null;
	
	private static UserInformation instance;
	
	public static UserInformation getInstance() {
		if (instance == null) {
			instance = new UserInformation();
		}
		return instance;
	}
	
	public void delete() {
		instance = null;
	}
	
	public void setAll(long id, String uacot, String upswd, String ucard,
			String uname, String umobi, String umail) {
		ID = id;
		USER_ACOT = uacot;
		USER_PSWD = "******";
		USER_CARD = ucard;
		USER_NAME = uname;
		USER_MOBI = umobi;
		USER_MAIL = umail;
	}
	
	public void setLoginQR(String str) {
		LOGINQR = str;
	}
	
	public String getLoginQR() {
		return LOGINQR;
	}
	
	public void setId(long id) {
		this.ID = id;
	}

	public void setUAcot(String uacot) {
		this.USER_ACOT = uacot;
	}

	public void setUPswd(String upswd) {
		this.USER_PSWD = upswd;
	}

	public void setUCard(String ucard) {
		this.USER_CARD = ucard;
	}

	public void setUName(String uname) {
		this.USER_NAME = uname;
	}

	public void setUMobi(String umobi) {
		this.USER_MOBI = umobi;
	}

	public void setUMail(String umail) {
		this.USER_MAIL = umail;
	}


	public long getId() {
		return ID;
	}

	public String getUAcot() {
		return USER_ACOT;
	}

	public String getUPswd() {
		return USER_PSWD;
	}

	public String getUCard() {
		return USER_CARD;
	}

	public String getUName() {
		return USER_NAME;
	}

	public String getUMobi() {
		return USER_MOBI;
	}

	public String getUMail() {
		return USER_MAIL;
	}
}
