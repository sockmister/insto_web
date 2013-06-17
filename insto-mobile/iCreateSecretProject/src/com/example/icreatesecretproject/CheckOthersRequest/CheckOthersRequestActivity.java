package com.example.icreatesecretproject.CheckOthersRequest;

import java.net.URL;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.example.icreatesecretproject.MainMenuFragment;
import com.example.icreatesecretproject.R;
import com.example.icreatesecretproject.TakePhoto.TakePhotoActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class CheckOthersRequestActivity extends SlidingFragmentActivity {

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		// sm.setShadowWidthRes(R.dimen.shadow_width);
		// sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		// getActionBar().setBackgroundDrawable(
		// new ColorDrawable(Color.parseColor("#A62A00")));

		Log.e("MAIN ACTIVITY", "first");
		setContentView(R.layout.activity_check_other_request);
		setBehindContentView(R.layout.menu_frame);
		Log.e("MAIN ACTIVITY", "second");
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new MainMenuFragment()).commit();
		setSlidingActionBarEnabled(true);

		ImageView imageView = (ImageView) findViewById(R.id.imageView);
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
		aq.id(R.id.imageView)
				.image("http://www.nus.edu.sg/identity/logo/images/nus-hlogo-color.gif");
		aq.ajax("http://www.google.com", String.class, this, "callback");

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		Log.i("CHECK OTHER REQUEST - before", " yo");
		mViewPager.setAdapter(mSectionsPagerAdapter);
		Log.i("CHECK OTHER REQUEST - after", " yo");
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

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.

			String testArray = "[[{\"count\":\"0\",\"faculty\":\"biz\",\"location_id\":5,\"location_name\":\"Aquarium\"},{\"count\":\"0\",\"faculty\":\"biz\",\"location_id\":6,\"location_name\":\"Fish Tank\"},{\"count\":\"0\",\"faculty\":\"biz\",\"location_id\":7,\"location_name\":\"Mochtar Riady Building\"},{\"count\":\"0\",\"faculty\":\"biz\",\"location_id\":8,\"location_name\":\"Biz Corridor\"}],[{\"count\":\"10\",\"faculty\":\"soc\",\"location_id\":1,\"location_name\":\"COM1-B1 Study Area\"},{\"count\":\"2\",\"faculty\":\"soc\",\"location_id\":2,\"location_name\":\"COM1 Level 2 Common Area\"},{\"count\":\"2\",\"faculty\":\"soc\",\"location_id\":3,\"location_name\":\"COM1 Level 1 Printer Area\"},{\"count\":\"0\",\"faculty\":\"soc\",\"location_id\":4,\"location_name\":\"COM1 Level 2 Tutorial Room Corridor\"}]]";

			try {
				JSONArray ja = new JSONArray(testArray);
				JSONArray temp = ja.getJSONArray(0);
				Log.i("CHECK OTHER REQUEST - get ITEM", temp.toString());
				Fragment fragment = new CheckOtherRequestFragments(temp);
				return fragment;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
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

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return rootView;
		}
	}

}