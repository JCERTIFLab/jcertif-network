package com.jcertif.reseaujcertif.interfaces.ihm;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.jcertif.reseaujcertif.R;

public class MainActivity extends SherlockActivity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent = new Intent(MainActivity.this, Home.class);
     	startActivity(intent);
		
     	getSupportActionBar().hide();
	}	

	protected void onStop() {
		super.onStop();
		finish();
	}

}