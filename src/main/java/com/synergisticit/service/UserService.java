package com.synergisticit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.User;
import com.synergisticit.repository.UserRepository;
@Service
public class UserService {
	@Autowired UserRepository userRepository;
	public Long getNextId() {
		return userRepository.nextId();
	}
	public User save(User user) {
		return userRepository.save(user);
		
	}
	public List<User> findAll()
	{
		return userRepository.findAll();
	}
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}
	public User findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
    public List<User> findAll(String sortBy) {
        return userRepository.findAll(Sort.by(sortBy));
    }
    public Page<User> findAll(Pageable page) {
        return userRepository.findAll(page);
    }
	public long getRecordCount() {
		return userRepository.count();
	}
	
}
