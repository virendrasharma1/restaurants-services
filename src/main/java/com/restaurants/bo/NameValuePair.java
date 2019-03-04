package com.restaurants.bo;

import java.io.Serializable;

public class NameValuePair implements Serializable{

	private static final long serialVersionUID = 5879021511562302024L;

	private String value;
	private String name;
	

	public NameValuePair() {
		super();
	}
	public NameValuePair(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
