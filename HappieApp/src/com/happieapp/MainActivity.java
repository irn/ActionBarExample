package com.happieapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences preferences = getSharedPreferences(getString(R.string.pref_file_name), Context.MODE_PRIVATE);
		int companyId = preferences.getInt(getString(R.string.pref_company_id), 0);
		Intent nextActivity = null;
		if (companyId == 0){
			nextActivity = new Intent(this, HappieAppActivity.class);
		} else {
			nextActivity = new Intent(this, WelcomeActivity.class);
		}
		startActivity(nextActivity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
