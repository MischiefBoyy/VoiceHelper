package com.inrich.VoiceHelper.mapper;


import com.inrich.VoiceHelper.model.QuestionModel;

public interface OutCallMapper {
	
	public QuestionModel getIndexQuestion();
	
	public QuestionModel getYesQuestion(int yesId);
	
	public QuestionModel getRefuseQuestion(int refuseId);
	
	public String getActionTableName(String actionName);
	
	public QuestionModel getYesQuestionByAction(String action);
	
	public QuestionModel getRefuseQuestionByAction(String action);
}
