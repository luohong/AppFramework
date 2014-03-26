package com.android.framework.core.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	
	private static final String DB_NAME = "mydb";

	private static final int DATABASE_VERSION=4;

	private static DBHelper mDBHelper;

	public static DBHelper getInstance(Context context) {
		if (mDBHelper == null) {
			mDBHelper = new DBHelper(context);
		}
		return mDBHelper;
	}

	private DBHelper(Context context) {
		super(context, DB_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(getTag(), "onCreate");
		createTabale(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(getTag(), "onUpgrade");
//		db.execSQL("DROP TABLE IF EXISTS " + CityDb.TABLE_NAME);
//		db.execSQL("DROP TABLE IF EXISTS " + SaleEvaluateDb.TABLE_NAME);
//		db.execSQL("DROP TABLE IF EXISTS " + QueryCacheDB.TABLE_NAME);
//		db.execSQL("DROP TABLE IF EXISTS " + RegionInfoDB.TABLE_NAME);
//		db.execSQL("DROP TABLE IF EXISTS " + SearChRecordDB.TABLE_NAME);
//		db.execSQL("DROP TABLE IF EXISTS " + AgentDb.TABLE_NAME);
		createTabale(db);
	}

	private void createTabale(SQLiteDatabase db) {
//		db.execSQL(CityDb.CREATE_TABLE);
//		db.execSQL(SaleEvaluateDb.CREATE_TABLE);
//		db.execSQL(QueryCacheDB.CREATE_TABLE);
//		db.execSQL(RegionInfoDB.CREATE_TABLE);
//		db.execSQL(SearChRecordDB.CREATE_TABLE);
//		db.execSQL(AgentDb.CREATE_TABLE);
	}

	private String getTag() {
		return this.getClass().toString();
	}

}