package com.synergisticit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
}