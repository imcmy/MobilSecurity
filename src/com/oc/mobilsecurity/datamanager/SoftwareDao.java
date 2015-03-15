package com.oc.mobilsecurity.datamanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SoftwareDao {
	/**
	 * 数据库辅助类
	 */
	private DBOpenHelper dbOpenHelper;

	public SoftwareDao(Context context) {
		super();
		this.dbOpenHelper = new DBOpenHelper(context);
	}

	/**
	 * 保存软件信息
	 * 
	 * @param software
	 */
	public void save(String software) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		database.execSQL("insert into SoftWare_Info(id,USER_SOFT) values(?,?)",
				new Object[] { 1, software });
	}

	/**
	 * 更新硬件信息
	 * 
	 * @param sware
	 */
	public void updateHardware(String sware) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		database.execSQL("update SoftWare_Info set USER_SOFT =? ",
				new Object[] { sware });
	}

	/**
	 * 查找用户信息
	 * 
	 * @param id
	 *            用户id
	 * @return 用户信息
	 */

	public String find(int id) {
		String software = null;
		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(
				"select * from SoftWare_Info where id=?", new String[] { "1" });
		if (cursor.moveToFirst()) {
			software = getSoftware(cursor);
		}
		// 注意关闭游标
		cursor.close();
		return software;
	}

	/**
	 * 从游标中读取软件信息
	 * 
	 * @param cursor
	 *            游标
	 * @return software
	 */

	private String getSoftware(Cursor cursor) {
		String software = cursor.getString(cursor.getColumnIndex("USER_SOFT"));

		return software;
	}
}
