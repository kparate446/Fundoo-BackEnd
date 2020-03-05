/**
 * @Created By :- krunal Parate
 * @Purpose :- Created the API
 */
package com.bridgelabz.user.controller;
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
import com.bridgelabz.user.dto.ForgotPasswordDTO;
import com.bridgelabz.user.dto.LoginDTO;
import com.bridgelabz.user.dto.RegistrationDTO;
import com.bridgelabz.user.dto.ResetPasswordDTO;
import com.bridgelabz.user.responce.Response;
import com.bridgelabz.user.service.IService;

@RestController // return the data converted JSON Automatically
@RequestMapping("/userapi")
public class UserController {
	@Autowired // Declaired the Dependency
	private IService service;
	/**
	 * Purpose :-Registration Users
	 * @param registrationDTO
	 * @return
	 * @RequestBody -> Pass the JSON
	 */
	@PostMapping("/addusers")
	public String addUser(@Valid @RequestBody RegistrationDTO registrationDTO) { 
		service.addUser(registrationDTO);
		return "Registration Successfull";
	}
	/**Login*/
	/**
	 *  Purpose :- Login Users
	 * @param loginDTO
	 * @return
	 */
	@PostMapping("/loginusers")
	public String loginUser(@RequestBody LoginDTO loginDTO) {
		return service.login(loginDTO);		
	}
	/**
	 *  Purpose :- Validation Token
	 *  
	 * @param token :-
	 * @return :- 
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
	 * @return
	 */
	@PutMapping("/resetpassword")
	public ResponseEntity<String> resetpassword(@RequestHeader String token,ResetPasswordDTO resetPasswordDTO){
		Response response = service.resetPassword(token,resetPasswordDTO);
		return new ResponseEntity<String>(response.getMessage(),HttpStatus.OK);
	}
}
