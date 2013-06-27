package com.example.icreatesecretproject;

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
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class POSTMultipart extends AsyncTask {
	private Activity ac;
	private File pic_file;
	String locationId;
	Dialog dialog;
	Context c;

	public POSTMultipart(Activity ac) {
		this.ac = ac;
	}

	public POSTMultipart(File pic_file, String locationId, Dialog dialog,
			Context c) {
		this.pic_file = pic_file;
		this.locationId = locationId;
		this.dialog = dialog;
		this.c = c;
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
			BufferedReader reader = new BufferedReader(new InputStreamReader(
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
		Toast.makeText(c, "Photo sent, MUTHER FUCKER!!!!", Toast.LENGTH_LONG)
				.show();
		// mImage.setVisibility(View.GONE);
		// mButton.setVisibility(View.GONE);
		// mText.setVisibility(View.VISIBLE);
		// mText.setText(((JSONObject) reply).toString());
	}
}