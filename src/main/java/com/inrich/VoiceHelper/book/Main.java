package com.inrich.VoiceHelper.book;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	public static void main(String[] args) {
//		AnnotationConfigApplicationContext context=new 
//				AnnotationConfigApplicationContext(Elconfig.class);
//		Elconfig elconfig=context.getBean(Elconfig.class);
//		
//		elconfig.outPrintResource();
//		context.close();
		
//		AnnotationConfigApplicationContext context1=new AnnotationConfigApplicationContext();
//		context1.getEnvironment().setActiveProfiles("prod");
//		
//		context1.register(ProfileConfig.class);
//		context1.refresh();
//		
//		
//		DemoBean bean=context1.getBean(DemoBean.class);
//		System.out.println(bean.getContent());
//		context1.close();
		
		AnnotationConfigApplicationContext context2=new AnnotationConfigApplicationContext(EventConfig.class);
		DemoPublisher demoPublisher=context2.getBean(DemoPublisher.class);
		
		
		DemoEvent demoEvent=new DemoEvent(context2,"类型AAAAA");
		demoEvent.setType(1);
		demoPublisher.publish(demoEvent);
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					DemoEvent demoEvent=new DemoEvent(context2,"类型BBBBBB");
					demoEvent.setType(2);
					demoPublisher.publish(demoEvent);
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}finally {
					context2.close();
				}
				
			}
		}).start();
		
		
		
	}

}
