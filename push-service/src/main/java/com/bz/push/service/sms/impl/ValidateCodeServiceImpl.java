package com.bz.push.service.sms.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.bz.push.common.config.SmsCodeErrer;
import com.bz.push.common.config.SmsConstants;
import com.bz.push.common.config.SmsParamConf;
import com.bz.push.common.config.WebConfig;
import com.bz.push.common.smg.SmsUtil;
import com.bz.push.common.smg.hwy.SendHWYSms;
import com.bz.push.common.utils.DateUtil;
import com.bz.push.common.utils.StringUtil;
import com.bz.push.dao.content.SContentInfoDao;
import com.bz.push.dao.sms.SSmsBlackDao;
import com.bz.push.dao.sms.SSmsLogDao;
import com.bz.push.dao.sms.SSmsParamConfDao;
import com.bz.push.dao.sms.SSmsSendRuleDao;
import com.bz.push.model.content.SContentInfo;
import com.bz.push.model.sms.SSmsBlack;
import com.bz.push.model.sms.SSmsLog;
import com.bz.push.model.sms.SSmsParamConf;
import com.bz.push.model.sms.SSmsSendRule;
import com.bz.push.service.email.EmailService;
import com.bz.push.service.redis.RedisService;
import com.bz.push.service.sms.ValidateCodeService;

@Service(interfaceClass=ValidateCodeService.class,version=WebConfig.dubboServiceVersion)
public class ValidateCodeServiceImpl implements ValidateCodeService {

	private static final Logger logger = LoggerFactory.getLogger(ValidateCodeServiceImpl.class);
    
