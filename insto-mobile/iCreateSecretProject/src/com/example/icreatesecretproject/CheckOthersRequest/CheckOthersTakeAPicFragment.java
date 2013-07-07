package com.example.icreatesecretproject.CheckOthersRequest;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.icreatesecretproject.Location;
import com.example.icreatesecretproject.POSTMultipart;
import com.example.icreatesecretproject.R;
import com.example.icreatesecretproject.TakePhoto.HandlePictureStorage;
import com.example.icreatesecretproject.TakePhoto.TakePhotoSurfaceView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link CheckOthersTakeAPicFragment.OnFragmentInteractionListener} interface
 * to handle interaction events. Use the
 * {@link CheckOthersTakeAPicFragment#newInstance} factory method to create an
 * instance of this fragment.
 * 
 */
@SuppressLint("ValidFragment")
public class CheckOthersTakeAPicFragment extends Fragment {

	int state = 0;
	Button takeAPicture;
	Button sendAPicture;
	File savedPictureFile;
	HandlePictureStorage handPicStore;
	Dialog dialog;
	ProgressDialog progressDialog;

	Location loc;
	ArrayList<ArrayList<Location>> locations = new ArrayList<ArrayList<Location>>();
	boolean ready = false;

	int id;

	public CheckOthersTakeAPicFragment() {
		// Required empty public constructor
	}

	public CheckOthersTakeAPicFragment(int id) {
		this.id = id;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.activity_take_photo, container,
				false);
		AQuery aq = new AQuery(getActivity());
		long expire = 60 * 60 * 1000; // 1 hr
		aq.ajax("http://insto-web.herokuapp.com/request/all", JSONArray.class,
				expire, new AjaxCallback<JSONArray>() {
					@Override
					public void callback(String url, JSONArray json,
							AjaxStatus status) {
						try {
							System.out.println(json.get(0));
							Gson g = new Gson();
							Type collectionType = new TypeToken<ArrayList<Location>>() {
							}.getType();
							for (int i = 0; i < json.length(); i++) {
								ArrayList<Location> temp = g.fromJson(
										json.get(i).toString(), collectionType);
								locations.add(temp);
							}
							ready = true;

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

		handPicStore = new HandlePictureStorage();
		// Setup the FrameLayout with the Camera Preview Screen
		final TakePhotoSurfaceView takePhotoSurfaceView = new TakePhotoSurfaceView(
				getActivity());
		FrameLayout preview = (FrameLayout) v.findViewById(R.id.preview);
		preview.addView(takePhotoSurfaceView);

		// Setup the 'Take Picture' button to take a picture
		takeAPicture = (Button) v.findViewById(R.id.button_take_photo);
		sendAPicture = (Button) v.findViewById(R.id.button_send_photo);
		sendAPicture.setAlpha(0.35f);

		takeAPicture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Camera camera = takePhotoSurfaceView.getCamera();

				if (state == 0) {
					camera.startPreview();
					camera.takePicture(null, null, handPicStore);
					state = 1;
					sendAPicture.setEnabled(true);
					sendAPicture.setAlpha(1.0f);
					// camera.stopPreview();
					// showPicture(handPicStore.getPictureByte());
				} else if (state == 1) {
					camera.startPreview();
					sendAPicture.setEnabled(false);
					sendAPicture.setAlpha(0.35f);
					state = 0;
				}

				while (ready == false) {

				}
				sendAPicture.setEnabled(true);
			}

		});

		sendAPicture.setEnabled(false);
		sendAPicture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				savedPictureFile = handPicStore.getPictureFile();
				progressDialog = ProgressDialog.show(getActivity(), "",
						"Sending...");

				new POSTMultipart(savedPictureFile, Integer.toString(id),
						dialog, progressDialog, getActivity()).execute();
				// final Dialog dialog = new Dialog(v.getContext());
				// dialog.setContentView(R.layout.dialog_select_photo_location);
				// dialog.setTitle("Select Location");
				//
				// final Spinner spinnerFaculty = (Spinner) dialog
				// .findViewById(R.id.spinner_faculty);
				// ArrayAdapter<CharSequence> adapter = ArrayAdapter
				// .createFromResource(v.getContext(), R.array.locations,
				// android.R.layout.simple_spinner_item);
				// // Specify the layout to use when the list of choices appears
				// adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// // // Apply the adapter to the spinner
				// spinnerFaculty.setAdapter(adapter);
				//
				// final Spinner spinnerId = (Spinner) dialog
				// .findViewById(R.id.spinner_id);
				// ArrayAdapter<CharSequence> adapterId = ArrayAdapter
				// .createFromResource(v.getContext(), R.array.arts_name,
				// android.R.layout.simple_spinner_item);
				// // Specify the layout to use when the list of choices appears
				// adapterId
				// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// // Apply the adapter to the spinner
				// spinnerId.setAdapter(adapterId);
				//
				// spinnerFaculty
				// .setOnItemSelectedListener(new OnItemSelectedListener() {
				// @Override
				// public void onItemSelected(AdapterView<?> parent,
				// View view, int position, long id) {
				// Spinner spinnerId = (Spinner) dialog
				// .findViewById(R.id.spinner_id);
				// ArrayAdapter<Location> adapterId = null;
				// switch (position) {
				// case 0:
				// // adapterId =
				// // ArrayAdapter.createFromResource(
				// // view.getContext(),
				// // R.array.arts_name,
				// // android.R.layout.simple_spinner_item);
				// adapterId = new ArrayAdapter<Location>(
				// view.getContext(),
				// android.R.layout.simple_spinner_item,
				// locations.get(position));
				// break;
				// case 1:
				// // adapterId =
				// // ArrayAdapter.createFromResource(
				// // view.getContext(),
				// // R.array.business_name,
				// // android.R.layout.simple_spinner_item);
				// adapterId = new ArrayAdapter<Location>(
				// view.getContext(),
				// android.R.layout.simple_spinner_item,
				// locations.get(position));
				// break;
				// case 2:
				// // adapterId =
				// // ArrayAdapter.createFromResource(
				// // view.getContext(),
				// // R.array.computing_name,
				// // android.R.layout.simple_spinner_item);
				// adapterId = new ArrayAdapter<Location>(
				// view.getContext(),
				// android.R.layout.simple_spinner_item,
				// locations.get(position));
				// break;
				// case 3:
				// // adapterId =
				// // ArrayAdapter.createFromResource(
				// // view.getContext(),
				// // R.array.engineering_name,
				// // android.R.layout.simple_spinner_item);
				// adapterId = new ArrayAdapter<Location>(
				// view.getContext(),
				// android.R.layout.simple_spinner_item,
				// locations.get(position));
				// break;
				// case 4:
				// // adapterId =
				// // ArrayAdapter.createFromResource(
				// // view.getContext(),
				// // R.array.medicine_name,
				// // android.R.layout.simple_spinner_item);
				// adapterId = new ArrayAdapter<Location>(
				// view.getContext(),
				// android.R.layout.simple_spinner_item,
				// locations.get(position));
				// break;
				// case 5:
				// // adapterId =
				// // ArrayAdapter.createFromResource(
				// // view.getContext(),
				// // R.array.science_name,
				// // android.R.layout.simple_spinner_item);
				// adapterId = new ArrayAdapter<Location>(
				// view.getContext(),
				// android.R.layout.simple_spinner_item,
				// locations.get(position));
				// break;
				// case 6:
				// // adapterId =
				// // ArrayAdapter.createFromResource(
				// // view.getContext(),
				// // R.array.sde_name,
				// // android.R.layout.simple_spinner_item);
				// adapterId = new ArrayAdapter<Location>(
				// view.getContext(),
				// android.R.layout.simple_spinner_item,
				// locations.get(position));
				// break;
				// case 7:
				// // adapterId =
				// // ArrayAdapter.createFromResource(
				// // view.getContext(),
				// // R.array.utown_name,
				// // android.R.layout.simple_spinner_item);
				// adapterId = new ArrayAdapter<Location>(
				// view.getContext(),
				// android.R.layout.simple_spinner_item,
				// locations.get(position));
				// break;
				// }
				// adapterId
				// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// // Apply the adapter to the spinner
				// spinnerId.setAdapter(adapterId);
				// }
				//
				// @Override
				// public void onNothingSelected(AdapterView<?> arg0) {
				// // TODO Auto-generated method stub
				//
				// }
				//
				// });
				//
				// Button send_button = (Button) dialog
				// .findViewById(R.id.send_button);
				// send_button.setOnClickListener(new OnClickListener() {
				//
				// @Override
				// public void onClick(View arg0) {
				// // TODO Auto-generated method stub
				//
				// savedPictureFile = handPicStore.getPictureFile();
				// progressDialog = ProgressDialog.show(getActivity(), "",
				// "Loading...");
				//
				// int location_id = ((Location) spinnerId
				// .getSelectedItem()).getLocation_id();
				// System.out.println(location_id);
				// new POSTMultipart(savedPictureFile, Integer
				// .toString(location_id), dialog, progressDialog,
				// getActivity())
				// .execute();
				// }
				//
				// });
				//
				// dialog.show();

			}

		});

		return v;
	}

}
