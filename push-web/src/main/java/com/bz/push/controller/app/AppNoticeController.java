package com.bz.push.controller.app;

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
import com.bz.push.model.app.AppNotice;
import com.bz.push.service.app.AppNoticeService;

@RestController
@RequestMapping("/appNotice")
public class AppNoticeController extends BaseController{
	
	@Reference(version=WebConfig.dubboServiceVersion,interfaceClass=AppNoticeService.class,check=false,timeout=10000)
	private AppNoticeService appNoticeService;
	
	/**
	 * 添加app公告
	 * @param request
	 * @param response
	 * @param appNotice
	 */
	@RequestMapping(value = "/add",produces = WebConfig.jsonMimeType)
	public void add(HttpServletRequest request, HttpServletResponse response,AppNotice appNotice) {
		Map<String, String> respMap = new HashMap<>();initResponseMap(request, respMap);
		Result result = new Result();
		result.setData(appNoticeService.add(appNotice));
		result.setSuccess(true);
		outStrJSONWithResult(response, result, respMap);
	}
    

}
