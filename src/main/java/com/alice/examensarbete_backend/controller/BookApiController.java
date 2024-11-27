package com.alice.examensarbete_backend.controller;

import com.alice.examensarbete_backend.model.AuthorApiModel;
import com.alice.examensarbete_backend.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookApiController {

    private final RestTemplate restTemplate;
    private final ApiService apiService;

    @Autowired
    public BookApiController(RestTemplate restTemplate, ApiService apiService) {
        this.restTemplate = restTemplate;
        this.apiService = apiService;
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<AuthorApiModel>> getAuthor(@PathVariable String author) {
        List<AuthorApiModel> authors = apiService.getAuthors(author);
        return ResponseEntity.ok(authors);
    }

}
