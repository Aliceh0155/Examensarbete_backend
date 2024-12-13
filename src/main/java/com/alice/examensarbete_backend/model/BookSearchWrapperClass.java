package com.alice.examensarbete_backend.model;

import java.util.List;

public class BookSearchWrapperClass {

  private int numFound;
  private List<BookSearchApiModel> docs;

  public int getNumFound() {
    return numFound;
  }

  public void setNumFound(int numFound) {
    this.numFound = numFound;
  }

  public List<BookSearchApiModel> getDocs() {
    return docs;
  }

  public void setDocs(List<BookSearchApiModel> docs) {
    this.docs = docs;
  }
}
