package com.alice.examensarbete_backend.service;

import com.alice.examensarbete_backend.authorities.UserRole;
import com.alice.examensarbete_backend.database.CustomUser;
import com.alice.examensarbete_backend.model.dto.CustomUserLoginDTO;
import com.alice.examensarbete_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public String registerUser(CustomUserLoginDTO userDto) {
    if (userRepository.existsByUsername(userDto.getUsername())) {
      return "Username is already taken";
    }

    CustomUser newUser = new CustomUser();
    newUser.setUsername(userDto.getUsername());
    newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
    newUser.setUserRole(UserRole.USER);
    newUser.setAccountNonExpired(true);
    newUser.setAccountNonLocked(true);
    newUser.setCredentialsNonExpired(true);
    newUser.setEnabled(true);

    userRepository.save(newUser);
    return "User registered successfully";
  }

  //Skapa en default user
  public CustomUser createDefaultUser() {
    CustomUser customUser = new CustomUser(
            "Benny",
            passwordEncoder.encode("123"),
            UserRole.ADMIN,
            true,
            true,
            true,
            true
    );
    return userRepository.save(customUser);
  }

  // Admin sida
  public String getAdminPageContent() {
    return "THIS IS THE ADMIN PAGE";
  }

}
