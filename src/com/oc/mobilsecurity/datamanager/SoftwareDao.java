package com.oc.mobilsecurity.datamanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SoftwareDao {
	/**
	 * ���ݿ⸨����
	 */
	private DBOpenHelper dbOpenHelper;

	public SoftwareDao(Context context) {
		super();
		this.dbOpenHelper = new DBOpenHelper(context);
	}

	/**
	 * ���������Ϣ
	 * 
	 * @param software
	 */
	public void save(String software) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		database.execSQL("insert into SoftWare_Info(id,USER_SOFT) values(?,?)",
				new Object[] { 1, software });
	}

	/**
	 * ����Ӳ����Ϣ
	 * 
	 * @param sware
	 */
	public void updateHardware(String sware) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		database.execSQL("update SoftWare_Info set USER_SOFT =? ",
				new Object[] { sware });
	}

	/**
	 * �����û���Ϣ
	 * 
	 * @param id
	 *            �û�id
	 * @return �û���Ϣ
	 */

	public String find(int id) {
		String software = null;
		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(
				"select * from SoftWare_Info where id=?", new String[] { "1" });
		if (cursor.moveToFirst()) {
			software = getSoftware(cursor);
		}
		// ע��ر��α�
		cursor.close();
		return software;
	}

	/**
	 * ���α��ж�ȡ�����Ϣ
	 * 
	 * @param cursor
	 *            �α�
	 * @return software
	 */

	private String getSoftware(Cursor cursor) {
		String software = cursor.getString(cursor.getColumnIndex("USER_SOFT"));

		return software;
	}
}
