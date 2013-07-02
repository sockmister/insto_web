package com.example.icreatesecretproject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.icreatesecretproject.R;

public class LatestRequestAdapter extends BaseAdapter {

	private Context mContext;
	JSONArray requestArray;

	public LatestRequestAdapter(Context c, JSONArray requestArray) {
		this.mContext = c;
		this.requestArray = requestArray;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return requestArray.length();
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
		if (v == null) {
			LayoutInflater vi;
			vi = LayoutInflater.from(mContext);
			v = vi.inflate(R.layout.fragment_check_other_request_fragments,
					null);
		}
		TextView textView = (TextView) v.findViewById(R.id.label);
		TextView gleamTextView = (TextView) v.findViewById(R.id.gleam_label);
		ImageView imageView = (ImageView) v.findViewById(R.id.logo);

		int count = 0;
		try {
			JSONObject jo = requestArray.getJSONObject(position);
			// count = Integer.parseInt(jo.getString("request_count"));
			gleamTextView.setText(jo.getString("request_count"));
			textView.setText(jo.getString("faculty") + "\n"
					+ jo.getString("location_name"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return v;
	}

	private String getDate(String dateCreated) {
		Log.i("IN FACULTY", dateCreated);
		String _date = "";
		String _time = "";
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss'Z'");
		// format.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date;
		try {
			date = format.parse(dateCreated);
			java.text.DateFormat dateFormat = android.text.format.DateFormat
					.getDateFormat(mContext);
			_date = dateFormat.format(date);
			dateFormat = android.text.format.DateFormat.getTimeFormat(mContext);
			_time = dateFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return _date + " " + _time;
	}

}
