package com.alice.examensarbete_backend.service;

import com.alice.examensarbete_backend.entity.AuthorEntity;
import com.alice.examensarbete_backend.entity.BookEntity;
import com.alice.examensarbete_backend.model.AuthorApiModel;
import com.alice.examensarbete_backend.model.AuthorWorksApiModel;
import com.alice.examensarbete_backend.model.OneBookApiModel;
import com.alice.examensarbete_backend.repository.AuthorRepository;
import com.alice.examensarbete_backend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseService {


  private final ApiService apiService;
  private final AuthorRepository authorRepository;
  private final BookRepository bookRepository;

  @Autowired
  public DatabaseService(ApiService apiService, AuthorRepository authorRepository, BookRepository bookRepository) {
    this.apiService = apiService;
    this.authorRepository = authorRepository;
    this.bookRepository = bookRepository;
  }

  public void addAuthorToDatabase(String authorId) {
    AuthorApiModel authorApiModel = apiService.getOneAuthor(authorId);
    AuthorEntity authorEntity = convertToAuthorEntity(authorApiModel);
    authorRepository.save(authorEntity);

  }

  public AuthorEntity convertToAuthorEntity(AuthorApiModel authorApiModel) {

    return new AuthorEntity(
            authorApiModel.getKey(),
            authorApiModel.getName(),
            authorApiModel.getBirth_date(),
            authorApiModel.getDeath_date(),
            authorApiModel.getTop_work(),
            authorApiModel.getWork_count(),
            authorApiModel.getBio()
    );
  }

  public List<AuthorEntity> getAllAuthors() {
    return authorRepository.findAll();
  }

  public void addBooksToDatabase() {
    List<AuthorEntity> authors = authorRepository.findAll();

    for (AuthorEntity authorEntity : authors) {

      List<AuthorWorksApiModel> works = apiService.getAuthorWorks(authorEntity.getKey());

      for (AuthorWorksApiModel work : works) {

        OneBookApiModel bookDetails = apiService.getOneBook(work.getKey());

        if (bookDetails.getKey() != null && bookDetails.getKey().startsWith("/works/")) {
          bookDetails.setKey(bookDetails.getKey().substring(7));
        }

        saveBookToDatabase(bookDetails);
      }
    }
  }

  private void saveBookToDatabase(OneBookApiModel bookDetails) {
    BookEntity book = new BookEntity();
    book.setTitle(bookDetails.getTitle());
    book.setKey(bookDetails.getKey());
    book.setDescription(bookDetails.getDescription());
    book.setCovers(bookDetails.getCovers());
    book.setSubjects(bookDetails.getSubjects());

    bookRepository.save(book);
  }



}
