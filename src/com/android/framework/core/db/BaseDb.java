package com.android.framework.core.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public abstract class BaseDb {
	protected Cursor cursor = null;

	protected DBHelper helper = null;

	protected SQLiteDatabase db = null;

	public BaseDb(Context context) {
		helper = DBHelper.getInstance(context);
		db = helper.getWritableDatabase();
	}

	public void beginTransaction() {
		if (db == null || !db.isOpen()) {
			db = helper.getWritableDatabase();
		}
		db.beginTransaction();
	}

	public void endTransaction() {
		if (db == null || !db.isOpen()) {
			db = helper.getWritableDatabase();
		}
		db.setTransactionSuccessful();
		db.endTransaction();
	}

	protected void checkDb() {
		if (db == null || !db.isOpen()) {
			db = helper.getWritableDatabase();
		}
	}

	public void closeDbAndCursor() {
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
		/*
		 * if (db != null) { db.close(); db = null; }
		 */
	}

	protected void clearAllData() {
		try {
			checkDb();
			String sql = "delete from " + getTableName() + ";";
			Log.e("SQL", sql);
			db.execSQL(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDbAndCursor();
		}
	}

	protected boolean isHasData() {
		checkDb();
		String sql = "select * from " + getTableName() + " limit 1";
		cursor = db.rawQuery(sql, null);
		if (cursor != null) {
			int count = cursor.getCount();
			return count > 0 ? true : false;
		}
		return false;
	}

    protected abstract String getTableName();
    
    protected abstract String getCreateTableSQL();
}
