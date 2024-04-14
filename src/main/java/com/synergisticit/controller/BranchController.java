package com.synergisticit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.synergisticit.domain.Branch;
import com.synergisticit.service.BranchService;
import com.synergisticit.validation.BranchValidator;

@Controller
@RequestMapping("/branch")// - this is no longer fucking it up
public class BranchController {
	@Autowired
	private BranchService branchService;
	@Autowired
	BranchValidator branchValidator;
	
	@RequestMapping({"/form","","/"})
	public String branchForm(Branch branch, Model m) {
		m.addAttribute("branches", branchService.findAll());
		return "branchForm";
	}
	
	@RequestMapping("/saveBranch")
	public String save(@ModelAttribute Branch branch,Model model,BindingResult br) {
		branchValidator.validate(branch, br);
		System.out.println("br.hasErrors(): "+br.hasErrors());
		System.out.println("branch e: "+branch.toString());
		model.addAttribute("branches", branchService.findAll());
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
		System.out.println("retrievedBranch: "+branch);
		model.addAttribute("branches", branchService.findAll());
		model.addAttribute("b", branch);
		return "branchForm";
	}
	
	@RequestMapping("/deleteBranch")
	public String delete(Branch b) {
		System.out.println("deletesTheBranch() b: "+b.getId());
		branchService.deleteById(b.getId());
		return "redirect:form";
	}
	
}
