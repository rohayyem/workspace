package com.moe.myapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import com.google.android.gms.maps.MapFragment;

public class JoinRoute extends Activity {

	private GoogleMap mMap;
	String[] params;

	Route route;

	Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.join_route);

		try {

			GoogleMapOptions options = new GoogleMapOptions();
			mMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();
			mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			mMap.setMyLocationEnabled(true);
			mMap.getMyLocation();

			bundle = getIntent().getExtras();

			

			if (bundle.getString("waypoints") != null
					&& !bundle.getString("waypoints").equals("N/A")
					&& !bundle.getString("waypoints").equals("")) {

				String[] waypointsArray = bundle.getString("waypoints").split("#");
				
				String[] bundleparams = new String[waypointsArray.length + 2];

				bundleparams[0] = bundle.getString("start").replace(" ", "");
				bundleparams[1] = bundle.getString("destination").replace(" ",
						"");
				for (int i = 2; i < bundleparams.length; i++) {

					bundleparams[i] = waypointsArray[i-2];
				}

				params = bundleparams;

			} else {

		

				String[] bundleparams = {
						bundle.getString("start").replace(" ", ""),
						bundle.getString("destination").replace(" ", "") };
				params = bundleparams;
			}
			

			route = new GoogleAPIService().execute(params).get();


			route.drawRoute(mMap, route);
			route.drawMarkers(mMap, route);
			if (bundle != null) {

				String[] waypoints = bundle.getString("waypoints").split("#");

				// route = new Route();
				route.setRouteid(Integer.parseInt(bundle.getString("routeid")));
				route.setStart(bundle.getString("start"));
				route.setDestination(bundle.getString("destination"));
				route.setWayPointsTitles(waypoints);
				route.setCapacity(Integer.parseInt(bundle.getString("capacity")));
				route.setPassengers(Integer.parseInt(bundle
						.getString("passengers")));
				route.setDate(bundle.getString("date"));
				route.setTime(bundle.getString("time"));
				route.setName(bundle.getString("name"));

			}
			String wayPointsString = "";

			for (int i = 0; i < route.getWayPointsTitles().length; i++) {
				wayPointsString += "(" + (i + 1) + ") "
						+ route.getWayPointsTitles()[i] + ".  ";

			}

			TableLayout lTableLayout;
			lTableLayout = (TableLayout) findViewById(R.id.tblayout);

			// //////////////////Route Name/////////////////////////////
			TableRow tr1 = new TableRow(this);
			tr1.setPadding(2, 2, 2, 2);
			lTableLayout.addView(tr1, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			TextView tv1 = new TextView(this);
			tv1.setText("Route Name: " + route.getName()+" @ "+route.getTime());
			tv1.setPadding(2, 2, 2, 2);
			tv1.setTextSize(12);
			tr1.addView(tv1, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT); // Can give width and height in
												// numbers also.

			// //////////////////Route Start/////////////////////////////

			TableRow tr2 = new TableRow(this);
			tr2.setPadding(2, 2, 2, 2);
			lTableLayout.addView(tr2, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			TextView tv2 = new TextView(this);
			tv2.setText("Start: " + route.getStart());
			tv2.setPadding(2, 2, 2, 2);
			tv2.setTextSize(12);
			tr2.addView(tv2, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT); // Can give width and height in
												// numbers also.

			// //////////////////Route Destination/////////////////////////////

			TableRow tr3 = new TableRow(this);
			tr3.setPadding(2, 2, 2, 2);
			lTableLayout.addView(tr3, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			TextView tv3 = new TextView(this);
			tv3.setText("Destination : " + route.getDestination());
			tv3.setPadding(2, 2, 2, 2);
			tv3.setTextSize(12);
			tr3.addView(tv3, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT); // Can give width and height in
												// numbers also.

			// //////////////////Route Waypoints/////////////////////////////

			TableRow tr4 = new TableRow(this);
			tr4.setPadding(2, 2, 2, 2);
			lTableLayout.addView(tr4, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			TextView tv4 = new TextView(this);
			tv4.setText("Stops : " + wayPointsString);
			tv4.setPadding(2, 2, 2, 2);
			tv4.setTextSize(12);
			tr4.addView(tv4, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT); // Can give width and height in
												// numbers also.

			// //////////////////Route Distance/////////////////////////////

			TableRow tr5 = new TableRow(this);
			tr5.setPadding(2, 2, 2, 2);
			lTableLayout.addView(tr5, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			TextView tv5 = new TextView(this);
			tv5.setText("Distance : " + route.getLength() + " mi");
			tv5.setPadding(2, 2, 2, 2);
			tv5.setTextSize(12);
			tr5.addView(tv5, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT); // Can give width and height in
												// numbers also.

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void join_click(View v) {

		
		try {
			bundle = getIntent().getExtras();
			EditText stop = (EditText) findViewById(R.id.editWayPoints);
			
			if(stop.getText().toString().length()==0){
				Toast.makeText(getApplicationContext(), "Please Enter A Stop",
						Toast.LENGTH_LONG).show();
				return;
			}
			
			String[] params = { route.getStart(), route.getDestination(),
					stop.getText().toString().replace(" ", "") };
			Log.i(getClass().getSimpleName(), "before GoogleAPI JOIN: "
					+ "st: " + params[0] + "De:" + params[1] + "WP" + params[2]);

			// route = new GoogleAPIService().execute(params).get();
			Intent intent = new Intent(this, JoinRoute.class);
			// Bundle bundle = new Bundle();

			

			if (route.getWayPointsTitles()[0].equals("N/A")) {
				String[] putString1 = { params[2] };
				route.setWayPointsTitles(putString1);

				
			} else {

				String wayPointsString = route.getWayPointsTitles()[0];
				for (int i = 1; i < route.getWayPointsTitles().length; i++) {
					wayPointsString += "#" + route.getWayPointsTitles()[i];
				}
				wayPointsString += "#" + params[2];
				String[] putString2 = wayPointsString.split("#");
				route.setWayPointsTitles(putString2);
				Log.i(getClass().getSimpleName(), "I AM IN THE ELSE STATEMENT"
						+ wayPointsString);
			}

			String wayPointsString = route.getWayPointsTitles()[0];
			if (route.getWayPointsTitles().length > 1) {
				for (int i = 1; i < route.getWayPointsTitles().length; i++) {
					wayPointsString += "#" + route.getWayPointsTitles()[i];
				}
			}
			
			bundle.putString("routeid", bundle.getString("routeid"));
			bundle.putString("start", route.getStart());
			bundle.putString("destination", route.getDestination());
			bundle.putString("waypoints", wayPointsString);
			bundle.putString("length", route.getLength() + "");
			bundle.putString("capacity", bundle.getString("capacity") + "");
			bundle.putString("passengers", bundle.getString("passengers") + "");
			bundle.putString("date", bundle.getString("date") + "");
			bundle.putString("time", bundle.getString("time") + "");
			bundle.putString("name", route.getName().toString());
			intent.putExtras(bundle);
			startActivity(intent);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void save_click(View v) {
		try {
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			//

			String wayPointsString = route.getWayPointsTitles()[0];
			if (route.getWayPointsTitles().length > 1) {
				for (int i = 1; i < route.getWayPointsTitles().length; i++) {
					wayPointsString += "#" + route.getWayPointsTitles()[i];
				}
			}

			Log.i(getClass().getSimpleName(), "save clicked!!!!!!!!!"
					+ "\nparameter start: " + route.getStart()
					+ "\nparameter start: " + route.getDestination()
					+ "\nparameter start: " + route.getWayPointsTitles()
					+ "\nparameter start: " + route.getLength()
					+ "\nparameter start: " + route.getCapacity()
					+ "\nparameter start: " + route.getPassengers()
					+ "\nparameter start: "
					+ route.getDate()// df.format(route.getDate())
					+ "\nparameter start: " + route.getTime()
					+ "\nparameter start: " + route.getName());
			String allRoutes = new DatabaseService().execute("updateRoute",
					route.getRouteid() + "", route.getStart(),
					route.getDestination(), wayPointsString,
					route.getLength() + "", route.getCapacity() + "",
					route.getPassengers() + "", route.getDate(),// df.format(route.getDate()),
					route.getTime().toString(), route.getName()).get();

			Toast.makeText(getApplicationContext(), "Route Joined Successfully",
					Toast.LENGTH_LONG).show();
			
			this.finish();
			Intent intent = new Intent(this, HomePage.class);
			startActivity(intent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
