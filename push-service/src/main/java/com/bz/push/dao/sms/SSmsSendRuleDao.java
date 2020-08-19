package com.bz.push.dao.sms;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bz.push.model.sms.SSmsSendRule;

import java.util.List;

@Mapper
public interface SSmsSendRuleDao {
	/**
	 * 更具code获取详情
	 * @param ruleNum
	 * @return
	 */
	SSmsSendRule getRuleId(@Param("ruleNum")String ruleNum);

	/**
	 * 获取所有信息规则
	 * @return
	 */
	List<SSmsSendRule> getRuleList();

}
