package com.example.androidworkshop.providers;

import java.util.HashMap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class Database extends SQLiteOpenHelper {
	
	private final HashMap<Uri, DataSet> mDataSets;

	public Database(Context context, String name, HashMap<Uri, DataSet> dataSets) {
		super(context, name, null, 1);
		mDataSets = dataSets;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		for (final DataSet dataset: mDataSets.values()) {
			dataset.onCreate(db);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (final DataSet dataset: mDataSets.values()) {
			dataset.onUpgrade(db);
		}
	}
	
	
}
