package com.synergisticit.controller;

import java.security.Principal;
import java.util.Collections;
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
	
	@ModelAttribute("defaultCustomerId")
	public Long getDefault(Principal principal) {
		return userService.findByUsername(principal.getName()).getId();
	}
	@ModelAttribute("pageSize")
	public int size()
	{
		return 3;
	}
	@ModelAttribute("totalPages")
	public int totalPages() {
		int records = (int) accountService.getRecordCount(), size = size();
		int quotient = (int) records/size;
		return records % size == 0?quotient:quotient+1;
	}
	
	@ModelAttribute("customers")
	public List<Customer> getCustomers(){
		return customerService.findAll();
	}
	@ModelAttribute("accounts")
	public List<Account> getAccounts(Principal principal){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("Admin"))) 
        	return accountService.findAll();
		else {
			 List<Account> userAccounts = accountService.findByUsername(principal.getName());
	         return userAccounts != null ? userAccounts : Collections.emptyList();
		}

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
	public String accountForm(Account account, Model model, Principal principal,@RequestParam(required=false) Integer pageNo, @RequestParam(required=false) String sortedBy) {
		model.addAttribute("ops", CRUDMethods);
		model.addAttribute("nextId",accountService.getNextId());
		pageNo=pageNo != null ? pageNo : 0;
		sortedBy=sortedBy != null?sortedBy:"id";
		model.addAttribute("sortedBy", sortedBy);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("Admin"))) {
        	Pageable pageable = PageRequest.of(pageNo, size(), Sort.by(sortedBy));
            Page<Account> pageOfAccounts = accountService.findAll(pageable);
            List<Account> accounts = pageOfAccounts.getContent();
            model.addAttribute("accounts", accounts);
       
        } else {
	    	try {
	    	 model.addAttribute("defaultCustomerId", userService.findByUsername(principal.getName()).getId());
	         }
	         catch(Exception e) {
	        	 return "home";
	         }
             
        }

//		model.addAttribute("accountTypes", AccountType.values());
//		model.addAttribute("accounts", accountService.findAll());
//		model.addAttribute("customers", customerService.findAll());
//		model.addAttribute("branches", branchService.findAll());
		return "accountForm";
	}
	
	@RequestMapping("/saveAccount")
	public String save(Account account,Principal principal, Model model,BindingResult br) {
		//model.addAttribute("defaultCustomerId", userService.findByUsername(principal.getName()).getId());
		accountValidator.validate(account, br);
		System.out.println(account.getBranch());
		System.out.println(model.getAttribute("branches"));
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
	public String update(Account acc,Model model,@RequestParam(required=false) String sortedBy) {
		Account account = accountService.findById(acc.getId());
		
		sortedBy=sortedBy != null?sortedBy:"id";
        model.addAttribute("sortedBy", sortedBy);
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
	@RequestMapping("findAll")
    public String findAll(Account account, @RequestParam String sortBy, Model model) {
		model.addAttribute("nextId", accountService.getNextId());
		sortBy=sortBy != null?sortBy:"id";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities().stream().anyMatch(
        	auth -> auth.getAuthority().equals("Admin")))
        model.addAttribute("accounts", accountService.findAll(sortBy));
        model.addAttribute("sortedBy", sortBy);
        return "accountForm";
    }
	
}