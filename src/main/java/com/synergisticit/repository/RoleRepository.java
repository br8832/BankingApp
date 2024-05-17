package com.synergisticit.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.synergisticit.domain.Role;
import com.synergisticit.domain.User;

public interface RoleRepository extends JpaRepository<Role, Long> {
	@Query("SELECT MAX(id) + 1 FROM Role")
    Long nextId();
	Page<Role> findAll(Pageable pageable);
	List<Role> findAll(Sort sort);
}
