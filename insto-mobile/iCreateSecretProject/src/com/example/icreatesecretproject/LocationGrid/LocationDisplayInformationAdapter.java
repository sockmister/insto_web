package com.example.icreatesecretproject.LocationGrid;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.example.icreatesecretproject.R;

public class LocationDisplayInformationAdapter extends BaseAdapter {

	private Context mContext;
	JSONArray locationArray;

	public LocationDisplayInformationAdapter(Context c, JSONArray locationArray) {
		this.mContext = c;
		// this.locationArray = locationArray;
		String temp = "[{created_at:\"2013-05-31T09:39:58Z\",faculty:\"soc\",id:1,name:\"COM1-B1 Study Area\",updated_at:\"2013-05-31T09:39:58Z\"},{created_at:\"2013-05-31T09:40:42Z\",faculty:\"soc\",id:2,name:\"COM1 Level 2 Common Area\",updated_at:\"2013-05-31T09:40:42Z\"},{created_at:\"2013-05-31T09:41:07Z\",faculty:\"soc\",id:3,name:\"COM1 Level 1 Printer Area\",updated_at:\"2013-05-31T09:41:07Z\"},{created_at:\"2013-05-31T09:42:02Z\",faculty:\"soc\",id:4,name:\"COM1 Level 2 Tutorial Room Corridor\",updated_at:\"2013-05-31T09:42:02Z\"}]";
		try {
			this.locationArray = new JSONArray(temp);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
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
		// TODO Auto-generated method stub
		LayoutInflater vi;
		vi = LayoutInflater.from(mContext);
		v = vi.inflate(R.layout.in_location_timeline_item, null);

		GridView gridView;
		gridView = (GridView) v.findViewById(R.id.gridview);

		final String[] numbers = new String[] { "A", "B", "C", "D", "E", "F",
				"G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "V", "W", "X", "Y", "Z" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(),
				android.R.layout.simple_list_item_1, numbers);

		gridView.setAdapter(new LocationGridAdapter(v.getContext()));

		ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
		layoutParams.height = convertDpToPixels(450, v.getContext()); // this is
																		// in
																		// pixels
		gridView.setLayoutParams(layoutParams);
		// TextView name = (TextView) v.findViewById(R.id.location_name);
		// try {
		// JSONObject jo = (JSONObject) locationArray.get(position);
		// name.setText(jo.getString("name"));
		//
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		return v;
	}

	public static int convertDpToPixels(float dp, Context context) {
		Resources resources = context.getResources();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				resources.getDisplayMetrics());
	}

	private Integer[] mThumbIds = {
			// R.drawable.head
			R.drawable.p_arts, R.drawable.p_business, R.drawable.p_computing,
			R.drawable.p_engineering, R.drawable.p_medicine,
			R.drawable.p_science, R.drawable.p_sde, R.drawable.p_utown };
}
