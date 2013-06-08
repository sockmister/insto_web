package com.example.icreatesecretproject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
//		sm.setShadowWidthRes(R.dimen.shadow_width);
//		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Log.e("MAIN ACTIVITY", "first");
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.menu_frame);
		Log.e("MAIN ACTIVITY", "second");
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.menu_frame, new MainMenuFragment()).commit();
		setSlidingActionBarEnabled(true);
		
		ImageView imageView = (ImageView) findViewById(R.id.imageView);
		URL url;
//		try {
//			url = new URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
//			Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//			imageView.setImageBitmap(bmp);
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		Uri imageuri = Uri.parse("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
//		imageView.setImageURI(imageuri);
		AQuery aq = new AQuery(this);
		aq.id(R.id.imageView).image("http://www.nus.edu.sg/identity/logo/images/nus-hlogo-color.gif");
	}




	public void switchContent(Fragment fragment) {
	getSupportFragmentManager().beginTransaction()
	.replace(R.id.dashboard_frame, fragment).commit();
	getSlidingMenu().showContent();
	}

	@Override
	public void onBackPressed() {
	getSlidingMenu().toggle();
	}
	

}
