package com.example.androidworkshop.providers;

import java.util.HashMap;
import java.util.Locale;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

	private static final Object LOCK = new Object();
	private HashMap<Uri, DataSet> dataSets;
	private HashMap<Uri, String> validators;
	private static SQLiteDatabase sDatabase;
	
	protected void createDatabase() {
		synchronized (LOCK) {
			if (sDatabase == null) {
				final Database database = new Database(getContext(), "MyDatabase", dataSets);
				sDatabase = database.getWritableDatabase();
			}
		}
	}
	
	private SQLiteDatabase getDatabase() {
		if (sDatabase == null) {
			createDatabase();
		}
		return sDatabase;
	}
	
	private String getValidator(Uri uri) {
		return validators.get(uri);
	}
	
	private String registerValidator(Uri uri, String validator) {
		return validators.put(uri, validator);
	}
	
	private DataSet getDataSet(Uri uri) {
		return dataSets.get(uri);
	}
	
	private void registerDataSet(Uri uri, DataSet dataSet) {
		dataSets.put(uri, dataSet);
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		final DataSet dataset = getDataSet(uri);
		final int numRowsDeleted = dataset.delete(getDatabase(), uri, selection, selectionArgs);
		return numRowsDeleted;
	}

	@Override
	public String getType(Uri uri) {
		final String dataSetName = getDataSet(uri).getName();
		final String type = "vnd.android.cursor.dir" + "/com.example.androidworkshop."
				+ dataSetName;
		return type.toLowerCase(Locale.getDefault());
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		final DataSet dataset = getDataSet(uri);
		final long id = dataset.insert(getDatabase(), uri, values);
		return ContentUris.withAppendedId(uri, id);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		final DataSet dataset = getDataSet(uri);
//		final String validator = getValidator(uri);
		final Cursor cursor = dataset.query(getDatabase(), uri, projection, selection, selectionArgs, sortOrder);
//		CursorUtils.updateCursorWithExtras(getContext(), uri, cursor, validator);
		return cursor;
	}


	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		final DataSet dataset = getDataSet(uri);
		final int numRowsAffected = dataset.update(getDatabase(), uri, values, selection, selectionArgs);
		return numRowsAffected;
	}

	@Override
	public boolean onCreate() {
		// Register a bunch of data sets
		registerDataSet(Uri.parse("FirstDataSet"),new MyFirstDataSet());
		registerDataSet(Uri.parse("SecondDataSet"),new MyFirstDataSet());
		return false;
	}

}
