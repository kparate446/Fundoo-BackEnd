/**
 * @Created By :- krunal Parate
 * @Purpose :- Implement the Method
 */
package com.bridgelabz.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import com.bridgelabz.user.dto.LoginDTO;
import com.bridgelabz.user.dto.RegistrationDTO;
import com.bridgelabz.user.message.MessageData;
import com.bridgelabz.user.message.MessageResponse;
import com.bridgelabz.user.model.User;
import com.bridgelabz.user.repository.UserRepository;
import com.bridgelabz.user.responce.Response;
import com.bridgelabz.user.utility.EmailSenderService;
import com.bridgelabz.user.utility.JwtToken;

@Service // Injecting the other class
public class UserService implements IService {
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private UserRepository userRepository;
	/*
	 * // @Autowired private JavaMailSender javaMailSender;
	 */
	@Autowired
	private EmailSenderService emailSenderService;
//	@Autowired
	private MessageData messageData;
	@Autowired

	private MessageResponse messageResponse;
	// SimpleMailMessage--> InBuild Class
	SimpleMailMessage email;
	@Autowired
	private JwtToken jwtToken;
	String message;

	/** Login User */
	public String login(LoginDTO loginUser) {
		// It is used in Mapped the Data-->JPA
		User user = userRepository.findByemail(loginUser.getEmail());
		// To generate the Token
		String token = jwtToken.generateToken(loginUser.getEmail());
		System.out.println(user.getPassword());
		System.out.println(loginUser.getPassword());
		// Print Token in Console
		System.out.println(token);
		if (user != null) {
			if (user.isValidate()!= false) {
				if (!user.getPassword().equals(loginUser.getPassword())) {
					return message = "invalid password";
				} else {
					email = messageResponse.verifyMail(user.getEmail(), user.getFirstName(), token);
					emailSenderService.sendEmail(email);
					userRepository.save(user);
					return message = "login success";
				}
			}
		}
		return message;
	}

	/** Varification Mail */
	@Override
	public Response validateUser(String token) {
		String email = jwtToken.getToken(token);
		User user = userRepository.findByemail(email);
		if (user == null)
			System.out.println("User Not Exit");
		user.setValidate(true);
		userRepository.save(user);
		return new Response(200, "Validation", messageData.validateUser);
	}

	/** Registration For User */
	public void addUser(RegistrationDTO registrationDTO) {
		User user = mapper.map(registrationDTO, User.class);
		System.out.println("USER CLASS " + user);
//		userRepository.save(user); // Add the Data
		String token = jwtToken.generateToken(user.getEmail());
		System.out.println(token);
//		userRepository.save(user);
		/*
		 * javaMailSender .send(MessageResponse.verifyMail(user.getEmail(),
		 * user.getFirstName(), token));
		 */
		email = messageResponse.verifyMail(user.getEmail(), user.getFirstName(), token);
		emailSenderService.sendEmail(email);
		userRepository.save(user);
	}
}
