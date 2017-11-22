package com.inrich.VoiceHelper.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface VoiceRecordMapper {
	
	/**
	 * 根据关键字获得该关键字是否生成过录音
	 * @param name
	 * @return
	 */
	Map<String,Object> getVoiceRecord(String name);
	
	/**
	 * 插入一条生成语音的记录
	 * @param map
	 * @return
	 */
	int addVoiceRecord(Map<String,Object> map);
	
	
	/**
	 * 
	 * @TODO 插入一条银行问答的语音记录
	 * @Time 2017年11月22日 下午2:19:30
	 * @author WEQ
	 * @return int
	 */
	int addBankVoiceRecord(Map<String,Object> map);
	
	/**
	 * 
	 * @TODO 获得银行的一条语音记录
	 * @Time 2017年11月22日 下午2:23:32
	 * @author WEQ
	 * @return String
	 */
	String getBankVoiceRecord(@Param("pid") Integer pid,@Param("num") Integer num);

}
