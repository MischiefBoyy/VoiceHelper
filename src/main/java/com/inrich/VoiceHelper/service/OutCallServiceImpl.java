package com.inrich.VoiceHelper.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inrich.VoiceHelper.mapper.OutCallMapper;
import com.inrich.VoiceHelper.model.OutprintMsg;
import com.inrich.VoiceHelper.model.QuestionModel;

@Service
public class OutCallServiceImpl implements OutCallService {
	@Autowired
	private OutCallMapper outCallMapper;
	
	/**
	 * 获取首条固定问答
	 */
	@Override
	public String outCallIndex() {
		return new OutprintMsg("success", outCallMapper.getIndexQuestion()).toJson();
	}
	
	/**
	 * 当用户选择肯定的按钮，根据id查询下一条的信息
	 */
	@Override
	public String outCallYes(int yesId) {
		
		String result=new OutprintMsg("success", outCallMapper.getYesQuestion(yesId)).toJson();
		return result;
	}
	
	/**
	 * 需要额外业务逻辑的  根据动作标识action选择进行
	 * @TODO TODO
	 * @Time 2017年12月11日 下午3:43:11
	 * @author WEQ
	 * @return void
	 */
	public void doSmByAction(String action, String data) {
		switch (action) {
		case "checkAddress":

			break;
		case "checkUserId":

			break;
		case "addUserTime":

			break;
		case "addRemark":

			break;
		case "addWorkAddress":

			break;

		default:
			break;
		}
	}
	
	/**
	 * 当用户点击否定按钮，查询相对应的否定回答
	 */
	@Override
	public String outCallRefues(int refuseId) {
		Map<String,Object> refuseMap=outCallMapper.getRefuseQuestion(refuseId);
		QuestionModel model=new QuestionModel();
		model.setQuestion(refuseMap.get("question").toString());
		model.setRefuseId(0);
		model.setYesId((int)refuseMap.get("questionId"));
		model.setAction(refuseMap.get("action").toString());
		return new OutprintMsg("success", model).toJson();
	}
	
	/**
	 * 文字输入 因为有action  做不同的业务
	 */
	@Override
	public String textDoAction(String data, String action) {
		// TODO Auto-generated method stub
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

}
