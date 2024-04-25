package com.synergisticit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.synergisticit.domain.Branch;
@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
	@Query("SELECT MAX(id) + 1 FROM Branch")
    Long nextId();
}
