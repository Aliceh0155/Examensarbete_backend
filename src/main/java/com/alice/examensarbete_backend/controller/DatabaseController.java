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
  @GetMapping("/getOneAuthorFromDatabase/{id}")
  public AuthorDocument getAuthorById(@PathVariable String id) {
    return databaseService.getAuthorById(id);
  }

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
