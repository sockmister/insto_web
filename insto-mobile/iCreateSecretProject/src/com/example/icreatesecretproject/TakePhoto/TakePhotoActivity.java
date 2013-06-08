package com.example.icreatesecretproject.TakePhoto;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.icreatesecretproject.R;

public class TakePhotoActivity extends Activity {
	int state = 0;
	Button takeAPicture;
	Button sendAPicture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_photo);

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
				HandlePictureStorage handPicStore = new HandlePictureStorage();
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
				dialog.show();
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

}