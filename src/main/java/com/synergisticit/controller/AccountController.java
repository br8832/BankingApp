package com.synergisticit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.synergisticit.domain.Account;
import com.synergisticit.domain.AccountType;
import com.synergisticit.service.AccountService;
import com.synergisticit.service.BranchService;
import com.synergisticit.service.CustomerService;
import com.synergisticit.validation.AccountValidator;

@Controller
@RequestMapping("/account")// - this is no longer fucking it up
public class AccountController {
	@Autowired
	private AccountService accountService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private BranchService branchService;
	@Autowired
	private AccountValidator accountValidator;
	
	
	@RequestMapping({"/form","","/"})
	public String accountForm(Account account, Model model) {
		model.addAttribute("accountTypes", AccountType.values());
		model.addAttribute("accounts", accountService.findAll());
		model.addAttribute("customers", customerService.findAll());
		model.addAttribute("branches", branchService.findAll());
		return "accountForm";
	}
	
	@RequestMapping("/saveAccount")
	public String save(@ModelAttribute Account account,Model model,BindingResult br) {
		accountValidator.validate(account, br);
		System.out.println("br.hasErrors(): "+br.hasErrors());
		//System.out.println("account e: "+account.toString());
		model.addAttribute("accountTypes", AccountType.values());
		model.addAttribute("branches", branchService.findAll());
		model.addAttribute("accounts", accountService.findAll());
		model.addAttribute("customers", accountService.findAll());
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
		model.addAttribute("accountTypes", AccountType.values());
		model.addAttribute("branches", branchService.findAll());	
		model.addAttribute("accounts", accountService.findAll());
		model.addAttribute("customers", customerService.findAll());
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