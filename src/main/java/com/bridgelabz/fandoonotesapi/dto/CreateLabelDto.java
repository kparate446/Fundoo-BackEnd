package com.bridgelabz.fandoonotesapi.dto;
/**
 * @author :- Krunal Parate
 * @Purpose :- Created DTO Class
 *
 */
public class CreateLabelDto {
	private String labelName;

	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	@Override
	public String toString() {
		return "CreateLabelDto [labelName=" + labelName + "]";
	}
}
