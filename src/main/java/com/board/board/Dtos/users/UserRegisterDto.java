package com.board.board.Dtos.users;

import com.board.board.Entity.User.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegisterDto {
  private String name;
  private String email;
  private String password;
  private UserRole role;
}
