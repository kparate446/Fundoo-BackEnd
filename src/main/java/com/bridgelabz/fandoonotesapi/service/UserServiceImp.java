package com.bridgelabz.fandoonotesapi.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fandoonotesapi.configuration.PasswordConfiguration;
import com.bridgelabz.fandoonotesapi.dto.ForgotPasswordDTO;
import com.bridgelabz.fandoonotesapi.dto.LoginDTO;
import com.bridgelabz.fandoonotesapi.dto.RegistrationDTO;
import com.bridgelabz.fandoonotesapi.dto.ResetPasswordDTO;
import com.bridgelabz.fandoonotesapi.exception.FileIsEmpty;
import com.bridgelabz.fandoonotesapi.exception.FileNotUploadedException;
import com.bridgelabz.fandoonotesapi.exception.InvalidPasswordException;
import com.bridgelabz.fandoonotesapi.exception.InvalidUserException;
import com.bridgelabz.fandoonotesapi.exception.UserAlreadyPresentException;
import com.bridgelabz.fandoonotesapi.message.MessageData;
import com.bridgelabz.fandoonotesapi.message.MessageResponse;
import com.bridgelabz.fandoonotesapi.model.User;
import com.bridgelabz.fandoonotesapi.repository.UserRepository;
import com.bridgelabz.fandoonotesapi.responce.Response;
import com.bridgelabz.fandoonotesapi.utility.EmailSenderService;
import com.bridgelabz.fandoonotesapi.utility.JwtToken;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
/**
 * @Created By :- krunal Parate
 * @Purpose :- Implement the Login,Registration,Forgot & ResetPassword
 */
@Service // Injecting the other class
public class UserServiceImp implements UserService {
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private UserRepository userRepository;
	/*
	 * // @Autowired private JavaMailSender javaMailSender;
	 */
	@Autowired
	private EmailSenderService emailSenderService;
	@Autowired
	private MessageData messageData;
	@Autowired
	private MessageResponse messageResponse;
	// SimpleMailMessage--> InBuild Class
	SimpleMailMessage email;
	@Autowired
	private JwtToken jwtToken;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	PasswordConfiguration passConfig;

	String message;

	/** Registration For User	 */
	public Response addUser(RegistrationDTO registrationDTO) {
		User checkEmail = userRepository.findByEmail(registrationDTO.getEmail());
		// User is Already Present or Not
		if(checkEmail !=null) 
			throw new UserAlreadyPresentException(messageData.userAlready_Present);
		User user = mapper.map(registrationDTO, User.class);
		System.out.println(registrationDTO.getEmail());
		String token = jwtToken.generateToken(user.getEmail());
		System.out.println(token);
		email = messageResponse.verifyMail(user.getEmail(), user.getFirstName(), token);
		emailSenderService.sendEmail(email);
		// Set Encoded the password
		user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
		userRepository.save(user);
		return new Response(200, "Registration Successfull", token);
		/*
		 * javaMailSender .send(MessageResponse.verifyMail(user.getEmail(),
		 * user.getFirstName(), token));
		 */
	}

	/** Verification Mail */
	@Override
	public Response validateUser(String token) {
		String email = jwtToken.getToken(token);
		User user = userRepository.findByEmail(email);
		if (user == null) {
			System.out.println("User Not Exit");
			throw new InvalidUserException(messageData.Invalid_User);
		} else
			user.setValidate(true);
		userRepository.save(user);
		return new Response(200, "Validation", token);
	}

	/** Login User */
	public Response login(LoginDTO loginUser) {
		// It is used in Mapped the Data-->JPA
		User user = userRepository.findByEmail(loginUser.getEmail());
		// To generate the Token
		String token = jwtToken.generateToken(loginUser.getEmail());
		// Print Token in Console
		System.out.println(token);
		if (user == null)
			throw new InvalidUserException(messageData.Invalid_User);
		if (user.isValidate()) {
			System.out.println(passConfig.encoder());
			// decoded the password and compaired
			if (passConfig.encoder().matches(loginUser.getPassword(),user.getPassword())) {
				if (user.isSignOut() == false) {
					email = messageResponse.verifyMail(user.getEmail(), user.getFirstName(), token);
					emailSenderService.sendEmail(email);
					user.setSignOut(true);
					userRepository.save(user);
					return new Response(200, "login success", token);
				}else {
					return new Response(200, "User Already login", "Logout");
				}
			} else {
				throw new InvalidPasswordException(messageData.Invalid_Password);
			}
		} else
			throw new InvalidUserException(messageData.Invalid_User);
	}

