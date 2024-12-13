package com.alice.examensarbete_backend.model;


import java.util.List;

public class BookSearchApiModel {

  private String title;
  private List<String> author_name;
  private List<String> author_key;
  private int first_publish_year;
  private double ratings_average;


  public BookSearchApiModel() {}

  public BookSearchApiModel(String title, List<String> author_name, List<String> author_key, int first_publish_year, double ratings_average) {
    this.title = title;
    this.author_name = author_name;
    this.author_key = author_key;
    this.first_publish_year = first_publish_year;
    this.ratings_average = ratings_average;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<String> getAuthor_name() {
    return author_name;
  }

  public void setAuthor_name(List<String> author_name) {
    this.author_name = author_name;
  }


  public List<String> getAuthor_key() {
    return author_key;
  }

  public void setAuthor_key(List<String> author_key) {
    this.author_key = author_key;
  }

  public int getFirst_publish_year() {
    return first_publish_year;
  }

  public void setFirst_publish_year(int first_publish_year) {
    this.first_publish_year = first_publish_year;
  }

  public double getRatings_average() {
    return ratings_average;
  }

  public void setRatings_average(double ratings_average) {
    this.ratings_average = ratings_average;
  }

}
