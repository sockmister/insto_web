package com.example.icreatesecretproject.MakeARequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.icreatesecretproject.BaseActivity;
import com.example.icreatesecretproject.InstoApplication;
import com.example.icreatesecretproject.Location;
import com.example.icreatesecretproject.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MyRequestActivity extends BaseActivity {

	ArrayList<ArrayList<Location>> locations = new ArrayList<ArrayList<Location>>();
	Spinner spinnerFaculty;
	Spinner spinnerId;
	ArrayAdapter adapterId;
	LinearLayout form;
	ProgressBar pb;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_request);

		form = (LinearLayout) findViewById(R.id.login_form_layout);
		pb = (ProgressBar) findViewById(R.id.progressBar1);

		showProgress(true);
		spinnerId = (Spinner) findViewById(R.id.spinner_id);
		adapterId = ArrayAdapter.createFromResource(this, R.array.arts_name,
				android.R.layout.simple_spinner_item);

		AQuery aq = new AQuery(this);
		long expire = 60 * 60 * 1000; // 1 hr
		aq.ajax("http://insto-web.herokuapp.com/request/all", JSONArray.class,
				expire, new AjaxCallback<JSONArray>() {
					@Override
					public void callback(String url, JSONArray json,
							AjaxStatus status) {
						try {
							System.out.println(json.get(0));
							Gson g = new Gson();
							Type collectionType = new TypeToken<ArrayList<Location>>() {
							}.getType();
							for (int i = 0; i < json.length(); i++) {
								ArrayList<Location> temp = g.fromJson(
										json.get(i).toString(), collectionType);
								locations.add(temp);
							}

							adapterId = new ArrayAdapter<Location>(
									MyRequestActivity.this,
									android.R.layout.simple_spinner_item,
									locations.get(0));
							// adapterId
							// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
							setupSpinner();

							spinnerId.setAdapter(adapterId);
							MyRequestActivity.this.showProgress(false);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

		spinnerFaculty = (Spinner) findViewById(R.id.spinner_faculty);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.locations, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// // Apply the adapter to the spinner
		spinnerFaculty.setAdapter(adapter);

		Button send_button = (Button) findViewById(R.id.send_button);
		send_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// location id
				// int location_id = (spinnerFaculty.getSelectedItemPosition() *
				// 10)
				// + (spinnerId.getSelectedItemPosition());

				int location_id = ((Location) spinnerId.getSelectedItem())
						.getLocation_id();

				EditText ed = (EditText) findViewById(R.id.message);
				String message = ed.getText().toString();

				int userid = InstoApplication.instance.getUserInfo().getId();

				AQuery aq = new AQuery(v.getContext());

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("request[location_id]", location_id);
				params.put("request[user_id]", userid);
				params.put("request[message]", message);

				aq.ajax("http://insto-web.herokuapp.com/request", params,
						JSONObject.class, new AjaxCallback<JSONObject>() {
							@Override
							public void callback(String url, JSONObject json,
									AjaxStatus status) {
								Log.i("MyRequestActivity - send return",
										json.toString());
								Toast.makeText(getApplicationContext(),
										"Request sent", Toast.LENGTH_LONG)
										.show();
							}
						});
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_request, menu);
		return true;
	}

	private void setupSpinner() {
		spinnerFaculty.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Spinner spinnerId = (Spinner) findViewById(R.id.spinner_id);
				ArrayAdapter<Location> adapterId = null;
				switch (position) {
				case 0:
					// adapterId = ArrayAdapter.createFromResource(
					// view.getContext(),
					// R.array.arts_name,
					// android.R.layout.simple_spinner_item);
					adapterId = new ArrayAdapter<Location>(view.getContext(),
							android.R.layout.simple_spinner_item, locations
									.get(position));
					break;
				case 1:
					// adapterId = ArrayAdapter.createFromResource(
					// view.getContext(),
					// R.array.business_name,
					// android.R.layout.simple_spinner_item);
					adapterId = new ArrayAdapter<Location>(view.getContext(),
							android.R.layout.simple_spinner_item, locations
									.get(position));
					break;
				case 2:
					// adapterId = ArrayAdapter.createFromResource(
					// view.getContext(),
					// R.array.computing_name,
					// android.R.layout.simple_spinner_item);
					adapterId = new ArrayAdapter<Location>(view.getContext(),
							android.R.layout.simple_spinner_item, locations
									.get(position));
					break;
				case 3:
					// adapterId = ArrayAdapter.createFromResource(
					// view.getContext(),
					// R.array.engineering_name,
					// android.R.layout.simple_spinner_item);
					adapterId = new ArrayAdapter<Location>(view.getContext(),
							android.R.layout.simple_spinner_item, locations
									.get(position));
					break;
				case 4:
					// adapterId = ArrayAdapter.createFromResource(
					// view.getContext(),
					// R.array.medicine_name,
					// android.R.layout.simple_spinner_item);
					adapterId = new ArrayAdapter<Location>(view.getContext(),
							android.R.layout.simple_spinner_item, locations
									.get(position));
					break;
				case 5:
					// adapterId = ArrayAdapter.createFromResource(
					// view.getContext(),
					// R.array.science_name,
					// android.R.layout.simple_spinner_item);
					adapterId = new ArrayAdapter<Location>(view.getContext(),
							android.R.layout.simple_spinner_item, locations
									.get(position));
					break;
				case 6:
					// adapterId = ArrayAdapter.createFromResource(
					// view.getContext(),
					// R.array.sde_name,
					// android.R.layout.simple_spinner_item);
					adapterId = new ArrayAdapter<Location>(view.getContext(),
							android.R.layout.simple_spinner_item, locations
									.get(position));
					break;
				case 7:
					// adapterId = ArrayAdapter.createFromResource(
					// view.getContext(),
					// R.array.utown_name,
					// android.R.layout.simple_spinner_item);
					adapterId = new ArrayAdapter<Location>(view.getContext(),
							android.R.layout.simple_spinner_item, locations
									.get(position));
					break;
				}
				adapterId
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// Apply the adapter to the spinner
				spinnerId.setAdapter(adapterId);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
	}

	public void showProgress(boolean show) {
		pb.setVisibility(show ? View.VISIBLE : View.GONE);
		form.setVisibility(show ? View.GONE : View.VISIBLE);
	}
}
