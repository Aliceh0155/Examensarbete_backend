package com.alice.examensarbete_backend.controller;

import com.alice.examensarbete_backend.database.AuthorDocument;
import com.alice.examensarbete_backend.database.BookDocument;
import com.alice.examensarbete_backend.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/database")
public class DatabaseController {


  private final DatabaseService databaseService;


  @Autowired
  public DatabaseController(DatabaseService databaseService) {
    this.databaseService = databaseService;
  }

  //Get all books by one author
  @GetMapping("/booksByAuthor/{authorKey}")
  public ResponseEntity<List<String>> getBooksByAuthorId(@PathVariable String authorKey) {
      List<String> bookKeys = databaseService.getBooksByAuthorId(authorKey);
      return ResponseEntity.ok(bookKeys);
  }

  //Get one book from database
  @GetMapping("/getOneBookFromDatabase/{id}")
  public ResponseEntity<BookDocument> getBookById(@PathVariable String id) {
    return ResponseEntity.ok(databaseService.getBookById(id));
  }

  //Get all books from database
  @GetMapping("/getAllBooksFromDatabase")
  public ResponseEntity<List<BookDocument>> getAllBooks() {
    return ResponseEntity.ok(databaseService.getAllBooks());
  }

  //Get one author from database
  @GetMapping("/getOneAuthorFromDatabase/{key}")
  public ResponseEntity<AuthorDocument> getAuthorByKey(@PathVariable String key) {
    return ResponseEntity.ok(databaseService.getAuthorByKey(key));
  }

  //Get all the authors from database
  @GetMapping("/getAllAuthorsFromDatabase")
  public ResponseEntity<List<AuthorDocument>> getAllAuthors() {
    List<AuthorDocument> authors = databaseService.getAllAuthors();
    return ResponseEntity.ok(authors);
  }

  // Update books cover url
  @PutMapping("/updateBookCovers")
  public ResponseEntity<String> updateBookCovers() {
    databaseService.updateCoverImageUrls();
    return ResponseEntity.ok("Cover images updated successfully!");
  }

  //Save all chosen authors to database
  @GetMapping("/fetchAndSaveAuthors")
  public ResponseEntity<String> fetchAndSaveAuthors() {
      databaseService.fetchAndSaveAuthors();
      return ResponseEntity.ok("Authors saved successfully");
  }

  //Add an author from api to database via authorID
  @PostMapping("/addAuthor/{authorId}")
  public ResponseEntity<String> addAuthorToDatabase(@PathVariable String authorId){
    databaseService.addAuthorToDatabase(authorId);
    return ResponseEntity.ok("Author added to database: " + authorId);
  }

  //Add all authors works(books) to the database
  @PostMapping("/addBooksToDatabase")
  public ResponseEntity<String> addBooksToDatabase(){
    databaseService.addBooksToDatabase();
    return ResponseEntity.ok("Books added to database");
  }

}