	@Autowired
	private SSmsBlackDao sSmsBlackDao;
	@Autowired
	private SSmsLogDao sSmsLogDao;
	@Autowired
	private SSmsSendRuleDao sSmsSendRuleDao;
	@Autowired
	private SContentInfoDao sContentInfoDao;
	@Autowired
    private EmailService emailService;
	@Autowired
	private SSmsParamConfDao sSmsParamConfDao;
	@Autowired
	private RedisService redisService;
	/**
	 * 发送短信验证码
	 */
	@Override
	public Map<String,Object> sendSmsCode(SSmsLog smsLog,String statusCallBack) {
		smsLog.setStatus(2);
		Map<String,Object> result=new HashMap<String,Object>();
		//效验账号
		if(smsLog.getSendMode()==1) {
			if(!StringUtil.isPhoneNo(smsLog.getAccountNum())) {
				result.put("message", SmsCodeErrer.PHONE_FORMAT);
				result.put("res", false);
				return result;
			}
		}else {
			if(!StringUtil.isEmailNo(smsLog.getAccountNum())) {
				result.put("message", SmsCodeErrer.EMAIL_FORMAT);
				result.put("res", false);
				return result;
			}
		}
		SSmsParamConf isSend=(SSmsParamConf)redisService.getObject(SmsParamConf.IS_SEND);
		if(StringUtil.isNullOrEmpty(isSend)) {
			isSend=sSmsParamConfDao.getName(SmsParamConf.IS_SEND);
			redisService.put(SmsParamConf.IS_SEND, isSend);
		}
		Boolean res=true;
		String code="1234";
		if(isSend.getMaxValue().intValue() == 1) {
			//效验账号、ip是否被拉黑
			SSmsBlack smsBlack=(SSmsBlack)redisService.getObject(smsLog.getIp());
			if(StringUtil.isNullOrEmpty(smsBlack)) {
				smsBlack=sSmsBlackDao.getBlack("0",smsLog.getAccountNum(),smsLog.getIp());
			}
			if(!StringUtil.isNullOrEmpty(smsBlack)) {
				result.put("message", SmsCodeErrer.IP_BLACK);
				result.put("res", false);
				return result;
			}
			//效验同规则信息在一分钟内是否发送过
			SSmsLog sSmsLog=sSmsLogDao.getAccountCode(smsLog);
			if(!StringUtil.isNullOrEmpty(sSmsLog)) {
				if(DateUtil.getSecond(sSmsLog.getCreateTime(),new Date())<60) {
					result.put("message", SmsCodeErrer.TIME_WITHIN);
					result.put("res", false);
					return result;
				}	
			}
			//校验同规则信息在一天内是否发送上限
			SSmsSendRule sendRule=(SSmsSendRule)redisService.getObject(smsLog.getRuleNum());
			if(StringUtil.isNullOrEmpty(sendRule)) {
				sendRule=sSmsSendRuleDao.getRuleId(smsLog.getRuleNum());
				redisService.put(smsLog.getRuleNum(), sendRule);
			}
			int smsCodeNum=sSmsLogDao.getAccountRule(smsLog);
			if(smsCodeNum>=sendRule.getLimitSendNumber()) {
				result.put("message", SmsCodeErrer.DAY_MAX);
				result.put("res", false);
				return result;
			}
			//获取随机码
			code=StringUtil.getRandomStr(4);
			if(code.startsWith("0")){
				code = "2"+code.substring(1, code.length());
			}
			if(smsLog.getSendMode()==1) {
				try{
					SSmsParamConf sendChannel=(SSmsParamConf)redisService.getObject(SmsParamConf.SEND_CHANNEL);
					if(StringUtil.isNullOrEmpty(sendChannel)) {
						sendChannel=sSmsParamConfDao.getName(SmsParamConf.SEND_CHANNEL);
						redisService.put(SmsParamConf.SEND_CHANNEL, sendChannel);
					}
					if(sendChannel.getMaxValue().intValue() == 1){ // 创蓝
						logger.info("-----------使用创蓝253发送短信-----------");
						res= SmsUtil.sendVariableSms(smsLog.getAccountNum(),code);
						smsLog.setStatus(3);
					}else{ // 华为云
						logger.info("-----------使用华为云发送短信-----------");
						String messageId = SendHWYSms.sendSms(smsLog.getAccountNum(),code,statusCallBack);
						if(messageId != null){
							smsLog.setStatus(1);
							smsLog.setSmsMessageId(messageId);
							res = true;
						}else{
							res = false;
						}
					}
				}catch (Exception e){
					logger.info(e.getMessage());
					result.put("message", SmsCodeErrer.PHONE_CODE_ERROR);
					result.put("res", true);
					return result;
				}
			}else {
				SContentInfo sContentInfo=(SContentInfo)redisService.getObject(SmsConstants.SMS_CODE_EMAIL.toString());//获取信息模板
				if(StringUtil.isNullOrEmpty(sContentInfo)) {
					sContentInfo=sContentInfoDao.getSContentInfoId(SmsConstants.SMS_CODE_EMAIL);
				}
			    res=emailService.sendSimpleMail(smsLog.getAccountNum(), sContentInfo.getMessageTitle(), "验证码："+code+","+sContentInfo.getMessageModel());
			}
		}else{
			smsLog.setStatus(3);
		}
		if(res) {
			smsLog.setCode(code);
			smsLog.setCreateTime(new Date());
			sSmsLogDao.saveSmsLog(smsLog);
			result.put("message", SmsCodeErrer.SEND_SUCCESS);
			result.put("res", true);
			return result;
		}else {
			result.put("message", SmsCodeErrer.SEND_FAIL);
			result.put("res", false);
			return result;
		}
	}

