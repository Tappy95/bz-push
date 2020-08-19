package com.bz.push.dao.content;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bz.push.model.content.SContentInfo;

import java.util.List;

@Mapper
public interface SContentInfoDao {
	/**
	 * 更具id获取发送内容
	 * @param id
	 * @return
	 */
	SContentInfo getSContentInfoId(@Param("id")Integer id);

	/**
	 * 获取启用的短信模板列表
	 * @return
	 */
	List<SContentInfo> getSContentInfoList();

}
