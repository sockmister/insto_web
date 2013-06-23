package com.example.icreatesecretproject.CheckOthersRequest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.icreatesecretproject.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link CheckOthersTakeAPicFragment.OnFragmentInteractionListener} interface
 * to handle interaction events. Use the
 * {@link CheckOthersTakeAPicFragment#newInstance} factory method to create an
 * instance of this fragment.
 * 
 */
public class CheckOthersTakeAPicFragment extends Fragment {

	// private OnFragmentInteractionListener mListener;

	public CheckOthersTakeAPicFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		// return inflater.inflate(R.layout.fragment_take_apic, container,
		// false);
		return inflater.inflate(R.layout.activity_take_photo, container, false);
	}

}
