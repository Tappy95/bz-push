package com.bz.push.common.config;

public class SmsParamConf {
    public static final String CODE_FAILURE_TIME ="failure_time";//验证码失效时间

    public static final String IP_PER_DAY ="ip_per_day";//短信m_sms_log表同一IP每天允许出现次数范围

    public static final String PHONE_PER_DAY ="phone_per_day";//短信m_sms_log表同一手机号每天允许出现次数范围

    public static final String EMAIL_PER_DAY ="email_per_day";//邮件m_sms_log表同一邮箱每天允许出现次数范围

    public static final String IP_BLACK_NUM ="ip_black_num";//ip被拉黑次数，为多少次时进入永久黑名单

    public static final String PHONE_BLACK_NUM ="phone_black_num";//手机号被拉黑次数，为多少次时进入永久黑名单

    public static final String EMAIL_BLACK_NUM ="email_black_num";//邮箱被拉黑次数，为多少次时进入永久黑名单

    public static final String PHONE_EMAIL_BLACK_TIME ="phone_email_black_time";//手机号和邮箱拉黑一次，有效时长

    public static final String IP_BLACK_TIME ="ip_black_time";//ip地址拉黑一次，有效时长
    
    public static final String IS_SEND="is_send";//是否发送短信，1发送0不发送

    public static final String SEND_CHANNEL="send_channel";//短信发送通道 1-创蓝 2-华为云
}
