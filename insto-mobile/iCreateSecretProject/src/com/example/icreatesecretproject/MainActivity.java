package com.example.icreatesecretproject;

import java.net.URL;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.example.icreatesecretproject.TakePhoto.TakePhotoActivity;

public class MainActivity extends BaseActivity {

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
