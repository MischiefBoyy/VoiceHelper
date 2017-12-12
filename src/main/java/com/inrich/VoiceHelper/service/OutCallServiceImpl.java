package com.inrich.VoiceHelper.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inrich.VoiceHelper.mapper.OutCallMapper;
import com.inrich.VoiceHelper.model.OutprintMsg;
import com.inrich.VoiceHelper.model.QuestionModel;
import com.inrich.VoiceHelper.util.MessageUtil;

@Service
public class OutCallServiceImpl implements OutCallService {
	protected static Logger log = LoggerFactory.getLogger(OutCallServiceImpl.class);
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

		String result = new OutprintMsg("success", outCallMapper.getYesQuestion(yesId)).toJson();
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
	private void doSmByAction(String action, String data) {
		switch (action) {
		case "checkAddress":
			log.info("----模拟checkAddress");
			break;
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
	}

	/**
	 * 当用户点击否定按钮，查询相对应的否定回答
	 */
	@Override
	public String outCallRefues(int refuseId) {
		QuestionModel model = outCallMapper.getRefuseQuestion(refuseId);
		return new OutprintMsg("success", model).toJson();
	}

	/**
	 * 文字输入 因为有action 做不同的业务
	 */
	@Override
	public String textDoAction(String data, String action) {
		// 1.根据业务类型做不同的相关业务，暂且不定
		doSmByAction(action, data);
		
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
