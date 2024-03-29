package com.moe.myapp;




import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;

import android.os.Bundle;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;


import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ViewRoute extends Activity {

	private GoogleMap mMap;
	Route route;
	String[] params = { "1303HillsideTerrAlexandriaVA",
			"FoggyBottomWashingtonDC" };
	String wayPointsString = "";
	String wayPointsDelimited = "";
	Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_route);

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

				String[] waypointsArray = bundle.getString("waypoints").split(
						"#");

				String[] bundleparams = new String[waypointsArray.length + 2];

				bundleparams[0] = bundle.getString("start").replace(" ", "");
				bundleparams[1] = bundle.getString("destination").replace(" ",
						"");
				for (int i = 2; i < bundleparams.length; i++) {

					bundleparams[i] = waypointsArray[i - 2];
				}

				params = bundleparams;

			} else {


				String[] bundleparams = {
						bundle.getString("start").replace(" ", ""),
						bundle.getString("destination").replace(" ", "") };
				params = bundleparams;
			}


			route = new GoogleAPIService().execute(params).get();


			if(route.getPoints().isEmpty()){
				Toast.makeText(getApplicationContext(), "Invalid Address Please Try Again",
						Toast.LENGTH_LONG).show();
				Intent intent = new Intent(this, AddRoute.class);
				this.finish();
				startActivity(intent);
			}
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

			mMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();
			mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

			mMap.setMyLocationEnabled(true);
			mMap.getMyLocation();

			String[] waypoints = null;
			Bundle bundle = getIntent().getExtras();
			
			if (bundle.getString("waypoints") != null
					&& !bundle.getString("waypoints").equals("N/A")
					&& !bundle.getString("waypoints").equals("")) {

				waypoints = bundle.getString("waypoints").split("#");
				
				route = new Route();

				String[] bundleparams = new String[2 + waypoints.length];
				bundleparams[0] = bundle.getString("start");
				bundleparams[1] = bundle.getString("destination");

				for (int i = 2; i < bundleparams.length; i++) {
					bundleparams[i] = waypoints[i - 2];
				}

				
				if (bundleparams != null)
					params = bundleparams;
			} else {

				

				String[] bundleparams = {
						bundle.getString("start").replace(" ", ""),
						bundle.getString("destination").replace(" ", "") };
				params = bundleparams;
			}
			route = new GoogleAPIService().execute(params).get();

			route.setRouteid(Integer.parseInt(bundle.getString("routeid")));
			//route.setStart(bundle.getString("start"));
			//route.setDestination(bundle.getString("destination"));
			route.setWayPointsTitles(waypoints);
			//route.setLength(Double.parseDouble(bundle.getString("length")));
			route.setCapacity(Integer.parseInt(bundle.getString("capacity")));
			route.setPassengers(Integer.parseInt(bundle.getString("passengers")));
			route.setDate(bundle.getString("date"));
			route.setTime(bundle.getString("time"));
			//route.setName(bundle.getString("name"));

			route.drawRoute(mMap, route);
			route.drawMarkers(mMap, route);



			TableLayout lTableLayout;
			lTableLayout = (TableLayout) findViewById(R.id.tblayout);

			// //////////////////Route Name/////////////////////////////
			TableRow tr1 = new TableRow(this);
			tr1.setPadding(4, 4, 4, 4);
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
			tr2.setPadding(4, 4, 4, 4);
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
			tr3.setPadding(4, 4, 4, 4);
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
			tr4.setPadding(4, 4, 4, 4);
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
			tr5.setPadding(4, 4, 4, 4);
			lTableLayout.addView(tr5, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			TextView tv5 = new TextView(this);
			tv5.setText("Distance : " + route.getLength() + " mi");
			tv5.setPadding(2, 2, 2, 2);
			tv5.setTextSize(12);
			tr5.addView(tv5, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT); // Can give width and height in
			Button join =(Button)findViewById(R.id.button1);
			Button save =(Button)findViewById(R.id.button2);
			
			Log.i(getClass().getSimpleName(), ">>>>>>> Route ID "
					+ bundle.getString("routeid"));
			
			if(bundle.getString("routeid").equals("0"))
			{
			join.setVisibility(-1);
			save.setVisibility(1);
			}
			else if(! bundle.getString("routeid").equals("0")){
				join.setVisibility(1);
				save.setVisibility(-1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void joinRoute_click(View v) {

		try {
			EditText start = (EditText) findViewById(R.id.editTextFrom);
			EditText destination = (EditText) findViewById(R.id.editTextTo);

			Intent intent = new Intent(this, JoinRoute.class);
//			String tempWayPoints[] = route.getWayPointsTitles();
			Bundle bundle = new Bundle();
//			Log.i(getClass().getSimpleName(), "waypoints test XXXXX "
//					+ tempWayPoints[0] + "length" + tempWayPoints.length);

			
			String wayPointsString;
			if (route.getWayPointsTitles() != null) {
				wayPointsString = route.getWayPointsTitles()[0];
				if (route.getWayPointsTitles().length > 1) {
					for (int i = 1; i < route.getWayPointsTitles().length; i++) {
						wayPointsString += "#" + route.getWayPointsTitles()[i];
					}
				}
			} else {
				wayPointsString = "N/A";
			}
//			Log.i(getClass().getSimpleName(),
//					"calling join Route from VIEW, WAYPOINTS: "
//							+ wayPointsString);
			bundle.putString("routeid", route.getRouteid() + "");
			bundle.putString("start", route.getStart());
			bundle.putString("destination", route.getDestination());
			bundle.putString("waypoints", wayPointsString);
			bundle.putString("length", route.getLength() + "");
			bundle.putString("capacity", route.getCapacity() + "");
			bundle.putString("passengers", route.getPassengers() + "");
			bundle.putString("date", route.getDate().toString());
			bundle.putString("time", route.getTime().toString());
			bundle.putString("name", route.getName().toString());
			intent.putExtras(bundle);
			this.finish();
			startActivity(intent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveRoute_click(View v) {

		try {
			//
//			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			String allRoutes = new DatabaseService().execute("createRoute",
					route.getStart(), route.getDestination(), wayPointsString,
					route.getLength() + "", route.getCapacity() + "",
					route.getPassengers() + "", route.getDate(),
					route.getTime().toString(), route.getName()).get();
			// String allRoutes = new
			// DatabaseService().execute("createRoute",route.getStart(),route.getDestination(),wayPointsString,route.getLength()+"",route.getCapacity()+"",route.getPassengers()+"","12/12/2012","12:12:12",route.getName()).get();
			Toast.makeText(getApplicationContext(), "Route Saved Successfully",
					Toast.LENGTH_LONG).show();
			this.finish();
			Intent intent = new Intent(this, HomePage.class);
			startActivity(intent);
			
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Error Saving Route",
					Toast.LENGTH_LONG).show();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
}
