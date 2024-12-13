package com.alice.examensarbete_backend.model;

import java.util.List;

public class AuthorWrapperClass {
  private List<AuthorApiModel> docs; // List of authors
  private int totalCount; // Total number of authors found


  public List<AuthorApiModel> getDocs() {
    return docs;
  }

  public void setDocs(List<AuthorApiModel> docs) {
    this.docs = docs;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }
}
