package com.synergisticit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.Customer;
import com.synergisticit.repository.CustomerRepository;
@Service
public class CustomerService {
@Autowired CustomerRepository customerRepository;
	
	public Customer save(Customer customer) {
		return customerRepository.save(customer);
		
	}
	public List<Customer> findAll()
	{
		return customerRepository.findAll();
	}
	public void deleteById(Long id) {
		customerRepository.deleteById(id);
	}
	public Customer findById(Long id) {
		return customerRepository.findById(id).orElse(null);
	}
}