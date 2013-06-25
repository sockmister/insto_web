package com.example.icreatesecretproject.LocationGrid.testgallery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.example.icreatesecretproject.R;

@SuppressLint("ValidFragment")
public final class PlaceSlideFragment extends Fragment {
	int imageResourceId;
	JSONObject jo;

	@SuppressLint("ValidFragment")
	public PlaceSlideFragment(int i) {
		imageResourceId = i;
	}

	public PlaceSlideFragment(JSONObject jo) {
		this.jo = jo;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.image_gallery_item, null);

		ImageView image = (ImageView) v.findViewById(R.id.image_in_gallery);
		TextView matiricNumber = (TextView) v.findViewById(R.id.user_matric);
		TextView imageDate = (TextView) v.findViewById(R.id.image_date);

		String url = "";
		String date = "";

		try {
			url = jo.getString("image_url");
			date = getDate(jo.getString("created_at"));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		imageDate.setText(date);
		AQuery aq = new AQuery(getActivity());
		aq.id(image).image(url, true, true, 600, 0);
		// image.setImageResource(imageResourceId);

		return v;

		// ImageView image = new ImageView(getActivity());
		//
		// AQuery aq = new AQuery(getActivity());
		// aq.id(image).image(url, true, true, 600, 0);
		// // image.setImageResource(imageResourceId);
		//
		// LinearLayout layout = new LinearLayout(getActivity());
		// layout.setLayoutParams(new LayoutParams());
		//
		// layout.setGravity(Gravity.CENTER);
		// layout.addView(image);
		//
		// return layout;
	}

	private String getDate(String dateCreated) {
		Log.i("PlaceSlideFragment", dateCreated);
		String _date = "";
		String _time = "";
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss'Z'");
		// format.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date;
		try {
			date = format.parse(dateCreated);
			java.text.DateFormat dateFormat = android.text.format.DateFormat
					.getDateFormat(getActivity());
			_date = dateFormat.format(date);
			dateFormat = android.text.format.DateFormat
					.getTimeFormat(getActivity());
			_time = dateFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// return _date + " " + _time;
		return _time;
	}
}
