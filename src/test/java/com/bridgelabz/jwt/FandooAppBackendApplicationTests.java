package com.bridgelabz.jwt;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.bridgelabz.fandoonotesapi.model.User;
import com.bridgelabz.fandoonotesapi.repository.UserRepository;
import com.bridgelabz.fandoonotesapi.service.UserService;

@SpringBootTest
class FandooAppBackendApplicationTests {
	@Autowired
	private UserService userService;
	@MockBean
	private UserRepository userRepository;
	@Test
	public void loginTest() {
		when(userRepository.findAll()).thenReturn(Stream.of(new User(28,"2020-03-05 17:53:12.649","vikkyghate12@gmail.com","Vikky",1,"Ghate","vikky777",9856321256)));
	}
	@Test
	void contextLoads() {
		 
	}

}
