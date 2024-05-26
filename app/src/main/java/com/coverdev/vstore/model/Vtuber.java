package com.coverdev.vstore.model;

public class Vtuber {
    private String id;
    private String name;
    private String branch;
    private String generation;
    private String imageUrl;

    public Vtuber() {

    }

    public Vtuber(String id, String name, String branch, String generation, String imageUrl) {
        this.id = id;
        this.name = name;
        this.branch = branch;
        this.generation = generation;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBranch() {
        return branch;
    }

    public String getGeneration() {
        return generation;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setGeneration(String generation) {
        this.generation = generation;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}


