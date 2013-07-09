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
import android.widget.TextView;

public class MainActivityNotificationAdapter extends BaseAdapter {

	JSONArray notificationArray;
	Context mContext;

	public void CheckOtherRequestLocationsAdapter(Context c, JSONArray ja) {
		notificationArray = ja;
		mContext = c;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return notificationArray.length();
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
		v = vi.inflate(R.layout.in_location_item, null);

		TextView name = (TextView) v.findViewById(R.id.location_name);
		TextView date = (TextView) v.findViewById(R.id.location_date);
		try {
			JSONObject jo = (JSONObject) notificationArray.get(position);
			name.setText(jo.getString("location_name"));
			date.setText(getDate(jo.getString("updated_at")));
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
