package com.example.icreatesecretproject.TakePhoto;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.icreatesecretproject.R;

public class TakePhotoActivity extends Activity {
	int state = 0;
	Button takeAPicture;
	Button sendAPicture;
	File savedPictureFile;
	HandlePictureStorage handPicStore;
	Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_photo);

		handPicStore = new HandlePictureStorage();
		// Setup the FrameLayout with the Camera Preview Screen
		final TakePhotoSurfaceView takePhotoSurfaceView = new TakePhotoSurfaceView(
				this);
		FrameLayout preview = (FrameLayout) findViewById(R.id.preview);
		preview.addView(takePhotoSurfaceView);

		// Setup the 'Take Picture' button to take a picture
		takeAPicture = (Button) findViewById(R.id.button_take_photo);
		sendAPicture = (Button) findViewById(R.id.button_send_photo);
		sendAPicture.setAlpha(0.35f);

		takeAPicture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Camera camera = takePhotoSurfaceView.getCamera();

				if (state == 0) {
					camera.startPreview();
					camera.takePicture(null, null, handPicStore);
					state = 1;
					takeAPicture.setText("Take Again");
					sendAPicture.setEnabled(true);
					sendAPicture.setAlpha(1.0f);
					// camera.stopPreview();
					// showPicture(handPicStore.getPictureByte());
				} else if (state == 1) {
					camera.startPreview();
					takeAPicture.setText("Capture");
					sendAPicture.setEnabled(false);
					sendAPicture.setAlpha(0.35f);
					state = 0;
				}
			}

		});

		sendAPicture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(v.getContext());
				dialog.setContentView(R.layout.dialog_select_photo_location);
				dialog.setTitle("Select Location");

				final Spinner spinnerFaculty = (Spinner) dialog
						.findViewById(R.id.spinner_faculty);
				ArrayAdapter<CharSequence> adapter = ArrayAdapter
						.createFromResource(v.getContext(), R.array.locations,
								android.R.layout.simple_spinner_item);
				// Specify the layout to use when the list of choices appears
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// // Apply the adapter to the spinner
				spinnerFaculty.setAdapter(adapter);

				final Spinner spinnerId = (Spinner) dialog
						.findViewById(R.id.spinner_id);
				ArrayAdapter<CharSequence> adapterId = ArrayAdapter
						.createFromResource(v.getContext(), R.array.arts_name,
								android.R.layout.simple_spinner_item);
				// Specify the layout to use when the list of choices appears
				adapterId
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// Apply the adapter to the spinner
				spinnerId.setAdapter(adapterId);

				spinnerFaculty
						.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(AdapterView<?> parent,
									View view, int position, long id) {
								Spinner spinnerId = (Spinner) dialog
										.findViewById(R.id.spinner_id);
								ArrayAdapter<CharSequence> adapterId = null;
								switch (position) {
								case 0:
									adapterId = ArrayAdapter.createFromResource(
											view.getContext(),
											R.array.arts_name,
											android.R.layout.simple_spinner_item);
									break;
								case 1:
									adapterId = ArrayAdapter.createFromResource(
											view.getContext(),
											R.array.business_name,
											android.R.layout.simple_spinner_item);
									break;
								case 2:
									adapterId = ArrayAdapter.createFromResource(
											view.getContext(),
											R.array.computing_name,
											android.R.layout.simple_spinner_item);
									break;
								case 3:
									adapterId = ArrayAdapter.createFromResource(
											view.getContext(),
											R.array.engineering_name,
											android.R.layout.simple_spinner_item);
									break;
								case 4:
									adapterId = ArrayAdapter.createFromResource(
											view.getContext(),
											R.array.medicine_name,
											android.R.layout.simple_spinner_item);
									break;
								case 5:
									adapterId = ArrayAdapter.createFromResource(
											view.getContext(),
											R.array.science_name,
											android.R.layout.simple_spinner_item);
									break;
								case 6:
									adapterId = ArrayAdapter.createFromResource(
											view.getContext(),
											R.array.sde_name,
											android.R.layout.simple_spinner_item);
									break;
								case 7:
									adapterId = ArrayAdapter.createFromResource(
											view.getContext(),
											R.array.utown_name,
											android.R.layout.simple_spinner_item);
									break;
								}
								adapterId
										.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
								// Apply the adapter to the spinner
								spinnerId.setAdapter(adapterId);
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub

							}

						});

				Button send_button = (Button) dialog
						.findViewById(R.id.send_button);
				send_button.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						savedPictureFile = handPicStore.getPictureFile();

						int location_id = (spinnerFaculty
								.getSelectedItemPosition() * 10)
								+ (spinnerId.getSelectedItemPosition());
						new POSTMultipart(savedPictureFile, Integer
								.toString(location_id), dialog).execute();
					}

				});

				dialog.show();
				//
				// Log.i("TakePhotoActivity - send picture", "sad");
				// Log.i("TakePhotoActivity - send picture",
				// savedPictureFile.toString());
				//
			}

		});
	}

	protected void showPicture(byte[] pictureByte) {
		final Bitmap bmp = BitmapFactory.decodeByteArray(pictureByte, 0,
				pictureByte.length);

		new Dialog(getBaseContext()) {
			@Override
			public void onCreate(Bundle unused) {
				ImageView myImage = new ImageView(getContext());
				myImage.setImageBitmap(bmp);
				setContentView(myImage);
			}
		}.show();

	}

	private class POSTMultipart extends AsyncTask {
		private Activity ac;
		private File pic_file;
		String locationId;
		Dialog dialog;

		public POSTMultipart(Activity ac) {
			this.ac = ac;
		}

		public POSTMultipart(File pic_file, String locationId, Dialog dialog) {
			this.pic_file = pic_file;
			this.locationId = locationId;
			this.dialog = dialog;
		}

		@Override
		protected Object doInBackground(Object... params) {
			// setup a http client.
			HttpClient httpclient = new DefaultHttpClient();
			httpclient.getParams().setParameter(
					CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

			// we want to do a HTTP POST method
			HttpPost httppost = new HttpPost(
					"http://insto-web.herokuapp.com/submission");

			// see conversion of bitmap to file method
			// File file = ((MainActivity) ac)
			// .convertBitmapToFile(((BitmapDrawable) mImage.getDrawable())
			// .getBitmap());

			File file = pic_file;
			Log.i("TakePhotoActivity - multipart", pic_file.toString());
			// the body of the http packet, containing 2 stings and 1 file
			MultipartEntity mpEntity = new MultipartEntity();
			ContentBody image = new FileBody(file, "image/jpeg");
			StringBody location_id = null;
			StringBody user_id = null;
			StringBody gleam = null;
			try {
				user_id = new StringBody("1");
				location_id = new StringBody(locationId);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// setting the variables required
			mpEntity.addPart("submission[image]", image);
			mpEntity.addPart("submission[user_id]", user_id);
			mpEntity.addPart("submission[location_id]", location_id);

			// set the entity to be part of our http post
			httppost.setEntity(mpEntity);
			System.out.println("executing request " + httppost.getEntity());

			try {
				// finally execute http
				HttpResponse response = httpclient.execute(httppost);

				// convert respose to JSON Object and return for post execute
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								response.getEntity().getContent(), "UTF-8"));
				StringBuilder builder = new StringBuilder();
				for (String line = null; (line = reader.readLine()) != null;) {
					builder.append(line).append("\n");
					System.out.println(line);
				}
				JSONTokener tokener = new JSONTokener(builder.toString());
				JSONObject finalResult = new JSONObject(tokener);

				return finalResult;

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object reply) {
			Log.i("TakePhotoActivity - reply", ((JSONObject) reply).toString());
			if (dialog != null)
				dialog.dismiss();
			Toast.makeText(getApplicationContext(),
					"Photo sent, MUTHER FUCKER!!!!", Toast.LENGTH_LONG).show();
			// mImage.setVisibility(View.GONE);
			// mButton.setVisibility(View.GONE);
			// mText.setVisibility(View.VISIBLE);
			// mText.setText(((JSONObject) reply).toString());
		}
	}

}
