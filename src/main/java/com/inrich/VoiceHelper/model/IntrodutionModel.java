package com.inrich.VoiceHelper.model;

import com.google.gson.Gson;

public class IntrodutionModel {
	private Object data;
	private boolean isBase;
	private String introdution;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isBase() {
		return isBase;
	}

	public void setBase(boolean isBase) {
		this.isBase = isBase;
	}

	public String getIntrodution() {
		return introdution;
	}

	public void setIntrodution(String introdution) {
		this.introdution = introdution;
	}
	
	

	/**
	 * 
	 * @param data 将要返回子类列表
	 * @param isBase 是否为最底层介绍
	 * @param introdution 当前层次的介绍
	 */
	public IntrodutionModel(Object data, boolean isBase, String introdution) {
		super();
		this.data = data;
		this.isBase = isBase;
		this.introdution = introdution;
	}

	private String toJson() {
		return new Gson().toJson(this);
	}
	
	public IntrodutionModel(String errorMsg) {
		this.data = null;
		this.isBase = true;
		this.introdution = errorMsg;
		toJson();
	}

}
