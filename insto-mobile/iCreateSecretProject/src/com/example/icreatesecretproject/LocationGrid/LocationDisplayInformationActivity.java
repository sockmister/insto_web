package com.example.icreatesecretproject.LocationGrid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.example.icreatesecretproject.R;

public class LocationDisplayInformationActivity extends Activity {
	ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_in_faculty);

		lv = (ListView) findViewById(R.id.list_view);
		lv.setAdapter(new LocationDisplayInformationAdapter(this, null));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.location_display_information, menu);
		return true;
	}

}
