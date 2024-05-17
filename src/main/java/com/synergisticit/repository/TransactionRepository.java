package com.synergisticit.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.synergisticit.domain.Transaction;
import com.synergisticit.domain.User;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	@Query(value="Select MAX(id)+1 from Transaction;", nativeQuery=true)
	Long getNextId();
	List<Transaction> findByDateBetween(LocalDateTime start, LocalDateTime end);
	@Query("SELECT t FROM Transaction t WHERE t.fromAccount = :accountId OR t.toAccount = :accountId")
	List<Transaction> findByAccountId(@Param("accountId") Long accountId);
//	@Query("SELECT t FROM Transaction t JOIN Account a ON (t.fromAccount = a.id OR t.toAccount = a.id) WHERE a.customer.user.username = :username AND t.date BETWEEN :start AND :end")
//	List<Transaction> findByUserUsernameAndDateBetween(@Param("username") String username, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
//	Page<Transaction> findAll(Pageable pageable);
	List<Transaction> findAll(Sort sort);
	@Query("SELECT t FROM Transaction t JOIN Account a ON (t.fromAccount = a.id OR t.toAccount = a.id) WHERE a.customer.user.username = :username AND t.date BETWEEN :start AND :end")
	Page<Transaction> findByUserUsernameAndDateBetween(@Param("username") String username, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);
    default List<Transaction> findByDateBetweenForUser(LocalDateTime start, LocalDateTime end, String username) {
        return this.findByUserUsernameAndDateBetween(username, start, end, PageRequest.of(0, Integer.MAX_VALUE)).getContent();
    }
}
