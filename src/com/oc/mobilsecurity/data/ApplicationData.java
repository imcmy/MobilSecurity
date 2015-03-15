package com.oc.mobilsecurity.data;

public class ApplicationData {
	
	private String FUNC_LOG = null; // ��־��Ϣ
	private String FUNC_ACOT = null; // �����˺�
	private String FUNC_PSWD = null; // ��������
	private String FUNC_OTHE = null; // ����ƽ̨�˺�
	
	public String[] titles = { "ע��", "��¼", "�鿴��־" };
	public String[] texts = { "GMT+8:14/05/11 18:32 �Ĵ�ʡ�ɶ���",
			"GMT+8:14/05/11 18:33 �Ĵ�ʡ�ɶ���", "GMT+8:14/05/11 18:34 �Ĵ�ʡ�ɶ���" };
	
	private static ApplicationData instance;

	public static ApplicationData getInstance() {
		if (instance == null) {
			instance = new ApplicationData();
		}
		return instance;
	}

	public void delete() {
		instance = null;
	}
	
	public String getLastLogin() {
		return " ";
	}
	
	public void setFLog(String flog) {
		this.FUNC_LOG = flog;
	}

	public void setFAcot(String facot) {
		this.FUNC_ACOT = facot;
	}

	public void setFPswd(String fpswd) {
		this.FUNC_PSWD = fpswd;
	}

	public void setFOthe(String fothe) {
		this.FUNC_OTHE = fothe;
	}

	public String getFLog() {
		return FUNC_LOG;
	}

	public String getFAcot() {
		return FUNC_ACOT;
	}

	public String getFPswd() {
		return FUNC_PSWD;
	}

	public String getFOthe() {
		return FUNC_OTHE;
	}

}
