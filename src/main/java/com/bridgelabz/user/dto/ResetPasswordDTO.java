package com.bridgelabz.user.dto;
/**
 * @Created By :- krunal Parate
 * @Purpose :- Created the ResetPassword DTO Class
 */
public class ResetPasswordDTO {
	private String password;
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
