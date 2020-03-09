package com.bridgelabz.fandoonotesapi.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class CollabratorDto {
	@NotEmpty
	@Email(message = "Email should be valid")
	private String mailReceiver;
	
	public String getMailReceiver() {
		return mailReceiver;
	}
	public void setMailReceiver(String mailReceiver) {
		this.mailReceiver = mailReceiver;
	}
	@Override
	public String toString() {
		return "CollabratorDto [mailReceiver=" + mailReceiver + "]";
	}
}
