package com.example.androidworkshop.providers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.androidworkshop.models.MyFirstModel;

public class MyFirstDataSet extends DataSet{
	
	@Override
	public void onCreate(final SQLiteDatabase db){
		final String query = 	"CREATE TABLE People " +
				"( " +
				"	P_Id int NOT NULL UNIQUE, " +
				"	LastName varchar(255) NOT NULL, " +
				"	FirstName varchar(255), " +
				"	Address varchar(255), " +
				"	City varchar(255) " +
				");";
		db.execSQL(query);
	}
	
	@Override
	public void drop(final SQLiteDatabase db){
		final String query = "DROP TABLE IF EXISTS People";
		db.execSQL(query);
	}

	@Override
	public String getName() {
		return "MyFirstDataSet";
	}

	@Override
	public int delete(SQLiteDatabase database, Uri uri, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long insert(SQLiteDatabase database, Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Cursor query(SQLiteDatabase database, Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(SQLiteDatabase database, Uri uri, ContentValues values,
			String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		drop(db);
		onCreate(db);
	}

	public static ContentValues getContentValues(MyFirstModel item) {
		final ContentValues value = new ContentValues();
        value.put("P_ID", item.getP_Id());
        value.put("FirstName", item.getFirstName());
        value.put("LastName", item.getLastName());
        value.put("Address", item.getAddress());
        value.put("City", item.getCity());
        return value;
	}
}
