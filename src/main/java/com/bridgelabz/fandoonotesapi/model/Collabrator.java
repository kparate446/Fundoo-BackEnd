package com.bridgelabz.fandoonotesapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "collabratorDetail")
public class Collabrator {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String mailReceiver;
	private String mailSender;
	@ManyToOne
	@JoinColumn(name = "notesId",nullable = false)
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
	public String getMailReceiver() {
		return mailReceiver;
	}
	public void setMailReceiver(String mailReceiver) {
		this.mailReceiver = mailReceiver;
	}
	public String getMailSender() {
		return mailSender;
	}
	public void setMailSender(String mailSender) {
		this.mailSender = mailSender;
	}
	@Override
	public String toString() {
		return "Collabrator [id=" + id + ", mailReceiver=" + mailReceiver + ", mailSender=" + mailSender + ", notes="
				+ notes + "]";
	}
}
