package com.bz.push.common.smg;

import java.util.List;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsUtil {
    private static final Logger logger = LoggerFactory.getLogger(SmsUtil.class);

    /**
     * httpClient:发送http请求的客户端
     */
    private static CloseableHttpClient httpClient;
    
    private static final String ACCOUNT = "YZM4732516";
    
    private static final String PWD = "NSw9x2aJvn45f9";

    static {
        PoolingHttpClientConnectionManager httpPool = new PoolingHttpClientConnectionManager();// 初始化http连接池
        httpPool.setMaxTotal(200);// 设置连接最大数
        httpClient = HttpClients.custom().setConnectionManager(httpPool).build();// 完成http客户端初始化
        logger.info("初始化成功");
    }

    
    /**
     * sendVariableSms:发送验证码到指定手机上
     *
     * @param mobile 手机号
     * @param code 随机码
     * @throws Exception 异常
     * @return 验证码
     */
    public static Boolean sendVariableSms(String mobile,String code) throws Exception {
        logger.info("宝猪乐园");
    	String msg = "【中青赚点】验证码为：{$var}，5分钟内有效，请勿泄露他人，如非本人操作请忽略。";
    	String params = mobile+","+code;
        // 封装请求参数，并转成json
    	SmsVariableRequest requeset = new SmsVariableRequest(ACCOUNT, PWD, msg, params, "true");
        String requestJson = JSON.toJSONString(requeset);
        // 创建post请求
        HttpPost post = new HttpPost("http://smssh1.253.com/msg/variable/json");
//        HttpPost post = new HttpPost("http://smssh1.253.com/msg/send/json");
        // 设置请求头
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Charset", "UTF-8");
        // 创建并设置参数实体
        StringEntity  paramsEntity = new StringEntity(requestJson,"UTF-8");
        post.setEntity(paramsEntity);
        // 发送请求并取得结果
        String rspText = EntityUtils.toString(httpClient.execute(post).getEntity(), "utf-8");// 发送http请求并获得响应结果
        SmsVariableResponse responseEntity = JSON.parseObject(rspText, SmsVariableResponse.class);
        // 根据状态码取得结果
        logger.info(responseEntity.toString());
        if (!"0".equals(responseEntity.getCode())) {
        	return false;
        }
        return true;
    }

    /**
     * sendVariableSms:发送验证码到指定手机上
     *
     * @param mobile 手机号
     * @param code 随机码
     * @throws Exception 异常
     * @return 验证码
     */
    public static Boolean sendVariableSmsZQ(String mobile,String code) throws Exception {
        logger.debug("中青賺點");

        String msg = "【中青赚点】验证码为：{$var}，5分钟内有效，请勿泄露他人，如非本人操作请忽略。";
    	String params = mobile+","+code;
    	// 封装请求参数，并转成json
    	SmsVariableRequest requeset = new SmsVariableRequest(ACCOUNT, PWD, msg, params, "true");
    	String requestJson = JSON.toJSONString(requeset);
    	// 创建post请求
    	HttpPost post = new HttpPost("http://smssh1.253.com/msg/variable/json");
//    	HttpPost post = new HttpPost("http://smssh1.253.com/msg/send/json");
    	// 设置请求头
    	post.addHeader("Content-Type", "application/json");
    	post.addHeader("Charset", "UTF-8");
    	// 创建并设置参数实体
    	StringEntity  paramsEntity = new StringEntity(requestJson,"UTF-8");
    	post.setEntity(paramsEntity);
    	// 发送请求并取得结果
    	String rspText = EntityUtils.toString(httpClient.execute(post).getEntity(), "utf-8");// 发送http请求并获得响应结果
    	SmsVariableResponse responseEntity = JSON.parseObject(rspText, SmsVariableResponse.class);
    	// 根据状态码取得结果
        logger.debug(responseEntity.toString());

        if (!"0".equals(responseEntity.getCode())) {
    		return false;
    	}
    	return true;
    }
    
    /**
     * sendVariableSms:发送验证码到指定手机上
     *
     * @param mobile 手机号
     * @param code 随机码
     * @throws Exception 异常
     * @return 验证码
     */
    public static Boolean sendVariableSmsSDM(String mobile,String code) throws Exception {
        logger.debug("神都盟");
    	String msg = "【神都盟】验证码为：{$var}，5分钟内有效，请勿泄露他人，如非本人操作请忽略。";
    	String params = mobile+","+code;
    	// 封装请求参数，并转成json
    	SmsVariableRequest requeset = new SmsVariableRequest(ACCOUNT, PWD, msg, params, "true");
    	String requestJson = JSON.toJSONString(requeset);
    	// 创建post请求
    	HttpPost post = new HttpPost("http://smssh1.253.com/msg/variable/json");
//    	HttpPost post = new HttpPost("http://smssh1.253.com/msg/send/json");
    	// 设置请求头
    	post.addHeader("Content-Type", "application/json");
    	post.addHeader("Charset", "UTF-8");
    	// 创建并设置参数实体
    	StringEntity  paramsEntity = new StringEntity(requestJson,"UTF-8");
    	post.setEntity(paramsEntity);
    	// 发送请求并取得结果
    	String rspText = EntityUtils.toString(httpClient.execute(post).getEntity(), "utf-8");// 发送http请求并获得响应结果
    	SmsVariableResponse responseEntity = JSON.parseObject(rspText, SmsVariableResponse.class);
    	// 根据状态码取得结果
    	if (!"0".equals(responseEntity.getCode())) {
    		return false;
    	}
    	return true;
    }
    
    public static Boolean getReport(String msgId) throws Exception{
    	boolean flag = false;
    	SmsSendReport report = new SmsSendReport(ACCOUNT, PWD, 100);
        String requestJson = JSON.toJSONString(report);
        // 创建post请求
        HttpPost post = new HttpPost("http://smssh1.253.com/msg/pull/report");
        // 设置请求头
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Charset", "UTF-8");
        // 创建并设置参数实体
        StringEntity  paramsEntity = new StringEntity(requestJson,"UTF-8");
        post.setEntity(paramsEntity);
        // 发送请求并取得结果
        String rspText = EntityUtils.toString(httpClient.execute(post).getEntity(), "utf-8");// 发送http请求并获得响应结果
        SmsSendReportResponse responseEntity = JSON.parseObject(rspText, SmsSendReportResponse.class);
        List<SmsSendReportResponseResult> results = responseEntity.getResult();
        for (int i = 0; i < results.size(); i++) {
			if(msgId.equals(results.get(i).getMsgId()) && "DELIVRD".equals(results.get(i).getStatus())){
				flag = true;
				break;
			}
		}
        return flag;
    }
    
}
