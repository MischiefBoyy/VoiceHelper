package com.inrich.VoiceHelper.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inrich.VoiceHelper.service.IntrodutionService;

@RestController
@RequestMapping("/getIntroduction")
public class HelloController {

	@Autowired
	private IntrodutionService introdutionService;
	
	//首页介绍，返回自动生成的问答
	@RequestMapping(value = "/indexIntroduction")
	public String getFirstIntrodution(HttpServletRequest request) {
		System.out.println(request.getServletContext().getRealPath("/"));
		return introdutionService.getIndexIntrodution();
	}
	
	//当点击url进行问答时，根据所点击内容进行返回值
	@RequestMapping(value = "/normalIntrodution",method=RequestMethod.POST)
	public String getUrlIntrodution(@RequestParam int level, @RequestParam String name, @RequestParam int id) {
		return introdutionService.getNormalIntroduction(name, id, level);

	}

}
