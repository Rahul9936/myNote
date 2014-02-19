package com.example.dear.diary;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database_diary extends Activity {

	public static final String DB_ROWID = "_id";
	public static final String DB_TEXT = "diary_text";
	public static final String DB_DATE = "diary_date";
	public static final String DB_TIME = "diary_time";
	public static final String DB_IMG = "diary_img";
	//public static final String DB_DATETIME = "diary_timeDATE";

	private static final String DATABASE_NAME = "DIARY_DATABASE";
	private static final String DATABASE_TABLE = "DIARY_TABLE";
	private static final int DATABASE_VERSION = 6;

	public Context diary_context;
	SQLiteDatabase DIARY_DB;
	DiaryDatabaseHelper ddHelper;

	public class DiaryDatabaseHelper extends SQLiteOpenHelper {

		public DiaryDatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
			diary_context = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " ( " + DB_ROWID
					+ " INTEGER," + DB_TEXT
					+ " TEXT NOT NULL," + DB_DATE + " DATE," + DB_TIME
					+ " TEXT," + DB_IMG + " TEXT);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			if (newVersion > oldVersion) {
				db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
				onCreate(db);
			}
		}
	}

	public Database_diary(Context c) {
		diary_context = c;
	}

	public Database_diary open() {
		ddHelper = new DiaryDatabaseHelper(diary_context);
		DIARY_DB = ddHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		DIARY_DB.close();
		ddHelper.close();
	}

	public void insert_diary(int pos, String s, String picturePath) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(DB_ROWID, pos);
		cv.put(DB_TEXT, s);
		
		String string = (String) android.text.format.DateFormat.format(
				"dd-MM-yyyy hh:mm:ss", new java.util.Date());
		String s1 = string.substring(11, 16);
		cv.put(DB_TIME, s1);
		cv.put(DB_DATE, string);
		cv.put(DB_IMG, picturePath);
		
		DIARY_DB.insert(DATABASE_TABLE, null, cv);
		
	}

	public Cursor getCursor() {
		// TODO Auto-generated method stub
		String[] columns = { DB_ROWID, DB_TEXT, DB_DATE, DB_TIME};
		Cursor cursor = DIARY_DB.query(DATABASE_TABLE, columns, null, null,
				null, null, null);

		return cursor;
	}

	public String getInfo(int i) {
		// TODO Auto-generated method stub
		String[] columns = { DB_ROWID, DB_TEXT, DB_DATE, DB_TIME };
		Cursor cursor = DIARY_DB.query(DATABASE_TABLE, columns, DB_ROWID
				+ " = " + i, null, null, null, null);
		String res = "";
		cursor.moveToFirst();
		res = res + cursor.getString(cursor.getColumnIndex(DB_TEXT));
		cursor.close();
		return res;
	}

	public void dBdelete(int position) {
		// TODO Auto-generated method stub
		DIARY_DB.delete(DATABASE_TABLE, DB_ROWID + " = " + position, null);
		String[] columns = { DB_ROWID, DB_TEXT, DB_DATE, DB_TIME, DB_IMG };
		Cursor cursor = DIARY_DB.query(DATABASE_TABLE, columns, DB_ROWID + " > " + position , null,
				null, null, null, null);
		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
			updateRowId(cursor.getInt(cursor.getColumnIndex(DB_ROWID)));
		}
		cursor.requery();
     	cursor.close();
	}

	private void updateRowId(int int1) {
		// TODO Auto-generated method stub
		ContentValues cvsr = new ContentValues();
		cvsr.put(DB_ROWID, int1 - 1);
		DIARY_DB.update(DATABASE_TABLE, cvsr, DB_ROWID + " = " + int1 , null);
	}

	public void updateImagePath(int i, String picPath){
		ContentValues cvsr = new ContentValues();
		cvsr.put(DB_IMG, picPath);
		DIARY_DB.update(DATABASE_TABLE, cvsr, DB_ROWID + " = " + i , null);
	}
	public String getTextFromFb(int i) {
		// TODO Auto-generated method stub
		String[] columns = { DB_ROWID, DB_TEXT, DB_DATE, DB_TIME, DB_IMG };
		Cursor cursor = DIARY_DB.query(DATABASE_TABLE, columns, DB_ROWID
				+ " = " + i, null, null, null, null, null);
		String result = "";
		cursor.moveToFirst();
		result = cursor.getString(cursor.getColumnIndex(DB_TEXT));
		cursor.close();
		return result;
	}

	public String getImgPath(int i) {
		// TODO Auto-generated method stub
		String[] columns = { DB_ROWID, DB_TEXT, DB_DATE, DB_TIME, DB_IMG };
		Cursor cursor = DIARY_DB.query(DATABASE_TABLE, columns, DB_ROWID
				+ " = " + i, null, null, null, null, null);
		String res = "";
		cursor.moveToFirst();
		res = cursor.getString(cursor.getColumnIndex(DB_IMG));
		cursor.close();
		return res;
	}

	public void updateText(String st, int pos) {
		// TODO Auto-generated method stub
		ContentValues cvs = new ContentValues();
		cvs.put(DB_TEXT, st);
		DIARY_DB.update(DATABASE_TABLE, cvs, DB_ROWID + " = " + pos, null);
	}

	public int getRowNo() {
		// TODO Auto-generated method stub
		String[] columns = { DB_ROWID, DB_TEXT, DB_DATE, DB_TIME, DB_IMG};
		Cursor cursor = DIARY_DB.query(DATABASE_TABLE, columns, null, null,
				null, null, null);
		int res = 0;
		if(cursor.moveToLast()){
		res = cursor.getInt(cursor.getColumnIndex(DB_ROWID));
		}
		cursor.close();
		return res;
	}

	public String getDate(int i) {
		// TODO Auto-generated method stub
		String[] columns = { DB_ROWID, DB_TEXT, DB_DATE, DB_TIME, DB_IMG};
		Cursor cursor = DIARY_DB.query(DATABASE_TABLE, columns, DB_ROWID + " = " + i, null,
				null, null, null);
		String res = "";
		cursor.moveToFirst();
		res = cursor.getString(cursor.getColumnIndex(DB_DATE));
		cursor.close();
		return res;
	}
}
