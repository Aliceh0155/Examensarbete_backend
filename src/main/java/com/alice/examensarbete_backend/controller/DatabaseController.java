package com.alice.examensarbete_backend.controller;

import com.alice.examensarbete_backend.entity.AuthorEntity;
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

  @PostMapping("/addAuthor/{authorId}")
  public ResponseEntity<String> addAuthorToDatabase(@PathVariable String authorId){

    databaseService.addAuthorToDatabase(authorId);
    return ResponseEntity.ok("Author added to database");
  }

  @GetMapping("/getAuthorsFromDatabase")
  public ResponseEntity<List<AuthorEntity>> getAllAuthors() {
    List<AuthorEntity> authors = databaseService.getAllAuthors();
    return ResponseEntity.ok(authors);
  }

  @PostMapping("/addBooksToDatabase")
  public ResponseEntity<String> addBooksToDatabase(){
    databaseService.addBooksToDatabase();
    return ResponseEntity.ok("Books added to database");
  }

}
