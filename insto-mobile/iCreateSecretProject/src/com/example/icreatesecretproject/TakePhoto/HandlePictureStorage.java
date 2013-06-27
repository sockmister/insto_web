package com.example.icreatesecretproject.TakePhoto;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.util.Log;

public class HandlePictureStorage implements PictureCallback {

	public byte[] picture;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static File savedPictureFile;

	@Override
	public void onPictureTaken(byte[] picture, Camera camera) {
		this.picture = picture;

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 7;
		Bitmap img = BitmapFactory.decodeByteArray(picture, 0, picture.length,
				options);

		Matrix rotateRight = new Matrix();
		rotateRight.preRotate(90);

		// if (android.os.Build.VERSION.SDK_INT > 13) {
		// float[] mirrorY = { -1, 0, 0, 0, 1, 0, 0, 0, 1 };
		// rotateRight = new Matrix();
		// Matrix matrixMirrorY = new Matrix();
		// matrixMirrorY.setValues(mirrorY);
		//
		// rotateRight.postConcat(matrixMirrorY);
		//
		// rotateRight.preRotate(270);
		//
		// }
		final Bitmap rImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(),
				img.getHeight(), rotateRight, true);

		File pictureFile = getOutputMediaFile();
		if (pictureFile == null) {
			return;
		}

		try {
			FileOutputStream fos = new FileOutputStream(pictureFile);

			// convert to byte array
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			rImg.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] byteArray = stream.toByteArray();

			fos.write(byteArray);
			fos.close();
			camera.stopPreview();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
		Log.d("MyCameraApp - on picturetake ", savedPictureFile.toString());
	}

	private static File getOutputMediaFile() {

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"MyCameraApp");

		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("MyCameraApp", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File mediaFile;
		mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "IMG_" + timeStamp + ".jpg");
		savedPictureFile = mediaFile;
		Log.d("MyCameraApp", mediaFile.toString());
		return mediaFile;
	}

	public byte[] getPictureByte() {
		return this.picture;
	}

	public File getPictureFile() {
		return HandlePictureStorage.savedPictureFile;
	}
}
