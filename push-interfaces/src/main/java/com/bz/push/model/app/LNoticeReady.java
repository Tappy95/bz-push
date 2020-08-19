package com.bz.push.model.app;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("LNoticeReady")
public class LNoticeReady implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer noticeType; // 提醒类型（1-每日红包提醒）
	private String userId; // 用户id
	private Long createTime; // 创建时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}
