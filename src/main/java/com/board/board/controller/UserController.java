package com.board.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.board.board.Entity.User;
import com.board.board.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  UserService service;

  @GetMapping({"/",""})
  public List<User> allusers(){
    return service.findAll();
  }

  @GetMapping({"/{id}"})
  public User oneUser(@PathVariable Long id){
    return service.find(id);
  }
}
