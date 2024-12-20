package com.alice.examensarbete_backend.service;

import com.alice.examensarbete_backend.database.AuthorDocument;
import com.alice.examensarbete_backend.database.BookDocument;
import com.alice.examensarbete_backend.model.AuthorApiModel;
import com.alice.examensarbete_backend.model.AuthorWorksApiModel;
import com.alice.examensarbete_backend.model.OneBookApiModel;
import com.alice.examensarbete_backend.repository.AuthorRepository;
import com.alice.examensarbete_backend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

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

  // Get books by an author
  public List<String> getBooksByAuthorId(String authorKey) {
    Optional<AuthorDocument> author = authorRepository.findByKey(authorKey);
    if (author.isPresent()) {
      AuthorDocument authorDocument = author.get();
      System.out.println("Found author: " + authorDocument.getName());
      return authorDocument.getBookKeys();
    } else {
      throw new RuntimeException("Author not found");
    }
  }

  //Get one book from database
  public BookDocument getBookById(String id) {
    return bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
  }

  //Get all books from database
  public List<BookDocument> getAllBooks() {
    return bookRepository.findAll();
  }

  //Get one author from database
  public AuthorDocument getAuthorByKey(String key) {
    return authorRepository.findByKey(key)
            .orElseThrow(() -> new RuntimeException("Author not found with key: " + key));
  }
//  public AuthorDocument getAuthorById(String id) {
//    return authorRepository.findById(id)
//            .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
//  }


  //Get all authors from database
  public List<AuthorDocument> getAllAuthors() {
    return authorRepository.findAll();
  }

  //Update all books cover url based on cover id
  public void updateCoverImageUrls() {
    List<BookDocument> books = bookRepository.findAll();

    for (BookDocument book : books) {
      System.out.println("BOOK:" + book);
      if (book.getCovers() != null && !book.getCovers().isEmpty()) {
        Long coverId = book.getCovers().get(0);
        String coverImageUrl = "https://covers.openlibrary.org/b/id/" + coverId + "-L.jpg";
        book.setCoverImageUrl(coverImageUrl);
        System.out.println("COVER URL: " + coverImageUrl);
      } else {
        book.setCoverImageUrl("");
      }
    }
    bookRepository.saveAll(books);
  }


  //List of chosen authors (id)
  private static final List<String> AUTHOR_IDS = List.of(
          "OL23919A", "OL7115219A", "OL32541A", "OL26680A", "OL177745A", "OL26320A", "OL7919580A", "OL8121764A", "OL19450A", "OL31574A", "OL22098A", "OL24638A", "OL113362A", "OL34184A", "OL30765A", "OL27349A", "OL9123091A", "OL7823565A", "OL1478799A", "OL6943290A", "OL28127A", "OL6591369A", "OL7722491A", "OL3072525A", "OL7758106A", "OL7544629A", "OL1425972A", "OL4586796A", "OL23767A", "OL20646A", "OL9180803A"

  );

  //Get id and populate the database
  public void fetchAndSaveAuthors() {
    for (String authorId : AUTHOR_IDS) {
      try {
        AuthorApiModel authorApiModel = apiService.getOneAuthor(authorId);

        String authorKey = authorApiModel.getKey();
        if (authorKey != null && authorKey.startsWith("/authors/")) {
          authorKey = authorKey.substring(9);
        }
        authorApiModel.setKey(authorKey);

        List<String> bookKeys = apiService.getBookKeysForAuthor(authorApiModel.getKey());

        for (int i = 0; i < bookKeys.size(); i++) {
          String bookKey = bookKeys.get(i);
          if (bookKey != null && bookKey.startsWith("/works/")) {
            bookKeys.set(i, bookKey.substring(7));
          }
        }
        AuthorDocument authorDocument = convertToAuthorEntity(authorApiModel);
        authorDocument.setBookKeys(bookKeys);

        authorRepository.save(authorDocument);

      } catch (Exception e) {
        System.err.println("Error while saving author to database: " + authorId + " - " + e.getMessage());
      }
    }
  }


  //Add one author to the database
  public void addAuthorToDatabase(String authorId) {
    AuthorApiModel authorApiModel = apiService.getOneAuthor(authorId);
    AuthorDocument authorDocument = convertToAuthorEntity(authorApiModel);
    authorRepository.save(authorDocument);

  }

  //Convert author from model to database document
  public AuthorDocument convertToAuthorEntity(AuthorApiModel authorApiModel) {

    AuthorDocument authorDocument = new AuthorDocument();
    authorDocument.setKey(authorApiModel.getKey());
    authorDocument.setName(authorApiModel.getName());
    authorDocument.setBirth_date(authorApiModel.getBirth_date());
    authorDocument.setDeath_date(authorApiModel.getDeath_date());
    authorDocument.setTop_work(authorApiModel.getTop_work());
    authorDocument.setWork_count(authorApiModel.getWork_count());
    authorDocument.setBio(authorApiModel.getBio());
    return authorDocument;
  }


  //Add all authors works(books) to the database
  public void addBooksToDatabase() {
    List<AuthorDocument> authors = authorRepository.findAll();

    for (AuthorDocument authorDocument : authors) {

      List<AuthorWorksApiModel> works = apiService.getAuthorWorks(authorDocument.getKey());

      for (AuthorWorksApiModel work : works) {

        OneBookApiModel bookDetails = apiService.getOneBook(work.getKey());

        if (bookDetails.getKey() != null && bookDetails.getKey().startsWith("/works/")) {
          bookDetails.setKey(bookDetails.getKey().substring(7));
        }

        saveBookToDatabase(bookDetails, authorDocument);
      }
    }
  }

  //Convert from book model to book document and save to database
  private void saveBookToDatabase(OneBookApiModel bookDetails, AuthorDocument authorDocument) {
    BookDocument book = new BookDocument();
    book.setTitle(bookDetails.getTitle());
    book.setKey(bookDetails.getKey());
    book.setDescription(bookDetails.getDescription());
    book.setCovers(bookDetails.getCovers());
    book.setSubjects(bookDetails.getSubjects());
    book.setCoverImageUrl("");
    book.setRatingsAverage(generateRating());
    book.setAuthorName(authorDocument.getName());
    book.setAuthorKey(authorDocument.getKey());

    bookRepository.save(book);
  }

  public double generateRating() {
    double randomRating = 1.0 + (4.6 - 1.0) * Math.random();
    return Math.round(randomRating * 100.0) / 100.0;
  }



}
