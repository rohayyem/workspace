package com.moe.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddRoute extends Activity {
	EditText start;
	EditText destination;
	EditText capacity;
	TimePicker time;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_route);
		 start =(EditText)findViewById(R.id.editTextFrom);
		 destination =(EditText)findViewById(R.id.editTextTo);
		 capacity =(EditText)findViewById(R.id.editTextCapacity);
		 time =(TimePicker)findViewById(R.id.timePicker1);
		
	}
	
	public void viewRoute_click(View v)
	{
		
		
		
		try {
			 start =(EditText)findViewById(R.id.editTextFrom);
			 destination =(EditText)findViewById(R.id.editTextTo);
			 capacity =(EditText)findViewById(R.id.editTextCapacity);
			 time =(TimePicker)findViewById(R.id.timePicker1);
			
			
			 if(start.getText().toString().length()==0|destination.getText().toString().length()==0|capacity.getText().toString().length()==0){
				Toast.makeText(getApplicationContext(), "Please Input All Parameters",
						Toast.LENGTH_LONG).show();
				return;
			}
			
				
			Intent intent = new Intent(this, ViewRoute.class);
			
			Bundle bundle=new Bundle();
			bundle.putString("routeid", "0");
			bundle.putString("passengers", "0");
			bundle.putString("length", "0");
			//bundle.putString("name", "0");
			bundle.putString("waypoints", "N/A");
			bundle.putString("start", start.getText().toString().replace(" ", ""));
			bundle.putString("destination", destination.getText().toString().replace(" ", ""));
			bundle.putString("capacity", capacity.getText().toString());
			bundle.putString("time", time.getCurrentHour().toString() + ":" + time.getCurrentMinute().toString() + ":00");
			bundle.putString("date","12/12/2012");
			intent.putExtras(bundle);
			this.finish();
			startActivity(intent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	

}
