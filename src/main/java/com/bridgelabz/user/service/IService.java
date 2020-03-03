package com.bridgelabz.user.service;

import com.bridgelabz.user.dto.LoginDTO;
import com.bridgelabz.user.dto.RegistrationDTO;

public interface IService {
	String login(LoginDTO loginUser);
	void addUser(RegistrationDTO registrationDTO);
}