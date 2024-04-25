package com.synergisticit.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.synergisticit.domain.Role;
import com.synergisticit.domain.User;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("username: "+username);
		User user = userService.findByUsername(username);
		
		//User(String username, String password, Collection<? extends GrantedAuthority> authorities)
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		for(Role role : user.getRoles()) {
			System.out.println("roleName: "+role.getName());
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), "{noop}"+user.getPassword(), authorities);
	}

}
