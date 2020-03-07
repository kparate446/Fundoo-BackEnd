package com.bridgelabz.fandoonotesapi.service;
import com.bridgelabz.fandoonotesapi.dto.ForgotPasswordDTO;
import com.bridgelabz.fandoonotesapi.dto.LoginDTO;
import com.bridgelabz.fandoonotesapi.dto.RegistrationDTO;
import com.bridgelabz.fandoonotesapi.dto.ResetPasswordDTO;
import com.bridgelabz.fandoonotesapi.responce.Response;
/**
 * @author - Krunal Parate
 * Purpose - User Service Interface Created
 */
public interface UserService {
	Response getLabels();
	String login(LoginDTO loginUser);
	void addUser(RegistrationDTO registrationDTO);
	Response validateUser(String token);
	Response forgetPassword(ForgotPasswordDTO forgotPasswordDTO);
	Response deleteUsers(String token, int id);
	Response resetPassword(String token, ResetPasswordDTO resetPasswordDTO);
}