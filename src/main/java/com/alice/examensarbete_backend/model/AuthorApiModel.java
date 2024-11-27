package com.alice.examensarbete_backend.model;

public class AuthorApiModel {

    private String key;
    private String name;
    private String birth_date;
    private String death_date;
    private String top_work;
    private int work_count;

    public AuthorApiModel() {}

    public AuthorApiModel(String key, String name, String birth_date, String death_date, String top_work, int work_count) {
        this.key = key;
        this.name = name;
        this.birth_date = birth_date;
        this.death_date = death_date;
        this.top_work = top_work;
        this.work_count = work_count;
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
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getDeath_date() {
        return death_date;
    }

    public void setDeath_date(String death_date) {
        this.death_date = death_date;
    }

    public String getTop_work() {
        return top_work;
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
}