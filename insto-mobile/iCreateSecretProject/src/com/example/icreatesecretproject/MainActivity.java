package com.example.icreatesecretproject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.icreatesecretproject.TakePhoto.TakePhotoActivity;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends BaseActivity {

	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private static final String PROPERTY_ON_SERVER_EXPIRATION_TIME = "onServerExpirationTimeMs";
	Button button1;
	Button button2;
	Button button3;

	boolean button1empty;
	boolean button2empty;
	boolean button3empty;
	/**
	 * Default lifespan (7 days) of a reservation until it is considered
	 * expired.
	 */
	public static final long REGISTRATION_EXPIRY_TIME_MS = 1000 * 3600 * 24 * 7;

	/**
	 * Substitute you own sender ID here.
	 */
	String SENDER_ID = "210989305308";

	/**
	 * Tag used on log messages.
	 */
	static final String TAG = "GCMDemo";

	TextView mDisplay;
	GoogleCloudMessaging gcm;
	AtomicInteger msgId = new AtomicInteger();
	SharedPreferences prefs;
	Context context;

	String regid;

	boolean ready;
	ArrayList<ArrayList<Location>> locations = new ArrayList<ArrayList<Location>>();

	// SectionsPagerAdapter mSectionsPagerAdapter;
	// ViewPager mViewPager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// customize the SlidingMenu
		// getActionBar().setBackgroundDrawable(
		// new ColorDrawable(Color.parseColor("#A62A00")));
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

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

		Log.e("MAIN ACTIVITY", "first");
		setContentView(R.layout.activity_main);

		Log.e("MAIN ACTIVITY", "second");

		URL url;

		GCMRegistrar.checkManifest(this);

		context = getApplicationContext();
		regid = getRegistrationId(context);

		if (regid.length() == 0) {
			registerBackground();
		} else {
			registerWithInstoBackend(regid);
		}
		gcm = GoogleCloudMessaging.getInstance(this);
		setupPins();
	}

	private void setupPins() {
		button1 = (Button) findViewById(R.id.pin1);
		button2 = (Button) findViewById(R.id.pin2);
		button3 = (Button) findViewById(R.id.pin3);

		loadPins();

	}

	private void loadPins() {
		String url = "http://insto-web.herokuapp.com/user/"
				+ InstoApplication.instance.getUserInfo().getUser();
		AQuery aq = new AQuery(this);
		aq.ajax(url, JSONObject.class, this, "loadPinsJsonCallback");
	}

	public void loadPinsJsonCallback(String url, JSONObject json,
			AjaxStatus status) {
		Log.i("loadPinsJsonCallback", url);
		try {
			final String pin1 = json.getString("pin1");
			final String pin2 = json.getString("pin2");
			final String pin3 = json.getString("pin3");

			button1.setEnabled(true);
			button2.setEnabled(true);
			button3.setEnabled(true);
			if (pin1 == null || pin1.equals("null")) {
				button1.setText("Click to pin locations here!");
				button1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// launch notification;
						pinLoaction(0, v);
					}
				});
			} else {
				button1.setText("Location: " + pin1);
				button1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// Intent intent = new (getContext(), )
					}

				});
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void callback(String url, String str, AjaxStatus status) {
		System.out.println(str);
	}

	public void switchContent(Fragment fragment) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.dashboard_frame, fragment).commit();
		getSlidingMenu().showContent();
	}

	@Override
	public void onBackPressed() {
		getSlidingMenu().toggle();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.take_photo, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.camera_icon:
			Intent intent2 = new Intent(getBaseContext(),
					TakePhotoActivity.class);
			startActivity(intent2);
			break;
		}
		return false;
	}

	/**
	 * Gets the current registration id for application on GCM service.
	 * <p>
	 * If result is empty, the registration has failed.
	 * 
	 * @return registration id, or empty string if the registration is not
	 *         complete.
	 */
	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.length() == 0) {
			Log.v(TAG, "Registration not found.");
			return "";
		}
		// check if app was updated; if so, it must clear registration id to
		// avoid a race condition if GCM sends a message
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion || isRegistrationExpired()) {
			Log.v(TAG, "App version changed or registration expired.");
			return "";
		}
		System.out.println(registrationId);
		return registrationId;
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGCMPreferences(Context context) {
		return getSharedPreferences(MainActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	/**
	 * Checks if the registration has expired.
	 * 
	 * <p>
	 * To avoid the scenario where the device sends the registration to the
	 * server but the server loses it, the app developer may choose to
	 * re-register after REGISTRATION_EXPIRY_TIME_MS.
	 * 
	 * @return true if the registration has expired.
	 */
	private boolean isRegistrationExpired() {
		final SharedPreferences prefs = getGCMPreferences(context);
		// checks if the information is not stale
		long expirationTime = prefs.getLong(PROPERTY_ON_SERVER_EXPIRATION_TIME,
				-1);
		return System.currentTimeMillis() > expirationTime;
	}

	/**
	 * Stores the registration id, app versionCode, and expiration time in the
	 * application's {@code SharedPreferences}.
	 * 
	 * @param context
	 *            application's context.
	 * @param regId
	 *            registration id
	 */
	private void setRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);
		Log.v(TAG, "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		long expirationTime = System.currentTimeMillis()
				+ REGISTRATION_EXPIRY_TIME_MS;

		Log.v(TAG, "Setting registration expiry time to "
				+ new Timestamp(expirationTime));
		editor.putLong(PROPERTY_ON_SERVER_EXPIRATION_TIME, expirationTime);
		editor.commit();
	}

	/**
	 * Registers the application with GCM servers asynchronously.
	 * <p>
	 * Stores the registration id, app versionCode, and expiration time in the
	 * application's shared preferences.
	 */
	@SuppressWarnings("unchecked")
	private void registerBackground() {
		new AsyncTask() {
			@Override
			protected String doInBackground(Object... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					regid = gcm.register(SENDER_ID);
					msg = "Device registered, registration id=" + regid;

					// You should send the registration ID to your server over
					// HTTP,
					// so it can use GCM/HTTP or CCS to send messages to your
					// app.

					// For this demo: we don't need to send it because the
					// device
					// will send upstream messages to a server that echo back
					// the message
					// using the 'from' address in the message.

					// Save the regid - no need to register again.
					setRegistrationId(context, regid);
					registerWithInstoBackend(regid);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
				}
				return msg;
			}

			@Override
			protected void onPostExecute(Object msg) {
				Log.v(TAG, msg.toString() + "\n");
			}
		}.execute(null, null, null);
	}

	private void registerWithInstoBackend(String regId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("registration_id", regId);
		System.out.println(regId);
		params.put("user_id", InstoApplication.instance.getUserInfo().getId());
		System.out.println(InstoApplication.instance.getUserInfo().getId());
		AQuery aq = new AQuery(this);
		aq.ajax("http://insto-web.herokuapp.com/user/register", params,
				JSONObject.class, new AjaxCallback<JSONObject>() {
					@Override
					public void callback(String url, JSONObject json,
							AjaxStatus status) {
						System.out.println(json.toString());
					}
				});
	}

	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			mDisplay.append(newMessage + "\n");
		}
	};

	protected void pinLoaction(final int pinNo, View v) {
		final Dialog dialog = new Dialog(v.getContext());
		dialog.setContentView(R.layout.dialog_select_photo_location);
		dialog.setTitle("Select Location");

		final Spinner spinnerFaculty = (Spinner) dialog
				.findViewById(R.id.spinner_faculty);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				v.getContext(), R.array.locations,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// // Apply the adapter to the spinner
		spinnerFaculty.setAdapter(adapter);

		final Spinner spinnerId = (Spinner) dialog
				.findViewById(R.id.spinner_id);
		ArrayAdapter<CharSequence> adapterId = ArrayAdapter.createFromResource(
				v.getContext(), R.array.arts_name,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapterId
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinnerId.setAdapter(adapterId);

		spinnerFaculty.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Spinner spinnerId = (Spinner) dialog
						.findViewById(R.id.spinner_id);
				ArrayAdapter<Location> adapterId = null;
				switch (position) {
				case 0:
					adapterId = new ArrayAdapter<Location>(view.getContext(),
							android.R.layout.simple_spinner_item, locations
									.get(position));
					break;
				case 1:
					adapterId = new ArrayAdapter<Location>(view.getContext(),
							android.R.layout.simple_spinner_item, locations
									.get(position));
					break;
				case 2:
					adapterId = new ArrayAdapter<Location>(view.getContext(),
							android.R.layout.simple_spinner_item, locations
									.get(position));
					break;
				case 3:
					adapterId = new ArrayAdapter<Location>(view.getContext(),
							android.R.layout.simple_spinner_item, locations
									.get(position));
					break;
				case 4:
					adapterId = new ArrayAdapter<Location>(view.getContext(),
							android.R.layout.simple_spinner_item, locations
									.get(position));
					break;
				case 5:
					adapterId = new ArrayAdapter<Location>(view.getContext(),
							android.R.layout.simple_spinner_item, locations
									.get(position));
					break;
				case 6:
					adapterId = new ArrayAdapter<Location>(view.getContext(),
							android.R.layout.simple_spinner_item, locations
									.get(position));
					break;
				case 7:
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

		Button send_button = (Button) dialog.findViewById(R.id.send_button);
		send_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int location_id = ((Location) spinnerId.getSelectedItem())
						.getLocation_id();
				AQuery aq = new AQuery(getBaseContext());
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("location_id", location_id);
				params.put("user_id", InstoApplication.instance.getUserInfo()
						.getId());
				params.put("pin", pinNo);
				aq.ajax("http://insto-web.herokuapp.com/user/pin", params,
						JSONObject.class, new AjaxCallback<JSONObject>() {
							@Override
							public void callback(String url, JSONObject json,
									AjaxStatus status) {
								Log.i("pin success", json.toString());

							}
						});
			}

		});

		dialog.show();

	}

}
