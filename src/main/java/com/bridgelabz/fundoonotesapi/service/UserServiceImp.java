package com.bridgelabz.fundoonotesapi.service;

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
import com.bridgelabz.fundoonotesapi.configuration.PasswordConfiguration;
import com.bridgelabz.fundoonotesapi.dto.ForgotPasswordDTO;
import com.bridgelabz.fundoonotesapi.dto.LoginDTO;
import com.bridgelabz.fundoonotesapi.dto.RegistrationDTO;
import com.bridgelabz.fundoonotesapi.dto.ResetPasswordDTO;
import com.bridgelabz.fundoonotesapi.exception.FileIsEmpty;
import com.bridgelabz.fundoonotesapi.exception.FileNotUploadedException;
import com.bridgelabz.fundoonotesapi.exception.InvalidPasswordException;
import com.bridgelabz.fundoonotesapi.exception.InvalidUserException;
import com.bridgelabz.fundoonotesapi.exception.UserAlreadyPresentException;
import com.bridgelabz.fundoonotesapi.message.MessageData;
import com.bridgelabz.fundoonotesapi.message.MessageResponse;
import com.bridgelabz.fundoonotesapi.model.User;
import com.bridgelabz.fundoonotesapi.repository.UserRepository;
import com.bridgelabz.fundoonotesapi.responce.Response;
import com.bridgelabz.fundoonotesapi.utility.EmailSenderService;
import com.bridgelabz.fundoonotesapi.utility.JwtToken;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.sun.istack.logging.Logger;
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
	 private static final Logger LOGGER = Logger.getLogger(UserServiceImp.class);

	/** Registration For User	 */
	public Response addUser(RegistrationDTO registrationDTO) {
		User checkEmail = userRepository.findByEmail(registrationDTO.getEmail());
		// User is Already Present or Not
		if(checkEmail !=null) {
			LOGGER.warning("User are already present");
			throw new UserAlreadyPresentException(messageData.userAlready_Present);
		}
		User user = mapper.map(registrationDTO, User.class);
		System.out.println(registrationDTO.getEmail());
		String token = jwtToken.generateToken(user.getEmail());
		System.out.println(token);
		email = messageResponse.verifyMail(user.getEmail(), user.getFirstName(), token);
		emailSenderService.sendEmail(email);
		// Set Encoded the password
		user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
		userRepository.save(user);
		LOGGER.info("Registration Successfull in User Table");
		return new Response(200, "Registration Successfull", token);
	}

	/** Verification Mail */
	@Override
	public Response validateUser(String token) {
		String email = jwtToken.getToken(token);
		User user = userRepository.findByEmail(email);
		if (user == null) {
			System.out.println("User Not Exit");
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		} else
			user.setValidate(true);
		userRepository.save(user);
		LOGGER.info("Successfull verified the user");
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
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (user.isValidate()) {
			// decoded the password and compaired
			if (passConfig.encoder().matches(loginUser.getPassword(),user.getPassword())) {
				if (user.isSignOut() == false) {
					email = messageResponse.verifyMail(user.getEmail(), user.getFirstName(), token);
					emailSenderService.sendEmail(email);
					user.setSignOut(true);
					userRepository.save(user);
					LOGGER.info("Login Successfull");
					return new Response(200, "login success", token);
				}else {
					LOGGER.info("User Already login");
					return new Response(200, "User Already login", "LOGIN");
				}
			} else {
				LOGGER.warning("Invalid Password");
				throw new InvalidPasswordException(messageData.Invalid_Password);
			}
		} else
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
	}

	/** Forgot Password */
	public Response forgetPassword(ForgotPasswordDTO forgotPasswordDTO) {
		User user = userRepository.findByEmail((forgotPasswordDTO.getEmail()));
		String token = jwtToken.generateToken(forgotPasswordDTO.getEmail());
		email = messageResponse.verifyMail(user.getEmail(), user.getFirstName(), token);
		emailSenderService.sendEmail(email);
		userRepository.save(user);
		System.out.println(token);
		LOGGER.info("Sent the token in mail");
		return new Response(200, "Validation", token);
	}

	/** Reset Password */
	@Override
	public Response resetPassword(String token, ResetPasswordDTO resetPasswordDTO) {
		String email1 = jwtToken.getToken(token);
		User user = userRepository.findByEmail(email1);
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		} else if (resetPasswordDTO.getPassword().equals(resetPasswordDTO.getConfirmpassword())) {
			user.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));
			userRepository.save(user);
			LOGGER.info("Password reset successfully");
			return new Response(200, "Password Reset Successfully", true);
		} else {
			LOGGER.warning("Invalid Password");
			throw new InvalidPasswordException(messageData.Invalid_Password);
		}
	}

	/** Getting All Users */
	public Response getUsers() {
		if (userRepository.findAll() == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		} else {
			List<User> users = userRepository.findAll();
			LOGGER.info("Successfully showing the User table data");
			return new Response(200, "Show All Users ", users);
		}
	}

	/** Deleted Users */
	public Response deleteUsers(String token, int id) {
		User user;
		String email = jwtToken.getToken(token);
		user = userRepository.findByEmail(email);
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (id == user.getId()) {
			userRepository.deleteById(id);
			LOGGER.info("Successfully user deleted");
			return new Response(200, "Deleted User Successfully", true);
		} else {
			throw new InvalidUserException(messageData.Invalid_User);
		}
	}
	/** User Signout*/
	public Response signOut(String token) {
		String email = jwtToken.getToken(token);
		User user = userRepository.findByEmail(email);
		// To generate the Token
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		}
		if (user.isValidate()) {
			// Check the user login or not
			if (user.isSignOut() == true) {
				user.setSignOut(false);
				userRepository.save(user);
				LOGGER.info("Successfully User logout");
				return new Response(200, "logout success", "Logout");
			}
			else {
				LOGGER.warning("User Already logout");
				return new Response(400, "User Already logout", false);
			}
		}
		LOGGER.warning("Invalid user");
		throw new InvalidUserException(messageData.Invalid_User);
	} 
	/** Uploaded the Profile Pic*/
	public Response uploadProfilePic(String token, MultipartFile file) {
		String email = jwtToken.getToken(token);
		// check whether user is present or not
		User user = userRepository.findByEmail(email);
		if (user == null) {
			LOGGER.warning("Invalid user");
			throw new InvalidUserException(messageData.Invalid_User);
		}
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
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", "dmlqjysiv", "api_key",
				"242443158528625", "api_secret", "q9p9sxtwVI-kSM5CVt-Yrc4_B0c"));
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
		LOGGER.info("Successfully uploaded the profile picture");
		return new Response(200, "Uploaded Profile picture Successfully", true);
	}
}

