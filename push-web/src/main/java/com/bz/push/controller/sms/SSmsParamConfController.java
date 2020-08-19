package com.bz.push.controller.sms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bz.push.base.BaseController;
import com.bz.push.common.config.WebConfig;
import com.bz.push.interfaces.Result;
import com.bz.push.model.sms.SSmsParamConf;
import com.bz.push.service.sms.SSmsParamConfService;

@RestController
@RequestMapping("/smsParamConf")
public class SSmsParamConfController extends BaseController{
	
	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=SSmsParamConfService.class,check=false,timeout=3000)
	private SSmsParamConfService sSmsParamConfService;
	
	@RequestMapping(value = "/list",produces = WebConfig.jsonMimeType)
	public void list(HttpServletRequest request, HttpServletResponse response,SSmsParamConf sSmsParamConf) {
		Result result = new Result();
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		result.setData(sSmsParamConfService.selectList(sSmsParamConf));
		result.setSuccess(true);
		result.setStatusCode("2000");
		result.setMessage("操作成功");
		outStrJSONWithResult(response, result, respMap);
	}

	@RequestMapping(value = "/info",produces = WebConfig.jsonMimeType)
	public void info(HttpServletRequest request, HttpServletResponse response,int id) {
		Result result = new Result();
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		result.setData(sSmsParamConfService.selectOne(id));
		result.setSuccess(true);
		result.setStatusCode("2000");
		result.setMessage("操作成功");
		outStrJSONWithResult(response, result, respMap);
	}
	
	@RequestMapping(value = "/update",produces = WebConfig.jsonMimeType)
	public void update(HttpServletRequest request, HttpServletResponse response,SSmsParamConf sSmsParamConf) {
		Result result = new Result();
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		int res=sSmsParamConfService.update(sSmsParamConf);
		if(res>0) {
			result.setSuccess(true);
			result.setStatusCode("2000");
			result.setMessage("操作成功");	
		}else {
			result.setSuccess(true);
			result.setStatusCode("3000");
			result.setMessage("操作失败");	
		}
		outStrJSONWithResult(response, result, respMap);
	}
	
	@RequestMapping(value = "/add",produces = WebConfig.jsonMimeType)
	public void add(HttpServletRequest request, HttpServletResponse response,SSmsParamConf sSmsParamConf) {
		Result result = new Result();
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		result.setData(sSmsParamConfService.insert(sSmsParamConf));
		result.setSuccess(true);
		result.setStatusCode("2000");
		result.setMessage("操作成功");
		outStrJSONWithResult(response, result, respMap);
	}
}
