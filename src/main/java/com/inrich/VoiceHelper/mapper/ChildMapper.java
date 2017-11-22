package com.inrich.VoiceHelper.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import com.inrich.VoiceHelper.model.ChildIntrodution;

//@Repository("childMapper")
//@Mapper
public interface ChildMapper {
	
	
	
	/**
	 * @TODO 查询所有的二级业务
	 * @Time 2017年11月8日 下午2:03:33
	 * @author WEQ
	 */
	List<ChildIntrodution> findAllChild();
	
	
	/**
	 * @TODO 根据关键字名字查询二级业务
	 * @Time 2017年11月8日 下午2:03:25
	 * @author WEQ
	 */
	String getChildByName(@Param("name") String name);
	
	/**
	 * @TODO 根据一级业务id，查询相对应的二级业务员
	 * @Time 2017年11月8日 下午2:02:45
	 * @author WEQ
	 */
	List<Map<String,Object>> findChildsByBid(int businessId);
	
	
}
