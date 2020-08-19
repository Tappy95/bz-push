package com.bz.push.service.email;

public interface EmailService {
    /**
     * 发送简单邮件
     * @param sendTo 收件人地址
     * @param titel  邮件标题
     * @param content 邮件内容
     */
    public boolean sendSimpleMail(String sendTo, String titel, String content);

}
