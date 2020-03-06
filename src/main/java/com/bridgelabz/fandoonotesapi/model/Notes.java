package com.bridgelabz.fandoonotesapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
/**
 * @author :- Krunal Parate
 * @@Purpose :- Create the Notes POJO Class & Table
 *
 */
@Entity
@Table(name = "notesDetails")
public class Notes {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotEmpty
	private String title;
	@NotEmpty
	private String discription;
	
	@ManyToOne
	@JoinColumn(name = "user",nullable = false)
	private User user;
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	@Override
	public String toString() {
		return "Notes [id=" + id + ", title=" + title + ", discription=" + discription + "]";
	}
}
