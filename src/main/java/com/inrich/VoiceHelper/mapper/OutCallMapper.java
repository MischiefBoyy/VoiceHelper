package com.inrich.VoiceHelper.mapper;

import java.util.Map;

import com.inrich.VoiceHelper.model.QuestionModel;

public interface OutCallMapper {
	
	public QuestionModel getIndexQuestion();
	
	public QuestionModel getYesQuestion(int yesId);
	
	public Map<String,Object> getRefuseQuestion(int refuseId);
}
