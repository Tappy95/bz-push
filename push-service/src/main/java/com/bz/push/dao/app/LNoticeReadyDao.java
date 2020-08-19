package com.bz.push.dao.app;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bz.push.model.app.LNoticeReady;

@Mapper
public interface LNoticeReadyDao {
	
	List<LNoticeReady> selectList(int noticeType);

}
