package com.inrich.VoiceHelper.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.inrich.VoiceHelper.service.OperateVoiceService;
import com.inrich.VoiceHelper.service.TokenService;
import com.inrich.VoiceHelper.util.MessageUtil;
import com.inrich.VoiceHelper.util.RemoteProperties;
import com.inrich.VoiceHelper.util.TransformVoice;

@RestController
@RequestMapping("/Jsapi")
public class JsapiController {
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private OperateVoiceService  operateVoiceService;
	
	@Autowired
	private RemoteProperties remoteProperties;
	
	/**
	 * @TODO 验证Jsapi是否可用
	 * @Time 2017年11月9日 上午11:32:23
	 * @author WEQ
	 */
	@RequestMapping(value="/checkJsapi",method=RequestMethod.POST)
	public String checkJsApi(@RequestParam String url) {
		System.out.println("url:"+url);
		String signature = null;
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
		String noncestr = UUID.randomUUID().toString();
		String jsapi_ticket = tokenService.getJsApiToken();

		StringBuilder builder = new StringBuilder();
		builder.append("jsapi_ticket=")
			   .append(jsapi_ticket)
			   .append("&noncestr=")
			   .append(noncestr)
			   .append("&timestamp=")
			   .append(timestamp)
			   .append("&url=").append(url);

		signature = DigestUtils.sha1Hex(builder.toString().getBytes());

		Map<String, String> map = new HashMap<String, String>();
		map.put("signature", signature);
		map.put("noncestr", noncestr);
		map.put("appId", MessageUtil.WX_APPID);
		map.put("timestamp", timestamp);
		
		return new Gson().toJson(map);
	}
	/**
	 * 
	 * @TODO 用户进行语音Qa
	 * @Time 2017年11月13日 上午9:21:27
	 * @author WEQ
	 * @return 返回所对应的
	 */
	@RequestMapping(value="/voiceQa",method=RequestMethod.POST)
	public String voiceQa(@RequestParam String mediaId,HttpServletRequest request) throws UnsupportedEncodingException {
		System.out.println("-----进入语音分析,mediaId:"+mediaId);
		long stime=System.currentTimeMillis();
		//语音文件下载所保存的路径
		String downloadPath=operateVoiceService.downloadVoice(mediaId);
		
		//经过ffmpeg 转换语音后重新生成的语音路径
		String servicePath = remoteProperties.getWxtoxf() + mediaId + ".wav";
		
		//转换成 XF需要的音频格式
		TransformVoice.doTransformWav2Wav(downloadPath, servicePath,remoteProperties.getFfmpeg());
		
		System.out.println("servicePath:"+servicePath);
		
		String outPrin=operateVoiceService.analysisVoice(servicePath,"main");
		System.out.println("共用时："+(System.currentTimeMillis()-stime));
		System.out.println(outPrin);
		//String result="{\"state\":\"success\",\"data\":{\"data\":{\"data\":[{\"level\":2.0,\"name\":\"大堂引导服务\",\"id\":3.0},{\"level\":2.0,\"name\":\"现金清分清点服务\",\"id\":4.0},{\"level\":2.0,\"name\":\"定点收款服务\",\"id\":5.0},{\"level\":2.0,\"name\":\"资料扫描录入服务\",\"id\":6.0}],\"isBase\":false,\"introdution\":\"由四部分组成，大堂引导服务、现金清分清点服务、定点收款服务、资料扫描录入服务\"},\"path\":\"3381a331-544d-4bcd-8ce5-1868f52d84c3.mp3\"}}";
		return outPrin;
		
	}
	/**
	 * 
	 * @TODO 用户进行文字Qa
	 * @Time 2017年11月13日 上午9:20:27
	 * @author WEQ
	 * @return 返回文字介绍用语
	 */
	@RequestMapping(value="/textQa",method=RequestMethod.POST)
	public String textQa(@RequestParam String question) throws UnsupportedEncodingException {
		String outPrint=operateVoiceService.analysisText(question,"main");
		return outPrint;
	}
	
	
//	@RequestMapping("/files")
//	public StreamingResponseBody getSteamingFile(HttpServletResponse response) throws IOException {
//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "attachment; filename=\"demo.pdf\"");
//        InputStream inputStream = new FileInputStream(new File("C:\\demo-file.pdf"));
//        return outputStream -> {
//            int nRead;
//            byte[] data = new byte[1024];
//            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
//                System.out.println("Writing some bytes..");
//                outputStream.write(data, 0, nRead);
//            }
//        };
//    }
	

}
