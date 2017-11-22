package com.inrich.VoiceHelper.book;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestScope {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ScopeConfig.class);
		SingletonService s1 = context.getBean(SingletonService.class);
		SingletonService s2 = context.getBean(SingletonService.class);

		PrototypeService p1 = context.getBean(PrototypeService.class);
		PrototypeService p2 = context.getBean(PrototypeService.class);

		System.out.println("s1==s2:" + (s1 == s2));
		System.out.println("s1==s2:" + (p1 == p2));

		context.close();
	}

}
