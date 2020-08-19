package com.bz.push.dao.app;

import org.apache.ibatis.annotations.Mapper;

import com.bz.push.model.app.AppNotice;

@Mapper
public interface AppNoticeDao {
	/**
	 * 添加公告
	 * @param appNotice
	 * @return
	 */
	Integer saveNotice(AppNotice appNotice);

}
