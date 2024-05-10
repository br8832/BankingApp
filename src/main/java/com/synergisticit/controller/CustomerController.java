package com.synergisticit.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.synergisticit.domain.Customer;
import com.synergisticit.domain.Gender;
import com.synergisticit.domain.Role;
import com.synergisticit.domain.User;
import com.synergisticit.service.CustomerService;
import com.synergisticit.service.UserService;
import com.synergisticit.validation.CustomerValidator;

@Controller
@RequestMapping("/customer")// - this is no longer fucking it up
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerValidator customerValidator;
	
	static List<String> CRUDMethods = List.of("CREATE","UPDATE","DELETE");
	
//	@ModelAttribute("customers")
//	public List<Customer> getCustomers(){
//		return customerService.findAll();
//	}
//	@ModelAttribute("users")
//	public List<User> getUsers(){
//		return customerService.availableUsers();
//	}
	@ModelAttribute("genders")
	public Gender[] genders(){
		return Gender.values();
	}
	@ModelAttribute("customers")
	public List<Customer> getCustomers(Principal principal){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("Admin"))) 
        	return customerService.findAll();
        else 
        	return customerService.findByUserUsername(principal.getName());
	}
	@ModelAttribute("users")
	public List<User> getUsers(Principal principal){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("Admin"))) 
        	return customerService.availableUsers();
        else 
        	return customerService.findUserInCustomer(principal.getName());
	}
	
	@RequestMapping({"/form","/"})
	public String customerForm(Customer customer, Model model,Principal principal) {
		model.addAttribute("nextId", customerService.getNextId());
		model.addAttribute("ops", CRUDMethods);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities().stream()
                .noneMatch(a -> a.getAuthority().equals("Admin"))) 
        	model.addAttribute("c", customerService.findByUserUsername(principal.getName()).get(0));
		//m.addAttribute("genders", Gender.values());
		//m.addAttribute("customers", customerService.findAll());
		//m.addAttribute("users", userService.findAll());
		return "customerForm";
	}
	
	@RequestMapping("/saveCustomer")
	public String save(@ModelAttribute Customer customer,Model model,BindingResult br) {
		customerValidator.validate(customer, br);
		System.out.println("br.hasErrors(): "+br.hasErrors());
		System.out.println("customer c: "+customer.toString());
		//model.addAttribute("customers", customerService.findAll());
		//model.addAttribute("genders", Gender.values());
		//model.addAttribute("users", userService.findAll());
		if(br.hasErrors()) {
			System.out.println(br.getAllErrors());
			model.addAttribute("hasErrors",br.hasErrors());
			return "customerForm";
		}
		else {
			customerService.save(customer);
		return "redirect:form";
		}
	}
	
	@RequestMapping("/updateCustomer")
	public String update(Customer customer,Model model) {
		Customer c = customerService.findById(customer.getId());
		System.out.println("retrievedAcustomer: "+c);
		//model.addAttribute("genders", Gender.values());
		//model.addAttribute("customers", customerService.findAll());
		//model.addAttribute("users", userService.findAll());
		model.addAttribute("c", c);
		return "customerForm";
	}
	
	@RequestMapping("/deleteCustomer")
	public String delete(Customer c) {
		System.out.println("deletesTheCustomer() c: "+c.getId());
		customerService.deleteById(c.getId());
		return "redirect:form";
	}
	
}
