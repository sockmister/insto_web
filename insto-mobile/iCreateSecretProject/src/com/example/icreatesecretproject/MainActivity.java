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
    private static final String PROPERTY_ON_SERVER_EXPIRATION_TIME =
            "onServerExpirationTimeMs";
    /**
     * Default lifespan (7 days) of a reservation until it is considered expired.
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
    
    

	// SectionsPagerAdapter mSectionsPagerAdapter;
	// ViewPager mViewPager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// customize the SlidingMenu
		// getActionBar().setBackgroundDrawable(
		// new ColorDrawable(Color.parseColor("#A62A00")));

		Log.e("MAIN ACTIVITY", "first");
		setContentView(R.layout.activity_main);

		Log.e("MAIN ACTIVITY", "second");

		// ImageView imageView = (ImageView) findViewById(R.id.imageView);
		URL url;
		// try {
		// url = new
		// URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
		// Bitmap bmp =
		// BitmapFactory.decodeStream(url.openConnection().getInputStream());
		// imageView.setImageBitmap(bmp);
		// } catch (MalformedURLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// Uri imageuri =
		// Uri.parse("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
		// imageView.setImageURI(imageuri);
		AQuery aq = new AQuery(this);
		// aq.id(R.id.imageView)
		// .image("http://www.nus.edu.sg/identity/logo/images/nus-hlogo-color.gif");
		// aq.ajax("http://www.google.com", String.class, this, "callback");

		// mSectionsPagerAdapter = new SectionsPagerAdapter(
		// getSupportFragmentManager());
		// Set up the ViewPager with the sections adapter.
		// mViewPager = (ViewPager) findViewById(R.id.viewpager);
		// mViewPager.setAdapter(mSectionsPagerAdapter);
		GCMRegistrar.checkManifest(this);

		context = getApplicationContext();
		regid = getRegistrationId(context);

        if (regid.length() == 0) {
            registerBackground();
        }
        else{
        	registerWithInstoBackend(regid);
        }
        gcm = GoogleCloudMessaging.getInstance(this);
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
	    int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
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
	 * <p>To avoid the scenario where the device sends the registration to the
	 * server but the server loses it, the app developer may choose to re-register
	 * after REGISTRATION_EXPIRY_TIME_MS.
	 *
	 * @return true if the registration has expired.
	 */
	private boolean isRegistrationExpired() {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    // checks if the information is not stale
	    long expirationTime =
	            prefs.getLong(PROPERTY_ON_SERVER_EXPIRATION_TIME, -1);
	    return System.currentTimeMillis() > expirationTime;
	}
	
	/**
	 * Stores the registration id, app versionCode, and expiration time in the
	 * application's {@code SharedPreferences}.
	 *
	 * @param context application's context.
	 * @param regId registration id
	 */
	private void setRegistrationId(Context context, String regId) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    int appVersion = getAppVersion(context);
	    Log.v(TAG, "Saving regId on app version " + appVersion);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(PROPERTY_REG_ID, regId);
	    editor.putInt(PROPERTY_APP_VERSION, appVersion);
	    long expirationTime = System.currentTimeMillis() + REGISTRATION_EXPIRY_TIME_MS;

	    Log.v(TAG, "Setting registration expiry time to " +
	            new Timestamp(expirationTime));
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

	                // You should send the registration ID to your server over HTTP,
	                // so it can use GCM/HTTP or CCS to send messages to your app.

	                // For this demo: we don't need to send it because the device
	                // will send upstream messages to a server that echo back the message
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
	            Log.v(TAG, msg.toString()+"\n");
	        }
	    }.execute(null, null, null);
	}
	

    private void registerWithInstoBackend(String regId){
      	Map<String, Object> params = new HashMap<String, Object>();
	    params.put("registration_id", regId);
	    System.out.println(regId);
	    params.put("user_id", InstoApplication.instance.getUserInfo().getId());
	    System.out.println(InstoApplication.instance.getUserInfo().getId());
	    AQuery aq = new AQuery(this);
    	aq.ajax("http://insto-web.herokuapp.com/user/register" , params, JSONObject.class, new AjaxCallback<JSONObject>(){
			@Override
	        public void callback(String url, JSONObject json, AjaxStatus status) {
				System.out.println(json.toString());
	        }
		});
    }
    
	
	private final BroadcastReceiver mHandleMessageReceiver =
            new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
            mDisplay.append(newMessage + "\n");
        }
    };
    

    // public class SectionsPagerAdapter extends FragmentPagerAdapter {
	//
	// public SectionsPagerAdapter(FragmentManager fm) {
	// super(fm);
	// }
	//
	// @Override
	// public Fragment getItem(int position) {
	// // getItem is called to instantiate the fragment for the given page.
	// // Return a DummySectionFragment (defined as a static inner class
	// // below) with the page number as its lone argument.
	//
	// if (position == 0) {
	// Fragment fragment = new checkPlacesListFragment();
	// return fragment;
	// }
	//
	// Fragment fragment = new DummySectionFragment();
	// Bundle args = new Bundle();
	// args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
	// fragment.setArguments(args);
	// return fragment;
	// }
	//
	// @Override
	// public int getCount() {
	// // Show 3 total pages.
	// return 3;
	// }
	//
	// @Override
	// public CharSequence getPageTitle(int position) {
	// Locale l = Locale.getDefault();
	// String tabName;
	// switch (position) {
	// // case 0: return "Home";
	// case 0:
	// return "Check Places";
	// case 1:
	// return "Take A Pic";
	// case 2:
	// return "Request A Pic";
	// }
	// return null;
	// }
	// }
	//
	// /**
	// * A dummy fragment representing a section of the app, but that simply
	// * displays dummy text.
	// */
	// public static class DummySectionFragment extends Fragment {
	// /**
	// * The fragment argument representing the section number for this
	// * fragment.
	// */
	// public static final String ARG_SECTION_NUMBER = "section_number";
	//
	// public DummySectionFragment() {
	// }
	//
	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// View rootView = inflater.inflate(R.layout.fragment_main_dummy,
	// container, false);
	// TextView dummyTextView = (TextView) rootView
	// .findViewById(R.id.section_label);
	// dummyTextView.setText(Integer.toString(getArguments().getInt(
	// ARG_SECTION_NUMBER)));
	// return rootView;
	// }
	// }

}
