package com.bz.push.service.impl.redis;

import com.bz.push.dao.sms.SSmsBlackDao;
import com.bz.push.dao.sms.SSmsParamConfDao;
import com.bz.push.dao.sms.SSmsSendRuleDao;
import com.bz.push.model.sms.SSmsBlack;
import com.bz.push.model.sms.SSmsParamConf;
import com.bz.push.model.sms.SSmsSendRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.bz.push.dao.content.SContentInfoDao;
import com.bz.push.model.content.SContentInfo;
import com.bz.push.service.redis.RedisService;

import java.util.List;

/**
 * 项目启动时将部分信息存入redis
 */
@Component
@Order(value = 2)
public class RunService implements ApplicationRunner{

	@Autowired
	private SContentInfoDao sContentInfoDao;
	@Autowired
	private RedisService redisService;
	@Autowired
	private SSmsBlackDao sSmsBlackDao;
	@Autowired
	private SSmsParamConfDao sSmsParamConfDao;
	@Autowired
	private SSmsSendRuleDao sSmsSendRuleDao;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		/**
		 * 获取短信模板内容
		 */
		List<SContentInfo> sContent=sContentInfoDao.getSContentInfoList();
		if(sContent.size() != 0){
			for(SContentInfo con:sContent){
				redisService.put(con.getId().toString(), con);
			}
		}
		/**
		 * 获取黑名单
		 */
		List<SSmsBlack> sBlack=sSmsBlackDao.getBlackList();
		if(sBlack.size() != 0){
			for(SSmsBlack black:sBlack){
				redisService.put(black.getAccountNum(), black);
			}
		}
		/**
		 * 获取推送配置信息
		 */
		List<SSmsParamConf> sConf=sSmsParamConfDao.getConfList();
		if(sConf.size() != 0){
			for(SSmsParamConf conf:sConf){
				redisService.put(conf.getName(), conf);
			}
		}
		/**
		 * 获取信息发送规则限制次数
		 */
		List<SSmsSendRule> sRule=sSmsSendRuleDao.getRuleList();
		if(sRule.size() != 0){
			for(SSmsSendRule rule:sRule){
				redisService.put(rule.getRuleNum(), rule);
			}
		}
		/*String msg="试试";
		String temp=new String(msg.getBytes(),"utf-8");*//*
		redisService.delete("50");
		redisService.put("50", sContent);
		SContentInfo result=(SContentInfo)redisService.getObject("50");
		*//*sContent=(SContentInfo)redisService.get("50");*//*
		System.out.println(result.getMessageModel());*/
	}

}
