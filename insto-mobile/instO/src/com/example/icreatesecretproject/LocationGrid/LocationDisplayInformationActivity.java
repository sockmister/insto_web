package com.example.icreatesecretproject.LocationGrid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.example.icreatesecretproject.BaseSubActivity;
import com.example.icreatesecretproject.InstoApplication;
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

	TextView latestTime;
	TextView earliestTime;
	TextView date;
	TextView gleamPoints;
	TextView requestFulfilledText;
	Button addGleam;
	Button minusGleam;
	Button requestFulfilled;
	LinearLayout emMsg;
	JSONArray ja;

	RelativeLayout addGleamLayout;
	RelativeLayout requestFulfilledLayout;

	int currPosition = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.test_gallery);
		aq = new AQuery(this);

		latestTime = (TextView) findViewById(R.id.latest_time);
		earliestTime = (TextView) findViewById(R.id.earliest_time);
		date = (TextView) findViewById(R.id.date);
		gleamPoints = (TextView) findViewById(R.id.gleam);
		addGleam = (Button) findViewById(R.id.button_add_gleam);
		requestFulfilled = (Button) findViewById(R.id.button_request_fulfilled);

		addGleamLayout = (RelativeLayout) findViewById(R.id.gleam_layout);
		requestFulfilledLayout = (RelativeLayout) findViewById(R.id.request_fulfilled_layout);
		emMsg = (LinearLayout) findViewById(R.id.empty_msg);
		requestFulfilledText = (TextView) findViewById(R.id.request_fufilled_text);
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
		if (json != null || json.length() > 0) {
			emMsg.setVisibility(View.INVISIBLE);
		}
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
		String userId = InstoApplication.instance.getUserInfo().getId()
				.toString();
		String url = "http://insto-web.herokuapp.com/user/" + userId
				+ "/location/" + id + "/submission";
		Log.i("LOCATION IN FACULTY ACTION)", "enter");
		aq.ajax(url, JSONArray.class, this, "jsonCallback2");
	}

	// get submission images callback
	// chnage getImgaes2() to new url
	// set request/gleam view based on boolean
	// setup request fulfilled post
	public void jsonCallback2(String url, JSONArray json, AjaxStatus status) {
		ja = json;
		Log.i("LOCATION DISPLAY INFORMATION 2", url + " " + status.getCode()
				+ "\n" + json.toString());
		System.out.println(json);

		if (json == null || json.length() == 0) {
			emMsg.setVisibility(View.VISIBLE);
		}
		mAdapter = new PlaceSlidesFragmentAdapter(getSupportFragmentManager(),
				json, addGleamLayout);

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);
		((CirclePageIndicator) mIndicator).setSnap(true);

		mIndicator
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						currPosition = position;
						try {
							gleamPoints.setText(ja.getJSONObject(position)
									.getString("gleam"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onPageScrolled(int position,
							float positionOffset, int positionOffsetPixels) {
					}

					@Override
					public void onPageScrollStateChanged(int state) {
					}
				});

		addGleam.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i("LOCATION IN FACULTY ACTION - onclick)", "");
				Map<String, Object> params = new HashMap<String, Object>();

				try {
					String id = ja.getJSONObject(currPosition).getString("id");
					params.put("user_id", InstoApplication.instance
							.getUserInfo().getId());
					params.put("submission_id", id);
					String url = "http://insto-web.herokuapp.com/submission/gleam";
					aq.ajax(url, params, JSONArray.class,
							LocationDisplayInformationActivity.this,
							"jsonCallback3");
					Log.i("LOCATION IN FACULTY ACTION)", id);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		requestFulfilled.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("LOCATION IN FACULTY ACTION - onclick)", "");
				Map<String, Object> params = new HashMap<String, Object>();

				try {
					String id = ja.getJSONObject(currPosition).getString("id");
					params.put("user_id", InstoApplication.instance
							.getUserInfo().getId());
					params.put("submission_id", id);
					String url = "http://insto-web.herokuapp.com/submission/gleam";
					aq.ajax(url, params, JSONArray.class,
							LocationDisplayInformationActivity.this,
							"jsonCallback3");
					Log.i("LOCATION IN FACULTY ACTION)", id);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});

		if (json.length() > 0) {
			try {
				JSONObject firstO = json.getJSONObject(0);
				JSONObject lastO = json.getJSONObject(json.length() - 1);

				latestTime.setText(getTime(firstO.getString("created_at")));
				earliestTime.setText(getTime(lastO.getString("created_at")));
				date.setText(getDate(firstO.getString("created_at")));
				gleamPoints.setText(ja.getJSONObject(0).getString("gleam"));

				// if request is sent and not fulfilled, then we allow user to
				// say request fulfilled
				// if request is sent and fulfilled, then user must have
				// considered to sent gleam
				// for that particular submission
				if (firstO.get("request_sent_by_user").equals("true")
						&& firstO.get("request_fulfilled").equals("f")) {
					addGleamLayout.setVisibility(View.GONE);
					requestFulfilledLayout.setVisibility(View.VISIBLE);
					if (firstO.get("sent_gleam").equals("true")) {
						requestFulfilled.setEnabled(false);
						requestFulfilled.setAlpha(0.35f);
					}
				} else {
					addGleamLayout.setVisibility(View.VISIBLE);
					requestFulfilledLayout.setVisibility(View.GONE);
					if (firstO.get("sent_gleam").equals("true")) {
						addGleam.setEnabled(false);
						addGleam.setAlpha(0.35f);
					}

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	// post gleam callback
	public void jsonCallback3(String url, JSONArray json, AjaxStatus status) {
		Log.i("LOCATION DISPLAY INFORMATION 3", url + " " + status.getCode()
				+ "\n" + status.toString());
		System.out.println(json.toString());
		addGleam.setEnabled(false);
		addGleam.setAlpha(0.35f);
		requestFulfilled.setEnabled(false);
		requestFulfilled.setAlpha(0.35f);
		requestFulfilledText.setVisibility(View.VISIBLE);
	}

	private String getDate(String dateCreated) {
		Log.i("IN FACULTY", dateCreated);
		String _date = "";
		String _time = "";
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss'Z'");
		// format.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date;
		try {
			date = format.parse(dateCreated);
			java.text.DateFormat dateFormat = android.text.format.DateFormat
					.getDateFormat(this);
			_date = dateFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return _date;
	}

	private String getTime(String dateCreated) {
		Log.i("IN FACULTY", dateCreated);
		String _time = "";
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss'Z'");
		// format.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date;
		try {
			date = format.parse(dateCreated);
			java.text.DateFormat dateFormat = android.text.format.DateFormat
					.getTimeFormat(this);
			_time = dateFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return _time;
	}

}
