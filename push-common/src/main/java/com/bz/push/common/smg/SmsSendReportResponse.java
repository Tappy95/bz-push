package com.bz.push.common.smg;

import java.util.List;

public class SmsSendReportResponse {
	private int ret;
	private List<SmsSendReportResponseResult> result;
	
	public int getRet() {
		return ret;
	}
	public void setRet(int ret) {
		this.ret = ret;
	}
	public List<SmsSendReportResponseResult> getResult() {
		return result;
	}
	public void setResult(List<SmsSendReportResponseResult> result) {
		this.result = result;
	}
	
	
}
