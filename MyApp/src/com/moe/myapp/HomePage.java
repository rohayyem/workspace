package com.moe.myapp;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HomePage extends Activity {

	int selectedRoute=-1;
	JSONArray allRoutesJSON;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_page);

		try {

			String allRoutes = new DatabaseService().execute("getRoutes").get();
			allRoutesJSON = new JSONArray(allRoutes);

			

			String[] savedRoutes = new String[allRoutesJSON.length()];

			if (allRoutesJSON != null) {
				int len = allRoutesJSON.length();

				for (int i = 0; i < len; i++) {
					savedRoutes[i] =  "From: "
							+ allRoutesJSON.getJSONObject(i).getString("start")
							+ " To: " + allRoutesJSON.getJSONObject(i)
							.getString("destination")
							+" @ "+allRoutesJSON.getJSONObject(i)
							.getString("time");
				}
			}

			// String []savedRoutes={allRoutesJSON,"Washington","Maryland"};
			ArrayAdapter<String> ad = new ArrayAdapter<String>(this,
					R.layout.list_routes_data, savedRoutes);

			ListView lv = (ListView) findViewById(R.id.listViewRoutes);
			lv.setAdapter(ad);
			lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			lv.setSelector(android.R.color.darker_gray);
			lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View veiw,
						int pos, long id) {
					try {
						// TODO Auto-generated method stub
						TextView clickedText = (TextView) veiw;
						clickedText.setSelected(true);

						selectedRoute = pos;

						Log.i(getClass().getSimpleName(), "selected route"
								+ pos + "------" + selectedRoute);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

	}

	public void addRoute_click(View v) {

		try {
			
			Intent intent = new Intent(this, AddRoute.class);
			startActivity(intent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteRoute_click(View v) {

		try {
			if(selectedRoute==-1){
				Toast.makeText(getApplicationContext(), "Please Select a Route First",
						Toast.LENGTH_LONG).show();
				return;
			}
				
			//getting the route id
			String routeid = allRoutesJSON.getJSONObject(selectedRoute).getString("routeid");
			
			
			String success = new DatabaseService().execute("deleteRoute",
					routeid).get();
			Toast.makeText(getApplicationContext(), "Route Deleted Successfully",
					Toast.LENGTH_LONG).show();

			this.finish();
			Intent intent = new Intent(this, HomePage.class);
			startActivity(intent);
			
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Error Deleting Route",
					Toast.LENGTH_LONG).show();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void viewRoute_click(View v) {

		try {
			if(selectedRoute==-1){
				Toast.makeText(getApplicationContext(), "Please Select a Route First",
						Toast.LENGTH_LONG).show();
				return;
			}
			Intent intent = new Intent(this, ViewRoute.class);

			JSONObject selectedRouteJSON =(JSONObject)allRoutesJSON.get(selectedRoute);
			JSONArray selectedWaypointsJSON=selectedRouteJSON.getJSONArray("wayPoints");

			String wayPointsString = selectedWaypointsJSON.getString(0);		
			for (int i = 1; i < selectedWaypointsJSON.length(); i++) {
				wayPointsString+= "#" + selectedWaypointsJSON.getString(i);	
			}

			Log.i(getClass().getSimpleName(), "save clicked!!!!!!!!!"
					+ "routeid: " +
					allRoutesJSON.getJSONObject(selectedRoute).getString(
							"routeid")
					+ "wayPoints: " + wayPointsString		
					
					);
			
			
			Bundle bundle = new Bundle();
			bundle.putString(
					"routeid",
					allRoutesJSON.getJSONObject(selectedRoute).getString(
							"routeid"));
			bundle.putString("start", allRoutesJSON
					.getJSONObject(selectedRoute).getString("start"));
			bundle.putString(
					"destination",
					allRoutesJSON.getJSONObject(selectedRoute).getString(
							"destination"));
			bundle.putString("waypoints", wayPointsString);
//			bundle.putString("waypoints", "nope");
			bundle.putString(
					"length",
					allRoutesJSON.getJSONObject(selectedRoute).getString(
							"length"));
			bundle.putString(
					"capacity",
					allRoutesJSON.getJSONObject(selectedRoute).getString(
							"capacity"));
			bundle.putString(
					"passengers",
					allRoutesJSON.getJSONObject(selectedRoute).getString(
							"passengers"));
			bundle.putString("date", allRoutesJSON.getJSONObject(selectedRoute)
					.getString("date"));
			bundle.putString("time", allRoutesJSON.getJSONObject(selectedRoute)
					.getString("time"));
			bundle.putString("name", allRoutesJSON.getJSONObject(selectedRoute)
					.getString("name"));

			intent.putExtras(bundle);
			startActivity(intent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
