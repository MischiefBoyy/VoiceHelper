package com.inrich.VoiceHelper.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.inrich.VoiceHelper.mapper.VoiceRecordMapper;
import com.inrich.VoiceHelper.model.IntrodutionModel;
import com.inrich.VoiceHelper.model.OutprintMsg;
import com.inrich.VoiceHelper.util.CodingUtils;
import com.inrich.VoiceHelper.util.DatetimeUtil;
import com.inrich.VoiceHelper.util.DownLoadMedia;
import com.inrich.VoiceHelper.util.FileUtils;
import com.inrich.VoiceHelper.util.HttpUtils;
import com.inrich.VoiceHelper.util.MessageUtil;
import com.inrich.VoiceHelper.util.RemoteProperties;
import com.inrich.VoiceHelper.util.TransformVoice;

@Service
public class OperateVoiceServiceImpl implements OperateVoiceService {

	@Autowired
	private TokenService tokenService;
	@Autowired
	private IntrodutionService introdutionService;
	@Autowired
	private VoiceRecordMapper voiceRecordMapper;
	@Autowired
	private ComposeVoiceService composeVoiceService;
	@Autowired
	private RemoteProperties remoteProperties;

	@Override
	public String downloadVoice(String mediaId) {

		String filePath = null;
		String requestUrl = MessageUtil.URL_DOWNLOAD_VOICE.replace("ACCESS_TOKEN", tokenService.getAccessToken())
				.replace("MEDIA_ID", mediaId);
		System.out.println(requestUrl);
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");

			// 根据内容类型获取扩展名
			String fileExt = DownLoadMedia.getFileEndWitsh(conn.getHeaderField("Content-Type"));
			//判断是否有文件夹，没有的话则创建
			FileUtils.createFile(remoteProperties.getWx());
			// 将mediaId作为文件名
			filePath = remoteProperties.getWx() + mediaId + fileExt;

			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1)
				fos.write(buf, 0, size);
			fos.close();
			bis.close();

