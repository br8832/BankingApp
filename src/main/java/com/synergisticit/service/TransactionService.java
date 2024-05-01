package com.synergisticit.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.Account;
import com.synergisticit.domain.Transaction;
import com.synergisticit.repository.AccountRepository;
import com.synergisticit.repository.TransactionRepository;
@Service
public class TransactionService {
@Autowired TransactionRepository transactionRepository;
@Autowired AccountRepository accountRepository;
	
	public Long nextId() {
		return transactionRepository.getNextId();
	}
	@SuppressWarnings("incomplete-switch")
	public Transaction save(Transaction transaction) {
		System.out.println("In service:" + transaction);
		switch(transaction.getType()) {
		case DEPOSIT:
			transaction.setFromAccount(null);
			break;
		case WITHDRAW:
			transaction.setToAccount(null);
			break;
		}
		System.out.println(transaction);
		//update accounts
		Account acc;
		if(transaction.getFromAccount() != null)
		{
			
			acc=accountRepository.findById(transaction.getFromAccount()).orElse(null);
			System.out.println(acc);
			acc.setBalance(acc.getBalance()-transaction.getAmount());
			System.out.println(acc);
			accountRepository.save(acc);
		}
		if(transaction.getToAccount() != null)
		{
			acc=accountRepository.findById(transaction.getToAccount()).orElse(null);
			System.out.println(acc);
			acc.setBalance(acc.getBalance()+transaction.getAmount());
			System.out.println(acc);
			accountRepository.save(acc);
		}
		 // Get the current LocalDateTime
        LocalDateTime now = LocalDateTime.now();
        
        // Set milliseconds to zero
        LocalDateTime truncatedDateTime = LocalDateTime.of(
            now.getYear(),
            now.getMonth(),
            now.getDayOfMonth(),
            now.getHour(),
            now.getMinute(),
            now.getSecond(),
            0 // Milliseconds set to zero
        );
        transaction.setDate(truncatedDateTime);
		return transactionRepository.save(transaction);	
	}
	public List<Transaction> findByAccountId(Long id){
		return transactionRepository.findByAccountId(id);
	}
	public List<Transaction> findAll()
	{
		return transactionRepository.findAll();
	}
	public void deleteById(Long id) {
		transactionRepository.deleteById(id);
	}
	public Transaction findById(Long id) {
		return transactionRepository.findById(id).orElse(null);
	}
	public List<Transaction> findByDateBetween(LocalDateTime start, LocalDateTime end){
		return transactionRepository.findByDateBetween(start, end);
	}
}
