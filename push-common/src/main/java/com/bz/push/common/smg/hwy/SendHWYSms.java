package com.bz.push.common.smg.hwy;

import com.bz.push.common.utils.HttpClient;

public class SendHWYSms {
	
	public static String sendSms(String mobile,String yzm,String statusCallBack) throws Exception {
		String url = "https://api.rtc.huaweicloud.com:10443/sms/batchSendSms/v1"; //APP接入地址+接口访问URI
        String appKey = ""; //APP_Key
        String appSecret = ""; //APP_Secret
        String sender = ""; //国内短信签名通道号或国际/港澳台短信通道号
        String templateId = ""; //模板ID
        String signature = "宝猪乐园"; //签名名称
        
        String receiver = "+86"+mobile; //短信接收人号码
        String templateParas = "["+yzm+"]"; //模板变量
        String body = HWYRequestBody.buildRequestBody(sender, receiver, templateId, templateParas, statusCallBack, signature);
        if (null == body || body.isEmpty()) {
            System.out.println("body is null.");
            return null;
        }
        
        //请求Headers中的X-WSSE参数值
        String wsseHeader = HWYWsseHeader.buildWsseHeader(appKey, appSecret);
        if (null == wsseHeader || wsseHeader.isEmpty()) {
            System.out.println("wsse header is null.");
            return null;
        }
        String messageId = HttpClient.sendHWYSmsNew(wsseHeader, url, body);
        return messageId;
	}
}