			conn.disconnect();
			String info = String.format("下载媒体文件成功，filePath=" + filePath);
			System.out.println(info);
		} catch (Exception e) {
			filePath = null;
			String error = String.format("下载媒体文件失败：%s", e);
			System.out.println(error);
		}
		return filePath;

	}

	@Override
	public String analysisVoice(String voidcePath, String scene) throws UnsupportedEncodingException {
		String result = null;
		try {
			result = doRecordToText(voidcePath, scene);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String text = null;
		String answer = null;
		JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
		String stateCode = jsonObject.get("code").getAsString();

		if (!("00000".equals(stateCode))) {
			return new OutprintMsg("error", new IntrodutionModel("访问讯飞出现错误！！")).toJson();
		}
		int rc = jsonObject.getAsJsonObject("data").get("rc").getAsInt();
		// 问答库
		if (rc == 0) {
			// 获得问答库对应的返回值
			answer = jsonObject.getAsJsonObject("data").getAsJsonObject("answer").get("text").getAsString();
		}
		if (rc == 4) {
			// 证明是语音语意技能识别，此处需要对text 进行去除处理，然后再进行文本语意解析
			text = jsonObject.getAsJsonObject("data").get("text").getAsString();
			// 向讯发发送文本，进行文本语意解析
			String semanticText = doText2Value(text.replaceAll("。", ""), scene);
			// 解析文本语意返回的结果
			answer = analyzeTextSemantics(semanticText, "voice");
		}

		return answer;
	}

	/**
	 * 文本语义接口 发送文本到XF服务器
	 * 
	 * @throws UnsupportedEncodingException
	 */
	private String doText2Value(String question, String scene) throws UnsupportedEncodingException {

		String result = "";
		// String question="财安";
		String base64Question = Base64.getEncoder().encodeToString(question.getBytes("utf-8"));

		// 获得 从1970年1月1日0点0 分0 秒开始到现在的秒数(String)
		// 参数二
		java.util.Calendar cal = java.util.Calendar.getInstance();
		String curTime = cal.getTimeInMillis() / 1000 + "";

		// 参数三
		Map<String, Object> paramJson = new HashMap<>();
		paramJson.put("scene", scene);
		paramJson.put("userid", "user_0001");
		String param = new Gson().toJson(paramJson);
		param = Base64.getEncoder().encodeToString(param.getBytes("utf-8"));

		// 参数四
		StringBuilder s2Md5 = new StringBuilder();
		s2Md5.append(MessageUtil.XF_APP_KEY).append(curTime).append(param).append("text=").append(base64Question);

		String checkSum = null;
		checkSum = CodingUtils.md5Encode(s2Md5.toString());

		// 请求设置body
		Map<String, Object> bodyMap = new HashMap<String, Object>();
		bodyMap.put("text", base64Question);

		// 请求设置头部
		Map<String, Object> HeaderMap = new HashMap<String, Object>();
		HeaderMap.put("X-Appid", MessageUtil.XF_APP_ID);
		HeaderMap.put("X-CurTime", curTime);
		HeaderMap.put("X-Param", param);
		HeaderMap.put("X-CheckSum", checkSum);
		HeaderMap.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

		try {
			// 发起请求
			result = HttpUtils.HttpClientPost(MessageUtil.URL_TEXTSEMANTIC_PATH, "utf-8", bodyMap, HeaderMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 
	 * @TODO 从科大讯飞获得语意解析结果，经处理得打我们需要的结果 analyzeType: 分为语音解析voice 和 文字解析 文字解析 为text
	 * @Time 2017年11月10日 下午4:21:29
	 * @author WEQ
	 * @return Object
	 */
	private String analyzeTextSemantics(String jsonResult, String analyzeType) {
		JsonObject jsonObject = new JsonParser().parse(jsonResult).getAsJsonObject();
		// 状态吗，为00000 则说明范文讯飞成功
		String code = jsonObject.get("code").getAsString();
		if (!"00000".equals(code)) {
			return new OutprintMsg("error", new IntrodutionModel("访问讯飞出现错误！！")).toJson();
		}
		// 访问讯飞成功，获得结果集
		jsonObject = jsonObject.get("data").getAsJsonObject();

		// 语意解析状态码，为4 则代表语意或者问答库成功
		int rc = jsonObject.get("rc").getAsInt();

		if (!(rc == 0)) {
			return new OutprintMsg("error", new IntrodutionModel("对不起，我没有明白您的意思！！")).toJson();
		}
		// 服务类型，可以openQA为问答库，其他的则为语意分析
		String serviceName = jsonObject.get("service").getAsString();
		//
		String answer = null;

		if ("openQA".equals(serviceName)) {
			// 得到问答库的结果
			answer = jsonObject.get("answer").getAsJsonObject().get("text").getAsString();
		} else {
			Map<String, Object> map = getAnswerByAnalyzeSemantics(jsonObject.get("semantic").getAsJsonArray());
			answer = introdutionService.getNormalIntroduction(map.get("keyWord").toString(), (int) map.get("level"));

			if (analyzeType.equals("text")) {
				return answer;
			}
			Gson gson = new Gson();
			OutprintMsg outprintMsg = gson.fromJson(answer, OutprintMsg.class);
			String introduction = ((Map) outprintMsg.getData()).get("introdution").toString();
			String voicePath = updateVoiceRecord(map.get("keyWord").toString(), introduction);
			// 如果是语音需要 返回 回答的内容和 语音的路径

			Map<String, Object> answerMap = new HashMap<String, Object>(2);
			answerMap.put("data", outprintMsg.getData());
			answerMap.put("path", voicePath);
			answer = new OutprintMsg("success", answerMap).toJson();
		}
		return answer;
	}

	/**
	 * 
	 * @TODO 分析语意，得到关键字，连接数据库获得将要回答的话术
	 * @Time 2017年11月10日 下午4:13:14
	 * @author WEQ
	 * @return 返回map key：keyWord--->数据库需要查询的关键字，level:当前业务的登记
	 */
	private Map<String, Object> getAnswerByAnalyzeSemantics(JsonArray jsonArray) {
		Map<String, Object> map = new HashMap<String, Object>(2);
		String keyWord = null;
		int level = -1;
		// 意图类型 分别为介绍财安的 jieshao_inrich 介绍财安6大服务的jieshao_business
		// 详细介绍服务子项的jieshao_b_child
		String type = jsonArray.get(0).getAsJsonObject().get("intent").getAsString();
		// 解析得到关键字对象列表
		JsonArray answerArray = jsonArray.get(0).getAsJsonObject().getAsJsonArray("slots");
		// 转换为关键字list
		List<Map<String, Object>> keyList = new Gson().fromJson(answerArray,
				new TypeToken<List<Map<String, Object>>>() {
				}.getType());

		if ("jieshao_inrich".equals(type)) {

			keyWord = keyList.get(0).get("normValue").toString();
			level = MessageUtil.IS_INRICH;
		} else if ("jieshao_b_child".equals(type)) {
			keyWord = keyList.get(0).get("normValue").toString();
			level = MessageUtil.IS_CHILD;

		} else if ("jieshao_business".equals(type)) {
			if (keyList.size() >= 2) {
				keyWord = keyList.get(1).get("normValue").toString();
			} else {
				keyWord = keyList.get(0).get("normValue").toString();
			}
			level = MessageUtil.IS_BUSINESS;
		}
		map.put("keyWord", keyWord);
		map.put("level", level);
		return map;

	}

	@Override
	public String analysisText(String question, String scene) throws UnsupportedEncodingException {
		String result = doText2Value(question, scene);
		String answer = analyzeTextSemantics(result, "text");
		return answer;
	}

	/**
	 * 对语音接口来说，看是否已经合成过该条录音，如果没有则讲结果发送到XF并添加结果
	 * 
	 * @param keyWord
	 *            关键字
	 * @param sqlAnswer
	 *            数据库查询的话术
	 * @return 语音的地址路径
	 */
	private String updateVoiceRecord(String keyWord, String sqlAnswer) {
		Map<String, Object> map = null;
		// 连接数据库获得合成语音结果
		map = voiceRecordMapper.getVoiceRecord(keyWord);
		if (map == null) {// 数据库没有该条语音，则进行合成
			System.out.println("---没有记录，进入合成");
			String fileName = UUID.randomUUID().toString();
			String beforePath = remoteProperties.getXfpcm() + fileName + ".pcm";
			String afterPath = remoteProperties.getPcm2mp3()+ fileName + ".mp3";
			try {
				composeVoiceService.composeVoice(sqlAnswer, beforePath, Thread.currentThread());
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				System.out.println("---合成完毕，将要进行转换语音格式");
				TransformVoice.doTransformPcm2Wav(beforePath, afterPath, "libmp3lame",remoteProperties.getFfmpeg());
				Map<String, Object> addMap = new HashMap<String, Object>();
				addMap.put("mediaId", "001");
				addMap.put("createTime", DatetimeUtil.toDateTimeStr(new Date()));
				addMap.put("updateTime", DatetimeUtil.toDateTimeStr(new Date()));
				addMap.put("keyword", keyWord);
				addMap.put("filePath", afterPath);
				addMap.put("fileName", fileName + ".mp3");
				voiceRecordMapper.addVoiceRecord(addMap);
				return fileName + ".mp3";
			}

		}
		return map.get("fileName").toString();

	}

	@Override
	public String doRecordToText(String voicePath, String scene) throws UnsupportedEncodingException {
		/**
		 * @TODO 语音语意分析doRecordToText
		 * @Time 2017年11月9日 下午5:25:24
		 * @author WEQ
		 * @return 语音语意分析得到的json串
		 * @throws UnsupportedEncodingException
		 */
		String result = null;
		// 获得 从1970年1月1日0点0 分0 秒开始到现在的秒数(String)
		// 参数二
		java.util.Calendar cal = java.util.Calendar.getInstance();
		String curTime = cal.getTimeInMillis() / 1000 + "";

		// 参数三
		Map<String, String> paramJson = new HashMap<>();
		paramJson.put("auf", "16k");
		paramJson.put("aue", "raw");
		paramJson.put("scene", scene);
		paramJson.put("userid", "user_0001");
		String paramsJson = new Gson().toJson(paramJson);
		String param = Base64.getEncoder().encodeToString(paramsJson.getBytes("utf-8"));

		// 参数四
		File file = new File(voicePath);
		byte[] bytes = null;
		try {
			bytes = IOUtils.toByteArray(new FileInputStream(file));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String base64File = Base64.getEncoder().encodeToString(bytes);

		StringBuilder s2Md5 = new StringBuilder();
		s2Md5.append(MessageUtil.XF_APP_KEY).append(curTime).append(param).append("data=").append(base64File);

		String checkSum = null;
		checkSum = CodingUtils.md5Encode(s2Md5.toString());

		Map<String, Object> bodyMap = new HashMap<String, Object>();
		bodyMap.put("data", base64File);

		Map<String, Object> headerMap = new HashMap<String, Object>();

		headerMap.put("X-Appid", MessageUtil.XF_APP_ID);
		headerMap.put("X-CurTime", curTime);
		headerMap.put("X-Param", param);
		headerMap.put("X-CheckSum", checkSum);
		headerMap.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

		try {
			result = HttpUtils.HttpClientPost(MessageUtil.URL_VOICESEMANTIC_PATH, "UTF-8", bodyMap, headerMap);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return result;

	}

}
