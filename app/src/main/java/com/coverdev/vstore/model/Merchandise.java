package com.coverdev.vstore.model;

public class Merchandise {
    private String id;
    private String name;
    private String category;
    private Boolean availability;
    private Number price;
    private String description;
    private String imageURL;

    public Merchandise() {
        // Default constructor required for calls to DataSnapshot.getValue(Merchandise.class)
    }

    public Merchandise(String id, String name, String category, Boolean availability, Number price, String description, String imageURL) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.availability = availability;
        this.price = price;
        this.description = description;
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

    public Number getPrice() {
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

    public void setPrice(Number price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
