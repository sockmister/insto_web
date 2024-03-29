package com.example.icreatesecretproject.TakePhoto;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TakePhotoSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback {

	private SurfaceHolder holder;
	private Camera camera;

	@SuppressWarnings("deprecation")
	public TakePhotoSurfaceView(Context context) {
		super(context);

		// Initiate the Surface Holder properly
		this.holder = this.getHolder();
		this.holder.addCallback(this);
		this.holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			// Open the Camera in preview mode
			this.camera = Camera.open();
			this.camera.setDisplayOrientation(90);
			this.camera.setPreviewDisplay(this.holder);
		} catch (IOException ioe) {
			ioe.printStackTrace(System.out);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// Now that the size is known, set up the camera parameters and begin
		// the preview.
		Camera.Parameters parameters = camera.getParameters();
		List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
		Camera.Size previewSize = previewSizes.get(0);

		parameters.setPreviewSize(previewSize.width, previewSize.height);
		camera.setParameters(parameters);
		camera.startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// Surface will be destroyed when replaced with a new screen
		// Always make sure to release the Camera instance
		camera.stopPreview();
		camera.release();
		camera = null;
	}

	public Camera getCamera() {
		return this.camera;
	}

}
