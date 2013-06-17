package com.example.icreatesecretproject.LocationGrid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.example.icreatesecretproject.R;

public class LocationGridActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_list);

		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new LocationGridAdapter(this));

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Intent intent = new Intent(getBaseContext(),
						LocationInFacultyActivity.class);
				intent.putExtra("facultyId", position);
				startActivity(intent);
				Toast.makeText(LocationGridActivity.this, "" + position,
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.location_list, menu);
		return true;
	}

}
