package com.alice.examensarbete_backend.controller;

import com.alice.examensarbete_backend.model.AuthorApiModel;
import com.alice.examensarbete_backend.model.BookApiModel;
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

    @GetMapping("/author/{author}")
    public ResponseEntity<List<AuthorApiModel>> getAuthor(@PathVariable String author) {
        List<AuthorApiModel> authors = apiService.getAuthors(author);
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/book/{bookName}")
    public ResponseEntity<List<BookApiModel>> getBook(@PathVariable String bookName) {
        List<BookApiModel> book = apiService.getBooks(bookName);
        return ResponseEntity.ok(book);
    }

}
