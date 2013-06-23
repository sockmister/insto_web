package com.example.icreatesecretproject.CheckOthersRequest;

import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.icreatesecretproject.MainMenuFragment.OnFragmentInteractionListener;
import com.example.icreatesecretproject.R;
import com.example.icreatesecretproject.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
@SuppressLint("ValidFragment")
public class CheckOthersRequestMessageFragment extends ListFragment implements
		OnFragmentInteractionListener {

	private OnFragmentInteractionListener mListener;
	JSONArray messageArray;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public CheckOthersRequestMessageFragment() {
	}

	public CheckOthersRequestMessageFragment(JSONArray ja) {
		messageArray = ja;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO: Change Adapter to display your content
		// setListAdapter(new
		// ArrayAdapter<DummyContent.DummyItem>(getActivity(),
		// android.R.layout.simple_list_item_1, android.R.id.text1,
		// DummyContent.ITEMS));
		setListAdapter(new messageAdapter(getActivity(), messageArray));
	}

	// @Override
	// public void onAttach(Activity activity) {
	// super.onAttach(activity);
	// try {
	// mListener = (OnFragmentInteractionListener) activity;
	// } catch (ClassCastException e) {
	// throw new ClassCastException(activity.toString()
	// + " must implement OnFragmentInteractionListener");
	// }
	// }

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		if (null != mListener) {
			// Notify the active callbacks interface (the activity, if the
			// fragment is attached to one) that an item has been selected.
			mListener
					.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
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

	@Override
	public void onFragmentInteraction(String id) {
		// TODO Auto-generated method stub

	}

	public class messageAdapter extends BaseAdapter {

		JSONArray messageArray;
		Context mContext;

		public messageAdapter(Context c, JSONArray ja) {
			messageArray = ja;
			mContext = c;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return messageArray.length();
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
				v = vi.inflate(R.layout.list_item_messages, null);
			}
			TextView tv = (TextView) v.findViewById(R.id.message);
			try {
				tv.setText("\" "
						+ messageArray.getJSONObject(position).getString(
								"message") + " \"");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return v;
		}

	}
}
