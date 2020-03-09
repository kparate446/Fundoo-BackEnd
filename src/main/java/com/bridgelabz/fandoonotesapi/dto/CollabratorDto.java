package com.bridgelabz.fandoonotesapi.dto;

public class CollabratorDto {
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
