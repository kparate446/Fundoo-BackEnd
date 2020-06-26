package com.bridgelabz.fundoonotesapi.service;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotesapi.dto.ForgotPasswordDTO;
import com.bridgelabz.fundoonotesapi.dto.LoginDTO;
import com.bridgelabz.fundoonotesapi.dto.RegistrationDTO;
import com.bridgelabz.fundoonotesapi.dto.ResetPasswordDTO;
import com.bridgelabz.fundoonotesapi.responce.Response;
/**
 * @author - Krunal Parate
 * Purpose - User Service Interface Created
 */
public interface UserService {
	Response getUsers();
	Response login(LoginDTO loginUser);
	Response addUser(RegistrationDTO registrationDTO);
	Response validateUser(String token);
	Response forgotPassword(ForgotPasswordDTO forgotPasswordDTO,HttpServletRequest request);
	Response deleteUsers(String token, int id);
	Response resetPassword(String token, ResetPasswordDTO resetPasswordDTO);
	Response signOut(String token);
	Response uploadProfilePic(String token, MultipartFile file);
}