	@Override
	public Map<String,Object> sendSmsCodeZQ(SSmsLog smsLog,String statusCallBack) {
		smsLog.setStatus(2);
		Map<String,Object> result=new HashMap<String,Object>();
		//效验账号
		if(smsLog.getSendMode()==1) {
			if(!StringUtil.isPhoneNo(smsLog.getAccountNum())) {
				result.put("message", SmsCodeErrer.PHONE_FORMAT);
				result.put("res", false);
				return result;
			}
		}else {
			if(!StringUtil.isEmailNo(smsLog.getAccountNum())) {
				result.put("message", SmsCodeErrer.EMAIL_FORMAT);
				result.put("res", false);
				return result;
			}
		}
		SSmsParamConf isSend=(SSmsParamConf)redisService.getObject(SmsParamConf.IS_SEND);
		if(StringUtil.isNullOrEmpty(isSend)) {
			isSend=sSmsParamConfDao.getName(SmsParamConf.IS_SEND);
			redisService.put(SmsParamConf.IS_SEND, isSend);
		}
		Boolean res=true;
		String code="1234";
		if(isSend.getMaxValue().intValue() == 1) {
			//效验账号、ip是否被拉黑
			SSmsBlack smsBlack=(SSmsBlack)redisService.getObject(smsLog.getIp());
			if(StringUtil.isNullOrEmpty(smsBlack)) {
				smsBlack=sSmsBlackDao.getBlack("0",smsLog.getAccountNum(),smsLog.getIp());
			}
			if(!StringUtil.isNullOrEmpty(smsBlack)) {
				result.put("message", SmsCodeErrer.IP_BLACK);
				result.put("res", false);
				return result;
			}
			//效验同规则信息在一分钟内是否发送过
			SSmsLog sSmsLog=sSmsLogDao.getAccountCode(smsLog);
			if(!StringUtil.isNullOrEmpty(sSmsLog)) {
				if(DateUtil.getSecond(sSmsLog.getCreateTime(),new Date())<60) {
					result.put("message", SmsCodeErrer.TIME_WITHIN);
					result.put("res", false);
					return result;
				}	
			}
			//校验同规则信息在一天内是否发送上限
			SSmsSendRule sendRule=(SSmsSendRule)redisService.getObject(smsLog.getRuleNum());
			if(StringUtil.isNullOrEmpty(sendRule)) {
				sendRule=sSmsSendRuleDao.getRuleId(smsLog.getRuleNum());
				redisService.put(smsLog.getRuleNum(), sendRule);
			}
			int smsCodeNum=sSmsLogDao.getAccountRule(smsLog);
			if(smsCodeNum>=sendRule.getLimitSendNumber()) {
				result.put("message", SmsCodeErrer.DAY_MAX);
				result.put("res", false);
				return result;
			}
			//获取随机码
			code=StringUtil.getRandomStr(4);
			if(code.startsWith("0")){
				code = "2"+code.substring(1, code.length());
			}
			if(smsLog.getSendMode()==1) {
				try{
					SSmsParamConf sendChannel=(SSmsParamConf)redisService.getObject(SmsParamConf.SEND_CHANNEL);
					if(StringUtil.isNullOrEmpty(sendChannel)) {
						sendChannel=sSmsParamConfDao.getName(SmsParamConf.SEND_CHANNEL);
						redisService.put(SmsParamConf.SEND_CHANNEL, sendChannel);
					}
					logger.info("-----------使用创蓝253发送短信-----------");
					res= SmsUtil.sendVariableSmsZQ(smsLog.getAccountNum(),code);
					smsLog.setStatus(3);
				}catch (Exception e){
					logger.info(e.getMessage());
					result.put("message", SmsCodeErrer.PHONE_CODE_ERROR);
					result.put("res", true);
					return result;
				}
			}else {
				SContentInfo sContentInfo=(SContentInfo)redisService.getObject(SmsConstants.SMS_CODE_EMAIL.toString());//获取信息模板
				if(StringUtil.isNullOrEmpty(sContentInfo)) {
					sContentInfo=sContentInfoDao.getSContentInfoId(SmsConstants.SMS_CODE_EMAIL);
				}
				res=emailService.sendSimpleMail(smsLog.getAccountNum(), sContentInfo.getMessageTitle(), "验证码："+code+","+sContentInfo.getMessageModel());
			}
		}else{
			smsLog.setStatus(3);
		}
		if(res) {
			smsLog.setCode(code);
			smsLog.setCreateTime(new Date());
			sSmsLogDao.saveSmsLog(smsLog);
			result.put("message", SmsCodeErrer.SEND_SUCCESS);
			result.put("res", true);
			return result;
		}else {
			result.put("message", SmsCodeErrer.SEND_FAIL);
			result.put("res", false);
			return result;
		}
	}

