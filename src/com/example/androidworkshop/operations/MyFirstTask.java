package com.example.androidworkshop.operations;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.example.androidworkshop.models.MyFirstModel;
import com.example.androidworkshop.providers.MyContentProvider;
import com.example.androidworkshop.providers.MyFirstDataSet;

public class MyFirstTask {
	
	public void requestData() {
		
	}
	
	public void processRequest(final Context context, final String data) {
		final MyFirstModel item = new Gson().fromJson(data, MyFirstModel.class);
		final ContentValues values = MyFirstDataSet.getContentValues(item);
		final ContentResolver resolver = context.getContentResolver();
		resolver.insert(MyContentProvider.sMyFirstDatasetURI, values);
	}

}
