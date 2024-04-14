package com.synergisticit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.synergisticit.domain.Transaction;
import com.synergisticit.domain.TransactionType;
import com.synergisticit.service.AccountService;
import com.synergisticit.service.TransactionService;
import com.synergisticit.validation.TransactionValidator;

@Controller
@RequestMapping("/transaction")// - this is no longer fucking it up
public class TransactionController {
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private TransactionValidator transactionValidator;
	
	@RequestMapping({"/form","","/"})
	public String transactionForm(Transaction transaction, Model m) {
		m.addAttribute("transactionTypes", TransactionType.values());
		m.addAttribute("transactions", transactionService.findAll());
		m.addAttribute("accounts", accountService.findAll());
		return "transactionForm";
	}
	
	@RequestMapping("/saveTransaction")
	public String save(@ModelAttribute Transaction transaction,Model model,BindingResult br) {
		transactionValidator.validate(transaction, br);
		System.out.println("br.hasErrors(): "+br.hasErrors());
		System.out.println("Transaction e: "+transaction.toString());
		model.addAttribute("transactionTypes", TransactionType.values());
		model.addAttribute("accounts", accountService.findAll());
		model.addAttribute("transactions", transactionService.findAll());
		if(br.hasErrors()) {
			System.out.println(br.getAllErrors());
			model.addAttribute("hasErrors",br.hasErrors());
			return "transactionForm";
		}
		else {
			transactionService.save(transaction);
		return "redirect:form";
		}
	}
	
	@RequestMapping("/updateTransaction")
	public String update(Transaction transaction,Model model) {
		Transaction t = transactionService.findById(transaction.getId());
		System.out.println("retrievedAccount: "+t);
		model.addAttribute("transactionTypes", TransactionType.values());
		model.addAttribute("transactions", transactionService.findAll());
		model.addAttribute("accounts", accountService.findAll());
		model.addAttribute("t", t);
		return "transactionForm";
	}
	
	@RequestMapping("/deleteTransaction")
	public String delete(Transaction t) {
		System.out.println("deletesTheTransaction() acc: "+t.getId());
		transactionService.deleteById(t.getId());
		return "redirect:form";
	}
	
}
