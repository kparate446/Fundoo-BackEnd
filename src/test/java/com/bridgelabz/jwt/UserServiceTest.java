package com.bridgelabz.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.bridgelabz.fundoonotesapi.dto.LoginDTO;
import com.bridgelabz.fundoonotesapi.dto.RegistrationDTO;
import com.bridgelabz.fundoonotesapi.message.MessageData;
import com.bridgelabz.fundoonotesapi.model.User;
import com.bridgelabz.fundoonotesapi.repository.UserRepository;
import com.bridgelabz.fundoonotesapi.responce.Response;
import com.bridgelabz.fundoonotesapi.service.UserServiceImp;
import com.bridgelabz.fundoonotesapi.utility.JwtToken;

class UserServiceTest {
	@InjectMocks
	private UserServiceImp userServiceImp;
	@Mock
	private UserRepository userRepository;
	@Mock
	PasswordEncoder passwordEncoder;
	@Mock
	private ModelMapper mapper;
	@Mock
	private JwtToken jwtToken;
	@Mock
	MessageData messageData;
	@Mock
	private LoginDTO loginDTO;
	/**
	 * Testing of user Registration method
	 */
	@Test
	public void registerTest() {
		RegistrationDTO registrationDTO  = new RegistrationDTO();
		User user =new User();
		registrationDTO.setEmail("kparate16@gmail.com");
		registrationDTO.setFirstName("Krunal");
		registrationDTO.setLastName("Parate");
		registrationDTO.setPassword("kunal");
		registrationDTO.setPhoneNo(986523122);
		Optional<User> already = Optional.of(user);
		if(already.isPresent()){
			return;
		}
		when(mapper.map(registrationDTO, User.class)).thenReturn(userRepository.findByEmail(registrationDTO.getPassword()));
		user.setPassword("password");
		when(userRepository.save(user)).thenReturn(new User());
		Response resp = userServiceImp.addUser(registrationDTO);
		Object email = resp.getData();
		assertEquals("kparate16@gmail.com", email);	
	}
	/**
	 * Testing of user Login method
	 */
	@Test
	public void loginTest() {
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setEmail("kparate16@gmail.com");
		loginDTO.setPassword("kunal");
		try {
			when(passwordEncoder.matches(Mockito.<String>any(),Mockito.<String>any())).thenReturn(false);
			when(jwtToken.getToken("a")).thenReturn(Mockito.<String>any());
			Response resp = userServiceImp.login(loginDTO);
			Object email = resp.getData();
			assertEquals(email, "kparate16@gmail.com");
		}catch(Exception e) {
		e.printStackTrace();
		}
	}
}
