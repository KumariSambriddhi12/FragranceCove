package com.FragranceCove.model;

public class CategoryModel {
    private int categoryId;
    private String categoryName;
    private String description; // Optional: if your categories table has a description

    public CategoryModel() {
    }

    public CategoryModel(int categoryId, String categoryName, String description) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
    }
     public CategoryModel(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }


    public CategoryModel(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CategoryModel{" +
               "categoryId=" + categoryId +
               ", categoryName='" + categoryName + '\'' +
               (description != null ? ", description='" + description + '\'' : "") +
               '}';
    }
}