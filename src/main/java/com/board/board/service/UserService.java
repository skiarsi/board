package com.board.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.board.board.Dtos.users.UserRegisterDto;
import com.board.board.Entity.User;
import com.board.board.repository.userRepository;

@Service
public class UserService {
  
  @Autowired
  userRepository repository;

  @Autowired
  private PasswordEncoder passwordEncoder;
  

  public List<User> findAll(){
    return repository.findAll();
  }

  // register user
  public User registerUser(UserRegisterDto dto){
    if(repository.existsByEmail(dto.getEmail())){
      throw new RuntimeException("Email already taken");
    }

    User user = new User();
    user.setName(dto.getName());
    user.setEmail(dto.getEmail());
    user.setRole(dto.getRole());
    user.setPassword(passwordEncoder.encode(dto.getPassword()));

    return repository.save(user);
  }

  public User findByEmail(String email){
    return repository.findByEmail(email)
                                  .orElseThrow(()->new RuntimeException("Can't find user email"));
  }
  

  public User find(Long id){
    return repository.findById(id).orElseThrow(()-> new RuntimeException("Can not find user"));
  }
  
}
