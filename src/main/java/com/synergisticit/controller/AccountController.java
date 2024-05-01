package com.synergisticit.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.synergisticit.domain.Account;
import com.synergisticit.domain.AccountType;
import com.synergisticit.domain.Branch;
import com.synergisticit.domain.Customer;
import com.synergisticit.service.AccountService;
import com.synergisticit.service.BranchService;
import com.synergisticit.service.CustomerService;
import com.synergisticit.service.UserService;
import com.synergisticit.validation.AccountValidator;

@Controller
@RequestMapping("/account")// - this is no longer fucking it up
public class AccountController {
	@Autowired
	private UserService userService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private BranchService branchService;
	@Autowired
	private AccountValidator accountValidator;
	
	static List<String> CRUDMethods = List.of("CREATE","UPDATE","DELETE");
	
	@ModelAttribute("customers")
	public List<Customer> getCustomers(){
		return customerService.findAll();
	}
	@ModelAttribute("accounts")
	public List<Account> getAccounts(){
		return accountService.findAll();
	}
	@ModelAttribute("branches")
	public List<Branch> getBranches(){
		return branchService.findAll();
	}
	@ModelAttribute("accountTypes")
	public AccountType[] AccountTypes(){
		return AccountType.values();
	}
	@RequestMapping({"/form","/"})
	public String accountForm(Account account, Model model, Principal principal) {
		model.addAttribute("ops", CRUDMethods);
		model.addAttribute("nextId",accountService.getNextId());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("Admin"))) {
        	model.addAttribute("accounts", accountService.findAll());
        } else {
        	System.out.println(principal);
        	 List<Account> userAccounts = accountService.findByUsername(principal.getName());
             model.addAttribute("accounts", userAccounts != null ? userAccounts : Collections.emptyList());
             try {
             model.addAttribute("defaultCustomerId", userService.findByUsername(principal.getName()).getId());
             }
             catch(Exception e) {
            	 return "accessDenied";
             }
             
        }

//		model.addAttribute("accountTypes", AccountType.values());
//		model.addAttribute("accounts", accountService.findAll());
//		model.addAttribute("customers", customerService.findAll());
//		model.addAttribute("branches", branchService.findAll());
		return "accountForm";
	}
	
	@RequestMapping("/saveAccount")
	public String save(@ModelAttribute Account account,Model model,BindingResult br) {
		accountValidator.validate(account, br);
		System.out.println("br.hasErrors(): "+br.hasErrors());
		//System.out.println("account e: "+account.toString());
//		model.addAttribute("accountTypes", AccountType.values());
//		model.addAttribute("branches", branchService.findAll());
//		model.addAttribute("accounts", accountService.findAll());
//		model.addAttribute("customers", customerService.findAll());
		if(br.hasErrors()) {
			System.out.println(br.getAllErrors());
			model.addAttribute("hasErrors",br.hasErrors());
			return "accountForm";
		}
		else {
			accountService.save(account);
		return "redirect:form";
		}
	}
	
	@RequestMapping("/updateAccount")
	public String update(Account acc,Model model) {
		Account account = accountService.findById(acc.getId());
		//System.out.println("retrievedAccount: "+account);
//		model.addAttribute("accountTypes", AccountType.values());
//		model.addAttribute("branches", branchService.findAll());	
//		model.addAttribute("accounts", accountService.findAll());
//		model.addAttribute("customers", customerService.findAll());
		model.addAttribute("acc", account);
		return "accountForm";
	}
	
	@RequestMapping("/deleteAccount")
	public String delete(Account acc) {
		System.out.println("deletesTheACcount() acc: "+acc.getId());
		accountService.deleteById(acc.getId());
		return "redirect:form";
	}
	
}