package com.FragranceCove.model;

import java.sql.Timestamp; // If you use dateAdded

public class PerfumeModel {
    private int id;
    private String name;
    private String brand;          // Display name for the brand (from JOIN)
    private int brandId;           // Foreign key to brands table
    private String category;       // Display name for the category (from JOIN)
    private int categoryId;        // Foreign key to categories table
    private String description;
    private double price;
    private int stockQuantity;
    private String imageUrl;
    private Timestamp dateAdded;   // Optional: Date when perfume was added

    public PerfumeModel() {
    }

    // Getters and Setters for all fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public String toString() {
        return "PerfumeModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brandId=" + brandId +
                ", categoryId=" + categoryId +
                ", price=" + price +
                // Add other fields if needed for debugging
                '}';
    }
}