package com.moe.myapp;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;


import android.os.AsyncTask;
import android.util.Log;

public class DatabaseService extends AsyncTask<String, Void, String> {

	String url = "http://128.164.25.3:8080/CarpoolingProject/xmlServletPath";

	HttpParams httpParams = new BasicHttpParams();

	HttpParams p = new BasicHttpParams();

	// Instantiate an HttpClient
	HttpClient httpclient = new DefaultHttpClient(p);

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		if (params[0] == "getRoutes") {
			String allRoutes = getRoutes();
			return allRoutes;
		} else if (params[0] == "getRouteInfo") {

		} else if (params[0] == "createRoute") {
			Log.i(getClass().getSimpleName(), "condition reached !!!!!!!!!");
			createRoute(params[1], params[2], params[3], params[4], params[5],
					params[6], params[7], params[8], params[9]);

		} else if (params[0] == "updateRoute") {
			Log.i(getClass().getSimpleName(), "condition reached !!!!!!!!!");
			updateRoute(params[1], params[2], params[3], params[4], params[5],
					params[6], params[7], params[8], params[9], params[10]);

		} else if (params[0] == "deleteRoute") {
			deleteRoute(params[1]);
		}

		return null;
	}

	public Connection connect() {
		return null;

	}

	public String getRoutes() {

		try {

			HttpGet httpGet = new HttpGet(url);

			// Instantiate a GET HTTP method
			Log.i(getClass().getSimpleName(), "send  task - start");

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = null;

			responseBody = httpclient.execute(httpGet, responseHandler);
			return responseBody;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;

	}

	public void getRouteInfo() {

		try {

			HttpPost httppost = new HttpPost(url);

			// Instantiate a GET HTTP method
			Log.i(getClass().getSimpleName(), "send  task - start");

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = null;

			responseBody = httpclient.execute(httppost, responseHandler);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void createRoute(String start, String destination, String waypoints,
			String length, String capacity, String passengers, String date,
			String time, String name) {

		try {
			HttpPost httppost = new HttpPost(url);
			ArrayList<NameValuePair> postParameters;
			postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("action", "add"));
			postParameters.add(new BasicNameValuePair("start", start));
			postParameters.add(new BasicNameValuePair("destination",
					destination));
			postParameters.add(new BasicNameValuePair("wayPoints", waypoints));
			postParameters.add(new BasicNameValuePair("length", length));
			postParameters.add(new BasicNameValuePair("capacity", capacity));
			postParameters
					.add(new BasicNameValuePair("passengers", passengers));
			postParameters.add(new BasicNameValuePair("date", date));
			postParameters.add(new BasicNameValuePair("time", time));
			postParameters.add(new BasicNameValuePair("name", name));

			httppost.setEntity(new UrlEncodedFormEntity(postParameters));

			Log.i(getClass().getSimpleName(), "create route parameters" + start
					+ "");

			HttpResponse response = httpclient.execute(httppost);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void updateRoute(String routeid, String start, String destination,
			String waypoints, String length, String capacity,
			String passengers, String date, String time, String name) {
		try {
			HttpPost httppost = new HttpPost(url);
			ArrayList<NameValuePair> postParameters;
			postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("action", "update"));
			postParameters.add(new BasicNameValuePair("routeid", routeid));
			postParameters.add(new BasicNameValuePair("start", start));
			postParameters.add(new BasicNameValuePair("destination",
					destination));
			postParameters.add(new BasicNameValuePair("wayPoints", waypoints));
			postParameters.add(new BasicNameValuePair("length", length));
			postParameters.add(new BasicNameValuePair("capacity", capacity));
			postParameters
					.add(new BasicNameValuePair("passengers", passengers));
			postParameters.add(new BasicNameValuePair("date", date));
			postParameters.add(new BasicNameValuePair("time", time));
			postParameters.add(new BasicNameValuePair("name", name));

			httppost.setEntity(new UrlEncodedFormEntity(postParameters));

			Log.i(getClass().getSimpleName(), "create route parameters" + start
					+ "");

			HttpResponse response = httpclient.execute(httppost);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void deleteRoute(String routeid) {
		try {
			HttpPost httppost = new HttpPost(url);
			ArrayList<NameValuePair> postParameters;
			postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("action", "delete"));
			postParameters.add(new BasicNameValuePair("routeid", routeid));

			httppost.setEntity(new UrlEncodedFormEntity(postParameters));

			HttpResponse response = httpclient.execute(httppost);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
