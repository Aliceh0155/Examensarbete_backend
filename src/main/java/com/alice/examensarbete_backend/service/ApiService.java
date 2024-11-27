package com.alice.examensarbete_backend.service;

import com.alice.examensarbete_backend.model.AuthorApiModel;
import com.alice.examensarbete_backend.model.AuthorWrapperClass;
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

//    public List<AuthorApiModel> getAuthors (String author){
//        String url = "https://openlibrary.org/search/authors.json?q={author}";
//        AuthorApiModel[] authors = restTemplate.getForObject(url, AuthorApiModel[].class, author);
//        return Arrays.asList(authors);
//
//    }
    public List<AuthorApiModel> getAuthors(String author) {
        String url = "https://openlibrary.org/search/authors.json?q={author}";

        // Create a wrapper class or map the response to a custom object with a "docs" field
        AuthorWrapperClass response = restTemplate.getForObject(url, AuthorWrapperClass.class, author);

        if (response != null && response.getDocs() != null) {
            return response.getDocs();
        }
        return new ArrayList<>(); // Return an empty list if no authors are found or response is null
    }

}
