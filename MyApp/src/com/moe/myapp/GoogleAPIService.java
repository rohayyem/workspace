package com.moe.myapp;

import java.io.Serializable;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class GoogleAPIService extends AsyncTask<String, Void, Route> {

	public GoogleAPIService() {
		super();

	}

	@Override
	protected Route doInBackground(String... arg0) {
		// TODO Auto-generated method stub

		Route r = new Route();
		try {
			String url = "http://maps.googleapis.com/maps/api/directions/json?origin="
					+ arg0[0].replace(" ", "")
					+ "&destination="
					+ arg0[1].replace(" ", "");

			if (arg0.length > 2) {
				url += "&waypoints=optimize%3Atrue%7C";

				for (int i = 2; i < arg0.length; i++) {
					if (arg0[2] != null && arg0[2] != "N/A" && arg0[2] != "")
						url += "%7C" + arg0[i].replace(" ", "");

				}
				
				
			}
			url += "&sensor=false";
			Log.i(getClass().getSimpleName(), "++++++++++++URL" + url);

			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 50000000);
			HttpParams p = new BasicHttpParams();
	

			// Instantiate an HttpClient
			HttpClient httpclient = new DefaultHttpClient(p);

			HttpPost httppost = new HttpPost(url);

			// Instantiate a GET HTTP method
			Log.i(getClass().getSimpleName(), "send  task - start");
			//

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			// Toast.makeText(this, "sign", Toast.LENGTH_LONG).show();
			String responseBody = null;

			responseBody = httpclient.execute(httppost, responseHandler);

			// Parse
			JSONObject json = null;

			json = new JSONObject(responseBody);
			Log.i(getClass().getSimpleName(), json.toString(4));
			Log.i(getClass().getSimpleName(), "--------------------------");

			JsonParser parser = new JsonParser(json.toString());

			r = parser.parse(json.toString());
			Log.i(getClass().getSimpleName(), r.getPoints().toString());

		} catch (Exception e) {

			e.printStackTrace();
		}
		return r;

	}

}
