package com.clearblade.clearchat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {				//_id
	public static final String KEY_ID = "_id";
	public static final String KEY_GROUP_NAME = "mgroup";
	public static final String TAG = "DBAdapter";
	
	public static final String DATABASE_NAME = "GroupsDB";
	public static final String DATABASE_TABLE = "groups";
	public static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE = "create table if not exists groups ( _id integer primary key autoincrement, mgroup VARCHAR not null);";

	private final Context context;
	
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	public DBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper 
	{
		DatabaseHelper(Context context) 
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			try {
				db.execSQL(DATABASE_CREATE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			db.execSQL("DROP TABLE IF EXISTS groups");
			onCreate(db);
		}
	}
			
		//---open the database
		public DBAdapter open() throws SQLException {
			db = DBHelper.getWritableDatabase();
			return this;
		}
		
		//---close the database
		public void close() {
			DBHelper.close();	
		}
		
		//---insert a record into the database
		public long insertRecord(String mgroup) {
			ContentValues initialValues = new ContentValues();
			initialValues.put(KEY_GROUP_NAME, mgroup);
			return db.insert(DATABASE_TABLE, null, initialValues);	
		}
		
		//---deletes a particular record----possible system variable naming conflict?
		public boolean deleteRecord(long ID){
			return db.delete(DATABASE_TABLE, KEY_ID + "=" + ID, null) > 0;
		}
		
		//---retrieves all the records
		public Cursor getAllRecords() {
			return db.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_GROUP_NAME},null, null, null, null, null);	
		}
		
		//---retrieves a particular record
		public Cursor getRecord(long ID) throws SQLException {
			Cursor mCursor =
					db.query(true, DATABASE_TABLE, new String[] {KEY_ID, KEY_GROUP_NAME}, KEY_ID + "=" + ID, null, null, null, null, null);
			if (mCursor != null){
				mCursor.moveToFirst();	
			}
			return mCursor;
		}
		
		//---update a record
		public boolean updateRecord(long ID, String mgroup) {
			ContentValues args = new ContentValues();
			args.put(KEY_GROUP_NAME, mgroup);
			return db.update(DATABASE_TABLE, args, KEY_ID + "=" + ID, null) > 0;
		}
	
	}
	
	
	
	
	
	
	
	
	
	
	

