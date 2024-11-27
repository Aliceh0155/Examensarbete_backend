package com.alice.examensarbete_backend.model;

import java.util.List;

public class AuthorWorksWrapperClass {

    private int size;
    private List<AuthorWorksApiModel> entries;

    public AuthorWorksWrapperClass() {}

    public AuthorWorksWrapperClass(int size, List<AuthorWorksApiModel> entries) {
        this.size = size;
        this.entries = entries;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<AuthorWorksApiModel> getEntries() {
        return entries;
    }

    public void setEntries(List<AuthorWorksApiModel> entries) {
        this.entries = entries;
    }
}
