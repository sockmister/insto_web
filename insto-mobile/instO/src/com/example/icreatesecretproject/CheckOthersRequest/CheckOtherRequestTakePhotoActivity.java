package com.example.icreatesecretproject.CheckOthersRequest;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.example.icreatesecretproject.MainMenuFragment.OnFragmentInteractionListener;
import com.example.icreatesecretproject.R;

public class CheckOtherRequestTakePhotoActivity extends FragmentActivity
		implements OnFragmentInteractionListener {

	JSONArray messageArray;
	int id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_others_request_take_photo);
		Switch ts = (Switch) this.findViewById(R.id.toggle_switch);
		id = getIntent().getIntExtra("locationId", 0);
		//
		// Fragment checkMessages = new CheckOthersRequestMessageFragment();
		// getSupportFragmentManager().beginTransaction()
		// .add(R.id.fragment_frame, checkMessages).commit();

		loadMessages();

		ts.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton cButton, boolean checked) {
				if (checked) {
					Fragment takeAPic = new CheckOthersTakeAPicFragment(id);
					FragmentTransaction ft = getSupportFragmentManager()
							.beginTransaction();
					ft.setCustomAnimations(R.anim.from_out_slide_left,
							R.anim.from_in_slide_left);
					ft.replace(R.id.fragment_frame, takeAPic).commit();
				} else {
					Fragment checkMessages = new CheckOthersRequestMessageFragment(
							messageArray);
					FragmentTransaction ft = getSupportFragmentManager()
							.beginTransaction();
					ft.setCustomAnimations(R.anim.from_out_slide_right,
							R.anim.from_in_slide_right);
					ft.replace(R.id.fragment_frame, checkMessages).commit();
				}
			}

		});
	}

	private void loadMessages() {
		// TODO Auto-generated method stub
		int id = getIntent().getIntExtra("locationId", 0);

		String url = "http://insto-web.herokuapp.com/location/" + id
				+ "/request";
		Log.i("COR- take pic)", url);
		AQuery aq = new AQuery(this);
		aq.ajax(url, JSONArray.class, this, "jsonCallback");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(
				R.menu.check_other_request_display_information, menu);
		return true;
	}

	public void jsonCallback(String url, JSONArray json, AjaxStatus status) {

		messageArray = json;
		Fragment checkMessages = new CheckOthersRequestMessageFragment(
				messageArray);
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment_frame, checkMessages).commit();
		Log.i("LOCATION IN FACULTY ACTION", url + " " + status.getCode());
		System.out.println(json);
		// TextView tv = (TextView) findViewById(R.id.text_view);
		// tv.setText(json.toString());

	}

	@Override
	public void onFragmentInteraction(String id) {

	}

}
