package com.board.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.board.board.Dtos.api.ApiResponse;
import com.board.board.Dtos.users.UserRegisterDto;
import com.board.board.Dtos.users.userLoginDTO;
import com.board.board.Dtos.users.UserResponseDto;
import com.board.board.Entity.User;
import com.board.board.service.UserService;
import com.board.board.utils.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
  

  @Autowired
  UserService service;
  
  @Autowired
  AuthenticationManager authManager;

  @Autowired
  private JwtUtil jwtUtil;
  
  // login
  @PostMapping({"/login/","/login"})
  public ResponseEntity<?> login(@RequestBody userLoginDTO dto, HttpServletResponse response) {
    try {
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        long expirationMillis = dto.isRememberMe() 
                ? 1000L * 60 * 60 * 24 * 7  
                : 1000L * 60 * 60;          

        int maxAgeSeconds = (int) (expirationMillis / 1000);

        String token = jwtUtil.generateJwtToke(authentication.getName(), maxAgeSeconds);

        Cookie jwtCookie = new Cookie("jwtToken", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(false);  
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(60 * 60);

        
        response.addCookie(jwtCookie);

        return ResponseEntity.ok(new ApiResponse<>("Logged in successfully", null));

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>("Invalid email or password", null));
    }
  }


  // register
  @PostMapping({"/register","/register/"})
  public ResponseEntity<?> newUser(@RequestBody UserRegisterDto dto){
    try {
      User newUser = service.registerUser(dto);

      UserResponseDto userRespone = new UserResponseDto(
        newUser.getName(),
        newUser.getEmail(),
        newUser.getRole()
      );

      return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("User Registred successfully", userRespone));

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage(),null));
    }
  }



  @PostMapping({"/logout", "/logout/"})
  public ResponseEntity<?> logout(HttpServletResponse response) {
      Cookie jwtCookie = new Cookie("jwtToken", null);
      jwtCookie.setHttpOnly(true);
      jwtCookie.setPath("/");
      jwtCookie.setMaxAge(0);

      response.addCookie(jwtCookie);

      return ResponseEntity.ok(new ApiResponse<>("Logged out successfully", null));
  }

  @GetMapping({"/me","/me/"})
  public ResponseEntity<?> me(@AuthenticationPrincipal UserDetails userDetails) {
    if (userDetails == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>("Not authenticated", null));
    }

    User user = service.findByEmail(userDetails.getUsername());
    UserResponseDto responseDto = new UserResponseDto(user.getName(), user.getEmail(), user.getRole());

    return ResponseEntity.ok(new ApiResponse<>("Current user info", responseDto));
  }



}
