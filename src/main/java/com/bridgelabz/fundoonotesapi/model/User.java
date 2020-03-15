package com.bridgelabz.fundoonotesapi.model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/**
 * @author :- Krunal Parate
 * Purpose :- User POJO Class & userInformation Table Created
 */
@Entity
@Table(name = "userDetails")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Date date = new Date();
	private long phoneNo;
	private String profilePic;
	private boolean isValidate = false;
	private boolean signOut = false;
	
	@OneToMany(mappedBy = "user")
	private List<Labels>labels = new ArrayList<Labels>();
	public List<Labels> getLabels() {
		return labels;
	}
	public void setLabels(List<Labels> labels) {
		this.labels = labels;
	}

	@OneToMany(mappedBy = "user")
	private List<Notes> notes = new ArrayList<Notes>();

	public List<Notes> getNotes() {
		return notes;
	}
	
	public void setNotes(List<Notes> notes) {
		this.notes = notes;
	}
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	
	public boolean isSignOut() {
		return signOut;
	}
	public void setSignOut(boolean signOut) {
		this.signOut = signOut;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
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
	public void setEmail( String email) {
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
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", date=" + date + ", phoneNo=" + phoneNo + ", isValidate=" + isValidate
				+ "]";
	}
	
}
