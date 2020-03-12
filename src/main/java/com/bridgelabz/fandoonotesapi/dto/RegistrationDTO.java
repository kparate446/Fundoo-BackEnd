package com.bridgelabz.fandoonotesapi.dto;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
/**
 * @Created By :- Krunal Parate
 * @Purpose :- Created the Registartion DTO Class
 */
public class RegistrationDTO  {
	@Valid
	@NotEmpty(message = "Please provide a firstName")
	private String firstName;
	@NotEmpty(message = "Please provide a lastName")
	private String lastName;
	@NotEmpty
	@Email(message = "Email should be valid")
	private String email;
	@NotEmpty
	@Size(min = 2, max = 30)
	private String password;
//	@NotBlank(message = "Please provide a Mobile Number")
//	/*@Size(min=0,max=10)*/
//	@Pattern(regexp = "^[0-9]*$")
	private long phoneNo;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(long phoneNo) {
		this.phoneNo = phoneNo;
	}
	@Override
	public String toString() {
		return "RegistrationDTO [firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", password=" + password + ", phoneNo=" + phoneNo + "]";
	}
}
