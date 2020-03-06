package com.bridgelabz.fandoonotesapi.dto;

import javax.validation.constraints.NotEmpty;

public class UpdateNoteDto {
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
		return "UpdateNoteDto [ title=" + title + ", discription=" + discription + "]";
	}
}
