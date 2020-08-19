package com.bz.push.common.smg.hwy;

import java.io.Serializable;

public class HWYSmsResponseResult implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String originTo;
	private String createTime;
	private String from;
	private String smsMsgId;
	private String status;
	
	public String getOriginTo() {
		return originTo;
	}
	public void setOriginTo(String originTo) {
		this.originTo = originTo;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getSmsMsgId() {
		return smsMsgId;
	}
	public void setSmsMsgId(String smsMsgId) {
		this.smsMsgId = smsMsgId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
