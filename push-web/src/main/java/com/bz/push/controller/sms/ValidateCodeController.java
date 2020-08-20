package com.bz.push.controller.sms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bz.push.base.BaseController;
import com.bz.push.base.IPUtil;
import com.bz.push.common.config.WebConfig;
import com.bz.push.interfaces.Result;
import com.bz.push.model.sms.SSmsLog;
import com.bz.push.service.sms.ValidateCodeService;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController extends BaseController{
	
	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=ValidateCodeService.class,check=false,timeout=10000)
	private ValidateCodeService validateCodeService;
	
	@Value("${hwy.callback}")
	private String callback;
	/**
	 * 发送验证码接口
	 * @param request
	 * @param response
	 * @param smsLog
	 */
	@RequestMapping(value = "/sendSmsCode",produces = WebConfig.jsonMimeType)
	public void sendSmsCode(HttpServletRequest request, HttpServletResponse response,SSmsLog smsLog,String channelCode) {
		smsLog.setIp(IPUtil.getIp(request));
		Result result = new Result();
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		if(StringUtils.isBlank(channelCode)){
			result.setData(validateCodeService.sendSmsCode(smsLog,callback));
		}else {
			if("wishxiaqiu".equals(channelCode)
					||"xiaqiu".equals(channelCode) 
					||"xiaqiu2".equals(channelCode) 
					|| "shua1".equals(channelCode) 
					|| "wish4".equals(channelCode) 
					|| "hlj".equals(channelCode)
					|| "wish7".equals(channelCode)
					|| "jie".equals(channelCode)){
				result.setData(validateCodeService.sendSmsCodeXQ(smsLog,callback,channelCode));
			}else{
				result.setData(validateCodeService.sendSmsCode(smsLog,callback));
			}
		}
		result.setSuccess(true);
		outStrJSONWithResult(response, result, respMap);
	}

	/**
	 * 发送验证码接口
	 * @param request
	 * @param response
	 * @param smsLog
	 */
	@RequestMapping(value = "/sendSmsCodeZQ",produces = WebConfig.jsonMimeType)
	public void sendSmsCodeZQ(HttpServletRequest request, HttpServletResponse response,SSmsLog smsLog) {
		smsLog.setIp(IPUtil.getIp(request));
		Result result = new Result();
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		result.setData(validateCodeService.sendSmsCodeZQ(smsLog,callback));
		result.setSuccess(true);
		outStrJSONWithResult(response, result, respMap);
	}
	
	/**
	 * 发送验证码接口
	 * @param request
	 * @param response
	 * @param smsLog
	 */
	@RequestMapping(value = "/sendSmsCodeSDM",produces = WebConfig.jsonMimeType)
	public void sendSmsCodeSDM(HttpServletRequest request, HttpServletResponse response,SSmsLog smsLog,String channelCode) {
		smsLog.setIp(IPUtil.getIp(request));
		Result result = new Result();
		Map<String, String> respMap = new HashMap<>();
		initResponseMap(request, respMap);
		if(StringUtils.isBlank(channelCode)){
			result.setData(validateCodeService.sendSmsCodeSDM(smsLog,callback));
		}else {
			if("wishxiaqiu".equals(channelCode) 
					||"xiaqiu".equals(channelCode) 
					||"xiaqiu2".equals(channelCode) 
					|| "shua1".equals(channelCode) 
					|| "wish4".equals(channelCode) 
					|| "hlj".equals(channelCode)
					|| "wish7".equals(channelCode)
					|| "jie".equals(channelCode)){
				result.setData(validateCodeService.sendSmsCodeXQ(smsLog,callback,channelCode));
			}else{
				result.setData(validateCodeService.sendSmsCodeSDM(smsLog,callback));
			}
		}
		result.setSuccess(true);
		outStrJSONWithResult(response, result, respMap);
	}
    /**
     * 验证验证码接口
     * @param request
     * @param response
     * @param smsLog
     */
	@RequestMapping(value = "/validateSmsCode",produces = WebConfig.jsonMimeType)
	public void validateSmsCode(HttpServletRequest request, HttpServletResponse response,SSmsLog smsLog) {
		smsLog.setIp(IPUtil.getIp(request));
		Result result = new Result();
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		result.setData(validateCodeService.validateSmsCode(smsLog));
		result.setSuccess(true);
		outStrJSONWithResult(response, result, respMap);
	}
	
	@RequestMapping(value = "/page",produces = WebConfig.jsonMimeType)
	public void page(HttpServletRequest request, HttpServletResponse response,SSmsLog smsLog) {
		Result result = new Result();
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		result.setData(validateCodeService.page(smsLog));
		result.setSuccess(true);
		outStrJSONWithResult(response, result, respMap);
	}
	
	@RequestMapping(value = "/remove",produces = WebConfig.jsonMimeType)
	public void remove(HttpServletRequest request, HttpServletResponse response,Integer id) {
		Result result = new Result();
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		validateCodeService.remove(id);
		result.setSuccess(true);
		outStrJSONWithResult(response, result, respMap);
	}

}
