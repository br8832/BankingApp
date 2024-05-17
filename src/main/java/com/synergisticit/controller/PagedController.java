package com.synergisticit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.synergisticit.domain.User;
import com.synergisticit.repository.UserRepository;

@Controller
public class PagedController {

    @Autowired UserRepository userRepository;

    
    @RequestMapping("/findTheUsers")
    public ResponseEntity<List<User>> findTheUsers(@RequestParam int pageNo, @RequestParam int pageSize, @RequestParam String sortedBy){
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy).descending());
        Page<User> pagedUser = userRepository.findAll(pageable);
        List<User> users = pagedUser.getContent();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
       
    }
    
    // getths to work

    @RequestMapping("/pagedUser")
    public String findThePagedUser(@RequestParam int pageNo, @RequestParam int pageSize, @RequestParam String sortedBy, Model model) {
        System.out.println(pageNo+" "+pageSize+" "+sortedBy);
    	Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy));
        Page<User> pageOfUsers = userRepository.findAll(pageable);
        System.out.println(pageOfUsers);
        List<User> users = pageOfUsers.getContent();
        System.out.println(model);
        model.addAttribute("users", users);
        model.addAttribute("totalPages", pageOfUsers.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("sortedBy", sortedBy);
        System.out.println(model);
        return "pagedUser";
    }


}