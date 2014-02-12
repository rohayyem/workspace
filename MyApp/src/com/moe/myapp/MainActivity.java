package com.moe.myapp;


import java.util.ArrayList;
import java.util.List;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;

import android.view.Menu;

public class MainActivity extends Activity {

	private GoogleMap mMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try{
			
			GoogleMapOptions options = new GoogleMapOptions();

			mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
					.getMap();
			mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			mMap.setMyLocationEnabled(true);
			mMap.getMyLocation();
			
			String [] params={"1303HillsideTerrAlexandriaVA","FoggyBottomWashingtonDC"};
			
			Route route = new GoogleAPIService().execute(params).get();
			
			route.drawRoute(mMap, route);
			route.drawMarkers(mMap, route);
			

		}catch(Exception e){
			e.printStackTrace();
		}
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		try{
		if(resultCode==RESULT_OK){
			Bundle bundle=data.getExtras();
			Route route=(Route) bundle.getSerializable("Route");
			
			PolylineOptions polylineOptions = new PolylineOptions();

			List<LatLng> l = new ArrayList<LatLng>();
            l=route.getPoints();
			polylineOptions.addAll(l);
			polylineOptions.color(Color.YELLOW);
			polylineOptions.width(20);
			mMap.addPolyline(polylineOptions);
		}
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
