package com.coverdev.vstore.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Merchandise {
    private String id;
    private String name;
    private String category;
    private Boolean availability;
    private Integer price;
    private String description;
    private List<String> keywords;// list of search words
    private String imageURL;

    public Merchandise() {
    }

    public Merchandise(String id, String name, String category, Boolean availability, Integer price, String description, List<String> keywords, String imageURL) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.availability = availability;
        this.price = price;
        this.description = description;
        this.keywords = keywords;
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public Integer getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Map<String, Object> toDocument() {
        Map<String, Object> merchandise = new HashMap<>();
        merchandise.put("name", this.name);
        merchandise.put("category", this.category);
        merchandise.put("availability", this.availability);
        merchandise.put("price", this.price);
        merchandise.put("description", this.description);
        merchandise.put("keywords", this.keywords);
        merchandise.put("imageUrl", this.imageURL);

        return merchandise;
    }

    public static Merchandise toMerchandise(String id, Map<String, Object> merchandiseMap) {
        String name = merchandiseMap.get("name").toString();
        String category = merchandiseMap.get("category").toString();
        Boolean availability = (Boolean) merchandiseMap.get("availability");
        Integer price = Integer.parseInt(merchandiseMap.get("price").toString());
        String description = merchandiseMap.get("description").toString();
        List<String> keywords = (List<String>) merchandiseMap.get("keywords");
        String imageUrl = merchandiseMap.get("imageUrl").toString();

        return new Merchandise(id, name, category, availability, price, description, keywords, imageUrl);
    }
}
