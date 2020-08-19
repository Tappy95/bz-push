package com.bz.push.controller.sms;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bz.push.common.config.WebConfig;
import com.bz.push.service.sms.ValidateCodeService;

@RestController
@RequestMapping("/smsStatusReport")
public class SmsStatusReportController {
	
	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=ValidateCodeService.class,check=false,timeout=10000)
	private ValidateCodeService validateCodeService;
	
	@RequestMapping(value="hwyReport")
	void hwyReport(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, Object> reqMap){
		
		Set<String> set = reqMap.keySet();
		Iterator<String> iterator= set.iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			System.out.println(key + ":" + reqMap.get(key));
		}
		
		String smsMsgId = (String) reqMap.get("smsMsgId");
		String status = (String) reqMap.get("status");
		validateCodeService.checkReSend(smsMsgId, status);
	}
}
