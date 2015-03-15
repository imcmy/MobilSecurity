package com.oc.mobilsecurity.datamanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * hardware��  ������
 * 
 * @author Administrator
 * 
 */
public class HardwareDao {
	/**
	 * ���ݿ⸨����
	 */
	private DBOpenHelper dbOpenHelper;

	public HardwareDao(Context context) {
		super();
		this.dbOpenHelper = new DBOpenHelper(context);
	}
	/**
	 * ����Ӳ����Ϣ
	 * 
	 * @param hardware
	 */
	public void save(String hardware) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		database.execSQL(
				"insert into HardWare_Info(id,USER_HARD) values(?,?)",
				new Object[] { 1,hardware });
	}
	
	/**
	 * ����Ӳ����Ϣ
	 * 
	 * @param hware 
	 *            
	 * 
	 */
	public void updateHardware(String hware) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		database.execSQL("update HardWare_Info set USER_HARD =? ", new Object[] {
				hware});
	}
	
	/**
	 * �����û���Ϣ
	 * 
	 * @param id
	 *            �û�id
	 * @return �û���Ϣ
	 */

	public String find(int id) {
		String hardware = null;
		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery("select * from HardWare_Info where id=?",
				new String[] {"1"});
		if (cursor.moveToFirst()) {
			hardware = getHardware(cursor);
		}
		// ע��ر��α�
		cursor.close();
		return hardware;
	}

	/**
	 * ���α��ж�ȡӲ����Ϣ
	 * 
	 * @param cursor
	 *            �α�
	 * @return hardware
	 */

	private String getHardware(Cursor cursor) {
		String hardware = cursor.getString(cursor.getColumnIndex("USER_HARD"));
		
		return hardware;
	}

}
