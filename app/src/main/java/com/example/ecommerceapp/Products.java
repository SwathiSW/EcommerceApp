package com.example.ecommerceapp;

import com.google.gson.JsonObject;


import java.io.Serializable;

public class Products implements Serializable {

    int id;
    String title;
    float price;
    String description, category, image;
    JsonObject rating;
//    JSONJsonObject rating;

    public Products(int id, String title, float price, String description, String category, String image, JsonObject rating) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = image;
        this.rating = rating;
    }


    public Products() {

    }

    public JsonObject getRating() {
        return rating;
    }

    public void setRating(JsonObject rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
