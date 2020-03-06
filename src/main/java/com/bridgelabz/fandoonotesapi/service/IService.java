package com.bridgelabz.fandoonotesapi.service;
import javax.validation.Valid;

import com.bridgelabz.fandoonotesapi.dto.CreateNoteDto;
import com.bridgelabz.fandoonotesapi.dto.ForgotPasswordDTO;
import com.bridgelabz.fandoonotesapi.dto.LoginDTO;
import com.bridgelabz.fandoonotesapi.dto.RegistrationDTO;
import com.bridgelabz.fandoonotesapi.dto.ResetPasswordDTO;
import com.bridgelabz.fandoonotesapi.responce.Response;

public interface IService {
	String login(LoginDTO loginUser);
	void addUser(RegistrationDTO registrationDTO);
	Response validateUser(String token);
	Response forgetPassword(ForgotPasswordDTO forgotPasswordDTO);
	Response resetPassword(String token, ResetPasswordDTO resetPasswordDTO);
}