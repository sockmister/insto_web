package com.example.icreatesecretproject;

import com.example.icreatesecretproject.TakePhoto.TakePhotoActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
		 String[] menuItems =
		 getResources().getStringArray(R.array.menu_items);
		 ArrayAdapter<String> menuItemsAdapter = new ArrayAdapter<String>(
		 getActivity(), android.R.layout.simple_list_item_1,
		 android.R.id.text1, menuItems);
		 setListAdapter(menuItemsAdapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		/*

		 * generated code if (null != mListener) { // Notify the active

		 * callbacks interface (the activity, if the // fragment is attached to

		 * one) that an item has been selected. mListener

		 * .onFragmentInteraction(DummyContent.ITEMS.get(position).id); }

		 * generaed code

		 */

		Fragment newFragment = null;
		switch (position) {
		// check places
		case 0:
			Intent intent = new Intent(getActivity(), LocationGridActivity.class);
			startActivity(intent);
			break;
		// help a soul
		case 1:
			Intent intent1 = new Intent(getActivity(), TakePhotoActivity.class);
			startActivity(intent1);
			break;
		// my request
		case 2:
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
}

