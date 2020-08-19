package com.bz.push.dao.sms;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bz.push.model.sms.SSmsLog;

import java.util.List;

@Mapper
public interface SSmsLogDao {
	/**
	 * 添加验证码记录
	 * @param smsLog
	 * @return
	 */
	Integer saveSmsLog(SSmsLog smsLog);
	/**
	 * 获取账号当天获取验证码次数
	 * @param accountNum
	 * @return
	 */
	Integer getAccountCount(@Param("accountNum")String accountNum);
	/**
	 * 获取ip地址当天获取验证码次数
	 * @param ip
	 * @return
	 */
	Integer getIpCount(@Param("ip")String ip);
	/**
	 * 获取当天同规则短信获取次数
	 * @param smsLog
	 * @return
	 */
	int getAccountRule(SSmsLog smsLog);
	/**
	 * 获取账号某类型验证码
	 * @param smsLog
	 * @return
	 */
	SSmsLog getAccountCode(SSmsLog smsLog);
	/**
	 * 验证发送的验证码
	 * @param smsLog
	 * @return
	 */
	SSmsLog getCodeValidate(SSmsLog smsLog);

	/**
	 * 获取超出限制的ip地址
	 * @param maxValue
	 * @return
	 */
	List<String> getIpOverrun(@Param("maxValue")Integer maxValue);

	/**
	 * 获取超出限制的账号
	 * @param sendMode
	 * @param maxValue
	 */
	List<String> getAccountOverrun(@Param("sendMode")Integer sendMode, @Param("maxValue")Integer maxValue);
	
	SSmsLog selectByMessageId(@Param("smsMessageId")String smsMessageId);
	
	void updateStatus(SSmsLog sSmsLog);
	
	int update(Integer id);
	
	List<SSmsLog> selectList(SSmsLog sSmsLog);
	
	int selectCount(SSmsLog sSmsLog);
	
	int delete();
	
	void deleteById(Integer id);
}
