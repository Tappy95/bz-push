package com.bz.push.common.aurora;

import java.util.List;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

public class PushUtil {
	
	private static String APP_KEY = "";  
	private static String MASTER_SECRET = "";
	private static JPushClient jPushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());
	
	/**
	 * 发送给单个人自定义消息
	 * @param alias
	 * @param alert
	 */
	public static int sendSingle(String alias,String msg_title, String msg_content, String informUrl,String contentType){
		int result = 0;
		PushPayload pushPayload = PushObject.pushSingle(alias,msg_title,msg_content,informUrl,contentType);
		try {
			PushResult pushResult=jPushClient.sendPush(pushPayload);
			if(pushResult.getResponseCode()==200){
                result=1;
            }
		} catch (APIConnectionException | APIRequestException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 发送给单个人通知
	 * @param alias
	 * @param alert
	 */
	public static int sendSingleNotice(String alias, String msg_content){
		int result = 0;
		PushPayload pushPayload = PushObject.pushSingleNotice(alias,msg_content);
		try {
			PushResult pushResult=jPushClient.sendPush(pushPayload);
			if(pushResult.getResponseCode()==200){
                result=1;
            }
		} catch (APIConnectionException | APIRequestException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 发送给所有人 自定义消息
	 * @param notification_title
	 * @param msg_title
	 * @param msg_content
	 * @param informUrl
	 * @param contentType
	 */
	public static int sendAll(String msg_title, String msg_content, String informUrl,String contentType){
		int result = 0;
		PushPayload pushPayload = PushObject.pushAll(msg_title,msg_content,informUrl,contentType);
		try {
			PushResult pushResult=jPushClient.sendPush(pushPayload);
			if(pushResult.getResponseCode()==200){
                result=1;
            }
		} catch (APIConnectionException | APIRequestException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 发送给所有人 通知
	 * @param notification_title
	 * @param msg_title
	 * @param msg_content
	 * @param informUrl
	 * @param contentType
	 */
	public static int sendAllNotice(String msg_content){
		int result = 0;
		PushPayload pushPayload = PushObject.pushAllNotice(msg_content);
		try {
			PushResult pushResult=jPushClient.sendPush(pushPayload);
			if(pushResult.getResponseCode()==200){
                result=1;
            }
		} catch (APIConnectionException | APIRequestException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 推送给部分人
	 * @param alias
	 * @param alert
	 */
	public static int sendPart(List<String> alias,String msg_title, String msg_content, String informUrl,String contentType){
		int result = 0;
		PushPayload pushPayload = PushObject.pushPart(alias,msg_title,msg_content,informUrl,contentType);
		try {
			PushResult pushResult=jPushClient.sendPush(pushPayload);
			if(pushResult.getResponseCode()==200){
                result=1;
            }
		} catch (APIConnectionException | APIRequestException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 推送给安卓全部
	 * @param alert
	 */
	public static int sendAndroidAll(String msg_title, String msg_content, String informUrl,String contentType){
		int result = 0;
		PushPayload pushPayload = PushObject.pushAndroidAll(msg_title,msg_content,informUrl,contentType);
		try {
			PushResult pushResult=jPushClient.sendPush(pushPayload);
			if(pushResult.getResponseCode()==200){
                result=1;
            }
		} catch (APIConnectionException | APIRequestException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 推送给IOS全部
	 * @param alert
	 */
	public static int sendIOSAll(String msg_title, String msg_content, String informUrl,String contentType){
		int result = 0;
		PushPayload pushPayload = PushObject.pushIOSAll(msg_title,msg_content,informUrl,contentType);
		try {
			PushResult pushResult=jPushClient.sendPush(pushPayload);
			if(pushResult.getResponseCode()==200){
                result=1;
            }
		} catch (APIConnectionException | APIRequestException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		sendSingleNotice("190e35f7e00d6d9ec46", "hello world");
	}
	
}
