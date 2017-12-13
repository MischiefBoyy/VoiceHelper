package com.inrich.VoiceHelper.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.inrich.VoiceHelper.mapper.OutCallMapper;
import com.inrich.VoiceHelper.model.IntrodutionModel;
import com.inrich.VoiceHelper.model.OutprintMsg;
import com.inrich.VoiceHelper.model.QuestionModel;
import com.inrich.VoiceHelper.util.CodingUtils;
import com.inrich.VoiceHelper.util.FileUtils;
import com.inrich.VoiceHelper.util.HttpUtils;
import com.inrich.VoiceHelper.util.MessageUtil;
import com.inrich.VoiceHelper.util.RemoteProperties;
import com.inrich.VoiceHelper.util.TransformVoice;

@Service
public class OutCallServiceImpl implements OutCallService {
	protected static Logger log = LoggerFactory.getLogger(OutCallServiceImpl.class);
	@Autowired
	private OutCallMapper outCallMapper;
	@Autowired
	private OperateVoiceService operateVoiceService;
	@Autowired
	private RemoteProperties remoteProperties;

	/**
	 * 获取首条固定问答
	 */
	@Override
	public String outCallIndex(HttpServletRequest request) {
		QuestionModel model=outCallMapper.getIndexQuestion();
		setSession(MessageUtil.YES_TABLE, model.getId(), request);
		return new OutprintMsg("success", model).toJson();
	}

	/**
	 * 当用户选择肯定的按钮，根据id查询下一条的信息
	 */
	@Override
	public String outCallYes(int yesId,HttpServletRequest request) {
		QuestionModel model=outCallMapper.getYesQuestion(yesId);
		changQuestionByKey(model);
		setSession(MessageUtil.YES_TABLE, model.getId(), request);
		String result = new OutprintMsg("success", model).toJson();
		return result;
	}

	/**
	 * 需要额外业务逻辑的 根据动作标识action选择进行
	 * 
	 * @TODO TODO
	 * @Time 2017年12月11日 下午3:43:11
	 * @author WEQ
	 * @return void
	 */
	private boolean doSmByAction(String action, String data) {
		switch (action) {
		case "checkAddress":
			log.info("----模拟checkAddress");
			//****弄**号**室
			char [] array=(data+"。").toCharArray();
			List<String> addressInfo=new ArrayList<>(16);
			String result="";
			for(char i:array) {
				if(isInteger(i)) {
					result+=i;
				}else {
					if(!result.isEmpty()) {
						addressInfo.add(result);
						result="";
					}
				}
			}
			if(addressInfo.size() == 3) {
				//说明家庭地址输入的正确
				//进行更新家庭地址操作
				return true;
			}else {
				return false;
			}
		case "checkUserId":
			log.info("----模拟checkUserId");
			break;
		case "addUserTime":
			log.info("----模拟addUserTime");
			break;
		case "addRemark":
			log.info("----模拟addRemark");
			break;
		case "addWorkAddress":
			log.info("----模拟addWorkAddress");
			break;

		default:
			break;
		}
		return false;
		
		 
	}

	/**
	 * 当用户点击否定按钮，查询相对应的否定回答
	 */
	@Override
	public String outCallRefues(int refuseId,HttpServletRequest request) {
		QuestionModel model = outCallMapper.getRefuseQuestion(refuseId);
		changQuestionByKey(model);
		setSession(MessageUtil.REFUSE_TABLE, model.getId(), request);
		return new OutprintMsg("success", model).toJson();
	}

	/**
	 * 文字输入 因为有action 做不同的业务
	 */
	@Override
	public String textDoAction(String data, String action) {
		return null;
		
		
	}

