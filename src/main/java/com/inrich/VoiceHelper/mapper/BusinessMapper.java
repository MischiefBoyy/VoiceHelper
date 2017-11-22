package com.inrich.VoiceHelper.mapper;

import java.util.List;
import java.util.Map;

import com.inrich.VoiceHelper.model.BusinessIntrodution;


public interface BusinessMapper {
	/**
	 * @TODO 获得所有的一级业务信息
	 * @Time 2017年11月8日 下午2:03:52
	 * @author WEQ
	 */
	List<Map<String,Object>> findAllBusiness();
	
	/**
	 * 
	 * @TODO 根据关键字获得一级业务的信息
	 * @Time 2017年11月8日 下午2:04:14
	 * @author WEQ
	 */
	String getIntrodutionByName(String name);
	
	/**
	 * 根据关键字获得busniess的id
	 * @param name
	 * @return
	 */
	BusinessIntrodution getBusinessByName(String name);
	
}
