package com.bz.push.model.app;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;
/**
 * app通知
 * @author admin
 *
 */
@Alias("AppNewsInform")
public class AppNewsInform implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    private Integer id;
    private String userId;
    private String mobile;
    private String informTitle;//通知标题
    private String informContent;//通知内容
    private Integer pushObject;//推送对象1个人2所有人
    private Long pushTime;//推送时间
    private Long createrTime;//创建时间
    private Integer informType;//通知类型1普通通知2自定义通知
    private Integer isRelease;//是否发布1.发布2.撤回
    private String informUrl;//自定义消息跳转链接
    private Integer isPush;//是否推送1未推送2已推送
    private Integer pushMode;//通知方式1短信2推送
    private Integer addMode;//添加方式1后台添加2自动添加
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInformTitle() {
		return informTitle;
	}
	public void setInformTitle(String informTitle) {
		this.informTitle = informTitle;
	}
	public String getInformContent() {
		return informContent;
	}
	public void setInformContent(String informContent) {
		this.informContent = informContent;
	}
	public Long getCreaterTime() {
		return createrTime;
	}
	public void setCreaterTime(Long createrTime) {
		this.createrTime = createrTime;
	}
	public Integer getInformType() {
		return informType;
	}
	public void setInformType(Integer informType) {
		this.informType = informType;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getIsRelease() {
		return isRelease;
	}
	public void setIsRelease(Integer isRelease) {
		this.isRelease = isRelease;
	}
	
	public String getInformUrl() {
		return informUrl;
	}
	public void setInformUrl(String informUrl) {
		this.informUrl = informUrl;
	}
	public Integer getIsPush() {
		return isPush;
	}
	public void setIsPush(Integer isPush) {
		this.isPush = isPush;
	}
	public Integer getPushMode() {
		return pushMode;
	}
	public void setPushMode(Integer pushMode) {
		this.pushMode = pushMode;
	}
	
	public Integer getPushObject() {
		return pushObject;
	}
	public void setPushObject(Integer pushObject) {
		this.pushObject = pushObject;
	}
	public Long getPushTime() {
		return pushTime;
	}
	public void setPushTime(Long pushTime) {
		this.pushTime = pushTime;
	}
	public Integer getAddMode() {
		return addMode;
	}
	public void setAddMode(Integer addMode) {
		this.addMode = addMode;
	}
	
	
}
