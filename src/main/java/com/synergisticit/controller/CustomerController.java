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

import com.synergisticit.domain.Customer;
import com.synergisticit.domain.Gender;
import com.synergisticit.domain.User;
import com.synergisticit.service.CustomerService;
import com.synergisticit.validation.CustomerValidator;

@Controller
@RequestMapping("/customer")// - this is no longer fucking it up
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerValidator customerValidator;
	
	static List<String> CRUDMethods = List.of("CREATE","UPDATE","DELETE");
	
	@ModelAttribute("pageSize")
	public int size()
	{
		return 2;
	}
	@ModelAttribute("totalPages")
	public int totalPages() {
		int records = (int) customerService.getRecordCount(), size = size();
		int quotient = (int) records/size;
		return records % size == 0?quotient:quotient+1;
	}
	
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
	private void needToUpdate(Principal principal, int pageNo, int pageSize, String sortedBy,Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(
        	auth -> auth.getAuthority().equals("Admin"))) {
        	Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy));
            Page<Customer> pageOfCustomers = customerService.findAll(pageable);
            List<Customer> customers = pageOfCustomers.getContent();
            model.addAttribute("customers", customers);
            System.out.println(model);
        }
	}
	
	@RequestMapping({"/form","/"})
	public String customerForm(Customer customer, Model model,Principal principal, @RequestParam(required=false) Integer pageNo, @RequestParam(required=false) String sortedBy) {
		System.out.println(model);
		model.addAttribute("nextId", customerService.getNextId());
		model.addAttribute("ops", CRUDMethods);
		pageNo=pageNo != null ? pageNo : 0;
		sortedBy=sortedBy != null?sortedBy:"id";
		int pageSize = (int)size();
		needToUpdate(principal,Integer.valueOf(pageNo),pageSize,sortedBy,model);
        model.addAttribute("sortedBy", sortedBy);
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
	public String update(Customer customer,Model model,@RequestParam(required=false) String sortedBy) {
		Customer c = customerService.findById(customer.getId());
		sortedBy=sortedBy != null?sortedBy:"id";
        model.addAttribute("sortedBy", sortedBy);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(
        	auth -> auth.getAuthority().equals("Admin")))
        model.addAttribute("customers", customerService.findAll(PageRequest.of(0, size(), Sort.by(sortedBy))).getContent());
		
        model.addAttribute("users",customerService.findUserInCustomer(c.getUser().getUsername()));
		//model.addAttribute("genders", Gender.values());
		//model.addAttribute("customers", customerService.findAll());
		//model.addAttribute("users", userService.findAll());
		model.addAttribute("c", c);
		System.out.println(model.getAttribute("users"));
		return "customerForm";
	}
	
	@RequestMapping("/deleteCustomer")
	public String delete(Customer c) {
		System.out.println("deletesTheCustomer() c: "+c.getId());
		customerService.deleteById(c.getId());
		return "redirect:form";
	}
	@RequestMapping("findAll")
    public String findAll(Customer customer, @RequestParam String sortBy, Model model) {
		model.addAttribute("nextId", customerService.getNextId());
		sortBy=sortBy != null?sortBy:"id";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(
        	auth -> auth.getAuthority().equals("Admin")))
        	model.addAttribute("customers", customerService.findAll(sortBy));
        model.addAttribute("sortedBy", sortBy);
        return "customerForm";
    }
	
}