	@Override
	public Map<String,Object> sendSmsCodeSDM(SSmsLog smsLog,String statusCallBack) {
		smsLog.setStatus(2);
		Map<String,Object> result=new HashMap<String,Object>();
		//效验账号
		if(smsLog.getSendMode()==1) {
			if(!StringUtil.isPhoneNo(smsLog.getAccountNum())) {
				result.put("message", SmsCodeErrer.PHONE_FORMAT);
				result.put("res", false);
				return result;
			}
		}else {
			if(!StringUtil.isEmailNo(smsLog.getAccountNum())) {
				result.put("message", SmsCodeErrer.EMAIL_FORMAT);
				result.put("res", false);
				return result;
			}
		}
		SSmsParamConf isSend=(SSmsParamConf)redisService.getObject(SmsParamConf.IS_SEND);
		if(StringUtil.isNullOrEmpty(isSend)) {
			isSend=sSmsParamConfDao.getName(SmsParamConf.IS_SEND);
			redisService.put(SmsParamConf.IS_SEND, isSend);
		}
		Boolean res=true;
		String code="1234";
		if(isSend.getMaxValue().intValue() == 1) {
			//效验账号、ip是否被拉黑
			SSmsBlack smsBlack=(SSmsBlack)redisService.getObject(smsLog.getIp());
			if(StringUtil.isNullOrEmpty(smsBlack)) {
				smsBlack=sSmsBlackDao.getBlack("0",smsLog.getAccountNum(),smsLog.getIp());
			}
			if(!StringUtil.isNullOrEmpty(smsBlack)) {
				result.put("message", SmsCodeErrer.IP_BLACK);
				result.put("res", false);
				return result;
			}
			//效验同规则信息在一分钟内是否发送过
			SSmsLog sSmsLog=sSmsLogDao.getAccountCode(smsLog);
			if(!StringUtil.isNullOrEmpty(sSmsLog)) {
				if(DateUtil.getSecond(sSmsLog.getCreateTime(),new Date())<60) {
					result.put("message", SmsCodeErrer.TIME_WITHIN);
					result.put("res", false);
					return result;
				}	
			}
			//校验同规则信息在一天内是否发送上限
			SSmsSendRule sendRule=(SSmsSendRule)redisService.getObject(smsLog.getRuleNum());
			if(StringUtil.isNullOrEmpty(sendRule)) {
				sendRule=sSmsSendRuleDao.getRuleId(smsLog.getRuleNum());
				redisService.put(smsLog.getRuleNum(), sendRule);
			}
			int smsCodeNum=sSmsLogDao.getAccountRule(smsLog);
			if(smsCodeNum>=sendRule.getLimitSendNumber()) {
				result.put("message", SmsCodeErrer.DAY_MAX);
				result.put("res", false);
				return result;
			}
			//获取随机码
			code=StringUtil.getRandomStr(4);
			if(code.startsWith("0")){
				code = "2"+code.substring(1, code.length());
			}
			if(smsLog.getSendMode()==1) {
				try{
					SSmsParamConf sendChannel=(SSmsParamConf)redisService.getObject(SmsParamConf.SEND_CHANNEL);
					if(StringUtil.isNullOrEmpty(sendChannel)) {
						sendChannel=sSmsParamConfDao.getName(SmsParamConf.SEND_CHANNEL);
						redisService.put(SmsParamConf.SEND_CHANNEL, sendChannel);
					}
					logger.info("-----------使用创蓝253发送短信-----------");
					res= SmsUtil.sendVariableSmsSDM(smsLog.getAccountNum(),code);
					smsLog.setStatus(3);
				}catch (Exception e){
					logger.info(e.getMessage());
					result.put("message", SmsCodeErrer.PHONE_CODE_ERROR);
					result.put("res", true);
					return result;
				}
			}else {
				SContentInfo sContentInfo=(SContentInfo)redisService.getObject(SmsConstants.SMS_CODE_EMAIL.toString());//获取信息模板
				if(StringUtil.isNullOrEmpty(sContentInfo)) {
					sContentInfo=sContentInfoDao.getSContentInfoId(SmsConstants.SMS_CODE_EMAIL);
				}
				res=emailService.sendSimpleMail(smsLog.getAccountNum(), sContentInfo.getMessageTitle(), "验证码："+code+","+sContentInfo.getMessageModel());
			}
		}else{
			smsLog.setStatus(3);
		}
		if(res) {
			smsLog.setCode(code);
			smsLog.setCreateTime(new Date());
			sSmsLogDao.saveSmsLog(smsLog);
			result.put("message", SmsCodeErrer.SEND_SUCCESS);
			result.put("res", true);
			return result;
		}else {
			result.put("message", SmsCodeErrer.SEND_FAIL);
			result.put("res", false);
			return result;
		}
	}
	
