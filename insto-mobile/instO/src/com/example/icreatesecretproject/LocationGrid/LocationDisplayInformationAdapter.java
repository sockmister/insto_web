package com.example.icreatesecretproject.LocationGrid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.example.icreatesecretproject.R;

public class LocationDisplayInformationAdapter extends BaseAdapter {

	private Context mContext;
	JSONArray picturesArray;

	public LocationDisplayInformationAdapter(Context c, JSONArray picturesArray) {
		this.mContext = c;
		this.picturesArray = picturesArray;
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
		return picturesArray.length();
		// return 1;
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
		Log.i("LOCATION DISPLAY INFORMATION - adapter", "" + position);

		ImageView userIcon = (ImageView) v.findViewById(R.id.user_image);

		ImageOptions options = new ImageOptions();
		options.round = 15;

		String url;
		try {
			JSONObject jo = picturesArray.getJSONObject(position);
			Log.i("LOCATION DISPLAY ADPTER _ OBJECTL", jo.toString());
			// url = picturesArray.getJSONObject(0).getString("image_url");
			url = jo.getString("image_url");
			Log.i("LOCATION DISPLAY ADPTER _ URL", url);
			AQuery aq = new AQuery(v);
			aq.id(R.id.image).progress(R.id.progressBar)
					.image(url, true, true, 200, 0);
			// aq.id(R.id.user_image).image(url, options);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// GridView gridView;
		// gridView = (GridView) v.findViewById(R.id.gridview);
		// gridView.setAdapter(new LocationDisplayInformationGridAdapter(v
		// .getContext(), picturesArray));
		// ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
		// layoutParams.height = convertDpToPixels(450, v.getContext()); // this
		// is
		// // in
		// // pixels
		// gridView.setLayoutParams(layoutParams);

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

}
