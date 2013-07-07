package com.example.imagehttppost;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

public class MainActivity extends Activity {

	private TextView mText;
	private ImageView mImage;
	private Button mButton;

	private AQuery aq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// setup
		mText = (TextView) findViewById(R.id.textView1);
		mImage = (ImageView) findViewById(R.id.imageView1);
		mButton = (Button) findViewById(R.id.button1);
		aq = new AQuery(this);
		aq.id(R.id.button1).clicked(this, "onPOSTButtonClicked");

		mText.setVisibility(View.GONE);

		// get a random image
		String url = "http://farm7.staticflickr.com/6125/6001024506_9739866a88.jpg";
		boolean memCache = true;
		boolean fileCache = true;
		int targetWidth = 1024;
		int fallbackId = 0;
		Bitmap preset = null;
		int animId = 0;
		float ratio = AQuery.RATIO_PRESERVE;
		aq.id(R.id.imageView1)
				.progress(R.id.progress)
				.image(url, memCache, fileCache, targetWidth, fallbackId,
						preset, animId, ratio);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onPOSTButtonClicked(View v) {
		new POSTMultipart(this).execute();
	}

	public File convertBitmapToFile(Bitmap image) {
		// create a file to write bitmap data
		File f = null;
		try {
			f = File.createTempFile("file", "file", this.getFilesDir());

			// Convert bitmap to byte array
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			// do you know of a better way to do this? this compress is actually
			// quite slow.
			image.compress(Bitmap.CompressFormat.JPEG,
					100 /* ignored for PNG */, bos);
			byte[] bitmapdata = bos.toByteArray();

			// write the bytes in file
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(bitmapdata);

			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return f;
	}

	private class POSTMultipart extends AsyncTask {
		private Activity ac;

		public POSTMultipart(Activity ac) {
			this.ac = ac;
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
			File file = ((MainActivity) ac)
					.convertBitmapToFile(((BitmapDrawable) mImage.getDrawable())
							.getBitmap());

			// the body of the http packet, containing 2 stings and 1 file
			MultipartEntity mpEntity = new MultipartEntity();
			ContentBody image = new FileBody(file, "image/jpeg");
			StringBody location_id = null;
			StringBody user_id = null;
			StringBody gleam = null;
			try {
				user_id = new StringBody("1");
				location_id = new StringBody("1");
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
			mImage.setVisibility(View.GONE);
			mButton.setVisibility(View.GONE);
			mText.setVisibility(View.VISIBLE);
			mText.setText(((JSONObject) reply).toString());
		}
	}

}
