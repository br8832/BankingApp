package com.synergisticit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.Transaction;
import com.synergisticit.repository.TransactionRepository;
@Service
public class TransactionService {
@Autowired TransactionRepository transactionRepository;
	
	public Transaction save(Transaction transaction) {
		return transactionRepository.save(transaction);
		
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
}
