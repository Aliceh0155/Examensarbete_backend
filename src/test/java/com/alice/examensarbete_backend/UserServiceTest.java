package com.alice.examensarbete_backend;

import com.alice.examensarbete_backend.authorities.UserRole;
import com.alice.examensarbete_backend.database.CustomUser;
import com.alice.examensarbete_backend.model.dto.CustomUserLoginDTO;
import com.alice.examensarbete_backend.repository.UserRepository;
import com.alice.examensarbete_backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock
  UserRepository userRepository;
  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  UserService userService;

  CustomUserLoginDTO loginUserDTO;

  @BeforeEach
  void setup() {
    loginUserDTO = new CustomUserLoginDTO();
    loginUserDTO.setUsername("newuser");
    loginUserDTO.setPassword("password");
  }

  @Test
  @DisplayName("Register a new user")
  void testRegisterUser() {

    when(userRepository.existsByUsername(loginUserDTO.getUsername())).thenReturn(false);
    when(passwordEncoder.encode(loginUserDTO.getPassword())).thenReturn("encodedPassword");

    String result = userService.registerUser(loginUserDTO);

    assertEquals("User registered successfully", result);

  }

  @Test
  @DisplayName("Register a user when username already exists")
  void testRegisterUserAlreadyExists() {

    when(userRepository.existsByUsername(loginUserDTO.getUsername())).thenReturn(true);

    String result = userService.registerUser(loginUserDTO);

    assertEquals("Username is already taken", result);
  }

  @Test
  @DisplayName("Create a default user")
  void testCreateDefaultUser() {
    String encodedPassword = "encoded123";
    when(passwordEncoder.encode("123")).thenReturn(encodedPassword);

    CustomUser mockUser = new CustomUser(
            "Benny",
            encodedPassword,
            UserRole.ADMIN,
            true,
            true,
            true,
            true
    );
    when(userRepository.save(any(CustomUser.class))).thenReturn(mockUser);

    CustomUser createdUser = userService.createDefaultUser();

    assertNotNull(createdUser);

    assertEquals("Benny", createdUser.getUsername());

    assertEquals(encodedPassword, createdUser.getPassword());
  }

}
