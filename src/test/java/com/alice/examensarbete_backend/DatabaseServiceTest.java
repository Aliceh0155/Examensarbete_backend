package com.alice.examensarbete_backend;

import com.alice.examensarbete_backend.database.AuthorDocument;
import com.alice.examensarbete_backend.model.AuthorApiModel;
import com.alice.examensarbete_backend.repository.AuthorRepository;
import com.alice.examensarbete_backend.service.ApiService;
import com.alice.examensarbete_backend.service.DatabaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DatabaseServiceTest {
  @Mock
  private ApiService apiService;

  @Mock
  private AuthorRepository authorRepository;

  @InjectMocks
  private DatabaseService databaseService;

  private String authorId;

  @BeforeEach
  void setUp() {
    authorId = "OL23919A";
  }

  @Test
  @DisplayName("Add author to the database")
  void testAddAuthorToDatabase() {
    AuthorApiModel mockAuthorApiModel = new AuthorApiModel();
    mockAuthorApiModel.setKey(authorId);
    mockAuthorApiModel.setName("Test Author");
    mockAuthorApiModel.setBio("Bio bio bio");

    AuthorDocument mockAuthorDocument = new AuthorDocument();
    mockAuthorDocument.setKey(mockAuthorApiModel.getKey());
    mockAuthorDocument.setName(mockAuthorApiModel.getName());
    mockAuthorDocument.setBio(mockAuthorApiModel.getBio());

    // Mock external calls
    when(apiService.getOneAuthor(authorId)).thenReturn(mockAuthorApiModel);
    when(authorRepository.save(any(AuthorDocument.class))).thenReturn(mockAuthorDocument);

    // Act
    databaseService.addAuthorToDatabase(authorId);

    assertEquals("OL23919A", mockAuthorDocument.getKey());
    assertEquals("Test Author", mockAuthorDocument.getName());
    assertEquals("Bio bio bio", mockAuthorDocument.getBio());
  }


}

