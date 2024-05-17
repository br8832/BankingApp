package com.synergisticit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.synergisticit.domain.Account;
import com.synergisticit.domain.Branch;
import com.synergisticit.domain.Customer;
import com.synergisticit.domain.Role;
import com.synergisticit.service.AccountService;
import com.synergisticit.service.BranchService;
import com.synergisticit.validation.BranchValidator;

@Controller
@PreAuthorize("hasAuthority('Admin')")
@RequestMapping("/branch")// - this is no longer fucking it up
public class BranchController {
	static List<String> CRUDMethods = List.of("CREAR","ACTUALIZAR","CANCELAR");
	@Autowired
	private BranchService branchService;
	@Autowired
	BranchValidator branchValidator;
	@Autowired
	AccountService accountService;
	
	@ModelAttribute("pageSize")
	public int size()
	{
		return 3;
	}
	@ModelAttribute("totalPages")
	public int totalPages() {
		int records = (int) branchService.getRecordCount(), size = size();
		int quotient = (int) records/size;
		return records % size == 0?quotient:quotient+1;
	}
	
	@ModelAttribute("cuentas")
	public List<Account> getAccounts(){
		return accountService.findAll();
	}
	
	@ModelAttribute("sucursales")
	public List<Branch> getranches(){
		return branchService.findAll();
	}
	
	@RequestMapping({"/form","/"})
	public String branchForm(Branch branch, @RequestParam(required=false) Integer pageNo, @RequestParam(required=false) String sortedBy, Model model) {
		//m.addAttribute("sucursales", branchService.findAll());
		model.addAttribute("ops", CRUDMethods);
		model.addAttribute("siguiente", branchService.getNextId());
		pageNo=pageNo != null ? pageNo : 0;
		sortedBy=sortedBy != null?sortedBy:"id";
		Pageable pageable = PageRequest.of(Integer.valueOf(pageNo), size(), Sort.by(sortedBy));
        Page<Branch> paginaDeSucursales = branchService.findAll(pageable);
        System.out.println(paginaDeSucursales);
        List<Branch> sucursales = paginaDeSucursales.getContent();
        model.addAttribute("sucursales", sucursales);
		//m.addAttribute("cuentas", accountService.findAll());
		return "branchForm";
	}
	
	@RequestMapping("/saveBranch")
	public String save(@ModelAttribute Branch branch, Model model,BindingResult br) {
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
	public String update(Branch b,Model model,@RequestParam(required=false) String sortedBy) {
		Branch branch = branchService.findById(b.getId());
		System.out.println("actual: "+branch);
		sortedBy=sortedBy != null?sortedBy:"id";
        model.addAttribute("sortedBy", sortedBy);
        model.addAttribute("sucursales", branchService.findAll(PageRequest.of(0, size(), Sort.by(sortedBy))).getContent());
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
	@RequestMapping("findAll")
    public String findAll(Branch branch, @RequestParam String sortBy, Model model) {
		model.addAttribute("siguiente", branchService.getNextId());
		sortBy=sortBy != null?sortBy:"id";
        model.addAttribute("sucursales", branchService.findAll(sortBy));
        model.addAttribute("sortedBy", sortBy);
        return "branchForm";
    }
	
}
