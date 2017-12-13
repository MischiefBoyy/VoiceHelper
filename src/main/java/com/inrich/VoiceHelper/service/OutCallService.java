package com.inrich.VoiceHelper.service;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

public interface OutCallService {
	
	public String outCallIndex(HttpServletRequest request) ;
	
	public String outCallYes(int yesId,HttpServletRequest request);
	
	public String outCallRefues(int refuseId,HttpServletRequest request);
	
	public String textDoAction(String data,String action);
	
	public String textDoProcess(int yesId,int noId,String data);
	
	/**
	 * 解析用户输入的文字意图 可能是流程控制，也可能是业务操作
	 * @TODO TODO
	 * @Time 2017年12月12日 下午5:00:51
	 * @author WEQ
	 * @return String
	 */
	public String dotext(String data,String action,int yesId,int refuseId,HttpServletRequest request);
	
	/**
	 *	语音解析
	 * @TODO TODO
	 * @Time 2017年12月13日 上午11:18:03
	 * @author WEQ
	 * @return String
	 */
	public String doVoice(String mediaId,String action,int yesId,int refuseId,HttpServletRequest request)throws UnsupportedEncodingException;
	 
	
	
	
	

}
