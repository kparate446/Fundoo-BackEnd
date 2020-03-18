package com.bridgelabz.fundoonotesapi.dto;

import javax.validation.constraints.NotEmpty;
/**
 * @author :- Krunal Parate
 * @Purpose :- Created DTO Class
 *
 */
public class CreateLabelDto {
	@NotEmpty
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
