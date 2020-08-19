package com.bz.push.base;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.dubbo.rpc.RpcContext;
import com.bz.push.common.constant.SysConstant;
import com.bz.push.common.utils.StringUtil;
import com.bz.push.interfaces.Result;

import net.sf.json.JSONObject;

public class BaseController {

	protected static Logger logger = LoggerFactory.getLogger(BaseController.class);
	public Map<String, String[]> paramMap = new HashMap<String, String[]>();
	
	/*public Result result = new Result();
	protected Map<String, String> respMap = new HashMap<>();*/

	public BaseController() {
	}

	@SuppressWarnings({ "unchecked" })
	public <T> T getDubboBean(HttpServletRequest request, String name) {
		return (T) getDubboBeanOrigin(request, name);
	}
	
	public Object getDubboBeanOrigin(HttpServletRequest request, String name) {
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(request.getSession().getServletContext());
		RpcContext.getContext().setAttachment(SysConstant.LOGGER_ID,
				(null != request.getAttribute(SysConstant.LOGGER_ID)) ? ((String) request.getAttribute(SysConstant.LOGGER_ID))
						: StringUtil.getLogId());
		return webApplicationContext.getBean(name);
	}

	protected void initResponseMap(HttpServletRequest request, Map<String, String> respMap) {
		/*if(this.result!=null) {
			this.result=new Result();
		}
		if(this.respMap.size()>0) {
			this.respMap.clear();
		}*/
		String requestUrl = "";
		Map<String, String[]> temp = request.getParameterMap();
		if (null != temp && temp.size() > 0) {
			for (Map.Entry<String, String[]> item : temp.entrySet()) {
				String key = item.getKey();
				String[] values = item.getValue();
				String value = "";
				for (String string : values) {
					value += string;
				}
				try {
					value = URLDecoder.decode(value, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				respMap.put(key, value);
				if (!requestUrl.isEmpty()) {
					requestUrl += "&";
				}
				requestUrl += key + "=" + value;
			}
		}
		if(respMap.containsKey("appkey")) {
			respMap.put("app_key", respMap.get("appkey"));
			respMap.remove("appkey");
		}
		// System.out.println("got request params is:" + requestUrl);
		logger.info("got request params is:" + requestUrl);
	}

	protected String getResponseSignature(Map<String, String> map) {
		StringBuffer buf = new StringBuffer();
		for (String key : map.keySet()) {
			if (StringUtils.isNotBlank(map.get(key)) && !"signature".equals(key)) {
				buf.append(key + "=" + map.get(key) + "&");
			}
		}
		return buf.toString().substring(0, buf.length() - 1);
	}

	protected void outStrJSON(HttpServletResponse response, Map<String, Object> map) {
		if (map.containsKey("password")) {
			map.remove("password");
		}
		String content =JSONObject.fromObject(map).toString();
		outStr(response, content);
	}

	protected void outStrJSONWithResult(HttpServletResponse response, Result result, Map<String, String> respMap) {
		Map<String, Object> map = new HashMap<>();
		if (result.getNeedChange()) {
			map = result.getChangeMap();
		} else {
			map.put("statusCode", result.getStatusCode());
			map.put("message", result.getMessage());
			map.put("data", result.getData());
			if (respMap.containsKey("token")) {
				map.put("token", respMap.get("token"));
			}
		}

		String content = "";
		String header = "application/json;charset=UTF-8";
		switch (result.getRespType()) {
		case NORMAL:
			header = "text/html;charset=UTF-8";
			if (result.getNeedChange()) {
				for (Map.Entry<String, Object> item : map.entrySet()) {
					Object value = item.getValue();

					if (!content.isEmpty()) {
						content += ",";
					}
					if (value == null || (value + "").isEmpty()) {
						content += item.getKey();
						continue;
					}
					content += item.getKey() + ":" + item.getValue();
				}
			} else {
				content =JSONObject.fromObject(map).toString();
			}

			break;
		case JSON:
			content = JSONObject.fromObject(map).toString();
			break;
		case XML:
			break;
		case SECURITY:
			break;

		default:
			content = JSONObject.fromObject(map).toString();
			break;
		}
		outStr(response, content, header);
	}

	protected void outStr(HttpServletResponse response, Map<String, String> map) {
		if (map.containsKey("password")) {
			map.remove("password");
		}
		String content = getResponseSignature(map);
		outStr(response, content);
	}

	protected void outStr(HttpServletResponse response, String content) {

		outStr(response, content, null);
	}

	protected void outStr(HttpServletResponse response, String content, String header) {
		logger.info("操作响应结果=>" + content + "----- hasHeader:" + header);

		OutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			if (header != null) {
				response.setHeader("content-type", header);
			} else {
				response.setHeader("content-type", "application/json;charset=UTF-8");
			}
			byte[] dataByteArr = content.getBytes("UTF-8");
			outputStream.write(dataByteArr);
		} catch (Exception e) {
			logger.error("outStr error", e);
		} finally {
			try {
				if (null != outputStream) {
					outputStream.flush();
					outputStream.close();
				}
			} catch (Exception e2) {
				logger.error("outStr close stream error", e2);
			}

		}
	}
}
