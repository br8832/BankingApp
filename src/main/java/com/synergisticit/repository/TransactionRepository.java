package com.synergisticit.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.synergisticit.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	@Query(value="Select MAX(id)+1 from Transaction;", nativeQuery=true)
	Long getNextId();
	List<Transaction> findByDateBetween(LocalDateTime start, LocalDateTime end);
}
