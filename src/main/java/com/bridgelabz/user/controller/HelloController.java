package com.bridgelabz.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author admin1 :- Demo API Created
 *
 */
@RestController
public class HelloController {
	@RequestMapping("/hello")
	public String Student() {
		System.out.println("krunal");
		return "Hii" ;
	}
}
