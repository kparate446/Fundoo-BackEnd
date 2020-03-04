package com.bridgelabz.user.service;
/**
 * @Created By :- krunal Parate
 * @Purpose :- Indterface of Service Method
 */
import com.bridgelabz.user.dto.LoginDTO;
import com.bridgelabz.user.dto.RegistrationDTO;
import com.bridgelabz.user.responce.Response;

public interface IService {
	String login(LoginDTO loginUser);
	void addUser(RegistrationDTO registrationDTO);
	/** Varification Mail */
	Response validateUser(String token);
}