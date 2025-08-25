package com.mycompany.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServicesImpl implements EmailServices {

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public String sendEmail(String toMail, String subject, String body) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(toMail);
			message.setSubject(subject);
			message.setText(body);
			message.setFrom("krishnarajput1709@gmail.com");
			
			javaMailSender.send(message);
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			return "fail";
		}
	}
	
	
}
