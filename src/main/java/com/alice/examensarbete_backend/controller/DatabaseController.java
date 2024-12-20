package com.alice.examensarbete_backend.controller;

import com.alice.examensarbete_backend.database.AuthorDocument;
import com.alice.examensarbete_backend.database.BookDocument;
import com.alice.examensarbete_backend.repository.AuthorRepository;
import com.alice.examensarbete_backend.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/database")
public class DatabaseController {


  private final DatabaseService databaseService;
  private final AuthorRepository authorRepository;


  @Autowired
  public DatabaseController(DatabaseService databaseService, AuthorRepository authorRepository) {
    this.databaseService = databaseService;
    this.authorRepository = authorRepository;
  }

  //Get all books by one author
  @GetMapping("/booksByAuthor/{authorKey}")
  public ResponseEntity<List<String>> getBooksByAuthorId(@PathVariable String authorKey) {
    try {
      List<String> bookKeys = databaseService.getBooksByAuthorId(authorKey);
      System.out.println("Book Keys: " + bookKeys);
      return ResponseEntity.ok(bookKeys);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
    }
  }

  //Get one book from database
  @GetMapping("/getOneBookFromDatabase/{id}")
  public BookDocument getBookById(@PathVariable String id) {
    return databaseService.getBookById(id);
  }

  //Get all books from database
  @GetMapping("/getAllBooksFromDatabase")
  public List<BookDocument> getAllBooks() {
    return databaseService.getAllBooks();
  }

  //Get one author from database
  @GetMapping("/getOneAuthorFromDatabase/{key}")
  public AuthorDocument getAuthorByKey(@PathVariable String key) {
    return databaseService.getAuthorByKey(key);
  }
//  @GetMapping("/getOneAuthorFromDatabase/{id}")
//  public AuthorDocument getAuthorById(@PathVariable String id) {
//    return databaseService.getAuthorById(id);
//  }

  //Get all the authors from database
  @GetMapping("/getAllAuthorsFromDatabase")
  public ResponseEntity<List<AuthorDocument>> getAllAuthors() {
    List<AuthorDocument> authors = databaseService.getAllAuthors();
    return ResponseEntity.ok(authors);
  }

  // Update books cover url
  @PutMapping("/updateBookCovers")
  public String updateBookCovers() {
    databaseService.updateCoverImageUrls();
    return "Cover images updated successfully!";
  }

  //Save all authors to database
  @GetMapping("/fetchAndSaveAuthors")
  public String fetchAndSaveAuthors() {
    try {
      databaseService.fetchAndSaveAuthors();
      return "Authors saved successfully";
    } catch (Exception e) {
      return "Error while saving authors" + e.getMessage();
    }
  }

  //Add an author to database via authorID
  @PostMapping("/addAuthor/{authorId}")
  public ResponseEntity<String> addAuthorToDatabase(@PathVariable String authorId){

    databaseService.addAuthorToDatabase(authorId);
    return ResponseEntity.ok("Author added to database");
  }

  //Add all authors works(books) to the database
  @PostMapping("/addBooksToDatabase")
  public ResponseEntity<String> addBooksToDatabase(){
    databaseService.addBooksToDatabase();
    return ResponseEntity.ok("Books added to database");
  }

}
