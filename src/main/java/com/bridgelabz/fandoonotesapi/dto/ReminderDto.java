package com.bridgelabz.fandoonotesapi.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ReminderDto {
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
	private Date dateAndTime;

	public Date getDateAndTime() {
		return dateAndTime;
	}
	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
}
