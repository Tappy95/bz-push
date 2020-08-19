package com.bz.push.dao.user;

import org.apache.ibatis.annotations.Mapper;

import com.bz.push.model.user.MUserInfo;

@Mapper
public interface MUserInfoDao {
	/**
	 * 查询用户全部信息
	 * @param userId
	 * @return
	 */
	MUserInfo selectOne(String userId);
}
