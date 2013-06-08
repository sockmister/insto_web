package com.example.icreatesecretproject.TakePhoto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.util.Log;

public class HandlePictureStorage implements PictureCallback
{
	
		public byte[] picture;
		public static final int MEDIA_TYPE_IMAGE = 1;
        @Override
        public void onPictureTaken(byte[] picture, Camera camera) 
        {
        	this.picture = picture;
        	 File pictureFile = getOutputMediaFile();
             if (pictureFile == null){
                 return;
             }

             try {
                 FileOutputStream fos = new FileOutputStream(pictureFile);
                 fos.write(picture);
                 fos.close();
                 camera.stopPreview();
             } catch (FileNotFoundException e) {

             } catch (IOException e) {
              
             }
        }

        private static File getOutputMediaFile(){
        	 
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                      Environment.DIRECTORY_PICTURES), "MyCameraApp");
     
            if (! mediaStorageDir.exists()){
                if (! mediaStorageDir.mkdirs()){
                    Log.d("MyCameraApp", "failed to create directory");
                    return null;
                }
            }
     
            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File mediaFile;
                mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
                Log.d("MyCameraApp", mediaFile.toString());
            return mediaFile;
        }
        
        public byte[] getPictureByte(){
        	return this.picture;
        }
}
