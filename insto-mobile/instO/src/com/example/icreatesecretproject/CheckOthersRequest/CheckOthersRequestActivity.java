package com.example.icreatesecretproject.CheckOthersRequest;

import java.net.URL;
import java.util.Locale;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.example.icreatesecretproject.BaseActivity;
import com.example.icreatesecretproject.MainMenuFragment;
import com.example.icreatesecretproject.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class CheckOthersRequestActivity extends BaseActivity {

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	ProgressBar pb;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// customize the SlidingMenu
		setContentView(R.layout.activity_check_other_request);
		SlidingMenu sm = getSlidingMenu();
		// sm.setShadowWidthRes(R.dimen.shadow_width);
		// sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		// getActionBar().setBackgroundDrawable(
		// new ColorDrawable(Color.parseColor("#A62A00")));
		pb = (ProgressBar) this.findViewById(R.id.progressBar2);

		Log.e("MAIN ACTIVITY", "first");
		setBehindContentView(R.layout.menu_frame);
		Log.e("MAIN ACTIVITY", "second");
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new MainMenuFragment()).commit();
		setSlidingActionBarEnabled(true);

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
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		Log.i("CHECK OTHER REQUEST - before", " yo");
		mViewPager.setAdapter(mSectionsPagerAdapter);
		Log.i("CHECK OTHER REQUEST - after", " yo");
		getRequestCount();
		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		// mViewPager
		// .setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
		// @Override
		// public void onPageSelected(int position) {
		// actionBar.setSelectedNavigationItem(position);
		// }
		// });

		// For each of the sections in the app, add a tab to the action bar.
		// for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
		// // Create a tab with text corresponding to the page title defined by
		// // the adapter. Also specify this Activity object, which implements
		// // the TabListener interface, as the callback (listener) for when
		// // this tab is selected.
		//
		// actionBar.addTab(actionBar.newTab()
		// .setText(mSectionsPagerAdapter.getPageTitle(i))
		// .setTabListener(this));
		// }
	}

	public void getRequestCount() {
		Intent intent = getIntent();
		int facultyId = intent.getIntExtra("facultyId", 0);
		String[] location = { "fass", "business", "soc", "engineering",
				"medicine", "science", "sde", "utown" };

		String url = "http://insto-web.herokuapp.com/request/all";
		Log.i("LOCATION IN FACULTY ACTION)", "enter");
		AQuery aq = new AQuery(this);
		
		showProgress(true);
		aq.ajax(url, JSONArray.class, this, "jsonCallback");

	}

	public void jsonCallback(String url, JSONArray json, AjaxStatus status) {
		Log.i("LOCATION IN FACULTY ACTION", url + " " + status.getCode());
		System.out.println(json);
		// TextView tv = (TextView) findViewById(R.id.text_view);
		// tv.setText(json.toString());

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager(), json);
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		Log.i("CHECK OTHER REQUEST - before", " yo");
		mViewPager.setAdapter(mSectionsPagerAdapter);
		Log.i("CHECK OTHER REQUEST - after", " yo");
		
		showProgress(false);
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

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		JSONArray ja;

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			String testArray = "[[{\"count\":\"10\",\"faculty\":\"soc\",\"location_id\":1,\"location_name\":\"COM1-B1 Study Area\"},{\"count\":\"2\",\"faculty\":\"soc\",\"location_id\":2,\"location_name\":\"COM1 Level 2 Common Area\"},{\"count\":\"2\",\"faculty\":\"soc\",\"location_id\":3,\"location_name\":\"COM1 Level 1 Printer Area\"},{\"count\":\"0\",\"faculty\":\"soc\",\"location_id\":4,\"location_name\":\"COM1 Level 2 Tutorial Room Corridor\"}]]";

			try {
				ja = new JSONArray(testArray);
				// ja = temp.getJSONArray(0);
			} catch (Exception e) {

			}
		}

		public SectionsPagerAdapter(FragmentManager fm, JSONArray ja) {
			super(fm);
			this.ja = ja;
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.

			try {

				Log.i("CHECK OTHER REQUEST - get ITEM", ja.toString());
				Log.i("CHECK OTHER REQUEST - get ITEM obj", ja.getJSONArray(0)
						.toString());
				Fragment fragment = new CheckOtherRequestFragments(
						ja.getJSONArray(position));
				return fragment;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 8;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			String tabName;
			switch (position) {
			// case 0: return "Home";
			case 0:
				return "Arts";
			case 1:
				return "Business";
			case 2:
				return "Computing";
			case 3:
				return "Engineering";
			case 4:
				return "Medicine";
			case 5:
				return "Science";
			case 6:
				return "SDE";
			case 7:
				return "Utown";
			}
			return null;
		}
	}
	
	public void showProgress(boolean show) {
		pb.setVisibility(show ? View.VISIBLE : View.GONE);
		mViewPager.setVisibility(show ? View.GONE : View.VISIBLE);
	}
}
