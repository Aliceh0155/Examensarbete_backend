package com.alice.examensarbete_backend.controller;

import com.alice.examensarbete_backend.database.AuthorDocument;
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

  @GetMapping("/fetchAndSaveAuthors")
  public String fetchAndSaveAuthors() {
    try {
      databaseService.fetchAndSaveAuthors();
      return "Författare och böcker har sparats i databasen.";
    } catch (Exception e) {
      return "Fel vid hämtning och sparande av författare och böcker: " + e.getMessage();
    }
  }

  //Add an author to database via authorID
  @PostMapping("/addAuthor/{authorId}")
  public ResponseEntity<String> addAuthorToDatabase(@PathVariable String authorId){

    databaseService.addAuthorToDatabase(authorId);
    return ResponseEntity.ok("Author added to database");
  }

  //Get all the authors from the database
  @GetMapping("/getAuthorsFromDatabase")
  public ResponseEntity<List<AuthorDocument>> getAllAuthors() {
    List<AuthorDocument> authors = databaseService.getAllAuthors();
    return ResponseEntity.ok(authors);
  }

  //Add all authors works(books) to the database
  @PostMapping("/addBooksToDatabase")
  public ResponseEntity<String> addBooksToDatabase(){
    databaseService.addBooksToDatabase();
    return ResponseEntity.ok("Books added to database");
  }

}
