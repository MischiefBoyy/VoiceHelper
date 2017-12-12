package com.inrich.VoiceHelper.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OutprintMsg {
	private String state;
	private Object data;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public OutprintMsg(String state, Object object) {
		super();
		this.state = state;
		this.data = object;
	}

	public String toJson() {
		Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
		return gson.toJson(this);
	}

}
