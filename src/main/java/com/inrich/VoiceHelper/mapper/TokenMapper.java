package com.inrich.VoiceHelper.mapper;

import java.util.List;
import java.util.Map;

import com.inrich.VoiceHelper.model.TokenInfo;


public interface TokenMapper {
	/**
	 * @TODO 更新token的时间和值
	 * @Time 2017年11月8日 下午3:40:35
	 * @author WEQ
	 */
	void update(Map<String,Object> map);
	
	/**
	 * @TODO 添加token
	 * @Time 2017年11月8日 下午3:40:57
	 * @author WEQ
	 */
	int add(TokenInfo token);
	
	/**
	 * @TODO 根据name 获得相对应的token的信息
	 * @Time 2017年11月8日 下午3:41:15
	 * @author WEQ
	 */
	Map<String,Object> get(String name);
	
	List<TokenInfo> getTestData();

}
