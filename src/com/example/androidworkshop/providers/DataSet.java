package com.example.androidworkshop.providers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;


public abstract class DataSet {
	
	public abstract String getName();

	public abstract int delete(SQLiteDatabase database, Uri uri, String selection,
			String[] selectionArgs);

	public abstract long insert(SQLiteDatabase database, Uri uri, ContentValues values);

	public abstract Cursor query(SQLiteDatabase database, Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder);

	public abstract int update(SQLiteDatabase database, Uri uri, ContentValues values,
			String selection, String[] selectionArgs);

	public abstract void onCreate(SQLiteDatabase db);

	public abstract void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion);
	
	public abstract void drop(SQLiteDatabase db);
}
