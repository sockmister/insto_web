package com.example.icreatesecretproject;

import java.util.Hashtable;

import com.google.gson.Gson;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class InstoApplication extends Application {

	public static InstoApplication instance = null;
	static private Context applicationContext;
	private Hashtable<String, Object> cache = new Hashtable<String, Object>();

	@Override
	public void onCreate() {
		super.onCreate();
		
		System.out.println("here");

		// provide an instance for our static accessors
		instance = this;

		// fetching application context
		applicationContext = getApplicationContext();
	}

	public Hashtable<String, Object> getCache() {
		return cache;
	}

	public void setCache(Hashtable<String, Object> cache) {
		this.cache = cache;
	}
	
	public User getUserInfo(){
		if(cache.get("USER") == null){	//hashtable was destroyed
			SharedPreferences pref = this.getSharedPreferences("USER", 0);
			String json = pref.getString("USER_INFO", "");
			
			System.out.println("json is " + json);
			User user = new Gson().fromJson(json, User.class);
			if(user == null)
				System.out.println("user is null");
			cache.put("USER", user);
			
			return user;
		}
		else
			return (User) cache.get("USER");
	}
}
