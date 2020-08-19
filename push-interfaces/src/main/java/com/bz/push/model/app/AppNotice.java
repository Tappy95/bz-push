package com.bz.push.model.app;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * app公告实体
 * @author admin
 *
 */
@Alias("AppNotice")
public class AppNotice {
    private Integer id;//公告id
    private String noticeTitle;//公告标题
    private String noticeContent;//公告内容
    private Date createrTime;//创建时间
    private Integer isRelease;//是否发布
    
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNoticeTitle() {
		return noticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	public Date getCreaterTime() {
		return createrTime;
	}
	public void setCreaterTime(Date createrTime) {
		this.createrTime = createrTime;
	}
	public Integer getIsRelease() {
		return isRelease;
	}
	public void setIsRelease(Integer isRelease) {
		this.isRelease = isRelease;
	} 
}
