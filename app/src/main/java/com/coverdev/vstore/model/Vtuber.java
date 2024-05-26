package com.coverdev.vstore.model;

import java.util.HashMap;
import java.util.Map;

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

    public Map<String, Object> toDocument() {
        Map<String, Object> vtuber = new HashMap<>();
        vtuber.put("name", this.name);
        vtuber.put("branch", this.branch);
        vtuber.put("generation", this.generation);
        vtuber.put("imageUrl", this.imageUrl);

        return vtuber;
    }

    public static Vtuber toVtuber(String id, Map<String, Object> vtuberMap) {
        String name = vtuberMap.get("name").toString();
        String branch = vtuberMap.get("branch").toString();
        String generation = vtuberMap.get("generation").toString();
        String imageUrl = vtuberMap.get("imageUrl").toString();

        return new Vtuber(id, name, branch, generation, imageUrl);
    }
}


