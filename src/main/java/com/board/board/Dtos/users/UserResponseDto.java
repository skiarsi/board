package com.board.board.Dtos.users;

import com.board.board.Entity.User.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {
  private String name;
  private String email;
  private UserRole role;
}
