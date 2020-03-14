package com.bridgelabz.fundoonotesapi.message;
/**
 * @Created By :- krunal Parate
 * @Purpose :- It is Used in Simple Message Varification
 */
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
@Component
public class MessageResponse {
	public  SimpleMailMessage verifyMail(String reciverEmail, String reciverName, String token) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(reciverEmail); 
		message.setFrom("forgotbridge70@gmail.com"); // To Send the Msg 
		message.setSubject("Complete Verification!!!! ");
		message.setText("Link "+"http://localhost:8080/userapi/validation/"+ token);
		return message;
	}
}

