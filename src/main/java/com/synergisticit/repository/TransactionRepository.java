package com.synergisticit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synergisticit.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
