package com.synergisticit.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import com.synergisticit.domain.Role;
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
	private Page<Transaction> convertListToPage(List<Transaction> transactions, Sort sort) {
	    return new PageImpl<Transaction>(transactions);
	}
	@ModelAttribute("sortedBy")
	public String sortedBy() {
		return "id";
	}
	@ModelAttribute("pageSize")
	public int size()
	{
		return 4;
	}
	@ModelAttribute("totalPages")
	public int totalPages() {
		int records = (int) transactionService.getRecordCount(), size = size();
		int quotient = (int) (records/size);
		return records % size == 0?quotient:quotient+1;
	}

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
    		Pageable pageable = PageRequest.of(0, size(), Sort.by("id"));
    		return transactionService.findAll(pageable).getContent();
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
    public String transactionForm(@RequestParam(required=false) String pageNo, @RequestParam(required=false) String sortedBy, Transaction transaction, Model model, Principal principal) {
    	model.addAttribute("ops", CRUDMethods);
    	pageNo = pageNo!=null ? pageNo : "0";
		sortedBy=sortedBy != null?sortedBy:"id";
		model.addAttribute("sortedBy", sortedBy);
		int pageSize = size();	
    	Pageable pageable = PageRequest.of(Integer.valueOf(pageNo), pageSize, Sort.by(sortedBy));
        Page<Transaction> pageOfTransactions = transactionService.findAll(pageable);
        List<Transaction> transactions = pageOfTransactions.getContent();
        model.addAttribute("transactions",transactions);
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
    public String update(@RequestParam(required=false) String sortedBy,Transaction transaction, Model model) {
        Transaction t = transactionService.findById(transaction.getId());
        model.addAttribute("t", t);
        sortedBy=sortedBy != null?sortedBy:"id";
        model.addAttribute("sortedBy", sortedBy);
		int pageSize=(int)size();
		model.addAttribute("transactions", transactionService.findAll(PageRequest.of(0, pageSize, Sort.by(sortedBy))).getContent());
        System.out.println(model);
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
    	else
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

    @RequestMapping("findAll")
    public String findAll(Transaction transaction, @RequestParam String sortBy, Model model) {
    	
    	model.addAttribute("nextId", transactionService.nextId());
    	sortBy=sortBy != null?sortBy:"id";
        model.addAttribute("sortedBy", sortBy);
        model.addAttribute("transactions", transactionService.findAll(sortBy));
        return "transactionForm";
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

