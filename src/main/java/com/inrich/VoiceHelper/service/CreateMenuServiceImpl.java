package com.inrich.VoiceHelper.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inrich.VoiceHelper.model.Button;
import com.inrich.VoiceHelper.model.CommonViewButton;
import com.inrich.VoiceHelper.model.ComplexButton;
import com.inrich.VoiceHelper.model.Menu;
import com.inrich.VoiceHelper.util.HttpUtils;
@Service
public class CreateMenuServiceImpl implements CreateMenuService{
	
	@Autowired
	private TokenService tokenService;
	@Override
	public void updateMenu() {
		
		CommonViewButton level11=new CommonViewButton();
		level11.setName("新闻动态");
		level11.setType("view");
		level11.setUrl("http://www.in-rich.com/");
		
		CommonViewButton level12=new CommonViewButton();
		level12.setName("财安官网");
		level12.setType("view");
		level12.setUrl("http://www.in-rich.com/");
		
		CommonViewButton level13=new CommonViewButton();
		level13.setName("银行问答");
		level13.setType("view");
		level13.setUrl("http://weq.free.ngrok.cc/voiceHelper/html/static/bank.html");
		
		CommonViewButton []level1List =new CommonViewButton[3];
		level1List[0]=level11;
		level1List[1]=level12;
		level1List[2]=level13;
		
		//一级,第一个菜单设置
		ComplexButton   level1Button=new ComplexButton();
		level1Button.setName("走进财安");
		level1Button.setSub_button(level1List);
		
		//一级，第二个菜单设置
		CommonViewButton level2Button=new CommonViewButton();
		level2Button.setName("智能客服");
		level2Button.setType("view");
		level2Button.setUrl("http://weq.free.ngrok.cc/voiceHelper/html/static/index.html");
		
		Menu menu=new Menu();
		menu.setButton(new Button[]{level1Button,level2Button});
		
		//json格式的菜单
		String jsonMenu=new Gson().toJson(menu);
		
		
		String url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
		url=url.replaceAll("ACCESS_TOKEN", tokenService.getAccessToken());
		
		try {
			HttpUtils.HttpClientPost(url, "UTF-8", jsonMenu, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
