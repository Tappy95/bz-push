package com.bz.push.service.email.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bz.push.config.EmailConfig;
import com.bz.push.service.email.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
    private EmailConfig emailConfig;
    @Autowired
    private JavaMailSender mailSender;
    
    public boolean sendSimpleMail(String sendTo, String titel, String content) {
        try {
        	SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailConfig.getEmailFrom());
            message.setTo(sendTo);
            message.setSubject(titel);
            message.setText(content);
            mailSender.send(message);
            return true;
        }catch (Exception e) {
			return false;
		}
    	
    }
}
