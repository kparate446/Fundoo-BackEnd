package com.bridgelabz.fandoonotesapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "collabratorDetails")
public class Collabrator {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String mailReceiver;
	private String mailSender;
	
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
		return "Collaborator [id=" + id + ", mailReceiver=" + mailReceiver + ", mailSender=" + mailSender + "]";
	}
}
