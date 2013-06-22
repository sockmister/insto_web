package com.example.icreatesecretproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.icreatesecretproject.CheckOthersRequest.CheckOthersRequestActivity;
import com.example.icreatesecretproject.LocationGrid.LocationGridActivity;

public class MainMenuFragment extends ListFragment {
	private OnFragmentInteractionListener mListener;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public MainMenuFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// String[] menuItems =
		// getResources().getStringArray(R.array.menu_items);
		// ArrayAdapter<String> menuItemsAdapter = new ArrayAdapter<String>(
		// getActivity(), R.layout.row, android.R.id.text1, menuItems);
		// setListAdapter(menuItemsAdapter);
		MenuListAdapter mA = new MenuListAdapter(getActivity());
		mA.add(new MenuListItem("Home", R.drawable.ic_notification));
		mA.add(new MenuListItem("Check Places", R.drawable.ic_notification));
		mA.add(new MenuListItem("Help A Soul", R.drawable.ic_notification));
		mA.add(new MenuListItem("My Request", R.drawable.ic_notification));
		setListAdapter(mA);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		/*
		 * 
		 * generated code if (null != mListener) { // Notify the active
		 * 
		 * callbacks interface (the activity, if the // fragment is attached to
		 * 
		 * one) that an item has been selected. mListener
		 * 
		 * .onFragmentInteraction(DummyContent.ITEMS.get(position).id); }
		 * 
		 * generaed code
		 */

		Fragment newFragment = null;
		switch (position) {
		// check places
		case 0:
			Intent intent0 = new Intent(getActivity(), MainActivity.class);
			startActivity(intent0);
			getActivity().overridePendingTransition(R.anim.from_out_slide_left,
					R.anim.from_in_slide_left);
			break;
		// check places
		case 1:
			Intent intent1 = new Intent(getActivity(),
					LocationGridActivity.class);
			startActivity(intent1);
			getActivity().overridePendingTransition(R.anim.from_out_slide_left,
					R.anim.from_in_slide_left);
			break;
		// help a soul
		case 2:
			Intent intent2 = new Intent(getActivity(),
					CheckOthersRequestActivity.class);
			startActivity(intent2);
			getActivity().overridePendingTransition(R.anim.from_out_slide_left,
					R.anim.from_in_slide_left);
			break;
		// my request
		case 3:
			Intent intent3 = new Intent(getActivity(), MyRequestActivity.class);
			startActivity(intent3);
			getActivity().overridePendingTransition(R.anim.from_out_slide_left,
					R.anim.from_in_slide_left);
			break;
		}
		if (newFragment != null) {
			MainActivity MA = (MainActivity) getActivity();
			MA.switchContent(newFragment);
		}
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */

	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(String id);

	}

	private class MenuListItem {
		public String tag;
		public int iconRes;

		public MenuListItem(String tag, int iconRes) {
			this.tag = tag;
			this.iconRes = iconRes;
		}
	}

	public class MenuListAdapter extends ArrayAdapter<MenuListItem> {
		public MenuListAdapter(Context context) {
			super(context, 0);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(
						R.layout.row, null);
			}
			ImageView icon = (ImageView) convertView
					.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView
					.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);
			return convertView;
		}
	}
}
