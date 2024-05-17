package com.synergisticit.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.synergisticit.domain.Role;
import com.synergisticit.domain.User;
import com.synergisticit.service.RoleService;
import com.synergisticit.validation.RoleValidator;
@Controller
@RequestMapping("/role")
public class RoleController {
	static List<String> CRUDMethods = List.of("CREATE","UPDATE","DELETE");
	@Autowired RoleService roleService;
	@Autowired RoleValidator roleValidator;
	
	@ModelAttribute("pageSize")
	public int size()
	{
		return 3;
	}
	@ModelAttribute("totalPages")
	public int totalPages() {
		int records = (int) roleService.getRecordCount(), size = size();
		int quotient = (int) records/size;
		return records % size == 0?quotient:quotient+1;
	}
	
	@ModelAttribute("roles")
	public List<Role> getRoles(){
		return roleService.findAll();
	}
	
	@RequestMapping({"/form","/"})
	public String displayRoleForm(@RequestParam(required=false) String pageNo, @RequestParam(required=false) String sortedBy, Role role, Model model){
		System.out.println(model);
		//model.addAttribute("roles", roleService.findAll());
		pageNo = pageNo!=null ? pageNo : "0";
		sortedBy=sortedBy != null?sortedBy:"id";
		int pageSize = (int)size();
    	Pageable pageable = PageRequest.of(Integer.valueOf(pageNo), pageSize, Sort.by(sortedBy));
        Page<Role> pageOfRoles = roleService.findAll(pageable);
        System.out.println(pageOfRoles);
        List<Role> roles = pageOfRoles.getContent();
        model.addAttribute("roles", roles);
        model.addAttribute("sortedBy", sortedBy);
		model.addAttribute("nextId",roleService.getNextId());
		model.addAttribute("ops",CRUDMethods);
		System.out.println(model);
		return "roleForm";
	}
	@RequestMapping("saveRole")
	public String savesRole( Role role, Model model, BindingResult br){
		roleValidator.validate(role, br);
		System.out.println("br.hasErrors(): "+br.hasErrors());
		System.out.println("Role r: "+role.toString());
		//model.addAttribute("roles", roleService.findAll());
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
	public String deleteRole( Role role){
		roleService.deleteById(role.getId());
		return "redirect:form";
	}
	
	@RequestMapping("updateRole")
	public String updateRole(@RequestParam(required=false) String sortedBy, Role role, Model model){
		System.out.println(role);
		sortedBy=sortedBy != null?sortedBy:"id";
        model.addAttribute("sortedBy", sortedBy);
		Role r = roleService.findById(role.getId());
		int pageSize=(int)size();
		model.addAttribute("roles", roleService.findAll(PageRequest.of(0, pageSize, Sort.by(sortedBy))).getContent());
		model.addAttribute("r", r);
		//model.addAttribute("roles", roleService.findAll());
		return "roleForm";
	}
	 @RequestMapping("findAll")
     public String findAll(Role role, @RequestParam String sortBy, Model model) {
		model.addAttribute("nextId", roleService.getNextId());
		sortBy=sortBy != null?sortBy:"id";
        model.addAttribute("roles", roleService.findAll(sortBy));
        model.addAttribute("sortedBy", sortBy);
        System.out.println(model);
         return "roleForm";
     }
}
