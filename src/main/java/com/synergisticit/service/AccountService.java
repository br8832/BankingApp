package com.synergisticit.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.Account;
import com.synergisticit.repository.AccountRepository;
@Service
public class AccountService {
@Autowired AccountRepository accountRepository;
	
	public Long getNextId() {
		return accountRepository.nextId();
	}
	public Account save(Account account) {
		account.setDateOpened(LocalDate.now());
		return accountRepository.save(account);
	}
	public List<Account> findByUsername(String username){
		return accountRepository.findByCustomer_User_Username(username);
	}
	public List<Account> findAll()
	{
		return accountRepository.findAll();
	}
	public void deleteById(Long id) {
		accountRepository.deleteById(id);
	}
	public Account findById(Long id) {
		return accountRepository.findById(id).orElse(null);
	}
}
