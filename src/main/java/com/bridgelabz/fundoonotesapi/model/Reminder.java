package com.bridgelabz.fundoonotesapi.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "reminderDetails")
@JsonIgnoreProperties({"notes"}) 
public class Reminder {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
//	private Date date = new Date();5/14/2020, 1:52:00 PM
//	@DateTimeFormat(pattern = "MM/dd/yyyy hh:mm:ss aa")
	private Date dateAndTime;
	
	@OneToOne
	@JoinColumn(name = "noteId",nullable = false)
	private Notes notes;
	
	public Notes getNotes() {
		return notes;
	}
	public void setNotes(Notes notes) {
		this.notes = notes;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDateAndTime() {
		return dateAndTime;
	}
	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
}
