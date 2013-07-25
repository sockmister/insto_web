package com.example.icreatesecretproject.LocationGrid;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.icreatesecretproject.BaseActivity;
import com.example.icreatesecretproject.Location;
import com.example.icreatesecretproject.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LocationGridActivity extends BaseActivity {

	ArrayList<ArrayList<Location>> locations = new ArrayList<ArrayList<Location>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_list);

		AQuery aq = new AQuery(this);
		aq.ajax("http://insto-web.herokuapp.com/request/all", JSONArray.class,
				new AjaxCallback<JSONArray>() {
					@Override
					public void callback(String url, JSONArray json,
							AjaxStatus status) {
						try {
							Gson g = new Gson();
							Type collectionType = new TypeToken<ArrayList<Location>>() {
							}.getType();
							for (int i = 0; i < json.length(); i++) {
								ArrayList<Location> temp = g.fromJson(
										json.get(i).toString(), collectionType);
								locations.add(temp);
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new LocationGridAdapter(this));

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Intent intent = new Intent(getBaseContext(),
						LocationInFacultyActivity.class);
				intent.putExtra("facultyId", position);
				startActivity(intent);
				overridePendingTransition(R.anim.from_out_slide_left,
						R.anim.from_in_slide_left);

			}
		});
	}

}
