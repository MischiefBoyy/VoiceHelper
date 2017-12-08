package com.inrich.VoiceHelper.book;

import org.springframework.context.ApplicationEvent;

public class DemoEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	private String msg;
	private int type;

	public DemoEvent(Object source, String msg) {
		super(source);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


	
	

}
