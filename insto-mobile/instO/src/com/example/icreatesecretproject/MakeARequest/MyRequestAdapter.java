package com.example.icreatesecretproject.MakeARequest;

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

import com.example.icreatesecretproject.R;

public class MyRequestAdapter extends BaseAdapter {

	private Context mContext;
	JSONArray requestArray;

	public MyRequestAdapter(Context c, JSONArray requestArray) {
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
			v = vi.inflate(R.layout.my_request_activity_list_item, null);
		}
		TextView locationName = (TextView) v.findViewById(R.id.location_name);
		TextView locationDate = (TextView) v.findViewById(R.id.location_date);
		TextView locationMemo = (TextView) v.findViewById(R.id.location_memo);

		int count = 0;
		try {
			JSONObject jo = requestArray.getJSONObject(position);
			// count = Integer.parseInt(jo.getString("request_count"));
			locationDate.setText(getDate(jo.getString("created_at")));
			locationMemo.setText(getMemo(jo.getString("message")));
			JSONObject locationO = jo.getJSONObject("location");
			if (locationO == null) {
				locationName.setText("Unknown");
			} else {
				locationName.setText(getLocation(locationO));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return v;
	}

	private CharSequence getLocation(JSONObject jo) {
		if (jo == null)
			return "UNKNOWN";
		String location = "UNKNOWN";
		try {
			location = jo.getString("faculty") + "\n" + jo.getString("name");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return location;
	}

	private String getMemo(String string) {
		// TODO Auto-generated method stub
		if (string == null || string.equals("null"))
			return "No Message";
		return "\"" + string + "\"";
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
