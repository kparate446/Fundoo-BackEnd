package com.bridgelabz.fandoonotesapi.message;

import org.springframework.stereotype.Service;

@Service
public class MessageData {
	/****************Status Exception************/
	public String validateUser = "Verified Email";	
	public String Bad_Request = "400";
	public String Success_Request = "200";
	public String Redirect_Request = "300";
	/***************Token Exception*************/
	public String Invalid_Token = "Invalid Token";
	public String Invalid_Password = "Invalid Password";
	public String Valid_Token = "Valid Token";
	public String Invalid_User = "Invalid User";
	public String Invalid_Note = "Note Not Present";
	public String userAlready_Present = "User Are Already Present";
	public String Invalid_Order = "Please choose the Correct Order";
	public String Invalid_Label = "Label Not Present";
	public String Invalid_Collabrator = "Invalid Collabrator";
	public String ReceiverMail_Already_Present = "User Are Already Present";
}
