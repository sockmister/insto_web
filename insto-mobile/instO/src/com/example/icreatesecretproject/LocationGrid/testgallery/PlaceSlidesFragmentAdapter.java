package com.example.icreatesecretproject.LocationGrid.testgallery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.RelativeLayout;

import com.example.icreatesecretproject.R;
import com.viewpagerindicator.IconPagerAdapter;

public class PlaceSlidesFragmentAdapter extends FragmentPagerAdapter implements
		IconPagerAdapter {

	JSONArray jA;
	private RelativeLayout addGleamLayout;
	private int[] Images = new int[] { R.drawable.head, R.drawable.head,
			R.drawable.head, R.drawable.head

	};

	protected static final int[] ICONS = new int[] { R.drawable.ic_dot_alt,
			R.drawable.ic_dot_alt, R.drawable.ic_dot_alt, R.drawable.ic_dot_alt };

	private int mCount = Images.length;

	public PlaceSlidesFragmentAdapter(FragmentManager fm, JSONArray json, RelativeLayout addGleamLayout) {
		super(fm);
		jA = json;
		mCount = jA.length();
		this.addGleamLayout = addGleamLayout;
	}

	@Override
	public Fragment getItem(int position) {
		// return new PlaceSlideFragment(Images[position]);
		JSONObject jo = null;
		try {
			jo = jA.getJSONObject(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new PlaceSlideFragment(jo, addGleamLayout);
	}

	@Override
	public int getCount() {
		// return mCount;
		return jA.length();
	}

	@Override
	public int getIconResId(int index) {
		return ICONS[index % ICONS.length];
	}

	public void setCount(int count) {
		mCount = count;
		notifyDataSetChanged();
	}
}