package com.FragranceCove.model;

public class PerfumeModel {
    private int Id;
    private String Brand;
    private String ItemForm;
    private String Volume;
    private String Scent;
    private String SpecialFeature;
    private int price;
    private int stockQuantity; // Added stock quantity

    public PerfumeModel() {
    }

    public PerfumeModel(int Id, String Brand, String ItemForm, String Volume, String Scent, String SpecialFeature, int price, int stockQuantity) {
        this.Id = Id;
        this.Brand = Brand;
        this.ItemForm = ItemForm;
        this.Volume = Volume;
        this.Scent = Scent;
        this.SpecialFeature = SpecialFeature;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String Brand) {
        this.Brand = Brand;
    }

    public String getItemForm() {
        return ItemForm;
    }

    public void setItemForm(String ItemForm) {
        this.ItemForm = ItemForm;
    }

    public String getVolume() {
        return Volume;
    }

    public void setVolume(String Volume) {
        this.Volume = Volume;
    }

    public String getScent() {
        return Scent;
    }

    public void setScent(String Scent) {
        this.Scent = Scent;
    }

    public String getSpecialFeature() {
        return SpecialFeature;
    }

    public void setSpecialFeature(String SpecialFeature) {
        this.SpecialFeature = SpecialFeature;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}