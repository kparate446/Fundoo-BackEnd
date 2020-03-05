package com.bridgelabz.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
/**
 * @Created By :- Krunal Parate
 * @Purpose :- Created the ForgotPassword DTO Class
 */
public class ForgotPasswordDTO {
	@NotEmpty
	@Email(message = "Email should be valid")
	private String email;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "ForgotPasswordDTO [email=" + email + "]";
	}
}
