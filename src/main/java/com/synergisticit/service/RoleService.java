package com.synergisticit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.Role;
import com.synergisticit.repository.RoleRepository;
@Service
public class RoleService {
@Autowired RoleRepository roleRepository;
	public Long getNextId() {
		return roleRepository.nextId();
	}	
	public Role save(Role role) {
		return roleRepository.save(role);
		
	}
	public List<Role> findAll()
	{
		return roleRepository.findAll();
	}
	public void deleteById(Long id) {
		roleRepository.deleteById(id);
	}
	public Role findById(Long id) {
		return roleRepository.findById(id).orElse(null);
	}
	public List<Role> findAll(String sortBy) {
        return roleRepository.findAll(Sort.by(sortBy));
    }
    public Page<Role> findAll(Pageable page) {
        return roleRepository.findAll(page);
    }
	public long getRecordCount() {
		return roleRepository.count();
	}
}