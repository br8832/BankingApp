package com.synergisticit.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.synergisticit.domain.Account;
import com.synergisticit.domain.Transaction;
import com.synergisticit.domain.TransactionType;
import com.synergisticit.service.AccountService;
import com.synergisticit.service.TransactionService;
import com.synergisticit.validation.TransactionValidator;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionValidator transactionValidator;

    private DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
    private DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	static List<String> CRUDMethods = List.of("CREATE","UPDATE","DELETE");


    @ModelAttribute("transactionTypes")
    public TransactionType[] transactionTypes() {
        return TransactionType.values();
    }

    @ModelAttribute("nextId")
    public Long getNextId() {
        return transactionService.nextId();
    }

    @ModelAttribute("accounts")
    public List<Account> getAccounts() {
        return accountService.findAll();
    }

    @ModelAttribute("transactions")
    public List<Transaction> getYourTransactions(Principal principal) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("Admin"))) {
    		return transactionService.findAll();
    	}
    	// else it's only your user transactions
        List<Transaction> yourTransactions = new ArrayList<>();
        for (Account a : accountService.findByUsername(principal.getName())) {
            yourTransactions.addAll(transactionService.findByAccountId(a.getId()));
        }
        return yourTransactions;
    }
    @ModelAttribute("fromAccounts")
    public List<Account> getYourAccounts(Principal principal){
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("Admin"))) {
    		return accountService.findAll();
    	}
    	return accountService.findByUsername(principal.getName());
    }
    
    @RequestMapping({ "/form", "/" })
    public String transactionForm(Transaction transaction, Model model, Principal principal) {
    	model.addAttribute("ops", CRUDMethods);
        return "transactionForm";
    }

    @RequestMapping("/saveTransaction")
    public String save(@ModelAttribute Transaction transaction, Model model, BindingResult br) {
        transactionValidator.validate(transaction, br);
        System.out.println(model);
        if (br.hasErrors()) {
        	model.addAttribute("hasErrors",true);
        	System.out.println(model);
            return "transactionForm";
        } else {
            transactionService.save(transaction);
            return "redirect:form";
        }
    }

    @RequestMapping("/updateTransaction")
    public String update(Transaction transaction, Model model) {
        Transaction t = transactionService.findById(transaction.getId());
        model.addAttribute("t", t);
        
        return "transactionForm";
    }

    @RequestMapping("/filterTransactions")
    public String filterTransactions(Transaction transaction, Model model,
            @RequestParam("startDate") String start, @RequestParam("endDate") String end, Principal principal) {
        LocalDateTime startDate = LocalDateTime.parse(start + "T00:00:00");
        LocalDateTime endDate = LocalDateTime.parse(end + "T23:59:59");
        List<Transaction> filteredTransactions;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("Admin"))) 
    		filteredTransactions = transactionService.findByDateBetween(startDate, endDate);
    	filteredTransactions = transactionService.findByDateBetweenForUser(startDate, endDate, principal.getName());
        model.addAttribute("transactions", filteredTransactions);
        model.addAttribute("startDate", LocalDate.parse(start));
        model.addAttribute("endDate", LocalDate.parse(end));
        return "transactionForm";
    }

    @RequestMapping("/deleteTransaction")
    public String delete(Transaction t) {
        transactionService.deleteById(t.getId());
        return "redirect:form";
    }

    @RequestMapping("/between")
    public ResponseEntity<?> between(Principal principal,@RequestParam String start, @RequestParam String end) {
    	
    	try {
            return ResponseEntity.ok(transactionService.findByDateBetween(
                    LocalDateTime.parse(start + "T00:00:00"), LocalDateTime.parse(end + "T23:59:59")));
        } catch (DateTimeParseException dt) {
            return ResponseEntity.badRequest().body(dt);
        }
    	
    }
}

