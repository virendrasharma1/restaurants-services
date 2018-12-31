package com.restaurants.restaurants.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResponseVO implements Serializable {

	private static final long serialVersionUID = -414086726754750613L;
	
	private String payload = null ;
	private int appStatusCode = -1;
	private List<String> messages = new ArrayList<>();

	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	public int getAppStatusCode() {
		return appStatusCode;
	}
	public void setAppStatusCode(int appStatusCode) {
		this.appStatusCode = appStatusCode;
	}
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
}
