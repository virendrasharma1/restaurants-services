package com.restaurants.bo;

import java.io.Serializable;

public class IdValueParams implements Serializable{

	private static final long serialVersionUID = -1754781810365487084L;
	private Long id;
	private String value;
	private String params;
	private Object payload;

	public IdValueParams() {
		super();
	}
	public IdValueParams(Long id, String value) {
		super();
		this.id = id;
		this.value = value;
	}
	public IdValueParams(String value, String params) {
		this.value = value;
		this.params = params;
	}

	public IdValueParams(Long id, String value, String params) {
		this(id, value);
		this.params = params;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}
}
