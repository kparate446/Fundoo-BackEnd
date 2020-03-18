package com.bridgelabz.fundoonotesapi.message;

import org.springframework.stereotype.Service;

@Service
public class MessageData {
//	private static final Logger LOGGER = Logger.getLogger(CollabratorServiceImp.class);
	
	/****************Status Exception************/
	public String validateUser = "Verified Email";	
	public String Bad_Request = "400";
	public String Success_Request = "200";
	public String Redirect_Request = "300";
	/***************Token Exception*************/
	public String Invalid_Token = "Invalid Token";
	public String Invalid_Password = "Invalid Password";
	public String Valid_Token = "Valid Token";
	public String Invalid_User = "Invalid user";
	public String Invalid_Note = "Note not present";
	public String userAlready_Present = "User are already present";
	public String Invalid_Order = "Please choose the correct Order";
	public String Invalid_Label = "Label Not Present";
	public String Invalid_Collabrator = "Invalid Collabrator";
	public String ReceiverMail_Already_Present = "User are already present";
	public String File_Not_Upload = " File Not Uploaded";
	public String File_Is_Empty = "File is empty";
	public String Reminder_Already_Present = "Reminder are already present";
	public String Invalid_Reminder = "Invalid Reminder";
	public String LabelAlready_Present = "Label Alredy Present";
	public String Invalid_Title = "Title Not Present";
}
