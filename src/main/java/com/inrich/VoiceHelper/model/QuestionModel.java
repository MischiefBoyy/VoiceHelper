package com.inrich.VoiceHelper.model;

public class QuestionModel {
	private int id;
	private int yesId;
	private int refuseId;
	private String question;
	private String action;

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

}
