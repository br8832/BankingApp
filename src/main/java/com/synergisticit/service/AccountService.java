package com.synergisticit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.Account;
import com.synergisticit.repository.AccountRepository;
@Service
public class AccountService {
@Autowired AccountRepository accountRepository;
	
	public Account save(Account account) {
		return accountRepository.save(account);
		
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
