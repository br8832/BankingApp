package com.synergisticit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.User;
import com.synergisticit.repository.UserRepository;
@Service
public class UserService {
	@Autowired UserRepository userRepository;
	
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
	
}