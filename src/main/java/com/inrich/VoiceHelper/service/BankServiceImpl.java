package com.inrich.VoiceHelper.service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.inrich.VoiceHelper.mapper.BankMapper;
import com.inrich.VoiceHelper.model.IntrodutionModel;
import com.inrich.VoiceHelper.model.OutprintMsg;
import com.inrich.VoiceHelper.util.MessageUtil;
import com.inrich.VoiceHelper.util.RemoteProperties;
import com.inrich.VoiceHelper.util.TransformVoice;

@Service
public class BankServiceImpl implements BankService {

	@Autowired
	private BankMapper bankMapper;
	@Autowired
	private OperateVoiceService operateVoiceService;
	@Autowired
	private RemoteProperties remoteProperties;

	@Override
	public String getIndexInfo() {

		// 获得首页特定的问答信息
		IntrodutionModel model = new IntrodutionModel(bankMapper.getBankIndexInfo(), false, "aaaaaaaaaaaaaaaa");
		return new OutprintMsg("success", model).toJson();
	}

	@Override
	public String getNormalInfo(int level, int id) {
		String result = null;
		if (level == 1) {
			// 此处父类的id
			IntrodutionModel model = new IntrodutionModel(bankMapper.getChildlistByPid(id), false, "aaaaaaaaaaaaaaaa");
			result = new OutprintMsg("success", model).toJson();
		}
		if (level == 2) {
			// 此处id为子类的id
			IntrodutionModel model = new IntrodutionModel(null, true, bankMapper.getOneChildInfo(id));
			result = new OutprintMsg("succes", model).toJson();
		}
		return result;
	}

	@Override
	public String doTxtQa(String question, String scene) throws UnsupportedEncodingException {
		// 此处进行科大讯飞的分析，假设已经分析完成。
		// 子类返回的格式为 2-1 横线之前的为父类id，横线之后的为子类在父类中排第几个
		// 如果为父类，返回格式为 2 代表的为父类id

		String result = null;
		String answer = operateVoiceService.analysisText(question, scene);
		IntrodutionModel model = null;
		if (answer.length() == 1) {
			model = new IntrodutionModel(bankMapper.getChildlistByPid(Integer.parseInt(answer)), false,
					"aaaaaaaaaaaaaaaa");
			result = new OutprintMsg("success", model).toJson();
			return result;
		}
		if (answer.length() == 3 || answer.length() ==4) {
			String[] aa = answer.split("-");
			int parentId = Integer.parseInt(aa[0]);
			int num = Integer.parseInt(aa[1]);
			model = new IntrodutionModel(null, true, bankMapper.getOneChildByQa(parentId, num));
			result = new OutprintMsg("success", model).toJson();
			return result;
		}
		return answer;
	}

	@Override
	public String doVoiceQa(String mediaId, String scene) throws UnsupportedEncodingException {
		 String downloadPath=operateVoiceService.downloadVoice(mediaId);
		
		 //经过ffmpeg 转换语音后重新生成的语音路径
		 String servicePath = remoteProperties.getWxtoxf() + mediaId + ".wav";
		
		 //转换成 XF需要的音频格式
		 TransformVoice.doTransformWav2Wav(downloadPath, servicePath,remoteProperties.getFfmpeg());

		String httpResult = operateVoiceService.doRecordToText(servicePath, scene);
		String result = null;
		String answer = null;
		JsonObject jsonObject = new JsonParser().parse(httpResult).getAsJsonObject();
		String stateCode = jsonObject.get("code").getAsString();

		if (!("00000".equals(stateCode))) {
			return new OutprintMsg("error", new IntrodutionModel("访问讯飞出现错误！！")).toJson();
		}
		int rc = jsonObject.getAsJsonObject("data").get("rc").getAsInt();
		if (rc == 0) {
			// 获得问答库对应的返回值
			answer = jsonObject.getAsJsonObject("data").getAsJsonObject("answer").get("text").getAsString();
			IntrodutionModel model = null;
			if (answer.length() == 1) {
				model = new IntrodutionModel(bankMapper.getChildlistByPid(Integer.parseInt(answer)), false,
						"aaaaaaaaaaaaaaaa");
			}
			if (answer.length() >=3) {
				String[] aa = answer.split("-");
				int parentId = Integer.parseInt(aa[0]);
				int num = Integer.parseInt(aa[1]);
				model = new IntrodutionModel(null, true, bankMapper.getOneChildByQa(parentId, num));
			}
			Map<String,Object> map=new HashMap<>();
			map.put("data", model);
			map.put("path", "61ad7cfa-797b-405e-b987-e504c7e6325f.mp3");
			result = new OutprintMsg("success", map).toJson();
			return result;

		} else {
			return new OutprintMsg("error", new IntrodutionModel("对不起，我没有明白您的意思！！")).toJson();
		}
		

	}

}
