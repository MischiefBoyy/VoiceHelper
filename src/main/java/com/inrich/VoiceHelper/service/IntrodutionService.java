package com.inrich.VoiceHelper.service;


public interface IntrodutionService {
	
	
	
	/**
	 * 获得首页自动设置的信息
	 * @return
	 */
	String getIndexIntrodution() ;
	
	/**
	 * 获得点击链接的时候，起所对应的下级信息
	 * @param name
	 * @param id
	 * @param level
	 * @return
	 */
	String getNormalIntroduction(String name,int id,int level);
	
	/**
	 * 用户直接输入关键字查询所得
	 * @param name
	 * @param level
	 * @return
	 */
	String getNormalIntroduction(String name,int level);
	
}
