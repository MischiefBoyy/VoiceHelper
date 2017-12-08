package com.inrich.VoiceHelper.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class DemoPublisher {

	@Autowired
	ApplicationContext applicationContext;
	
	public void publish(DemoEvent event) {
		applicationContext.publishEvent(event);
		//applicationContext.publishEvent(new DemoEvent2(this,msg));
	}
}
