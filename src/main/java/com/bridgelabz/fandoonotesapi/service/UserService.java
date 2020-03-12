package com.bridgelabz.fandoonotesapi.service;
import org.springframework.web.multipart.MultipartFile;

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
	Response getUsers();
	Response login(LoginDTO loginUser);
	Response addUser(RegistrationDTO registrationDTO);
	Response validateUser(String token);
	Response forgetPassword(ForgotPasswordDTO forgotPasswordDTO);
	Response deleteUsers(String token, int id);
	Response resetPassword(String token, ResetPasswordDTO resetPasswordDTO);
	Response signOut(String token);
	Response uploadProfilePic(String token, MultipartFile file);
}