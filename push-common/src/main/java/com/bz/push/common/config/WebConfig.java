package com.bz.push.common.config;

public class WebConfig {

	/**
     * jsonMimeType:用于springMVC返回json格式时指定字符集
     */
    public static final String jsonMimeType = "application/json; charset=utf-8";

    /**
     * maxExpireDuration:请求的最大过期时长
     */
    public static final Long maxExpireDuration = 3 * 60 * 1000l;
    
    /**
     * jsonMimeType:用于springMVC返回json格式时指定字符集
     */
    public static final String dubboServiceVersion = "1.0.1";
}
