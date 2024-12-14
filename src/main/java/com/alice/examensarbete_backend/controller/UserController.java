package com.alice.examensarbete_backend.controller;

import com.alice.examensarbete_backend.config.customUser.CustomUserDetails;
import com.alice.examensarbete_backend.config.security.JwtConfig.JwtUtil;
import com.alice.examensarbete_backend.database.CustomUser;
import com.alice.examensarbete_backend.model.dto.CustomUserLoginDTO;
import com.alice.examensarbete_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
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

  //Get a users want to read list
  @GetMapping("/getWantToRead")
  public ResponseEntity<List<String>> getWantToRead() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    try {
      List<String> wantToRead = userService.getWantToRead(username);
      return ResponseEntity.ok(wantToRead);
    } catch (NoSuchElementException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  //Add a book to a users want to read list
  @PostMapping("/addBookToWantToRead/{bookId}")
  public ResponseEntity<String> addBookToWantToRead(@PathVariable String bookId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    String result = userService.addBookToWantToRead(username, bookId);

    if ("User not found".equals(result)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    } else if ("This book is already in your want to read list".equals(result)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    return ResponseEntity.ok(result);
  }

  //Get a users currently reading list
  @GetMapping("/getCurrentlyReading")
  public ResponseEntity<List<String>> getCurrentlyReading() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    try {
      List<String> currentlyReading = userService.getCurrentlyReading(username);
      return ResponseEntity.ok(currentlyReading);
    } catch (NoSuchElementException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  //Add a book to a users currently reading list
  @PostMapping("/addBookToCurrentlyReading/{bookId}")
  public ResponseEntity<String> addBookToCurrentlyReading(@PathVariable String bookId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    String result = userService.addBookToCurrentlyReading(username, bookId);

    if ("User not found".equals(result)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    } else if ("This book is already in your currently reading list".equals(result)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    return ResponseEntity.ok(result);
  }

  //Get a users favorite books list
  @GetMapping("/getFavoriteBooks")
  public ResponseEntity<List<String>> getFavoriteBooks() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    try {
      List<String> favoriteBooks = userService.getFavoriteBooks(username);
      return ResponseEntity.ok(favoriteBooks);
    } catch (NoSuchElementException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  //Add a book to a users favorites list
  @PostMapping("/addBookToFavorites/{bookId}")
  public ResponseEntity<String> addBookToFavorites(@PathVariable String bookId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    String result = userService.addBookToFavorites(username, bookId);

    if ("User not found".equals(result)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    } else if ("This book is already in your favorites list".equals(result)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    return ResponseEntity.ok(result);
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
