package com.example.icreatesecretproject;

import java.util.ArrayList;


import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.Transformer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class User {
	private String created_at;
	private Integer id;
	private Integer points;
	private String updated_at;
	private String user;
	private String email;
	private String name;
	
	//transformer, specifically to return a ArrayList of Products
	public static class UserTransformer implements Transformer{		
		@Override
		public <T> T transform(String url, Class<T> type, String encoding, byte[] data, AjaxStatus status) {
			Gson g = new Gson();
			T result = g.fromJson(new String(data), new TypeToken<User>(){}.getType());				
			
			return result;
		}
	}
	
	public User(){
		
	}
	
	public User(String created_at, int id, int points, String updated_at, String user){
		this.created_at = created_at;
		this.id = id;
		this.points = points;
		this.updated_at = updated_at;
		this.user = user;
	}
	
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public Integer getId() {
		return id;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
}
