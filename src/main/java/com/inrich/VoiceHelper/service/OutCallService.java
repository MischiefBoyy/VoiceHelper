package com.inrich.VoiceHelper.service;

public interface OutCallService {
	
	public String outCallIndex() ;
	
	public String outCallYes(int yesId);
	
	public String outCallRefues(int refuseId);
	
	public String textDoAction(String data,String action);
	
	public String textDoProcess(int yesId,int noId,String data);
	 
	
	
	
	

}
