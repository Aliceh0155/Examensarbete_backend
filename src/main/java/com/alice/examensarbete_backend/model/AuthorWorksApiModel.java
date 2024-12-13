package com.alice.examensarbete_backend.model;

public class AuthorWorksApiModel {
  private String title;
  private String key;

  public AuthorWorksApiModel() {}

  public AuthorWorksApiModel(String title, String key) {
    this.title = title;
    this.key = key;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }
}