	/** Forgot Password */
	public Response forgetPassword(ForgotPasswordDTO forgotPasswordDTO) {
		User user = userRepository.findByEmail((forgotPasswordDTO.getEmail()));
		String token = jwtToken.generateToken(forgotPasswordDTO.getEmail());
		System.out.println(token);
		System.out.println(user.getPassword());
		System.out.println(forgotPasswordDTO.getEmail());
		email = messageResponse.verifyMail(user.getEmail(), user.getFirstName(), token);
		emailSenderService.sendEmail(email);
		userRepository.save(user);
		return new Response(200, "Validation", token);
	}

	/** Reset Password */
	@Override
	public Response resetPassword(String token, ResetPasswordDTO resetPasswordDTO) {
		String email1 = jwtToken.getToken(token);
		User user = userRepository.findByEmail(email1);
		if (user == null) {
			System.out.println("User Not Exit");
			throw new InvalidUserException(messageData.Invalid_User);
		} else if (resetPasswordDTO.getPassword().equals(resetPasswordDTO.getConfirmpassword())) {
			user.setPassword(resetPasswordDTO.getPassword());
			userRepository.save(user);
			return new Response(200, "Password Reset Successfully", token);
		} else {
			throw new InvalidPasswordException(messageData.Invalid_Password);
		}
	}

	/** Getting All Users */
	public Response getUsers() {
		if (userRepository.findAll() == null) {
			return new Response(200, "Password Reset Successfully", "No User");
		} else {
			List<User> users = userRepository.findAll();
			return new Response(200, "Show All Users ", users);
		}
	}

	/** Deleted Users */
	public Response deleteUsers(String token, int id) {
		User user;
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		System.out.println(token);
		if (user == null) {
			throw new InvalidUserException(messageData.Invalid_User);
		}
		// else if(user.getNotes()!=null)
		if (id == user.getId()) {
			userRepository.deleteById(id);
			return new Response(200, "Deleted User Successfully", token);
		} else {
			throw new InvalidUserException(messageData.Invalid_User);
		}
	}
	/** User Signout*/
	public Response signOut(String token) {
		String email = jwtToken.getToken(token);
		User user = userRepository.findByEmail(email);
		// To generate the Token
		System.out.println(token);
		if (user == null)
			throw new InvalidUserException(messageData.Invalid_User);
		if (user.isValidate()) {
			// Check the user login or not
			if (user.isSignOut() == true) {
				user.setSignOut(false);
				userRepository.save(user);
				return new Response(200, "logout success", "Logout");
			}
			else {
				return new Response(200, "User Already logout", "Logout");
			}
		}
		throw new InvalidUserException(messageData.Invalid_User);
	} 
	public Response uploadProfilePic(String token, MultipartFile file) {
		String email = jwtToken.getToken(token);
		// check whether user is present or not
		User user = userRepository.findByEmail(email);
		System.out.println(file);
		if (user == null)
			throw new InvalidUserException(messageData.Invalid_User);
		// file is not selected to upload
		if (file.isEmpty())
			throw new FileIsEmpty(messageData.File_Is_Empty);

		File uploadFile = new File(file.getOriginalFilename());
		try {
			// save the file backup
			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(uploadFile));
			try {
				outputStream.write(file.getBytes());
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// Connection of Closet cloudinary properties
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", "dg7m3whxu", "api_key",
				"662363956759336", "api_secret", "znXxkQ9ODQo-Z6FjiW8lyZ0JP5A"));
		Map<?, ?> uploadProfile;
		try {
			// this upload the image on cloudinary ->Query -> Mapped
			uploadProfile = cloudinary.uploader().upload(uploadFile, ObjectUtils.emptyMap());
		} catch (IOException e) {
			throw new FileNotUploadedException(messageData.File_Not_Upload);
		}
		// Set profile picture in Url type in table
		user.setProfilePic(uploadProfile.get("secure_url").toString());
		userRepository.save(user);
		return new Response(200, "Uploaded Pic Successfully", token);
	}
}
