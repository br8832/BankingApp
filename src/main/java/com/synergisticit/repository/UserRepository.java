package com.synergisticit.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.synergisticit.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT MAX(id) + 1 FROM User")
    Long nextId();

	User findByUsername(String username);
	Page<User> findAll(Pageable pageable);
	List<User> findAll(Sort sort);
}
