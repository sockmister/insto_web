package com.example.icreatesecretproject.LocationGrid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.example.icreatesecretproject.R;

public class LocationGridAdapter extends BaseAdapter {
	private Context mContext;

	public LocationGridAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {
		return mThumbIds.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater vi;
		vi = LayoutInflater.from(mContext);
		View v = null;
		v = vi.inflate(R.layout.location_grid, null);
		ImageView imageView;
		imageView = (ImageView) v.findViewById(R.id.image);
		imageView.setImageResource(mThumbIds[position]);

		TextView tv = (TextView) v.findViewById(R.id.name);
		tv.setText(mThumbIdsName[position]);
		// if (convertView != null) { // if it's not recycled, initialize some
		// // attributes
		// // imageView = new ImageView(mContext);
		// // imageView.setLayoutParams(new GridView.LayoutParams(350, 285));
		// // imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		// // imageView.setPadding(0, 1, 0, 1);
		// } else {
		// imageView = (ImageView) convertView;
		// }
		AQuery aq = new AQuery(v.getContext());
		aq.id(R.id.image).image(mThumbIds[position]);
		// imageView.setImageResource(mThumbIds[position]);
		return v;
	}

	// references to our images
	private Integer[] mThumbIds = {
			// R.drawable.head
			R.drawable.o_arts, R.drawable.o_business, R.drawable.o_computing,
			R.drawable.o_engineering, R.drawable.o_medicine,
			R.drawable.o_science, R.drawable.o_sde, R.drawable.o_utown };

	private String[] mThumbIdsName = {
			// R.drawable.head
			"Arts", "Business", "Computing", "Engineering", "Medicine",
			"Science", "SDE", "UTown" };
}