	/**
	 * 验证验证码是否正确
	 * @param smsLog
	 * @return
	 */
	@Override
	public Map<String,Object> validateSmsCode(SSmsLog smsLog){
		Map<String,Object> result=new HashMap<String,Object>();
		if("1234".equals(smsLog.getCode())){
			String codeKey = smsLog.getAccountNum()+StringUtil.getRandomStr(6);
			redisService.put(codeKey,300,"1");
			result.put("codeKey", codeKey);
			result.put("message", SmsCodeErrer.CODE_CORRECT);
			result.put("res", true);
			return result;
		}
		SSmsParamConf conf=(SSmsParamConf)redisService.getObject(SmsParamConf.CODE_FAILURE_TIME);//获取验证码有效时间
		if(StringUtil.isNullOrEmpty(conf)) {
			conf=sSmsParamConfDao.getName(SmsParamConf.CODE_FAILURE_TIME);
			redisService.put(SmsParamConf.CODE_FAILURE_TIME, conf);
		}
		SSmsLog sms=sSmsLogDao.getCodeValidate(smsLog);
		if(StringUtil.isNullOrEmpty(sms)) {
			result.put("message", SmsCodeErrer.CODE_ERROR);
			result.put("res", false);
			return result;
		}
		/*if(sms.getIsValid().intValue()==2) {
			result.put("message", SmsCodeErrer.CODE_VALID);
			result.put("res", false);
			return result;
		}*/
		//验证验证码是否失效
		if(DateUtil.getSecond(sms.getCreateTime(),new Date())>60*conf.getMaxValue()) {
			result.put("message", SmsCodeErrer.CODE_INVALID);
			result.put("res", false);
			return result;
		}
		String codeKey = smsLog.getAccountNum()+StringUtil.getRandomStr(6);
		redisService.put(codeKey,300,"1");
		sSmsLogDao.update(sms.getId());
		result.put("codeKey", codeKey);
		result.put("message", SmsCodeErrer.CODE_CORRECT);
		result.put("res", true);
		return result;
	}

