package com.alice.examensarbete_backend.service;

import com.alice.examensarbete_backend.model.*;
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

    public List<BookSearchApiModel> getBookList(String bookName) {
        String url = "https://openlibrary.org/search.json?q={bookName}";

        BookSearchWrapperClass response = restTemplate.getForObject(url, BookSearchWrapperClass.class, bookName);

        if (response != null && response.getDocs() != null) {
            return response.getDocs();
        }
        return new ArrayList<>(); // Return an empty list if no books are found or response is null
    }

    public List<AuthorWorksApiModel> getAuthorWorks(String authorId) {
        if (authorId.startsWith("/authors/")) {
            authorId = authorId.substring(9);  // Tar bort "/authors/"
        }
        System.out.println("Fetching works for authorId: " + authorId);
        String url = "https://openlibrary.org/authors/{authorId}/works.json";

        AuthorWorksWrapperClass response = restTemplate.getForObject(url, AuthorWorksWrapperClass.class, authorId);

        if (response != null && response.getEntries() != null) {
            return response.getEntries();
        }

        // Returnera en tom lista om inget hittas
        return List.of();
    }

    public OneBookApiModel getOneBook(String bookKey) {
        if (bookKey.startsWith("/works/")) {
            bookKey = bookKey.substring(7);
        }

        System.out.println("BOOK KEY: " +bookKey);
        String url = "https://openlibrary.org/works/{bookKey}.json";

        OneBookApiModel book = restTemplate.getForObject(url, OneBookApiModel.class, bookKey);

        if (book == null) {
            throw new RuntimeException("Work not found for key: " + bookKey);
        }

        return book;
    }

    public AuthorApiModel getOneAuthor(String authorId) {
        String url = "https://openlibrary.org/authors/{authorId}.json";
        AuthorApiModel author = restTemplate.getForObject(url, AuthorApiModel.class, authorId);

        if (author == null) {
            throw new RuntimeException("Author not found for key: " + authorId);
        }
        return author;
    }

}
