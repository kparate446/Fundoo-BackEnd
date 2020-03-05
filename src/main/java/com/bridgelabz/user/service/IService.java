package com.bridgelabz.user.service;
import com.bridgelabz.user.dto.ForgotPasswordDTO;
/**
 * @Created By :- krunal Parate
 * @Purpose :- Indterface of Service Method
 */
import com.bridgelabz.user.dto.LoginDTO;
import com.bridgelabz.user.dto.RegistrationDTO;
import com.bridgelabz.user.dto.ResetPasswordDTO;
import com.bridgelabz.user.responce.Response;

public interface IService {
	String login(LoginDTO loginUser);
	void addUser(RegistrationDTO registrationDTO);
	Response validateUser(String token);
	Response forgetPassword(ForgotPasswordDTO forgotPasswordDTO);
	Response resetPassword(String token, ResetPasswordDTO resetPasswordDTO);
}