package com.bridgelabz.user.model;
/**
 * @author :- krunal Parate
 * @Purpose :- Create the POJO Class & Table
 */
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "userDetails")
public class User {
	@Id
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private long phoneNo;
	private boolean isValidate=false;

	public boolean isValidate() {
		return isValidate;
	}
	public void setValidate(boolean isValidate) {
		this.isValidate = isValidate;
	}
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
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password=" + password
				+ ", phoneNo=" + phoneNo + ", isValidate=" + isValidate + "]";
	}	
}





