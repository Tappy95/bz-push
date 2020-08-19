package com.bz.push.dao.app;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.alibaba.dubbo.config.support.Parameter;
import com.bz.push.model.app.AppNewsInform;

@Mapper
public interface AppNewsInformDao {
	/**
	 * 通知列表
	 * @param appNewsInform
	 * @return
	 */
	List<AppNewsInform> selectList(@Param("pushMode")Integer pushMode,@Param("beginDate")Long beginDate,@Param("endDate")Long endDate);
	/**
	 * 修改通知
	 * @param appNewsInform
	 * @return
	 */
	Integer update(@Param("id")Integer id);
}
