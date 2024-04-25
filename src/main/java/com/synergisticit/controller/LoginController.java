package com.synergisticit.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

	/*
	http://localhost:8081/robinApp/home
	http://localhost:8081/robinApp/
	*/
	@RequestMapping({"/","/home"})
	public String home() {
		return "home";
	}
	
	@RequestMapping("login")
	public String login(@RequestParam(value="error", required=false) String error,
			@RequestParam(value="logout", required=false) String logout,
			HttpServletRequest req, HttpServletResponse res, Model model
			
			) {
		
		System.out.println("LoginController-login...");
		String message = null;
		
		if(logout != null) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if(auth != null) {
				SecurityContextLogoutHandler sclh = new SecurityContextLogoutHandler();
				sclh.logout(req, res, auth);
				//message="You are logged out.";
				message="You can login to the application with appropraite credentials.";
			}
		}
		
		if(error != null) {
			message="Either username or password is incorrect.";
		}
		model.addAttribute("message", message);
		return "loginForm";
	}
	
	@RequestMapping("/accessDenied")
	public String accessDenied() {
		return "accessDenied";
	}
	
}