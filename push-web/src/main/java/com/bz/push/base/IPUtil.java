package com.bz.push.base;

import javax.servlet.http.HttpServletRequest;

public class IPUtil {
	
	/**
	 * 获取IP地址
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request){
		
		String clientIp = request.getHeader("x-forwarded-for");  
	    if(clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {  
	        clientIp = request.getHeader("Proxy-Client-IP");  
	    }  
	    if(clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {  
	        clientIp = request.getHeader("WL-Proxy-Client-IP");  
	    }
	    if(clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {  
	        clientIp = request.getRemoteAddr();  
	    }  
	    return clientIp;
	}
}
