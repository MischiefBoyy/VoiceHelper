package com.inrich.VoiceHelper.model;

/**
 * 类名: Button </br>
 * 包名：  描述: 菜单项的基类 </br>
 */
public class Button {
	private String name;// 所有一级菜单、二级菜单都共有一个相同的属性，那就是name

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
