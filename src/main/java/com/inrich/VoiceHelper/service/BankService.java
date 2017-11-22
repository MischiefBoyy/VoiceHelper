package com.inrich.VoiceHelper.service;

import java.io.UnsupportedEncodingException;

public interface BankService {
	
	String getIndexInfo();
	
	String getNormalInfo(int level,int id);
	
	String doTxtQa(String question,String scene)throws UnsupportedEncodingException;
	
	String doVoiceQa(String mediaId,String scene)throws UnsupportedEncodingException;

}
