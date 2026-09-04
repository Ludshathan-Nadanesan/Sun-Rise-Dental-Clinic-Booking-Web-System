package com.sunrise.model;

import java.util.List;

public class HelpTopic {
    private String id;
    private String title;
    private String description;
    private List<String> imageUrls;

    public HelpTopic(String id, String title, String description, List<String> imageUrls) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrls = imageUrls;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
