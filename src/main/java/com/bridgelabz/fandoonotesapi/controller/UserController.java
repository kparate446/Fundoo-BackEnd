
package com.bridgelabz.fandoonotesapi.controller;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fandoonotesapi.dto.ForgotPasswordDTO;
import com.bridgelabz.fandoonotesapi.dto.LoginDTO;
import com.bridgelabz.fandoonotesapi.dto.RegistrationDTO;
import com.bridgelabz.fandoonotesapi.dto.ResetPasswordDTO;
import com.bridgelabz.fandoonotesapi.responce.Response;
import com.bridgelabz.fandoonotesapi.service.UserService;
/**
 * @author :- Krunal Parate
 * Purpose :- API Created By User Registration, User Login, Validation,Forgot Password,Reset Password.
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
	public String addUser(@Valid @RequestBody RegistrationDTO registrationDTO) { 
		service.addUser(registrationDTO);
		return "Registration Successfull";
	}
	/**
	 *  Purpose :- Login Users
	 * @param loginDTO
	 * @return :- Response
	 */
	@PostMapping("/loginusers")
	public String loginUser(@RequestBody LoginDTO loginDTO) {
		return service.login(loginDTO);		
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
	public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO){
		Response response = service.forgetPassword(forgotPasswordDTO);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
	/**
	 * Purpose :- Reset Password
	 * @param token :- Varified the Token
	 * @param resetPasswordDTO :- Access the resetPasswordDTO Data
	 * @return :- Response
	 */
	@PutMapping("/resetpassword")
	public ResponseEntity<String> resetpassword(@RequestHeader String token,ResetPasswordDTO resetPasswordDTO){
		Response response = service.resetPassword(token,resetPasswordDTO);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
}
