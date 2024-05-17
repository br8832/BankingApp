package com.synergisticit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.Branch;
import com.synergisticit.repository.BranchRepository;
@Service
public class BranchService {
	@Autowired BranchRepository branchRepository;
	public Long getNextId() {
		return branchRepository.nextId();
	}
	public Branch save(Branch branch) {
		return branchRepository.save(branch);
		
	}
	public List<Branch> findAll()
	{
		return branchRepository.findAll();
	}
	public void deleteById(Long id) {
		branchRepository.deleteById(id);
	}
	public Branch findById(Long id) {
		// TODO Auto-generated method stub
		return branchRepository.findById(id).orElse(null);
	}
	public List<Branch> findAll(String sortBy) {
        return branchRepository.findAll(Sort.by(sortBy));
    }
    public Page<Branch> findAll(Pageable page) {
        return branchRepository.findAll(page);
    }
	public long getRecordCount() {
		return branchRepository.count();
	}
	
}
