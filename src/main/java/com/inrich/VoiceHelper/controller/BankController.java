package com.inrich.VoiceHelper.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inrich.VoiceHelper.service.BankService;

@RestController
@RequestMapping("/bankQa")
public class BankController {
	@Autowired
	private BankService bankService;

	// 首页介绍，返回自动生成的问答
	@RequestMapping(value = "/index")
	public String doIndexQa() {
		return bankService.getIndexInfo();
	}

	@RequestMapping(value = "/normal")
	public String doNormalQa(@RequestParam int level, @RequestParam int id) {
		return bankService.getNormalInfo(level, id);
	}
	@RequestMapping(value = "/textQa")
	public String doTextQa(@RequestParam String question) throws UnsupportedEncodingException {
		System.out.println("问题："+question);
		return bankService.doTxtQa(question,"bank");
	}
	
	@RequestMapping(value = "/voiceQa")
	public String doVoiceQa(@RequestParam String mediaId) throws UnsupportedEncodingException {
		System.out.println("mediaId:"+mediaId);
		return bankService.doVoiceQa(mediaId, "bank");
	}
}
