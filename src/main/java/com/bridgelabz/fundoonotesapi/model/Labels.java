package com.bridgelabz.fundoonotesapi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author :- Krunal Parate
 * Purpose :- labelDetails Table Created 
 */
@Entity
@Table(name = "labelDetails")
@JsonIgnoreProperties({"user","noteList"}) 

public class Labels {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotEmpty
	private String labelName;
	// Mapping with User to Labels
	@ManyToOne
	@JoinColumn(name = "userId",nullable = false)
	private User user;
	// Mapping with Notes to Labels
	@ManyToMany
	@JoinTable(name = "NoteLabelList",joinColumns = @JoinColumn(referencedColumnName = "id") )
	private List<Notes> noteList = new ArrayList<>();

	
	public List<Notes> getNoteList() {
		return noteList;
	}
	public void setNoteList(List<Notes> noteList) {
		this.noteList = noteList;
	}
	
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
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	@Override
	public String toString() {
		return "Lable [id=" + id + ", labelName=" + labelName + "]";
	}
}