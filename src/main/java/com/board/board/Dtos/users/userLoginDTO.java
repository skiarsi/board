package com.board.board.Dtos.users;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class userLoginDTO {
  private String email;
  private String password;
  private boolean rememberMe;

  // public boolean isRememberMe() { return rememberMe; }
  // public void setRememberMe(boolean rememberMe) { this.rememberMe = rememberMe; }
}
