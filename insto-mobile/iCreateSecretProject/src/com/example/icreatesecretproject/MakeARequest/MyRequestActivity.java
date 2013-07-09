package com.example.icreatesecretproject.MakeARequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.example.icreatesecretproject.LocationGrid.LocationDisplayInformationActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MyRequestActivity extends BaseActivity {

	ArrayList<ArrayList<Location>> locations = new ArrayList<ArrayList<Location>>();
	boolean ready = false;
	Spinner spinnerFaculty;
	Spinner spinnerId;
	ArrayAdapter adapterId;
	LinearLayout form;
	ProgressBar pb;
	ListView lv;
	Button addRequest;
	ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_request_2);
		lv = (ListView) findViewById(R.id.my_request_list);
		addRequest = (Button) findViewById(R.id.add_request_button);
		loadMyRequest();
		loadLocationArray();
		setupAddButton();

	}

	private void setupAddButton() {
		addRequest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(v.getContext());
				dialog.setContentView(R.layout.activity_my_request);
				dialog.setTitle("Select Location");

				spinnerFaculty = (Spinner) dialog
						.findViewById(R.id.spinner_faculty);
				ArrayAdapter<CharSequence> adapter = ArrayAdapter
						.createFromResource(v.getContext(), R.array.locations,
								android.R.layout.simple_spinner_item);
				// Specify the layout to use when the list of choices appears
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// // Apply the adapter to the spinner
				spinnerFaculty.setAdapter(adapter);

				spinnerId = (Spinner) dialog.findViewById(R.id.spinner_id);
				ArrayAdapter<CharSequence> adapterId = ArrayAdapter
						.createFromResource(v.getContext(), R.array.arts_name,
								android.R.layout.simple_spinner_item);
				// Specify the layout to use when the list of choices appears
				adapterId
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// Apply the adapter to the spinner
				spinnerId.setAdapter(adapterId);

				spinnerFaculty
						.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> parent,
									View view, int position, long id) {
								Spinner spinnerId = (Spinner) dialog
										.findViewById(R.id.spinner_id);
								ArrayAdapter<Location> adapterId = null;
								switch (position) {
								case 0:
									// adapterId =
									// ArrayAdapter.createFromResource(
									// view.getContext(),
									// R.array.arts_name,
									// android.R.layout.simple_spinner_item);
									Log.i("LINE - 217", locations.toString());
									adapterId = new ArrayAdapter<Location>(
											view.getContext(),
											android.R.layout.simple_spinner_item,
											locations.get(position));
									break;
								case 1:
									// adapterId =
									// ArrayAdapter.createFromResource(
									// view.getContext(),
									// R.array.business_name,
									// android.R.layout.simple_spinner_item);
									adapterId = new ArrayAdapter<Location>(
											view.getContext(),
											android.R.layout.simple_spinner_item,
											locations.get(position));
									break;
								case 2:
									// adapterId =
									// ArrayAdapter.createFromResource(
									// view.getContext(),
									// R.array.computing_name,
									// android.R.layout.simple_spinner_item);
									adapterId = new ArrayAdapter<Location>(
											view.getContext(),
											android.R.layout.simple_spinner_item,
											locations.get(position));
									break;
								case 3:
									// adapterId =
									// ArrayAdapter.createFromResource(
									// view.getContext(),
									// R.array.engineering_name,
									// android.R.layout.simple_spinner_item);
									adapterId = new ArrayAdapter<Location>(
											view.getContext(),
											android.R.layout.simple_spinner_item,
											locations.get(position));
									break;
								case 4:
									// adapterId =
									// ArrayAdapter.createFromResource(
									// view.getContext(),
									// R.array.medicine_name,
									// android.R.layout.simple_spinner_item);
									adapterId = new ArrayAdapter<Location>(
											view.getContext(),
											android.R.layout.simple_spinner_item,
											locations.get(position));
									break;
								case 5:
									// adapterId =
									// ArrayAdapter.createFromResource(
									// view.getContext(),
									// R.array.science_name,
									// android.R.layout.simple_spinner_item);
									adapterId = new ArrayAdapter<Location>(
											view.getContext(),
											android.R.layout.simple_spinner_item,
											locations.get(position));
									break;
								case 6:
									// adapterId =
									// ArrayAdapter.createFromResource(
									// view.getContext(),
									// R.array.sde_name,
									// android.R.layout.simple_spinner_item);
									adapterId = new ArrayAdapter<Location>(
											view.getContext(),
											android.R.layout.simple_spinner_item,
											locations.get(position));
									break;
								case 7:
									// adapterId =
									// ArrayAdapter.createFromResource(
									// view.getContext(),
									// R.array.utown_name,
									// android.R.layout.simple_spinner_item);
									adapterId = new ArrayAdapter<Location>(
											view.getContext(),
											android.R.layout.simple_spinner_item,
											locations.get(position));
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

				Button send_button = (Button) dialog
						.findViewById(R.id.send_button);
				setUpSendButton(send_button, dialog);
				dialog.show();
				//
				// Log.i("TakePhotoActivity - send picture", "sad");
				// Log.i("TakePhotoActivity - send picture",
				// savedPictureFile.toString());
				//
			}

		});
	}

	protected void setUpSendButton(Button send_button, final Dialog dialog) {
		send_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				progressDialog = ProgressDialog.show(MyRequestActivity.this,
						"", "Loading...");
				// location id
				int location_id = (spinnerFaculty.getSelectedItemPosition() * 10)
						+ (spinnerId.getSelectedItemPosition());
				location_id = locations
						.get(spinnerFaculty.getSelectedItemPosition())
						.get(spinnerId.getSelectedItemPosition())
						.getLocation_id();

				EditText ed = (EditText) dialog.findViewById(R.id.message);
				String message = ed.getText().toString();

				int userid = InstoApplication.instance.getUserInfo().getId();

				AQuery aq = new AQuery(v.getContext());

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("request[location_id]", location_id);
				params.put("request[user_id]", userid);
				params.put("request[message]", message);

				MyRequestActivity.this.showProgress(true);
				aq.ajax("http://insto-web.herokuapp.com/request", params,
						JSONObject.class, new AjaxCallback<JSONObject>() {
							@Override
							public void callback(String url, JSONObject json,
									AjaxStatus status) {
								Log.i("MyRequestActivity - send return",
										json.toString());
								MyRequestActivity.this.showProgress(false);
								EditText ed = (EditText) dialog
										.findViewById(R.id.message);
								ed.setText("");
								progressDialog.dismiss();
								if (json.has("error")) {
									Toast.makeText(getApplicationContext(),
											"You have made a request already!",
											Toast.LENGTH_LONG).show();
								} else {
									if (dialog != null)
										dialog.dismiss();
									Toast.makeText(getApplicationContext(),
											"Request sent", Toast.LENGTH_LONG)
											.show();
									loadMyRequest();
								}
							}
						});
			}

		});
	}

	private void loadLocationArray() {
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
							ready = true;
							// addRequest.setAlpha(0.35f);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

	}

	private void loadMyRequest() {
		String url = "http://insto-web.herokuapp.com/user/"
				+ InstoApplication.instance.getUserInfo().getId() + "/request";
		AQuery aq = new AQuery(this);
		aq.ajax(url, JSONArray.class, this, "loadRequestJsonCallback");
	}

	public void loadRequestJsonCallback(String url, final JSONArray json,
			AjaxStatus status) {
		Log.i("MY REQUEST", json.toString());
		// locations = g.fromJson(json.toString(), collectionType);
		// lv = (ListView) findViewById(R.id.list_view);
		final JSONArray reqArray;
		try {
			reqArray = json.getJSONArray(0);
			lv.setAdapter(new MyRequestAdapter(this, reqArray));
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> av, View v,
						int position, long arg3) {
					Intent intent = new Intent(getBaseContext(),
							LocationDisplayInformationActivity.class);
					try {
						intent.putExtra(
								"locationId",
								reqArray.getJSONObject(position).getInt(
										"location_id"));
						Log.i("MY REQUEST- loc id",
								reqArray.getJSONObject(position).getInt(
										"location_id")
										+ "");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					startActivity(intent);

				}

			});

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.take_photo, menu);
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
		// pb.setVisibility(show ? View.VISIBLE : View.GONE);
		// form.setVisibility(show ? View.GONE : View.VISIBLE);
	}
}
