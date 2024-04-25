package com.synergisticit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.synergisticit.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	@Query("SELECT MAX(id) + 1 FROM Role")
    Long nextId();
}
