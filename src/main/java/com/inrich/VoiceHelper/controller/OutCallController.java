package com.inrich.VoiceHelper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inrich.VoiceHelper.service.OutCallService;

@RestController
@RequestMapping("/outcall")
public class OutCallController {
	@Autowired
	private OutCallService outCallService;
	
	
	@RequestMapping("/index")
	public String outCallIndex() {
		return outCallService.outCallIndex();
	}
	
	@RequestMapping("/yes")
	public String outCallYes(@RequestParam int yesId) {
		return outCallService.outCallYes(yesId);
	}
	
	@RequestMapping("/refuse")
	public String outCallRefuse(@RequestParam int refuseId) {
		return outCallService.outCallRefues(refuseId);
	}
	
	@RequestMapping("/textDoAction")
	public String outCallRefuse(@RequestParam String action ,@RequestParam String data) {
		return outCallService.textDoAction(data, action);
	}

}
