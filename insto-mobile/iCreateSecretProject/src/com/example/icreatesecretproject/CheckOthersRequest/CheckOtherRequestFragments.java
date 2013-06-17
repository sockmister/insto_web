package com.example.icreatesecretproject.CheckOthersRequest;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.icreatesecretproject.DisplayPictureActivity;
import com.example.icreatesecretproject.dummy.DummyContent;
import com.example.icreatesecretproject.tabFragments.requestListFragment.OnFragmentInteractionListener;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
@SuppressLint("ValidFragment")
public class CheckOtherRequestFragments extends ListFragment {

	private OnFragmentInteractionListener mListener;
	JSONArray locationArray;

	public CheckOtherRequestFragments() {
		// Required empty public constructor
	}

	public CheckOtherRequestFragments(JSONArray ja) {
		locationArray = ja;
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// CYW
		String[] values = new String[] { "Arts", "Business", "Computing",
				"Design and Environment", "Engineering", "Science", "Utown" };
		Log.i("CHECK OTHER REQUEST - FRAGMENT", locationArray.toString());
		setListAdapter(new CheckOtherRequestLocationsAdapter(getActivity(),
				locationArray));
		// setListAdapter(new ArrayAdapter<String>(getActivity(),
		// R.layout.fragment_name_list,values));
		// CYW..
	}

	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// // Inflate the layout for this fragment
	// return inflater.inflate(
	// R.layout.fragment_check_other_request_fragments, container,
	// false);
	// }

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Intent intent = new Intent(getActivity(), DisplayPictureActivity.class);
		startActivity(intent);

		if (null != mListener) {
			// Notify the active callbacks interface (the activity, if the
			// fragment is attached to one) that an item has been selected.
			mListener
					.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
		}
	}
}
