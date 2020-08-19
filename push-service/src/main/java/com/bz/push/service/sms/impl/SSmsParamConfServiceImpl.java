package com.bz.push.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.bz.push.common.config.WebConfig;
import com.bz.push.dao.sms.SSmsParamConfDao;
import com.bz.push.model.sms.SSmsParamConf;
import com.bz.push.service.redis.RedisService;
import com.bz.push.service.sms.SSmsParamConfService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service(interfaceClass=SSmsParamConfService.class,version=WebConfig.dubboServiceVersion)
public class SSmsParamConfServiceImpl implements SSmsParamConfService{
	
    @Autowired
	private SSmsParamConfDao SSmsParamConfDao;
    @Autowired
	private RedisService redisService;
	
	@Override
	public int update(SSmsParamConf sSmsParamConf) {
		int i=SSmsParamConfDao.update(sSmsParamConf);
		if(i>0) {
		   redisService.put(sSmsParamConf.getName(), sSmsParamConf);	
		}
		return i;
	}

	@Override
	public SSmsParamConf selectOne(int id) {
		return SSmsParamConfDao.selectOne(id);
	}

	@Override
	public PageInfo<SSmsParamConf> selectList(SSmsParamConf sSmsParamConf) {
		PageHelper.startPage(sSmsParamConf.getPageNum(), sSmsParamConf.getPageSize());
		List<SSmsParamConf> sSmsParamConfList =SSmsParamConfDao.selectList(sSmsParamConf);
		return new PageInfo<>(sSmsParamConfList);
	}
 
	@Override
	public int insert(SSmsParamConf sSmsParamConf) {
		int i=SSmsParamConfDao.insert(sSmsParamConf);
		if(i>0) {
			redisService.put(sSmsParamConf.getName(), sSmsParamConf);	
		}
		return i;
	}

}
