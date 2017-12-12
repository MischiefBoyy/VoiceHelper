package com.inrich.VoiceHelper.model;

public class QuestionModel {
	private int id;
	private int yesId;
	private int refuseId;
	private String question;
	private String action;
	private String yesName;
	private String refuseName;
	private String keyWord;
	private int doneId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getYesId() {
		return yesId;
	}

	public void setYesId(int yesId) {
		this.yesId = yesId;
	}

	public int getRefuseId() {
		return refuseId;
	}

	public void setRefuseId(int refuseId) {
		this.refuseId = refuseId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getYesName() {
		return yesName;
	}

	public void setYesName(String yesName) {
		this.yesName = yesName;
	}

	public String getRefuseName() {
		return refuseName;
	}

	public void setRefuseName(String refuseName) {
		this.refuseName = refuseName;
	}

	public int getDoneId() {
		return doneId;
	}

	public void setDoneId(int doneId) {
		this.doneId = doneId;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	

}
