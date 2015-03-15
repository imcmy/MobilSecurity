package com.oc.mobilsecurity.datamanager;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * ���ݿ⸨���࣬���ڴ������ݿ⣬�������������ݿ⣬���±�ṹ
 * 
 * @author Administrator
 * 
 */
public class DBOpenHelper extends SQLiteOpenHelper {

public DBOpenHelper(Context context) {
		// ���������� �[�˹��S��null��ʾʹ��ϵ�yĬ�J�ģ� ������汾��>0��
		super(context, "information", null, 1);
	}

	/**private SQLiteDatabase mSQLiteDatabase = null;
	 * �������һ�΄����� �r���{�� ������SQLiteOpenHelper��getWritableDatabase()
	 * ����getReadableDatabase()������ȡ���ڲ������ݿ��SQLiteDatabaseʵ����ʱ��
	 * ������ݿⲻ���ڣ�Androidϵͳ���Զ�����һ�����ݿ� ���ŵ���onCreate()���� onCreate()�����ڳ����������ݿ�ʱ�Żᱻ����
	 */
	/**
	 * �����󱣴��� data/data/��/database/
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		//db.execSQL("CREATE TABLE HardWare_Info (id integer primary key,USER_HARD char[400] )");
		db.execSQL("CREATE TABLE HardWare_Info (id integer,USER_HARD char[32] )");
		db.execSQL("CREATE TABLE SoftWare_Info (id integer,USER_SOFT char[32] )");
	}
	
	/**
	 * 
	 * ������汾��׃�ĕr���{��
	 */
	public void onUpgrade(SQLiteDatabase db, String hardware) {
		// db.execSQL(" ALTER TABLE person ADD phone VARCHAR(12) NULL"); //
		// ����������һ��
		// DROP TABLE IF EXISTS person ɾ����	
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
