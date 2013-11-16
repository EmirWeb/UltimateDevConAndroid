package com.example.androidworkshop.network;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public class NetworkRequests {
	private static final String BASE_URL = "http://somewebsite.com/api/v1.0";


	public static String getFirstData(final int limit) throws ClientProtocolException, IOException {
		final String url = BASE_URL + "/firstData?limit=" + limit;
		return "";
//		return executeRequest(new NetworkRequest(url), new Gson(), MoviesResponse.class);
	}
}
