package com.inrich.VoiceHelper.mapper;

import java.util.Map;

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

}
