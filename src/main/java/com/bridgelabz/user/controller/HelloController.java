package com.bridgelabz.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@RequestMapping("/hello")
	public String Student() {
		System.out.println("krunal");
		return "Hii" ;
	}
}