	/**
	 * 文字输入 分析文本意图 进行业务的流程
	 */
	@Override
	public String textDoProcess(int yesId, int noId, String data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 模拟拼接客户地址内容
	 * @TODO TODO
	 * @Time 2017年12月12日 下午3:18:10
	 * @author WEQ
	 * @return void
	 */
	private void changQuestionByKey(QuestionModel model) {
		if(model != null && model.getKeyWord() !=null) {
			switch (model.getKeyWord()) {
			case "checkAddress":
				String address="上海市宝山区大场镇锦秋路";
				model.setQuestion(model.getQuestion().replace("**", address));
				break;
			case "checkWorkAddress":
				String workAddress="上海市虹口区邯郸路43号";
				model.setQuestion(model.getQuestion().replace("**", workAddress));
				break;

			default:
				break;
			}
		}
	}
	
	/**
	 * 解析用户输入的文字意图 可能是流程控制，也可能是业务操作
	 */
	@Override
	public String dotext(String data, String action, int yesId, int refuseId,HttpServletRequest request) {
		// 1.根据业务类型做不同的相关业务，暂且不定
				if(doSmByAction(action, data)) {//证明该条输入信息已经被业务消费，不是业务流程，
					// 2.处理完业务，需要返回该条问题之后的问答
					String tableName = outCallMapper.getActionTableName(action);
					QuestionModel model=null;
					if (MessageUtil.YES_TABLE.equals(tableName)) {
						// 说明该动作对应的业务是肯定表
						 model=outCallMapper.getYesQuestionByAction(action);
						//修改肯定id，为了查找对应的下一条问答
						
					} else if (MessageUtil.REFUSE_TABLE.equals(tableName)) {
						// 说明该动作对应的业务是否定表
						 model=outCallMapper.getRefuseQuestionByAction(action);
					}
					return new OutprintMsg("success", model).toJson();
				}else {
					String result=null;
					int dataType=checkStringMean(data);
					switch (dataType) {
					case 0:
						String tableName=request.getSession().getAttribute("tableName").toString();
						int id=(int)request.getSession().getAttribute("id");
						QuestionModel model=errorGetQuestion(tableName,id);
						Map<String,Object> map=new HashMap<>(3);
						map.put("title", "对不起,你输入的信息犹如，请按照规定的样式输入。");
						map.put("data", model);
						result=new OutprintMsg("error", map).toJson();
						break;
					case 1://肯定回答
						result=outCallYes(yesId,request);
						break;
					case 2://否定回答
						result=outCallRefues(refuseId,request);
						break;

					default:
						break;
					}
					return result;
				}
	}
	
	/**
	 * 判断是不是数字
	 * @TODO TODO
	 * @Time 2017年12月13日 上午10:47:30
	 * @author WEQ
	 * @return boolean
	 */
	private  boolean isInteger(char str) {  
        Pattern pattern = Pattern.compile("^[0-9]*$");  
        return pattern.matcher(str+"").matches();  
  }
	
	/**
	 * 
	 * @TODO 简单判断用户输入的信息是不是 流程控制，
	 * 并且 返回值为1则是肯定，2则是否定，0则是输入的是错误信息
	 * @Time 2017年12月13日 上午10:06:44
	 * @author WEQ
	 * @return int
	 */
	private int checkStringMean(String data) {
		String []yesStr= {"愿意","正确","可以","方便","有兴趣","对","好","好的","行"};
		String []noStr= {"不愿意","没兴趣","无兴趣","不正确","不可以","不方便","不对","错误","不敢兴趣"};
		
		for(String refuse:noStr) {
			if(data.contains(refuse)) {
				return 2;
			}
		}
		for(String yes:yesStr) {
			if(data.contains(yes)) {
				return 1;
			}
		}
		return 0;
	}
	
	/**
	 * session  保存最后一条问起的信息
	 * @TODO TODO
	 * @Time 2017年12月13日 上午10:47:59
	 * @author WEQ
	 * @return void
	 */
	private void setSession(String tableName,int id,HttpServletRequest request) {
		request.getSession().setAttribute("id", id);
		request.getSession().setAttribute("tableName", tableName);
	}
	
	/**
	 * 当输入的信息有错误，那么就通过获取session中最后一条问答的信息，从新查询插入
	 * @TODO TODO
	 * @Time 2017年12月13日 上午10:48:15
	 * @author WEQ
	 * @return QuestionModel
	 */
	private QuestionModel errorGetQuestion(String tableName,int id ) {
		QuestionModel model=null;
		if(MessageUtil.YES_TABLE.equals(tableName)) {
			model=outCallMapper.getYesQuestion(id);
		}else if(MessageUtil.REFUSE_TABLE.equals(tableName)) {
			model=outCallMapper.getRefuseQuestion(id);
		}
		return model;
	}
	
	
	/**
	 *	录音转换成文本 无语意分析
	 * @TODO TODO
	 * @Time 2017年12月13日 上午11:14:01
	 * @author WEQ
	 * @return String
	 */
	private String doRecordToText(String voicePath, String scene) throws UnsupportedEncodingException {
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
			result = HttpUtils.HttpClientPost(MessageUtil.URL_VOICE2TEXT_PATH, "UTF-8", bodyMap, headerMap);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return result;

	}

	@Override
	public String doVoice(String mediaId, String action, int yesId, int refuseId,
			HttpServletRequest request) throws UnsupportedEncodingException {
		String downloadPath=operateVoiceService.downloadVoice(mediaId);
		
		//判断是否有文件夹，没有的话则创建
		FileUtils.createFile(remoteProperties.getWxtoxf());
		 //经过ffmpeg 转换语音后重新生成的语音路径
		 String servicePath = remoteProperties.getWxtoxf() + mediaId + ".wav";
		
		 //转换成 XF需要的音频格式
		 TransformVoice.doTransformWav2Wav(downloadPath, servicePath,remoteProperties.getFfmpeg());

		String httpResult = doRecordToText(servicePath, "main");
		//解析返回的数据
		JsonObject jsonObject = new JsonParser().parse(httpResult).getAsJsonObject();
		String stateCode = jsonObject.get("code").getAsString();

		if (!("00000".equals(stateCode))) {
			//连接讯飞出现错误，可以友情的提醒用户  使用文本输入
		}
		String data=jsonObject.get("data").getAsJsonObject().get("result").getAsString();
		//获得文字 直接进入文本解析
		String result=dotext(data,action,yesId,refuseId,request);
		
		return result;
	}

}
