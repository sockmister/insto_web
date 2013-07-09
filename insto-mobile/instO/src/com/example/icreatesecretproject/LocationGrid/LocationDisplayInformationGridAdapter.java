package com.example.icreatesecretproject.LocationGrid;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.example.icreatesecretproject.R;

public class LocationDisplayInformationGridAdapter extends BaseAdapter {
	private Context mContext;
	JSONArray jArray;

	public LocationDisplayInformationGridAdapter(Context c, JSONArray jArray) {
		mContext = c;
		this.jArray = jArray;
	}

	public int getCount() {
		return jArray.length() - 1;
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

		String url;
		try {
			url = jArray.getJSONObject(position + 1).getString("image_url");
			AQuery aq = new AQuery(v);
			aq.id(R.id.image).progress(R.id.progressBar)
					.image(url, true, true, 200, 0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		TextView tv = (TextView) v.findViewById(R.id.name);
		// if (convertView != null) { // if it's not recycled, initialize some
		// // attributes
		// // imageView = new ImageView(mContext);
		// // imageView.setLayoutParams(new GridView.LayoutParams(350, 285));
		// // imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		// // imageView.setPadding(0, 1, 0, 1);
		// } else {
		// imageView = (ImageView) convertView;
		// }

		// imageView.setImageResource(mThumbIds[position]);
		return v;
	}

	// references to our images

}