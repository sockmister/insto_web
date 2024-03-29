package com.example.icreatesecretproject.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

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
		ImageView imageView;
		v = vi.inflate(R.layout.location_grid, null);
		imageView = (ImageView) v.findViewById(R.id.image);
		imageView.setImageResource(mThumbIds[position]);
		// if (convertView != null) { // if it's not recycled, initialize some
		// // attributes
		// // imageView = new ImageView(mContext);
		// // imageView.setLayoutParams(new GridView.LayoutParams(350, 285));
		// // imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		// // imageView.setPadding(0, 1, 0, 1);
		// } else {
		// imageView = (ImageView) convertView;
		// }

		imageView.setImageResource(mThumbIds[position]);
		return v;
	}

	// references to our images
	private Integer[] mThumbIds = {
			// R.drawable.head
			R.drawable.p_arts, R.drawable.p_business, R.drawable.p_computing,
			R.drawable.p_engineering, R.drawable.p_medicine,
			R.drawable.p_science, R.drawable.p_sde, R.drawable.p_utown };
}