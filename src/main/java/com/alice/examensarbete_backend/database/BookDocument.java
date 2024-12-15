package com.alice.examensarbete_backend.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "book")
public class BookDocument {

  @Id
  private String id;

  private String title;
  private String key;
  private String description;
  private List<Long> covers;
  private String coverImageUrl;
  private List<String> subjects;
  private double ratingsAverage;

  public BookDocument() {}

  public BookDocument(double ratingsAverage,String title, String key, String description, List<Long> covers, List<String> subjects) {
    this.title = title;
    this.key = key;
    this.description = description;
    this.covers = covers;
    this.subjects = subjects;
    this.ratingsAverage = ratingsAverage;
  }

  public double getRatingsAverage() {
    return ratingsAverage;
  }

  public void setRatingsAverage(double ratingsAverage) {
    this.ratingsAverage = ratingsAverage;
  }

  public String getCoverImageUrl() {
    return coverImageUrl;
  }

  public void setCoverImageUrl(String coverImageUrl) {
    this.coverImageUrl = coverImageUrl;
  }

  public String getId() {
    return id;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Long> getCovers() {
    return covers;
  }

  public void setCovers(List<Long> covers) {
    this.covers = covers;
  }

  public List<String> getSubjects() {
    return subjects;
  }

  public void setSubjects(List<String> subjects) {
    this.subjects = subjects;
  }
}
