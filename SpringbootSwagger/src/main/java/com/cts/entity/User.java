package com.cts.entity;

import org.apache.commons.lang.builder.ToStringBuilder;

public class User {
	private String name;
	private String date;
	public String getName() {
	
		return name;
	}
	public void setName(String name) {
	
		this.name = name;
	}
	public String getDate() {
	
		return date;
	}
	public void setDate(String date) {
	
		this.date = date;
	}
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}

	