package com.example.icreatesecretproject.LocationGrid.testgallery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.example.icreatesecretproject.R;

@SuppressLint("ValidFragment")
public final class PlaceSlideFragment extends Fragment {
	int imageResourceId;
	String url;

	@SuppressLint("ValidFragment")
	public PlaceSlideFragment(int i) {
		imageResourceId = i;
	}

	public PlaceSlideFragment(String url) {
		this.url = url;
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
}
