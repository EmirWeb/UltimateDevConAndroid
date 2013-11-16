package com.example.androidworkshop.operations;

import android.content.Context;
import android.graphics.Movie;

public class MyFirstTask {
	
	public void requestData() {
		
	}
	
	public void processRequest(final Context context, final String data) {
		final Movie item = new Gson().fromJson(data, Movie.class);
		// Get content values?
		// insert values into resolver?
	}

}
