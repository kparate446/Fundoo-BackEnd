package com.bridgelabz.user.controller;
/**
 * @author :- krunal Parate
 * @Purpose :- Created the API
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.user.dto.LoginDTO;
import com.bridgelabz.user.dto.RegistrationDTO;
import com.bridgelabz.user.service.IService;
import com.bridgelabz.user.service.UserService;

@RestController // return the data converted JSON Automatically
@RequestMapping("/userapi")
public class UserController {
	@RequestMapping("/demo")
	public String Demo() {
		System.out.println("Hii");
		return "Hii" ;
	}
	@Autowired() // Declaired the Dependency
	private IService service;
	// method=RequestMethod --> Custom Property
	/** Registration*/
	@PostMapping("/addusers")
	public String addUser(@RequestBody RegistrationDTO registrationDTO) { // @RequestBody -> Pass the JSON
		service.addUser(registrationDTO);
		return "Registration Successfull";
	}
	/**Login*/
	@PostMapping("/loginusers")
	public String loginUser(@RequestBody LoginDTO loginDTO) {
		return service.login(loginDTO);		
	}
}
