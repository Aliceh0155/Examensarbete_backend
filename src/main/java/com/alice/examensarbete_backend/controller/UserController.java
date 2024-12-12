package com.alice.examensarbete_backend.controller;

import com.alice.examensarbete_backend.config.customUser.CustomUserDetails;
import com.alice.examensarbete_backend.config.security.JwtConfig.JwtUtil;
import com.alice.examensarbete_backend.database.CustomUser;
import com.alice.examensarbete_backend.model.dto.CustomUserLoginDTO;
import com.alice.examensarbete_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final UserService userService;
  private final JwtUtil jwtUtil;
  private final AuthenticationManager authenticationManager;


  @Autowired
  public UserController(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
    this.userService = userService;
    this.jwtUtil = jwtUtil;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/login")
  public ResponseEntity<String> loginUser(@Valid @RequestBody CustomUserLoginDTO userDTO) {
    UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
    Authentication authentication = authenticationManager.authenticate(authenticationToken);

    CustomUserDetails customUser = (CustomUserDetails) authentication.getPrincipal();

    if (customUser instanceof CustomUserDetails customUserDetails) {
      String role = customUserDetails.getAuthorities().stream()
              .filter(authority -> authority.getAuthority().startsWith("ROLE_"))
              .map(authority -> authority.getAuthority().substring(5))
              .findFirst()
              .orElse("USER");
      String token = jwtUtil.generateJwtToken(customUserDetails.getUsername(), role);
      return ResponseEntity.ok(token);
    } else {
      return ResponseEntity.badRequest().body("Invalid credentials");
    }
  }


  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@RequestBody @Valid CustomUserLoginDTO userDto) {
    try {
      String result = userService.registerUser(userDto);

      if (result.equals("Username is already taken")) {
        return ResponseEntity.badRequest().body(result);
      }

      return ResponseEntity.status(201).body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest()
              .body("Could not register user: " + e.getMessage());
    }
  }


  @GetMapping("/createDefaultUser")
  public CustomUser createDefaultUser() {
    return userService.createDefaultUser();
  }

  @GetMapping("/adminPage")
  public String adminPage() {
    return userService.getAdminPageContent();
  }
}
