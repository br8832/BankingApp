package com.synergisticit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.synergisticit.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT MAX(id) + 1 FROM User")
    Long nextId();

	User findByUsername(String username);
}