//package com.synergisticit.controller;
//
//import java.security.Principal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import com.synergisticit.domain.Account;
//import com.synergisticit.domain.Transaction;
//import com.synergisticit.domain.TransactionType;
//import com.synergisticit.service.AccountService;
//import com.synergisticit.service.TransactionService;
//import com.synergisticit.validation.TransactionValidator;
//
//@Controller
//@RequestMapping("/transaction")// - this is no longer fucking it up
//// probably need to PageIT
//public class TransactionController {
//	@Autowired
//	private TransactionService transactionService;
//	@Autowired
//	private AccountService accountService;
//	@Autowired
//	private TransactionValidator transactionValidator;
//	 
//	private DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
//    private DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//    
//	@RequestMapping({"/form","/"})
//	public String transactionForm(Transaction transaction, Model m,Principal principal) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		m.addAttribute("transactionTypes", TransactionType.values());
//		m.addAttribute("nextId", transactionService.nextId());
//		// Check if the user has a specific authority
//	    if (authentication != null && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Admin"))) {
//	        // Serve a different page or modify the model contents for users with admin authority
//	    	m.addAttribute("transactions",transactionService.findAll());
//	    	m.addAttribute("accounts", accountService.findAll());
//	    	
//	    } else {
//	        // Serve the default page or modify the model contents for other users
//	    	ArrayList<Transaction> yourTransactions = new ArrayList<>();
//	    	ArrayList<Account> yourAccounts = new ArrayList<>();
//			for(Account a:accountService.findByUsername(principal.getName())){
//				yourAccounts.add(a);
//				yourTransactions.addAll(transactionService.findByAccountId(a.getId()));
//			}
//	    	m.addAttribute("transactions", yourTransactions);
//	    	m.addAttribute("accounts",yourAccounts);
//	    }
//	    return "transactionForm";
//	}
//	
//	@RequestMapping("/saveTransaction")
//	public String save(@ModelAttribute Transaction transaction,Model model,BindingResult br) {
//		transactionValidator.validate(transaction, br);
//		System.out.println("br.hasErrors(): "+br.hasErrors());
//		System.out.println("Transaction trans: "+transaction.toString());
//		model.addAttribute("transactionTypes", TransactionType.values());
//		model.addAttribute("accounts", accountService.findAll());
//		model.addAttribute("transactions", transactionService.findAll());
//		if(br.hasErrors()) {
//			System.out.println(br.getAllErrors());
//			model.addAttribute("hasErrors",br.hasErrors());
//			return "transactionForm";
//		}
//		else {
//			transactionService.save(transaction);
//		return "redirect:form";
//		}
//	}
//	
//	@RequestMapping("/updateTransaction")
//	public String update(Transaction transaction,Model model) {
//		Transaction t = transactionService.findById(transaction.getId());
//		System.out.println("retrievedAccount: "+t);
//		model.addAttribute("transactionTypes", TransactionType.values());
//		model.addAttribute("transactions", transactionService.findAll());
//		model.addAttribute("accounts", accountService.findAll());
//		model.addAttribute("t", t);
//		return "transactionForm";
//	}
//	
//	@RequestMapping("/filterTransactions") 
//	public String filterTransactions(Transaction transaction, Model model,
//            @RequestParam("startDate") String start,
//            @RequestParam("endDate") String end) {
//		LocalDateTime startDate=LocalDateTime.parse(start+"T00:00:00");
//		LocalDateTime endDate=LocalDateTime.parse(end+"T23:59:59");
//        List<Transaction> filteredTransactions = transactionService.findByDateBetween(startDate,endDate);
//        model.addAttribute("accounts", accountService.findAll());
//        model.addAttribute("nextId", transactionService.nextId());
//        model.addAttribute("transactions", filteredTransactions);
//        model.addAttribute("startDate", LocalDate.parse(start));
//        model.addAttribute("endDate", LocalDate.parse(end));
//        model.addAttribute("transactionTypes", TransactionType.values());
//        return "transactionForm"; 
//        }
//	
//	@RequestMapping("/deleteTransaction")
//	public String delete(Transaction t) {
//		System.out.println("deletesTheTransaction() acc: "+t.getId());
//		transactionService.deleteById(t.getId());
//		return "redirect:form";
//	}
//	
//	// expects yyyy-MM-dd
//	@RequestMapping("/between") 
//	public ResponseEntity<?> between(@RequestParam String start, @RequestParam String end){
//		try {
//			return  ResponseEntity.ok(transactionService.findByDateBetween(
//				LocalDateTime.parse(start+"T00:00:00"),
//				LocalDateTime.parse(end+"T23:59:59")));}
//		catch (DateTimeParseException dt){
//			return ResponseEntity.badRequest().body(dt);
//		}
//	}
//	
//}
