package com.alice.examensarbete_backend.service;

import com.alice.examensarbete_backend.authorities.UserRole;
import com.alice.examensarbete_backend.database.CustomUser;
import com.alice.examensarbete_backend.model.dto.CustomUserLoginDTO;
import com.alice.examensarbete_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  //Get a users want to read list
  public List<String> getWantToRead(String username) {
    Optional<CustomUser> customUser = userRepository.findByUsername(username);
    if (customUser.isEmpty()) {
      throw new NoSuchElementException("User not found");
    }
    CustomUser user = customUser.get();
    return user.getWantToRead();
  }

  //Add a book to a users want to read list
  public String addBookToWantToRead(String username, String bookId) {
    Optional<CustomUser> customUser = userRepository.findByUsername(username);
    if (customUser.isEmpty()) {
      return "User not found";
    }

    CustomUser user = customUser.get();
    List<String> wantToReadBookId = user.getWantToRead();

    if (!wantToReadBookId.contains(bookId)) {
      wantToReadBookId.add(bookId);
      user.setWantToRead(wantToReadBookId);
      userRepository.save(user);
      return "Book added to want to read list";
    }

    return "This book is already in your want to read list";
  }

  // Remove a book from a users want to read list
  public String removeBookFromWantToRead(String username, String bookId) {
    Optional<CustomUser> customUser = userRepository.findByUsername(username);
    if (customUser.isEmpty()) {
      return "User not found";
    }

    CustomUser user = customUser.get();
    List<String> wantToReadBookId = user.getWantToRead();

    if (wantToReadBookId.contains(bookId)) {
      wantToReadBookId.remove(bookId);
      user.setWantToRead(wantToReadBookId);
      userRepository.save(user);
      return "Book removed from want to read list";
    }

    return "This book is not in your want to read list";
  }

  //Get a users currently reading list
  public List<String> getCurrentlyReading(String username) {
    Optional<CustomUser> customUser = userRepository.findByUsername(username);
    if (customUser.isEmpty()) {
      throw new NoSuchElementException("User not found");
    }
    CustomUser user = customUser.get();
    return user.getCurrentlyReading();
  }

  //Add a book to a users currently reading list
  public String addBookToCurrentlyReading(String username, String bookId) {
    Optional<CustomUser> customUser = userRepository.findByUsername(username);
    if (customUser.isEmpty()) {
      return "User not found";
    }

    CustomUser user = customUser.get();
    List<String> currentlyReadingBookId = user.getCurrentlyReading();

    if (!currentlyReadingBookId.contains(bookId)) {
      currentlyReadingBookId.add(bookId);
      user.setCurrentlyReading(currentlyReadingBookId);
      userRepository.save(user);
      return "Book added to currently reading list";
    }

    return "This book is already in your currently reading list";
  }

  // Remove a book from a users currently reading list
  public String removeBookFromCurrentlyReading(String username, String bookId) {
    Optional<CustomUser> customUser = userRepository.findByUsername(username);
    if (customUser.isEmpty()) {
      return "User not found";
    }

    CustomUser user = customUser.get();
    List<String> currentlyReadingBookId = user.getCurrentlyReading();

    if (currentlyReadingBookId.contains(bookId)) {
      currentlyReadingBookId.remove(bookId);
      user.setCurrentlyReading(currentlyReadingBookId);
      userRepository.save(user);
      return "Book removed from currently reading list";
    }

    return "This book is not in your currently reading list";
  }

  //Get a users favorite books list
  public List<String> getFavoriteBooks(String username) {
    Optional<CustomUser> customUser = userRepository.findByUsername(username);
    if (customUser.isEmpty()) {
      throw new NoSuchElementException("User not found");
    }
    CustomUser user = customUser.get();
    return user.getFavoriteBooks();
  }

  //Add a book to a users favorites list
  public String addBookToFavorites(String username, String bookId) {
    Optional<CustomUser> customUser = userRepository.findByUsername(username);
    if (customUser.isEmpty()) {
      return "User not found";
    }

    CustomUser user = customUser.get();
    List<String> favoriteBookId = user.getFavoriteBooks();

    if (!favoriteBookId.contains(bookId)) {
      favoriteBookId.add(bookId);
      user.setFavoriteBooks(favoriteBookId);
      userRepository.save(user);
      return "Book added to favorite books list";
    }

    return "This book is already in your favorites list";
  }

  // Remove a book from a users favorites list
  public String removeBookFromFavorites(String username, String bookId) {
    Optional<CustomUser> customUser = userRepository.findByUsername(username);
    if (customUser.isEmpty()) {
      return "User not found";
    }

    CustomUser user = customUser.get();
    List<String> favoriteBookId = user.getFavoriteBooks();

    if (favoriteBookId.contains(bookId)) {
      favoriteBookId.remove(bookId);
      user.setFavoriteBooks(favoriteBookId);
      userRepository.save(user);
      return "Book removed from favorite books list";
    }

    return "This book is not in your favorites list";
  }

  //Register a user
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
