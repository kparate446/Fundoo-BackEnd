package com.bridgelabz.fandoonotesapi.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * @author :- Krunal Parate
 * @@Purpose :- Create the Notes POJO Class & Table
 *
 */
@Entity
@Table(name = "notesDetails")
// Egnoring the All User Data
@JsonIgnoreProperties({"user"}) 
public class Notes {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotEmpty
	private String title;
	@NotEmpty
	private String discription;
	private Date date = new Date();
	
	private boolean isTrash = false;
	private boolean isAchieve = false;
	private boolean isPin = false;
	
	public boolean isTrash() {
		return isTrash;
	}
	public void setTrash(boolean isTrash) {
		this.isTrash = isTrash;
	}
	public boolean isAchieve() {
		return isAchieve;
	}
	public void setAchieve(boolean isAchieve) {
		this.isAchieve = isAchieve;
	}
	public boolean isPin() {
		return isPin;
	}
	public void setPin(boolean isPin) {
		this.isPin = isPin;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	// Mapped Collabrator to Notes
	@OneToMany(mappedBy = "notes")
	private List<Collabrator>collabrators = new ArrayList<Collabrator>();
	public List<Collabrator> getCollabrators() {
		return collabrators;
	}
	public void setCollabrators(List<Collabrator> collabrators) {
		this.collabrators = collabrators;
	}
	// Mapped User to Notes
	@ManyToOne
	@JoinColumn(name = "userId",nullable = false)
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
	public Date getDate() {
		return date;
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
