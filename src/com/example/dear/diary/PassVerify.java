package com.example.dear.diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PassVerify {

	public static final String DB_ROWID = "_id";
	
	private static final String DATABASE_NAME = "pass_database";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_TABLE = "pass_table";
	
	public SQLiteDatabase DB;
	public DbHelper dbh;
	Context ourContext;
	
	public class DbHelper extends SQLiteOpenHelper{

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + 
					"(" + DB_ROWID + " TEXT);");
			db.execSQL("INSERT INTO " + DATABASE_TABLE + " VALUES " +"('');");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}	
	}
	
	public PassVerify(Context c){
		ourContext = c;
	}
	
	public PassVerify open(){
		dbh = new DbHelper(ourContext);
		DB = dbh.getWritableDatabase();
		return this;
	}
	
	public PassVerify close(){
		dbh.close();
		return this;
	}

	public void update_pass(String string) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(DB_ROWID, string);
		DB.update(DATABASE_TABLE, cv, null, null);
	}

	public String getPass() {
		// TODO Auto-generated method stub
		String[] columns = new String[]{DB_ROWID};
		Cursor corsor = DB.query(DATABASE_TABLE, columns, null, null, null, null, null);
		corsor.moveToFirst();
		String s = "";
		s = corsor.getString(corsor.getColumnIndex(DB_ROWID));
		corsor.close();
		return s;
	}
}