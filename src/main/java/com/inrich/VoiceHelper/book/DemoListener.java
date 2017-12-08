package com.inrich.VoiceHelper.book;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DemoListener implements ApplicationListener<DemoEvent> {

	@Override
	public void onApplicationEvent(DemoEvent arg0) {
		
		switch (arg0.getType()) {
		case 1:
			  System.out.println("监听类型A");
			break;

		default:
			 System.out.println("监听类型其他");
			break;
		}
		
		String msg=arg0.getMsg();
		
		System.out.println("接收到发布的信息为："+msg);
	}

}
