package com.synergisticit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.synergisticit.domain.Role;
import com.synergisticit.service.RoleService;
import com.synergisticit.validation.RoleValidator;

@Controller
@RequestMapping("/role")
public class RoleController {
	
	@Autowired RoleService roleService;
	@Autowired RoleValidator roleValidator;
		
	@RequestMapping({"/form","/"})
	public String displayRoleForm(Role role, Model model){
		System.out.println(model);
		model.addAttribute("roles", roleService.findAll());
		model.addAttribute("nextId",roleService.getNextId());
		return "roleForm";
	}
	@RequestMapping("saveRole")
	public String savesRole(@ModelAttribute Role role, Model model, BindingResult br){
		roleValidator.validate(role, br);
		System.out.println("br.hasErrors(): "+br.hasErrors());
		System.out.println("Role r: "+role.toString());
		model.addAttribute("roles", roleService.findAll());
		if(br.hasErrors())
		{
			model.addAttribute("hasErros", br.hasErrors());
			return "roleForm";
		}
		else 
		{
		roleService.save(role);
		return "redirect:form";
		}
	}

	@RequestMapping("deleteRole")
	public String deleteRole(@ModelAttribute Role role){
		roleService.deleteById(role.getId());
		return "redirect:form";
	}
	
	@RequestMapping("updateRole")
	public String updateRole(@ModelAttribute Role role, Model model){
		System.out.println(role);
		Role r = roleService.findById(role.getId());
		model.addAttribute("r", r);
		model.addAttribute("roles", roleService.findAll());
		return "roleForm";
	}
}
