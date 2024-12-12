package com.alice.examensarbete_backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.List;

public class OneBookApiModel {

    private String title;
    private String key;
    @JsonProperty("description")
    @JsonDeserialize(using = ObjectToStringDeserializer.class)
    private String description;
    private List<Long> covers;
    private List<String> subjects;



    public OneBookApiModel() {
    }

    public OneBookApiModel(String title, String key, String description, List<Long> covers, List<String> subjects) {
        this.title = title;
        this.key = key;
        this.description = description;
        this.covers = covers;
        this.subjects = subjects;
    }

    public List<Long> getCovers() {
        return covers == null ? new ArrayList<>() : covers;
    }

    public void setCovers(List<Long> covers) {
        this.covers = covers;
    }

    public List<String> getSubjects() {
        return subjects == null ? new ArrayList<>() : subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public String getTitle() {
        return title == null ? "Untitled" : title;
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
        return description == null ? "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat." : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
