package com.bridgelabz.fandoonotesapi.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @Created By :- Krunal Parate
 * @Purpose :- Created the ResetPassword DTO Class
 */
public class ResetPasswordDTO {
	@NotEmpty
	@Size(min = 2, max = 30)
	private String password;
	@NotEmpty
	@Size(min = 2, max = 30)
	private String confirmpassword;

	public ResetPasswordDTO(String password, String confirmpassword) {
		this.password = password;
		this.confirmpassword = confirmpassword;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmpassword() {
		return confirmpassword;
	}
	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}
	@Override
	public String toString() {
		return "ResetPasswordDTO [password=" + password + ", confirmpassword=" + confirmpassword + "]";
	}
}
