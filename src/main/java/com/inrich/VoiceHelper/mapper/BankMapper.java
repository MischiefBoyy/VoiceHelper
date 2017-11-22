package com.inrich.VoiceHelper.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface BankMapper {
	
	/**
	 * 获得进入界面的固定介绍
	 * @return
	 */
	List<Map<String,Object>> getBankIndexInfo();
	
	/**
	 * 
	 *  获得当前父类的子类列表
	 * @return
	 */
	List<Map<String,Object>> getChildlistByPid(Integer pid);
	
	/**
	 * 
	 * 获得一个孩子的具体信息
	 * @return
	 */
	String getOneChildInfo(Integer id);
	/**
	 * 
	 * 获得一个孩子的具体信息(重载),使用在文本语义中
	 * @return
	 */
	String getOneChildByQa(@Param("parentId")Integer parentId,@Param("num")Integer num);

}
