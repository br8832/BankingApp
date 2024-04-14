package com.synergisticit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.synergisticit.validation.UserValidator;
import com.synergisticit.domain.Role;
import com.synergisticit.domain.User;
import com.synergisticit.service.RoleService;
import com.synergisticit.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired UserService userService;
	@Autowired RoleService roleService;
	@Autowired UserValidator validator;
	
	@RequestMapping({"/form","","/"})
	public String userForm(User user, Model model) {
		model.addAttribute("users", userService.findAll());
		model.addAttribute("roles", roleService.findAll());
		return "userForm";
	}
	
	
	@RequestMapping("saveUser")
	public String savesTheUser( @ModelAttribute User user, Model model, BindingResult br) {
		validator.validate(user, br);
		System.out.println("br.hasErrors(): "+br.hasErrors());
		System.out.println("User e: "+user.toString());
		model.addAttribute("users", userService.findAll());
		model.addAttribute("roles", roleService.findAll());
		if(br.hasErrors()) {
			System.out.println(br.getAllErrors());
			model.addAttribute("hasErrors",br.hasErrors());
			model.addAttribute("email",br.getFieldError("email"));
			return "userForm";
		}
		else {
		userService.save(user);
		return "redirect:form";
		}
	}
	
	@RequestMapping("updateUser")
	public String forUpdatingTheUser(User user, Model model) {
		
		User u = userService.findById(user.getId());
		List<Role> retrievedRoles = u.getRoles();
		model.addAttribute("users", userService.findAll());
		model.addAttribute("roles", roleService.findAll());
		model.addAttribute("u", u);
		model.addAttribute("retrieved", retrievedRoles);
		return "userForm";
	}
	
	//http://localhost:8081/robinApp/deleteUser?userId=6
	@RequestMapping("deleteUser")
	public String deletesTheUser(User user) {
		userService.deleteById(user.getId());
		return "redirect:form";
	}
}
