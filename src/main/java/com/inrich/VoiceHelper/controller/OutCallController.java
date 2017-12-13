package com.inrich.VoiceHelper.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

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
	public String outCallIndex(HttpServletRequest request) {
		return outCallService.outCallIndex(request);
	}
	
	@RequestMapping("/yes")
	public String outCallYes(@RequestParam int yesId,HttpServletRequest request) {
		return outCallService.outCallYes(yesId,request);
	}
	
	@RequestMapping("/refuse")
	public String outCallRefuse(@RequestParam int refuseId,HttpServletRequest request) {
		return outCallService.outCallRefues(refuseId,request);
	}
	
	@RequestMapping("/textDoAction")
	public String outCallDotext(@RequestParam String action ,@RequestParam String data,@RequestParam int yesId ,@RequestParam int refuseId,HttpServletRequest request) {
		return outCallService.dotext(data, action,yesId,refuseId,request);
	}
	
	@RequestMapping("/doVoice")
	public String outCalldoVoice(@RequestParam String action ,@RequestParam String mediaId,@RequestParam int yesId ,@RequestParam int refuseId,HttpServletRequest request) throws UnsupportedEncodingException {
		return outCallService.doVoice(mediaId, action, yesId, refuseId, request);
	}
	

}
