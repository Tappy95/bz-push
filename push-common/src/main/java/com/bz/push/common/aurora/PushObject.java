package com.bz.push.common.aurora;

import java.util.List;

import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 创建需要推送的对象
 * @author ROOT
 *
 */
public class PushObject {

	/**
	 * 推送给Android所有
	 */
	public static PushPayload pushAndroidAll(String msg_title, String msg_content, String informUrl,String contentType){
		return PushPayload.newBuilder()
				.setPlatform(Platform.android())
				.setAudience(Audience.all())
				.setMessage(Message.newBuilder()
						.setContentType(contentType)
                        .setMsgContent(msg_content)
 
                        .setTitle(msg_title)
 
                        .addExtra("url",informUrl)
 
                        .build())
				.build();
	}
	
	/**
	 * 推送给IOS所有
	 */
	public static PushPayload pushIOSAll( String msg_title, String msg_content, String informUrl,String contentType){
		return PushPayload.newBuilder()
				.setPlatform(Platform.ios())
				.setAudience(Audience.all())
				.setMessage(Message.newBuilder()
						.setContentType(contentType)
                        .setMsgContent(msg_content)
 
                        .setTitle(msg_title)
 
                        .addExtra("url",informUrl)
 
                        .build())
				.build();
	}
	
	/**
	 * 推送给个人
	 */
	public static PushPayload pushSingle(String alias, String msg_title, String msg_content, String informUrl,String contentType){
		return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(alias))
                .setMessage(Message.newBuilder()
                		.setContentType(contentType)
                        .setMsgContent(msg_content)
 
                        .setTitle(msg_title)
 
                        .addExtra("url",informUrl)
 
                        .build())
                .build();
	}
	
	public static PushPayload pushSingleNotice(String alias, String msg_content){
		return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(alias))
                .setNotification(Notification.alert(msg_content))
                .build();
	}

	/**
	 * 推送给多个人
	 */
	public static PushPayload pushPart(List<String> alias, String msg_title, String msg_content, String informUrl,String contentType){
		return PushPayload.newBuilder()
				.setPlatform(Platform.all())
				.setAudience(Audience.registrationId(alias))
				.setMessage(Message.newBuilder()
						.setContentType(contentType)
                        .setMsgContent(msg_content)
 
                        .setTitle(msg_title)
 
                        .addExtra("url",informUrl)
 
                        .build())
                .build();
	}
	
	/**
	 * 发送给所有人自动以消息
	 * @param alert
	 * @return
	 */
	public static PushPayload pushAll(String msg_title, String msg_content, String informUrl,String contentType){
		return PushPayload.newBuilder()
				.setAudience(Audience.all())
				.setPlatform(Platform.android_ios())
				.setMessage(Message.newBuilder()
						.setContentType(contentType)
                        .setMsgContent(msg_content)
                        .setTitle(msg_title)
                        .addExtra("url",informUrl)
 
                        .build())
				.build();
	}
	
	/**
	 * 发送给所有人通知
	 * @param alert
	 * @return
	 */
	public static PushPayload pushAllNotice( String msg_content){
		return PushPayload.alertAll(msg_content);
	}
}
