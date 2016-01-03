package com.utilities;

import com.email.SendMailTLS;

public class Utilities {
	private static final String EXCEPTION_SUBJECT = "EXCEPTION DETECTED!";
	
	public static void sendExceptionEmail(String excMSG){
		SendMailTLS sendMail = new SendMailTLS();
		sendMail.sendEmail(EXCEPTION_SUBJECT, excMSG);
	}
	
	public static void sendGeneralEmail(String subject, String bondyMSG){
		SendMailTLS sendMail = new SendMailTLS();
		sendMail.sendEmail(subject, bondyMSG);
	}
	
}
