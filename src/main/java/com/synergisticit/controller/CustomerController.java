package com.synergisticit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.synergisticit.domain.Customer;
import com.synergisticit.domain.Gender;
import com.synergisticit.service.CustomerService;
import com.synergisticit.service.UserService;
import com.synergisticit.validation.CustomerValidator;

@Controller
@RequestMapping("/customer")// - this is no longer fucking it up
public class CustomerController {
	@Autowired
	private UserService userService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerValidator customerValidator;
	
	@RequestMapping({"/form","/"})
	public String customerForm(Customer customer, Model m) {
		m.addAttribute("nextId", customerService.getNextId());
		m.addAttribute("genders", Gender.values());
		m.addAttribute("customers", customerService.findAll());
		m.addAttribute("users", userService.findAll());
		return "customerForm";
	}
	
	@RequestMapping("/saveCustomer")
	public String save(@ModelAttribute Customer customer,Model model,BindingResult br) {
		customerValidator.validate(customer, br);
		System.out.println("br.hasErrors(): "+br.hasErrors());
		System.out.println("customer c: "+customer.toString());
		model.addAttribute("customers", customerService.findAll());
		model.addAttribute("genders", Gender.values());
		model.addAttribute("users", userService.findAll());
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
		model.addAttribute("genders", Gender.values());
		model.addAttribute("customers", customerService.findAll());
		model.addAttribute("users", userService.findAll());
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
