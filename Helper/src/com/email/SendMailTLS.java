package com.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailTLS {

	final String username = "mcpchelper81@gmail.com";
	final String password = "ru68ce48";
	Properties props = new Properties();
	public SendMailTLS(){
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
	}

	public String sendEmail(String subject,String bodyMSG){
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
		try {
	
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("mcpchelper81@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("slkbludger@gmail.com"));
			message.setSubject(subject);
			message.setText(bodyMSG);
	
			Transport.send(message);
	
			return "SUCCESS";
	
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
