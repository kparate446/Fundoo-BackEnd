package com.bridgelabz.fundoonotesapi.dto;

//import javax.validation.constraints.NotEmpty;

public class CreateNoteDto {
//	@NotEmpty
	private String title;
//	@NotEmpty
	private String discription;

	private String color;
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
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
		return "CreateNoteDto [title=" + title + ", discription=" + discription + ", color=" + color + "]";
	}
}
