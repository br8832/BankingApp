package com.synergisticit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.synergisticit.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	@Query("SELECT MAX(id) + 1 FROM Customer")
    Long nextId();
}
