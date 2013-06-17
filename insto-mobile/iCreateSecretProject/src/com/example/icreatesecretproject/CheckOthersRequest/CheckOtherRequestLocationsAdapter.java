package com.example.icreatesecretproject.CheckOthersRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.icreatesecretproject.R;

public class CheckOtherRequestLocationsAdapter extends BaseAdapter {

	JSONArray locationArray;
	Context mContext;

	public CheckOtherRequestLocationsAdapter(Context c, JSONArray ja) {
		locationArray = ja;
		mContext = c;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return locationArray.length();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View v, ViewGroup vg) {
		LayoutInflater vi;
		vi = LayoutInflater.from(mContext);
		v = vi.inflate(R.layout.fragment_check_other_request_fragments, null);
		TextView textView = (TextView) v.findViewById(R.id.label);
		TextView gleamTextView = (TextView) v.findViewById(R.id.gleam_label);
		ImageView imageView = (ImageView) v.findViewById(R.id.logo);
		try {
			JSONObject jo = locationArray.getJSONObject(position);
			gleamTextView.setText(jo.getString("count"));
			textView.setText(jo.getString("location_name"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return v;
	}

}
