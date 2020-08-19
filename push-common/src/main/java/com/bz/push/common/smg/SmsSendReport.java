package com.bz.push.common.smg;

public class SmsSendReport {

	private String account;
	private String password;
	private Integer count;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public SmsSendReport() {
		// TODO Auto-generated constructor stub
	}
	public SmsSendReport(String account, String password, Integer count) {
		super();
		this.account = account;
		this.password = password;
		this.count = count;
	}
}
