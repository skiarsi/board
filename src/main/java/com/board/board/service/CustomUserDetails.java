package com.board.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.board.board.Entity.User;
import com.board.board.repository.userRepository;

@Component
public class CustomUserDetails implements UserDetailsService {

  @Autowired
  userRepository repository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    
    try {
      User user = repository.findByEmail(email)
              .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));  
      
      return new CustomUserDetailsPrincipal(user);

    } catch (Exception e) {
      throw e;
    }
    
    
  }
  
}
