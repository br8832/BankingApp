package com.synergisticit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.synergisticit.domain.Account;
import com.synergisticit.domain.Branch;
import com.synergisticit.domain.Role;
import com.synergisticit.service.AccountService;
import com.synergisticit.service.BranchService;
import com.synergisticit.validation.BranchValidator;

@Controller
@PreAuthorize("hasAuthority('Admin')")
@RequestMapping("/branch")// - this is no longer fucking it up
public class BranchController {
	static List<String> CRUDMethods = List.of("CREATE","UPDATE","DELETE");
	@Autowired
	private BranchService branchService;
	@Autowired
	BranchValidator branchValidator;
	@Autowired
	AccountService accountService;
	
	@ModelAttribute("cuentas")
	public List<Account> getAccounts(){
		return accountService.findAll();
	}
	
	@ModelAttribute("sucursales")
	public List<Branch> getranches(){
		return branchService.findAll();
	}
	
	@RequestMapping({"/form","/"})
	public String branchForm(Branch branch, Model m) {
		//m.addAttribute("sucursales", branchService.findAll());
		m.addAttribute("ops", CRUDMethods);
		m.addAttribute("siguiente", branchService.getNextId());
		//m.addAttribute("cuentas", accountService.findAll());
		return "branchForm";
	}
	
	@RequestMapping("/saveBranch")
	public String save(@ModelAttribute Branch branch,Model model,BindingResult br) {
		branchValidator.validate(branch, br);
		System.out.println("br.hasErrors(): "+br.hasErrors());
		System.out.println("branch br: "+branch.toString());
		//model.addAttribute("sucursales", branchService.findAll());
		//model.addAttribute("cuentas", accountService.findAll());
		if(br.hasErrors()) {
			System.out.println(br.getAllErrors());
			model.addAttribute("hasErrors",br.hasErrors());
			return "branchForm";
		}
		else {
		branchService.save(branch);
		return "redirect:form";
		}
	}
	
	@RequestMapping("/updateBranch")
	public String update(Branch b,Model model) {
		Branch branch = branchService.findById(b.getId());
		System.out.println("actual: "+branch);
		//model.addAttribute("sucursales", branchService.findAll());
		//model.addAttribute("cuentas", accountService.findAll());
		model.addAttribute("s", branch);
		return "branchForm";
	}
	
	@RequestMapping("/deleteBranch")
	public String delete(Branch b) {
		System.out.println("deletesTheBranch() b: "+b.getId());
		branchService.deleteById(b.getId());
		return "redirect:form";
	}
	
}
