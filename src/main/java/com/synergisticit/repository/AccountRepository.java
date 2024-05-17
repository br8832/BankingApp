package com.synergisticit.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.synergisticit.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	 @Query("SELECT MAX(id) + 1 FROM Account")
	    Long nextId();
	 	List<Account> findByCustomer_User_Username(String name);
	 	Page<Account> findAll(Pageable pageable);
		List<Account> findAll(Sort sort);
}
