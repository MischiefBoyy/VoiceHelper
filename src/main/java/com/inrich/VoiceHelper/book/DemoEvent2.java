package com.inrich.VoiceHelper.book;

import org.springframework.context.ApplicationEvent;

public class DemoEvent2 extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	private String msg;

	public DemoEvent2(Object source, String msg) {
		super(source);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
