package com.synergisticit.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.Customer;
import com.synergisticit.domain.User;
import com.synergisticit.repository.CustomerRepository;
@Service
public class CustomerService {
	@Autowired CustomerRepository customerRepository;
	public List<User> availableUsers(){
		return customerRepository.findUsersNotAssociatedWithCustomer();
	}
	public boolean existsAlready(String username) {
		return customerRepository.existsByUserUsername(username);
	}
	public Long getNextId() {
		return customerRepository.nextId();
	}
	public Customer save(Customer customer) {
		Optional<Customer> persisted = customerRepository.findById(customer.getId());
		if(persisted.isPresent()){
			Customer c = persisted.get();
			c.setName(customer.getName());
			c.setGender(customer.getGender());
			c.setDob(customer.getDob());
			c.setMobile(customer.getMobile());
			c.setAddress(customer.getAddress());
			c.setSSN(customer.getSSN());
			c.setAccounts(customer.getAccounts());
			c.setUser(customer.getUser());
			System.out.println("C in sercice"+c);
			customerRepository.save(c);
		}
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
	public List<Customer> findByUserUsername(String name) {
		return new ArrayList<Customer>(List.of(customerRepository.findByUserUsername(name)));
	}
	public List<User> findUserInCustomer(String name) {
		return new ArrayList<User>(List.of(customerRepository.findUserInCustomer(name)));
	}
	public List<Customer> findAll(String sortBy) {
        return customerRepository.findAll(Sort.by(sortBy));
    }
    public Page<Customer> findAll(Pageable page) {
        return customerRepository.findAll(page);
    }
	public long getRecordCount() {
		return customerRepository.count();
	}
}