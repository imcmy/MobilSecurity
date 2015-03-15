package com.oc.mobilsecurity.datamanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * hardware表  操作类
 * 
 * @author Administrator
 * 
 */
public class HardwareDao {
	/**
	 * 数据库辅助类
	 */
	private DBOpenHelper dbOpenHelper;

	public HardwareDao(Context context) {
		super();
		this.dbOpenHelper = new DBOpenHelper(context);
	}
	/**
	 * 保存硬件信息
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
	 * 更新硬件信息
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
	 * 查找用户信息
	 * 
	 * @param id
	 *            用户id
	 * @return 用户信息
	 */

	public String find(int id) {
		String hardware = null;
		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery("select * from HardWare_Info where id=?",
				new String[] {"1"});
		if (cursor.moveToFirst()) {
			hardware = getHardware(cursor);
		}
		// 注意关闭游标
		cursor.close();
		return hardware;
	}

	/**
	 * 从游标中读取硬件信息
	 * 
	 * @param cursor
	 *            游标
	 * @return hardware
	 */

	private String getHardware(Cursor cursor) {
		String hardware = cursor.getString(cursor.getColumnIndex("USER_HARD"));
		
		return hardware;
	}

}
