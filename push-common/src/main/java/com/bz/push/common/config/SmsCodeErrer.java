package com.bz.push.common.config;
/**
 * 短信发送失败错误
 * @author admin
 *
 */
public class SmsCodeErrer {
	
    public static final String IP_BLACK ="ip地址受限" ;
	
	public static final String PHONE_BLACK ="手机号码受限";
	
	public static final String EMAIL_BLACK ="邮箱受限";
	
	public static final String TIME_WITHIN ="60秒内已发送过同类型验证码";
	
	public static final String DAY_MAX ="今日验证码发送次数超过限制";
	
	public static final String PHONE_FORMAT ="手机号码格式错误";
	
	public static final String EMAIL_FORMAT ="邮箱格式错误";
	
	public static final String SEND_SUCCESS ="验证码发送成功";
	
	public static final String SEND_FAIL ="验证码发送失败";

	public static final String CODE_ERROR ="验证码错误";

	public static final String CODE_INVALID ="验证码失效";

	public static final String CODE_CORRECT ="验证成功";

	public static final String PHONE_CODE_ERROR ="短信验证码发送异常";
	
	public static final String CODE_VALID ="验证码已被使用";

}
