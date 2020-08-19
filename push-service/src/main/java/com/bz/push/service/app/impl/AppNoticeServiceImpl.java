package com.bz.push.service.app.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.bz.push.common.config.SmsCodeErrer;
import com.bz.push.common.config.WebConfig;
import com.bz.push.dao.app.AppNoticeDao;
import com.bz.push.model.app.AppNotice;
import com.bz.push.service.app.AppNoticeService;

@Service(interfaceClass=AppNoticeService.class,version=WebConfig.dubboServiceVersion)
public class AppNoticeServiceImpl implements AppNoticeService {
	
    @Autowired
	private AppNoticeDao appNoticeDao;
	
	@Override
	public Map<String, Object> add(AppNotice appNotice) {
		Map<String,Object> result=new HashMap<String,Object>();
		appNotice.setCreaterTime(new Date());
		Integer num=appNoticeDao.saveNotice(appNotice);
		if(num>0) {
			result.put("message", "添加成功");
			result.put("res", true);
			return result;
		}else {
			result.put("message", "添加失败");
			result.put("res", false);
			return result;
		}
	}

}
