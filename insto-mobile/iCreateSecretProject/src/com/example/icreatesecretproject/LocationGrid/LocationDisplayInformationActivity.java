package com.example.icreatesecretproject.LocationGrid;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.example.icreatesecretproject.R;

public class LocationDisplayInformationActivity extends Activity {
	AQuery aq;
	ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_in_faculty);

		aq = new AQuery(this);
		// lv = (ListView) findViewById(R.id.list_view);
		// lv.setAdapter(new LocationDisplayInformationAdapter(this, null));
		getImages();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.location_display_information, menu);
		return true;
	}

	public void getImages() {
		String url = "http://insto-web.herokuapp.com/location/1/submission";
		Log.i("LOCATION IN FACULTY ACTION)", "enter");
		aq.ajax(url, JSONArray.class, this, "jsonCallback");
	}

	public void jsonCallback(String url, JSONArray json, AjaxStatus status) {
		Log.i("LOCATION DISPLAY INFORMATION", url + " " + status.getCode()
				+ "\n" + json.toString());
		System.out.println(json);
		// TextView tv = (TextView) findViewById(R.id.text_view);
		// tv.setText(json.toString());

		lv = (ListView) findViewById(R.id.list_view);
		lv.setAdapter(new LocationDisplayInformationAdapter(this, json));
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
