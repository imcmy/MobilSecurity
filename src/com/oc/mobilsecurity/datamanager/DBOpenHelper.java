package com.oc.mobilsecurity.datamanager;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库辅助类，用于创建数据库，创建表，更新数据库，更新表结构
 * 
 * @author Administrator
 * 
 */
public class DBOpenHelper extends SQLiteOpenHelper {

public DBOpenHelper(Context context) {
		// 數據庫名字 遊標工廠（null表示使用系統默認的） 數據庫版本（>0）
		super(context, "information", null, 1);
	}

	/**private SQLiteDatabase mSQLiteDatabase = null;
	 * 數據庫第一次創建的 時候調用 当调用SQLiteOpenHelper的getWritableDatabase()
	 * 或者getReadableDatabase()方法获取用于操作数据库的SQLiteDatabase实例的时候，
	 * 如果数据库不存在，Android系统会自动生成一个数据库 接着调用onCreate()方法 onCreate()方法在初次生成数据库时才会被调用
	 */
	/**
	 * 創建后保存在 data/data/包/database/
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		//db.execSQL("CREATE TABLE HardWare_Info (id integer primary key,USER_HARD char[400] )");
		db.execSQL("CREATE TABLE HardWare_Info (id integer,USER_HARD char[32] )");
		db.execSQL("CREATE TABLE SoftWare_Info (id integer,USER_SOFT char[32] )");
	}
	
	/**
	 * 
	 * 數據庫版本改變的時候調用
	 */
	public void onUpgrade(SQLiteDatabase db, String hardware) {
		// db.execSQL(" ALTER TABLE person ADD phone VARCHAR(12) NULL"); //
		// 往表中增加一列
		// DROP TABLE IF EXISTS person 删除表	
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
