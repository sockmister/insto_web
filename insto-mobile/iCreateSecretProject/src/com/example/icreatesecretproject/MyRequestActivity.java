package com.example.icreatesecretproject;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;

public class MyRequestActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_request);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_request, menu);
		return true;
	}

}
