package com.synergisticit.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.synergisticit.domain.Customer;
import com.synergisticit.domain.User;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	@Query("SELECT MAX(id) + 1 FROM Customer")
    Long nextId();
	@Query("SELECT COUNT(c) > 0 FROM Customer c WHERE c.user.username = :username")
    boolean existsByUserUsername(String username);
	@Query("SELECT u FROM User u WHERE NOT EXISTS (SELECT c FROM Customer c WHERE c.user = u)")
    List<User> findUsersNotAssociatedWithCustomer();
	@Query("SELECT u FROM User u WHERE u.username IN (SELECT c.user.username from Customer c where c.user.username=:username)")
	User findUserInCustomer(@Param("username") String username);
	Customer findByUserUsername(String username);
	Page<Customer> findAll(Pageable pageable);
	List<Customer> findAll(Sort sort);
	
}
