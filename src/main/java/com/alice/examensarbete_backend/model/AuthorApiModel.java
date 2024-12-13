package com.alice.examensarbete_backend.model;

import java.util.ArrayList;

public class AuthorApiModel {

  private String key;
  private String name;
  private String birth_date;
  private String death_date;
  private String top_work;
  private int work_count;
  private String bio;

  public AuthorApiModel() {}

  public AuthorApiModel(String name, String birth_date, String death_date, String top_work, int work_count, String bio) {
    this.name = name;
    this.birth_date = birth_date;
    this.death_date = death_date;
    this.top_work = top_work;
    this.work_count = work_count;
    this.bio = bio;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBirth_date() {
    return birth_date == null ? "" : birth_date;
  }

  public void setBirth_date(String birth_date) {
    this.birth_date = birth_date;
  }

  public String getDeath_date() {
    return death_date == null ? "" : death_date;
  }

  public void setDeath_date(String death_date) {
    this.death_date = death_date;
  }

  public String getTop_work() {
    return top_work == null ? "" : top_work;
  }

  public void setTop_work(String top_work) {
    this.top_work = top_work;
  }

  public int getWork_count() {
    return work_count;
  }

  public void setWork_count(int work_count) {
    this.work_count = work_count;
  }

  public String getBio() {
    return bio == null ? "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat." : bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }
}