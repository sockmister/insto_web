package com.example.icreatesecretproject.LocationGrid;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.example.icreatesecretproject.BaseSubActivity;
import com.example.icreatesecretproject.Location;
import com.example.icreatesecretproject.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LocationInFacultyActivity extends BaseSubActivity {

	AQuery aq;
	ListView lv;
	ProgressBar pb;
	
	ArrayList<Location> locations;
	int location_id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_in_faculty);
		aq = new AQuery(this);
		lv = (ListView) findViewById(R.id.list_view);
		// lv.setAdapter(new LocationInFacultyAdapter(this, null));

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				Intent intent = new Intent(getBaseContext(),
						LocationDisplayInformationActivity.class);
				location_id = locations.get(position).getLocation_id();
				intent.putExtra("locationId", location_id);
				startActivity(intent);
				overridePendingTransition(R.anim.from_out_slide_left,
						R.anim.from_in_slide_left);
				// Toast.makeText(LocationGridActivity.this, "" + position,
				// Toast.LENGTH_SHORT).show();
			}
		});
		pb = (ProgressBar) this.findViewById(R.id.progressBar2);
		getLocationPlaces();
	}

	public void getLocationPlaces() {
		showProgress(true);
		Intent intent = getIntent();
		int facultyId = intent.getIntExtra("facultyId", 0);
		String[] location = { "arts", "biz", "computing", "engineering",
				"medicine", "science", "sde", "utown" };

		String url = "http://insto-web.herokuapp.com/location/"
				+ location[facultyId];
		Log.i("LOCATION IN FACULTY ACTION)", "enter");
		aq.ajax(url, JSONArray.class, this, "jsonCallback");

	}

	public void jsonCallback(String url, JSONArray json, AjaxStatus status) {
		Log.i("LOCATION IN FACULTY ACTION", url + " " + status.getCode());
		System.out.println(json);
		// TextView tv = (TextView) findViewById(R.id.text_view);
		// tv.setText(json.toString());
		
		Gson g = new Gson();
		Type collectionType = new TypeToken<ArrayList<Location>>(){}.getType();
		locations = g.fromJson(json.toString(), collectionType);
		lv = (ListView) findViewById(R.id.list_view);
		lv.setAdapter(new LocationInFacultyAdapter(this, json));
		showProgress(false);
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
	
	public void showProgress(boolean show) {
		pb.setVisibility(show ? View.VISIBLE : View.GONE);
		lv.setVisibility(show ? View.GONE : View.VISIBLE);
	}

}
