package com.bridgelabz.fundoonotesapi.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotesapi.dto.ForgotPasswordDTO;
import com.bridgelabz.fundoonotesapi.dto.LoginDTO;
import com.bridgelabz.fundoonotesapi.dto.RegistrationDTO;
import com.bridgelabz.fundoonotesapi.dto.ResetPasswordDTO;
import com.bridgelabz.fundoonotesapi.responce.Response;
import com.bridgelabz.fundoonotesapi.service.UserService;
/**
 * @author :- Krunal Parate
 * Purpose :- API Created
 */
@RestController // return the data converted JSON Automatically
@RequestMapping("/userapi")
public class UserController {
	@Autowired // Declaired the Dependency
	private UserService service;
	/**
	 * Purpose :-Registration Users
	 * @param registrationDTO
	 * @return :- Response
	 * @RequestBody -> Pass the JSON
	 */
	@PostMapping("/addusers")
	public ResponseEntity<String> addUser(@Valid @RequestBody RegistrationDTO registrationDTO) { 
		Response response = service.addUser(registrationDTO);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
	/**
	 *  Purpose :- Login Users
	 * @param loginDTO
	 * @return :- Response
	 */
	@PostMapping("/loginusers")
	public ResponseEntity<Response> loginUser(@Valid @RequestBody LoginDTO loginDTO) {
		Response response = service.login(loginDTO);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	/**
	 *  Purpose :- Validation Token
	 *  
	 * @param token :- Get the Token in User
	 * @return :- Response
	 */
	@PostMapping("/validation")
	public ResponseEntity<String> validation(@RequestHeader String token){
		Response response = service.validateUser(token);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
	/**
	 * Purpose :- Forgot Password
	 * @param forgotPasswordDTO :- Forgot Password
	 * @return :- Status
	 */
	@PostMapping("/forgotpassword")
	public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO){
		Response response = service.forgetPassword(forgotPasswordDTO);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
	/**
	 * Purpose :- Reset Password
	 * @param token :- Verified the Token
	 * @param resetPasswordDTO :- Access the resetPasswordDTO Data
	 * @return :- Response
	 */
	@PutMapping("/resetpassword")
	public ResponseEntity<String> resetpassword(@RequestHeader String token,ResetPasswordDTO resetPasswordDTO){
		Response response = service.resetPassword(token,resetPasswordDTO);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
	/**
	 * Purpose :- Getting All Users
	 * @return :- Response
	 */
	@GetMapping("/getusers")
	public ResponseEntity<Response> getUsers() 
	{
		Response response= service.getUsers();
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	/**
	 * Purpose :- Deleted Users
	 * @param token :-Verified the Token
	 * @param id :- Id of Person
	 * @return :- Response
	 */
	@DeleteMapping("/deleteuser/{userId}")
	public ResponseEntity<String> deleteUser(@RequestHeader String token,@PathVariable  int userId){
		Response response = service.deleteUsers(token,userId);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
	/**
	 * Purpose :- Signout the User
	 * @param token :- Verified the Token
	 * @return :- Response
	 */
	@PostMapping("/signout")
	public ResponseEntity<String> signoutUser(@Valid @RequestHeader String token) {
		Response response = service.signOut(token);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
	/**
	 * Purpose :- Uploaded the Profile Picture
	 * @param token :- Verified the Token
	 * @param file :- 
	 * @return :- Response
	 */
	@PostMapping("/uploadedProfile")
	public ResponseEntity<String> uploadedProfile(@RequestHeader String token,@RequestHeader MultipartFile file){
		Response response = service.uploadProfilePic(token, file);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
}
