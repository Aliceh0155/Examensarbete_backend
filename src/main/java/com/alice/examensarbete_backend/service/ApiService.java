package com.alice.examensarbete_backend.service;

import com.alice.examensarbete_backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiService {

  private final RestTemplate restTemplate;

  @Autowired
  public ApiService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  //Get book keys from books by an author
  public List<String> getBookKeysForAuthor(String authorId) {
    if (authorId.startsWith("/authors/")) {
      authorId = authorId.substring(9);
    }
    String url = "https://openlibrary.org/authors/{authorId}/works.json?limit=50";
    ResponseEntity<AuthorWorksWrapperClass> response = restTemplate.exchange(url, HttpMethod.GET, null, AuthorWorksWrapperClass.class, authorId);

    if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
      return response.getBody().getEntries().stream()
              .map(AuthorWorksApiModel::getKey)
              .collect(Collectors.toList());
    }
    return new ArrayList<>();
  }

  //Search for authors by name
  public List<AuthorApiModel> searchAuthors(String author) {
    String url = "https://openlibrary.org/search/authors.json?q={author}";

    AuthorWrapperClass response = restTemplate.getForObject(url, AuthorWrapperClass.class, author);

    if (response != null && response.getDocs() != null) {
      return response.getDocs();
    }
    return new ArrayList<>(); // Return an empty list if no authors are found or response is null
  }

  //Search for books by title
  public List<BookSearchApiModel> getBookList(String bookName) {
    String url = "https://openlibrary.org/search.json?q={bookName}";

    BookSearchWrapperClass response = restTemplate.getForObject(url, BookSearchWrapperClass.class, bookName);

    if (response != null && response.getDocs() != null) {
      return response.getDocs();
    }
    return new ArrayList<>(); // Return an empty list if no books are found or response is null
  }

  //Get works(books) by an author
  public List<AuthorWorksApiModel> getAuthorWorks(String authorId) {
    if (authorId.startsWith("/authors/")) {
      authorId = authorId.substring(9);  // Tar bort "/authors/"
    }
    System.out.println("Fetching works for authorId: " + authorId);
//    try {
//      Thread.sleep(3000);
//    } catch (InterruptedException e) {
//      Thread.currentThread().interrupt();
//    }
    String url = "https://openlibrary.org/authors/{authorId}/works.json?limit=50";
    AuthorWorksWrapperClass response = restTemplate.getForObject(url, AuthorWorksWrapperClass.class, authorId);
    if (response != null && response.getEntries() != null) {
      return response.getEntries();
    }
    return List.of();
  }

  //Get one book
  public OneBookApiModel getOneBook(String bookKey) {
    if (bookKey.startsWith("/works/")) {
      bookKey = bookKey.substring(7);
    }

    System.out.println("BOOK KEY: " + bookKey);
    String url = "https://openlibrary.org/works/{bookKey}.json";

    OneBookApiModel book = restTemplate.getForObject(url, OneBookApiModel.class, bookKey);

    if (book.getKey().startsWith("/works/")) {
      book.setKey(book.getKey().substring(7));
    }

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
