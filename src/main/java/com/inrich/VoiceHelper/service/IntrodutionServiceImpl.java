package com.inrich.VoiceHelper.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inrich.VoiceHelper.mapper.BusinessMapper;
import com.inrich.VoiceHelper.mapper.ChildMapper;
import com.inrich.VoiceHelper.model.BusinessIntrodution;
import com.inrich.VoiceHelper.model.ChildIntrodution;
import com.inrich.VoiceHelper.model.IntrodutionModel;
import com.inrich.VoiceHelper.model.OutprintMsg;
import com.inrich.VoiceHelper.util.MessageUtil;

@Service
public class IntrodutionServiceImpl implements IntrodutionService {
	@Autowired
	private BusinessMapper businessMapper;
	@Autowired
	private ChildMapper childMapper;

	@Override
	public String getIndexIntrodution() {
		// 获得一级的业务
		List<Map<String, Object>> list = businessMapper.findAllBusiness();
		String introdution = businessMapper.getIntrodutionByName("上海财安金融服务股份有限公司");
		boolean isBase = false;

		return new OutprintMsg("success", new IntrodutionModel(list, isBase, introdution)).toJson() ;
	}

	@Override
	public String getNormalIntroduction(String name, int id, int level) {
		String introdution = null;
		boolean isBase = false;
		List<Map<String, Object>> map = null;
		if (level == MessageUtil.IS_BUSINESS ) {
			introdution = businessMapper.getIntrodutionByName(name);
			map = childMapper.findChildsByBid(id);
		}
		if (level == MessageUtil.IS_CHILD) {
			introdution = childMapper.getChildByName(name);
			isBase = true;
		}
		String result = new OutprintMsg("success", new IntrodutionModel(map, isBase, introdution)).toJson();
		return result;
	}

	@Override
	public String getNormalIntroduction(String name, int level) {
		String introdution = null;
		boolean isBase = false;
		List<Map<String, Object>> map = null;
		if (level == MessageUtil.IS_BUSINESS ) {
			BusinessIntrodution bean = businessMapper.getBusinessByName(name);
			map = childMapper.findChildsByBid(bean.getId());
			introdution=bean.getIntrodution();
		}
		if (level == MessageUtil.IS_CHILD) {
			introdution = childMapper.getChildByName(name);
			isBase = true;
		}
		if(level == MessageUtil.IS_INRICH) {
			return getIndexIntrodution();
		}
		
		String result = new OutprintMsg("success", new IntrodutionModel(map, isBase, introdution)).toJson() ;
		return result;
	}
	
	
	

}
