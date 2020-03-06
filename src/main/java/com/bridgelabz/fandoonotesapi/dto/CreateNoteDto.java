package com.bridgelabz.fandoonotesapi.dto;

import javax.validation.constraints.NotEmpty;

public class CreateNoteDto {
	@NotEmpty
	private String title;
	@NotEmpty
	private String discription;
	
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
		return "CreateNote [title=" + title + ", discription=" + discription + "]";
	}
}
