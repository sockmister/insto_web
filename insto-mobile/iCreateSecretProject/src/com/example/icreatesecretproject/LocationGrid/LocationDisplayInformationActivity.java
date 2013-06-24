package com.example.icreatesecretproject.LocationGrid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.example.icreatesecretproject.BaseSubActivity;
import com.example.icreatesecretproject.R;
import com.example.icreatesecretproject.LocationGrid.testgallery.PlaceSlidesFragmentAdapter;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class LocationDisplayInformationActivity extends BaseSubActivity {
	AQuery aq;
	ListView lv;
	JSONArray jsonArray;

	PlaceSlidesFragmentAdapter mAdapter;
	ViewPager mPager;
	PageIndicator mIndicator;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.test_gallery);
		aq = new AQuery(this);

		getImages2(getIntent().getIntExtra("locationId", 0));
		// Set the pager with an adapter
		// mAdapter = new
		// PlaceSlidesFragmentAdapter(getSupportFragmentManager());
		//
		// mPager = (ViewPager) findViewById(R.id.pager);
		// mPager.setAdapter(mAdapter);
		//
		// mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
		// mIndicator.setViewPager(mPager);
		// ((CirclePageIndicator) mIndicator).setSnap(true);
		//
		// mIndicator
		// .setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
		// @Override
		// public void onPageSelected(int position) {
		// Toast.makeText(getBaseContext(),
		// "Changed to page " + position,
		// Toast.LENGTH_SHORT).show();
		// }
		//
		// @Override
		// public void onPageScrolled(int position,
		// float positionOffset, int positionOffsetPixels) {
		// }
		//
		// @Override
		// public void onPageScrollStateChanged(int state) {
		// }
		// });

		// setContentView(R.layout.activity_location_in_faculty);
		//
		// aq = new AQuery(this);
		// // lv = (ListView) findViewById(R.id.list_view);
		// // lv.setAdapter(new LocationDisplayInformationAdapter(this, null));
		// Intent intent = getIntent();
		// int id = intent.getIntExtra("locationId", 0);
		// getImages(id);
		//
		// lv = (ListView) findViewById(R.id.list_view);
	}

	protected void showPictureDetails(View v, int position) {
		final Dialog dialog = new Dialog(v.getContext());
		dialog.setContentView(R.layout.dialog_location_info);
		dialog.setTitle("Picture Information");

		ImageView iv = (ImageView) dialog.findViewById(R.id.image);
		AQuery aq = new AQuery(v.getContext());

		JSONObject jo;
		try {
			jo = jsonArray.getJSONObject(position);
			aq.id(iv).image(jo.getString("image_url"), true, true, 600, 0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dialog.show();

	}

	public void getImages(int id) {
		String url = "http://insto-web.herokuapp.com/location/" + id
				+ "/submission";
		Log.i("LOCATION IN FACULTY ACTION)", "enter");
		aq.ajax(url, JSONArray.class, this, "jsonCallback");
	}

	public void jsonCallback(String url, JSONArray json, AjaxStatus status) {
		Log.i("LOCATION DISPLAY INFORMATION", url + " " + status.getCode()
				+ "\n" + json.toString());
		System.out.println(json);
		// TextView tv = (TextView) findViewById(R.id.text_view);
		// tv.setText(json.toString());
		jsonArray = json;
		lv.setAdapter(new LocationDisplayInformationAdapter(this, json));
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				showPictureDetails(v, position);
			}

		});
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

	public void getImages2(int id) {
		String url = "http://insto-web.herokuapp.com/location/" + id
				+ "/submission";
		Log.i("LOCATION IN FACULTY ACTION)", "enter");
		aq.ajax(url, JSONArray.class, this, "jsonCallback2");
	}

	public void jsonCallback2(String url, JSONArray json, AjaxStatus status) {
		Log.i("LOCATION DISPLAY INFORMATION", url + " " + status.getCode()
				+ "\n" + json.toString());
		System.out.println(json);

		mAdapter = new PlaceSlidesFragmentAdapter(getSupportFragmentManager(),
				json);

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);
		((CirclePageIndicator) mIndicator).setSnap(true);

		mIndicator
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						Toast.makeText(getBaseContext(),
								"Changed to page " + position,
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onPageScrolled(int position,
							float positionOffset, int positionOffsetPixels) {
					}

					@Override
					public void onPageScrollStateChanged(int state) {
					}
				});

	}
}
