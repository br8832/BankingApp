package com.synergisticit.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.synergisticit.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	@Query(value="Select MAX(id)+1 from Transaction;", nativeQuery=true)
	Long getNextId();
	List<Transaction> findByDateBetween(LocalDateTime start, LocalDateTime end);
	@Query("SELECT t FROM Transaction t WHERE t.fromAccount = :accountId OR t.toAccount = :accountId")
	List<Transaction> findByAccountId(@Param("accountId") Long accountId);
	@Query("SELECT t FROM Transaction t JOIN Account a ON (t.fromAccount = a.id OR t.toAccount = a.id) WHERE a.customer.user.username = :username AND t.date BETWEEN :start AND :end")
	List<Transaction> findByUserUsernameAndDateBetween(@Param("username") String username, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
	
}
