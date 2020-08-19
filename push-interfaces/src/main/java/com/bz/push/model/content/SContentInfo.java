package com.bz.push.model.content;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * 配置短信内容
 * @author admin
 *
 */
@Alias("SContentInfo")
public class SContentInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;//短信内容id
	private String messageTitle;//消息标题
	private String messageModel;//消息模板
	private String paramCaption;//参数说明
	private Integer sendMode;//发送方式1,手机号 2邮箱 3.公众号
	private Integer isTiming;//是否定时
	private Integer timingTime;//定时发送发送时间
	private Integer isActive;//是否启用(0:未启用 1:启用
	private Date createTime;//创建时间
	private Date updateTime;//更新时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMessageTitle() {
		return messageTitle;
	}
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	public String getMessageModel() {
		return messageModel;
	}
	public void setMessageModel(String messageModel) {
		this.messageModel = messageModel;
	}
	public String getParamCaption() {
		return paramCaption;
	}
	public void setParamCaption(String paramCaption) {
		this.paramCaption = paramCaption;
	}
	public Integer getSendMode() {
		return sendMode;
	}
	public void setSendMode(Integer sendMode) {
		this.sendMode = sendMode;
	}
	public Integer getIsTiming() {
		return isTiming;
	}
	public void setIsTiming(Integer isTiming) {
		this.isTiming = isTiming;
	}
	public Integer getTimingTime() {
		return timingTime;
	}
	public void setTimingTime(Integer timingTime) {
		this.timingTime = timingTime;
	}
	public Integer getIsActive() {
		return isActive;
	}
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}



}
