package com.alice.examensarbete_backend.service;

import com.alice.examensarbete_backend.model.AuthorApiModel;
import com.alice.examensarbete_backend.model.AuthorWrapperClass;
import com.alice.examensarbete_backend.model.BookApiModel;
import com.alice.examensarbete_backend.model.BookWrapperClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApiService {

    private final RestTemplate restTemplate;

    @Autowired
    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<AuthorApiModel> getAuthors(String author) {
        String url = "https://openlibrary.org/search/authors.json?q={author}";

        AuthorWrapperClass response = restTemplate.getForObject(url, AuthorWrapperClass.class, author);

        if (response != null && response.getDocs() != null) {
            return response.getDocs();
        }
        return new ArrayList<>(); // Return an empty list if no authors are found or response is null
    }

    public List<BookApiModel> getBooks(String bookName) {
        String url = "https://openlibrary.org/search.json?q={bookName}";

        BookWrapperClass response = restTemplate.getForObject(url, BookWrapperClass.class, bookName);

        if (response != null && response.getDocs() != null) {
            return response.getDocs();
        }
        return new ArrayList<>(); // Return an empty list if no books are found or response is null
    }

}
