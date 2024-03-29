package com.moe.myapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

	private String username;
	private String password;

	EditText eTextUsername;
	EditText eTextPassword;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

	

		
		
		 Button mButton = (Button)findViewById(R.id.button1);
		 
	
	}
	
	public void login_click(View v){
	
		
		try {
			if(checkOnlineState()){
			Intent intent = new Intent(this, HomePage.class);
			startActivity(intent);
			}else{Toast.makeText(getApplicationContext(), "No Internet Connection !! Please make sure you are connected to the internet",
					Toast.LENGTH_LONG).show();}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	
	
	}
	public boolean checkOnlineState() {
	    ConnectivityManager CManager =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo NInfo = CManager.getActiveNetworkInfo();
	    if (NInfo != null && NInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
}
