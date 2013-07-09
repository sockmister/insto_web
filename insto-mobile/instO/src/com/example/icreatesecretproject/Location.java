package com.example.icreatesecretproject;

public class Location {
	private String faculty;
	private Integer location_id;
	private String location_name;
	
	public Location (){
		
	}
	
	@Override
	public String toString(){
		return this.location_name;
	}
	
	public String getFaculty() {
		return faculty;
	}
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
	public Integer getLocation_id() {
		return location_id;
	}
	public void setLocation_id(Integer location_id) {
		this.location_id = location_id;
	}
	public String getLocation_name() {
		return location_name;
	}
	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}
	
	
}
