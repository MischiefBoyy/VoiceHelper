package com.inrich.VoiceHelper.service;

import java.io.UnsupportedEncodingException;

public interface OperateVoiceService {
	/**
	 * 
	 * @TODO 从WX下载服务语音临时素材
	 * @Time 2017年11月9日 下午3:05:16
	 * @author WEQ
	 * @return 语音下载的本地保存路径
	 */
	String downloadVoice(String mediaId);
	
	/**
	 * @TODO 解析经过调用语音语意后的结果,
	 * @Time 2017年11月10日 下午3:19:54
	 * @author WEQ
	 * @return 
	 */
	String analysisVoice(String voicePath,String scene) throws UnsupportedEncodingException;
	
	
	/**
	 * 文本语音解析
	 * @param question
	 * @return 返回相对应的介绍话语
	 */
	String analysisText(String question,String scene) throws UnsupportedEncodingException;
	
	/**
	 * 语意语意分析
	 * @TODO TODO
	 * @Time 2017年11月20日 下午3:32:45
	 * @author WEQ
	 * @return String
	 */
	String doRecordToText(String voicePath,String scene)throws UnsupportedEncodingException;
	

}
