package com.example.icreatesecretproject.LocationGrid;

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

public class LocationInFacultyAdapter extends BaseAdapter {

	private Context mContext;
	JSONArray locationArray;

	public LocationInFacultyAdapter(Context c, JSONArray locationArray) {
		this.mContext = c;
		this.locationArray = locationArray;
		// String temp =
		// "[{created_at:\"2013-05-31T09:39:58Z\",faculty:\"soc\",id:1,name:\"COM1-B1 Study Area\",updated_at:\"2013-05-31T09:39:58Z\"},{created_at:\"2013-05-31T09:40:42Z\",faculty:\"soc\",id:2,name:\"COM1 Level 2 Common Area\",updated_at:\"2013-05-31T09:40:42Z\"},{created_at:\"2013-05-31T09:41:07Z\",faculty:\"soc\",id:3,name:\"COM1 Level 1 Printer Area\",updated_at:\"2013-05-31T09:41:07Z\"},{created_at:\"2013-05-31T09:42:02Z\",faculty:\"soc\",id:4,name:\"COM1 Level 2 Tutorial Room Corridor\",updated_at:\"2013-05-31T09:42:02Z\"}]";
		// try {
		// this.locationArray = new JSONArray(temp);
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
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
		v = vi.inflate(R.layout.in_location_item, null);

		TextView name = (TextView) v.findViewById(R.id.location_name);
		TextView date = (TextView) v.findViewById(R.id.location_date);
		try {
			JSONObject jo = (JSONObject) locationArray.get(position);
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
