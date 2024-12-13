package com.alice.examensarbete_backend.controller;

import com.alice.examensarbete_backend.model.AuthorApiModel;
import com.alice.examensarbete_backend.model.AuthorWorksApiModel;
import com.alice.examensarbete_backend.model.BookSearchApiModel;
import com.alice.examensarbete_backend.model.OneBookApiModel;
import com.alice.examensarbete_backend.service.ApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

  private final ApiService apiService;

  public ApiController(ApiService apiService) {
    this.apiService = apiService;
  }

  //Search for authors
  @GetMapping("/author/{author}")
  public ResponseEntity<List<AuthorApiModel>> getAuthor(@PathVariable String author) {
    List<AuthorApiModel> authors = apiService.searchAuthors(author);
    return ResponseEntity.ok(authors);
  }

  //Search for books by title
  @GetMapping("/book/{bookName}")
  public ResponseEntity<List<BookSearchApiModel>> getBook(@PathVariable String bookName) {
    List<BookSearchApiModel> book = apiService.getBookList(bookName);
    return ResponseEntity.ok(book);
  }

  //Works(books) by an author(author key)
  @GetMapping("/author/{authorId}/works")
  public ResponseEntity<List<AuthorWorksApiModel>> getAuthorWorks(@PathVariable String authorId) {
    List<AuthorWorksApiModel> works = apiService.getAuthorWorks(authorId);
    return ResponseEntity.ok(works);
  }

  //Get one book
  @GetMapping("/works/{bookKey}")
  public ResponseEntity<OneBookApiModel> getOneBook(@PathVariable String bookKey) {
    OneBookApiModel book = apiService.getOneBook(bookKey);
    return ResponseEntity.ok(book);
  }

  //Get one author
  @GetMapping("/oneAuthor/{authorId}")
  public ResponseEntity<AuthorApiModel> getOneAuthor(@PathVariable String authorId) {
    AuthorApiModel author = apiService.getOneAuthor(authorId);
    return ResponseEntity.ok(author);
  }

}
