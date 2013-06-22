package com.example.icreatesecretproject;

import java.util.Hashtable;

import android.app.Application;
import android.content.Context;

public class InstoApplication extends Application {

	private static InstoApplication instance = null;
	static private Context applicationContext;
	private Hashtable<String, Object> cache = new Hashtable<String, Object>();

	@Override
	public void onCreate() {
		super.onCreate();

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

}
