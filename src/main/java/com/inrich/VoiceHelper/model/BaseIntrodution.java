package com.inrich.VoiceHelper.model;


import javax.persistence.Id;

/**
 * 介绍内容的基类
 * @author weq
 *
 */
public class BaseIntrodution {
	@Id
	public int id;
	public String name;
	public String introdution;
	public String createTime;
	public int level;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntrodution() {
		return introdution;
	}

	public void setIntrodution(String introdution) {
		this.introdution = introdution;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	

}
