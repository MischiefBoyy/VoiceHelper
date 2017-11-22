package com.inrich.VoiceHelper.service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.inrich.VoiceHelper.mapper.BankMapper;
import com.inrich.VoiceHelper.mapper.TokenMapper;
import com.inrich.VoiceHelper.mapper.VoiceRecordMapper;
import com.inrich.VoiceHelper.model.IntrodutionModel;
import com.inrich.VoiceHelper.model.OutprintMsg;
import com.inrich.VoiceHelper.util.DatetimeUtil;
import com.inrich.VoiceHelper.util.MessageUtil;
import com.inrich.VoiceHelper.util.RemoteProperties;
import com.inrich.VoiceHelper.util.TransformVoice;

@Service
public class BankServiceImpl implements BankService {

	@Autowired
	private BankMapper bankMapper;
	@Autowired
	private VoiceRecordMapper voiceRecordMapper;
	@Autowired
	private OperateVoiceService operateVoiceService;
	@Autowired
	private RemoteProperties remoteProperties;
	@Autowired
	private ComposeVoiceService  composeVoiceService;

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
			//父类id
			Integer parentId=null;
			//再父类所对应的位数
			Integer num=null;
			//语音记录文件的路径
			String voiceRecoed=null;
			//返回的介绍术语
			String introduction=null;
			if (answer.length() == 1) {
				parentId=Integer.parseInt(answer);
				introduction=bankMapper.getParentIntroduction(parentId);
				model = new IntrodutionModel(bankMapper.getChildlistByPid(parentId), false,introduction);
			}
			if (answer.length() >=3) {
				String[] aa = answer.split("-");
				 parentId = Integer.parseInt(aa[0]);
				 num = Integer.parseInt(aa[1]);
				// child的介绍文本
				introduction=bankMapper.getOneChildByQa(parentId, num);
				model = new IntrodutionModel(null, true, introduction);
			}
			//获得银行问答的语音记录
			voiceRecoed=voiceRecordMapper.getBankVoiceRecord(parentId, num);
			//如果没有则进行合成
			if(voiceRecoed == null || voiceRecoed == "") {
				String fileName=UUID.randomUUID().toString();
				String beforePath = remoteProperties.getXfpcm() + fileName + ".pcm";
				String afterPath = remoteProperties.getPcm2mp3()+ fileName + ".mp3";
				try {
					//发送文本到科大讯飞进行语音合成
					composeVoiceService.composeVoice(introduction, beforePath, Thread.currentThread());
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					//将科大讯飞生成的pcm格式的语音转换成MP3
					TransformVoice.doTransformPcm2Wav(beforePath, afterPath, "libmp3lame",remoteProperties.getFfmpeg());
					//更新语音记录数据库
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("parentId", parentId);
					map.put("num", num==null?0:num);
					map.put("path", afterPath);
					map.put("name", fileName + ".mp3");
					map.put("createTime", DatetimeUtil.toDateTimeStr(new Date()));
					map.put("updateTime", DatetimeUtil.toDateTimeStr(new Date()));
					voiceRecordMapper.addBankVoiceRecord(map);
					voiceRecoed=fileName + ".mp3";
				}
			}
			//返回json到前台
			Map<String,Object> map=new HashMap<>();
			map.put("data", model);
			map.put("path", voiceRecoed);
			result = new OutprintMsg("success", map).toJson();
			return result;

		} else {
			return new OutprintMsg("error", new IntrodutionModel("对不起，我没有明白您的意思！！")).toJson();
		}
		

	}

}