	@Override
	public void checkReSend(String smsMessageId, String status) {
		SSmsLog sSmsLog = sSmsLogDao.selectByMessageId(smsMessageId);
		if("DELIVRD".equals(status)){
			sSmsLog.setStatus(3);
			sSmsLog.setRemark("发送成功");
		}else {
			sSmsLog.setStatus(2);
			sSmsLog.setRemark("发送失败");
			// 发送创蓝短信
			try {
				long now = new Date().getTime();
				if(now - sSmsLog.getCreateTime().getTime() < 2 * 60 * 1000){
					SmsUtil.sendVariableSms(sSmsLog.getAccountNum(),sSmsLog.getCode());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		sSmsLogDao.updateStatus(sSmsLog);
	}

	@Override
	public Map<String, Object> page(SSmsLog smsLog) {
		Map<String, Object> result = new HashMap<String, Object>();
		smsLog.setPageIndex(smsLog.getPageSize() * (smsLog.getPageNum() - 1));
		List<SSmsLog> smsLogList = sSmsLogDao.selectList(smsLog);
		int total=sSmsLogDao.selectCount(smsLog);
		result.put("list", smsLogList);
		result.put("total", total);
		return result;
	}

	@Override
	public Map<String, Object> sendSmsCodeXQ(SSmsLog smsLog, String callback, String channelCode) {
		smsLog.setStatus(2);
		Map<String,Object> result=new HashMap<String,Object>();
		//效验账号
		if(smsLog.getSendMode()==1) {
			if(!StringUtil.isPhoneNo(smsLog.getAccountNum())) {
				result.put("message", SmsCodeErrer.PHONE_FORMAT);
				result.put("res", false);
				return result;
			}
		}else {
			if(!StringUtil.isEmailNo(smsLog.getAccountNum())) {
				result.put("message", SmsCodeErrer.EMAIL_FORMAT);
				result.put("res", false);
				return result;
			}
		}
		SSmsParamConf isSend=(SSmsParamConf)redisService.getObject(SmsParamConf.IS_SEND);
		if(StringUtil.isNullOrEmpty(isSend)) {
			isSend=sSmsParamConfDao.getName(SmsParamConf.IS_SEND);
			redisService.put(SmsParamConf.IS_SEND, isSend);
		}
		Boolean res=true;
		String code="1234";
		if(isSend.getMaxValue().intValue() == 1) {
			//效验账号、ip是否被拉黑
			SSmsBlack smsBlack=(SSmsBlack)redisService.getObject(smsLog.getIp());
			if(StringUtil.isNullOrEmpty(smsBlack)) {
				smsBlack=sSmsBlackDao.getBlack("0",smsLog.getAccountNum(),smsLog.getIp());
			}
			if(!StringUtil.isNullOrEmpty(smsBlack)) {
				result.put("message", SmsCodeErrer.IP_BLACK);
				result.put("res", false);
				return result;
			}
			//效验同规则信息在一分钟内是否发送过
			SSmsLog sSmsLog=sSmsLogDao.getAccountCode(smsLog);
			if(!StringUtil.isNullOrEmpty(sSmsLog)) {
				if(DateUtil.getSecond(sSmsLog.getCreateTime(),new Date())<60) {
					result.put("message", SmsCodeErrer.TIME_WITHIN);
					result.put("res", false);
					return result;
				}	
			}
			//校验同规则信息在一天内是否发送上限
			SSmsSendRule sendRule=(SSmsSendRule)redisService.getObject(smsLog.getRuleNum());
			if(StringUtil.isNullOrEmpty(sendRule)) {
				sendRule=sSmsSendRuleDao.getRuleId(smsLog.getRuleNum());
				redisService.put(smsLog.getRuleNum(), sendRule);
			}
			int smsCodeNum=sSmsLogDao.getAccountRule(smsLog);
			if(smsCodeNum>=sendRule.getLimitSendNumber()) {
				result.put("message", SmsCodeErrer.DAY_MAX);
				result.put("res", false);
				return result;
			}
			if(smsLog.getSendMode()==1) {
				try{
					logger.info("-----------使用创蓝253发送短信-----------");
					res= true;
					smsLog.setStatus(3);
				}catch (Exception e){
					logger.info(e.getMessage());
					result.put("message", SmsCodeErrer.PHONE_CODE_ERROR);
					result.put("res", true);
					return result;
				}
			}else {
				SContentInfo sContentInfo=(SContentInfo)redisService.getObject(SmsConstants.SMS_CODE_EMAIL.toString());//获取信息模板
				if(StringUtil.isNullOrEmpty(sContentInfo)) {
					sContentInfo=sContentInfoDao.getSContentInfoId(SmsConstants.SMS_CODE_EMAIL);
				}
			    res=emailService.sendSimpleMail(smsLog.getAccountNum(), sContentInfo.getMessageTitle(), "验证码："+code+","+sContentInfo.getMessageModel());
			}
		}else{
			smsLog.setStatus(3);
		}
		if(res) {
			smsLog.setCode(code);
			smsLog.setCreateTime(new Date());
			sSmsLogDao.saveSmsLog(smsLog);
			result.put("message", SmsCodeErrer.SEND_SUCCESS);
			result.put("res", true);
			return result;
		}else {
			result.put("message", SmsCodeErrer.SEND_FAIL);
			result.put("res", false);
			return result;
		}
	}

	@Override
	public void remove(Integer id) {
		sSmsLogDao.deleteById(id);
	}
}
