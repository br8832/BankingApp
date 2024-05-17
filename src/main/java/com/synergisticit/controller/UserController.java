package com.synergisticit.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.synergisticit.validation.UserValidator;
import com.synergisticit.domain.Role;
import com.synergisticit.domain.User;
import com.synergisticit.service.RoleService;
import com.synergisticit.service.UserService;

@Controller
@RequestMapping("/user")
// for now my solution passes it in the request params to keep track of info, maybe can do with databases
public class UserController {
	static List<String> CRUDMethods = List.of("CREATE","UPDATE","DELETE");
	@Autowired UserService userService;
	@Autowired RoleService roleService;
	@Autowired UserValidator validator;
	
	@ModelAttribute("pageSize")
	public int size()
	{
		return 3;
	}
	@ModelAttribute("totalPages")
	public int totalPages() {
		int records = (int) userService.getRecordCount(), size = size();
		int quotient = (int) records/size;
		return records % size == 0?quotient:quotient+1;
	}
	
	@ModelAttribute("roles")
	public List<Role> getRoles(){
		return roleService.findAll();
		
	}
	@ModelAttribute("users")
	public List<User> getUsers(Principal principal){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(
        	auth -> auth.getAuthority().equals("Admin"))) {
        	return userService.findAll("id");
        }
        return List.of(userService.findByUsername(principal.getName()));
	}
	private void needToUpdate(Principal principal, int pageNo, int pageSize, String sortedBy,Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(
        	auth -> auth.getAuthority().equals("Admin"))) {
        	Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy));
            Page<User> pageOfUsers = userService.findAll(pageable);
            List<User> users = pageOfUsers.getContent();
            model.addAttribute("users", users);
            System.out.println(model);
        }
	}
	@RequestMapping({"/form","/"})
	 public String findThePagedUser(Principal principal, @RequestParam(required=false) Integer pageNo, @RequestParam(required=false) String sortedBy, Model model, @ModelAttribute User user) {
		model.addAttribute("nextId", userService.getNextId());
		sortedBy=sortedBy != null?sortedBy:"id";
		pageNo=pageNo!=null?pageNo:0;
		int pageSize = (int)size();
		needToUpdate(principal,pageNo,pageSize,sortedBy,model);
        model.addAttribute("sortedBy", sortedBy);
        return "userForm";
    }
//	@RequestMapping("/")
//	public String userForm(User user, Model model) {
//		model.addAttribute("nextId", userService.getNextId());
//		
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getAuthorities().stream().anyMatch(
//        	auth -> auth.getAuthority().equals("Admin"))) {
//        	//System.out.println(pageNo+" "+pageSize +" "+sortedBy);
//        	Pageable pageable = PageRequest.of(0, size(), Sort.by("id"));
//            Page<User> pageOfUsers = userService.findAll(pageable);
//            //System.out.println(pageOfUsers);
//            List<User> users = pageOfUsers.getContent();
//            //System.out.println(model);
//            model.addAttribute("users", users);
//            System.out.println(model);
//        }
//        model.addAttribute("sortedBy", "id");
//		return "userForm";
//	}
	
	
	@RequestMapping("saveUser")
	public String savesTheUser( @ModelAttribute User user, Model model, BindingResult br) {
		validator.validate(user, br);
		System.out.println("br.hasErrors(): "+br.hasErrors());
		System.out.println("User u: "+user.toString());
		//model.addAttribute("users", userService.findAll());
		//model.addAttribute("roles", roleService.findAll());
		if(br.hasErrors()) {
			System.out.println(br.getAllErrors());
			model.addAttribute("hasErrors",br.hasErrors());
			model.addAttribute("email",br.getFieldError("email"));
			return "userForm";
		}
		else {
		userService.save(user);
		return "redirect:/user/";
		}
	}
	
	@RequestMapping("updateUser")
	public String forUpdatingTheUser(Principal principal, User user, Model model,@RequestParam(required=false) String sortedBy) {
		sortedBy=sortedBy != null?sortedBy:"id";
		model.addAttribute("sortedBy", sortedBy);
        int pageSize=(int)size();
		needToUpdate(principal,0,pageSize,sortedBy,model);
        //model.addAttribute("users", userService.findAll(PageRequest.of(0, pageSize, Sort.by(sortedBy))).getContent());
		User u = userService.findById(user.getId());
//		List<Role> retrievedRoles = u.getRoles();
		//model.addAttribute("users", userService.findAll());
		//model.addAttribute("roles", roleService.findAll());
		model.addAttribute("u", u);
		//model.addAttribute("retrieved", retrievedRoles);
		return "userForm";
	}
	
	//http://localhost:8081/robinApp/deleteUser?userId=6
	@RequestMapping("deleteUser")
	public String deletesTheUser(User user) {
		userService.deleteById(user.getId());
		return "redirect:form";
	}
	
	 @RequestMapping("findAll")
     public String findAll(User user, @RequestParam String sortBy, Model model) {
		model.addAttribute("nextId", userService.getNextId());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(
        	auth -> auth.getAuthority().equals("Admin")))
        model.addAttribute("users", userService.findAll(sortBy));
        model.addAttribute("sortedBy", sortBy);
        System.out.println(model);
         return "userForm";
     }
}
