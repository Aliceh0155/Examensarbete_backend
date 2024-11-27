package com.alice.examensarbete_backend.model;

import java.util.List;

public class BookWrapperClass {

    private int numFound;
    private List<BookApiModel> docs;

    public int getNumFound() {
        return numFound;
    }

    public void setNumFound(int numFound) {
        this.numFound = numFound;
    }

    public List<BookApiModel> getDocs() {
        return docs;
    }

    public void setDocs(List<BookApiModel> docs) {
        this.docs = docs;
    }
}
