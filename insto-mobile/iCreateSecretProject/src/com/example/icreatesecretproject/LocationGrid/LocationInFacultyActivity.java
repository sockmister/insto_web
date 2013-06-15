package com.example.icreatesecretproject.LocationGrid;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.example.icreatesecretproject.R;

public class LocationInFacultyActivity extends Activity {

	AQuery aq;
	ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_in_faculty);

		lv = (ListView) findViewById(R.id.list_view);
		lv.setAdapter(new LocationInFacultyAdapter(this, null));

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Intent intent = new Intent(getBaseContext(),
						LocationDisplayInformationActivity.class);
				startActivity(intent);
				// Toast.makeText(LocationGridActivity.this, "" + position,
				// Toast.LENGTH_SHORT).show();
			}
		});
		// aq = new AQuery(this);
		// getLocationPlaces();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.location_in_faculty, menu);
		return true;
	}

	public void getLocationPlaces() {
		String url = "http://penbites.info.tm/location/soc";
		Log.i("LOCATION IN FACULTY ACTION)", "enter");
		aq.ajax(url, JSONArray.class, this, "jsonCallback");

	}

	public void jsonCallback(String url, JSONArray json, AjaxStatus status) {
		Log.i("LOCATION IN FACULTY ACTION", url + " " + status.getCode());
		System.out.println(json);
		TextView tv = (TextView) findViewById(R.id.text_view);
		tv.setText(json.toString());

		lv = (ListView) findViewById(R.id.list_view);
		lv.setAdapter(new LocationInFacultyAdapter(this, json));
		// if(json != null){
		// //successful ajax call
		// TextView tv = (TextView) findViewById(R.id.text_view);
		// tv.setText(json.toString());
		// }else{
		// //ajax error
		// TextView tv = (TextView) findViewById(R.id.text_view);
		// tv.setText(json.toString());
		// }

	}

}