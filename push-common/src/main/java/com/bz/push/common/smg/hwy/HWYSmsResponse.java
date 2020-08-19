package com.bz.push.common.smg.hwy;

import java.io.Serializable;
import java.util.List;

public class HWYSmsResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<HWYSmsResponseResult> result;
	private String code;
	private String description;
	public List<HWYSmsResponseResult> getResult() {
		return result;
	}
	public void setResult(List<HWYSmsResponseResult> result) {
		this.result = result;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
