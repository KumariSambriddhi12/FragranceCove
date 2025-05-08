package com.FragranceCove.model;

import java.time.LocalDateTime;

public class CustomerModel {
    private int customerId;
    private String customerEmail;
    private String customerPassword;
    private String customerFirstName;
    private String customerLastName;
    private String customerPhone;
    private String customerRole;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CustomerModel() {
    }

    public CustomerModel(int customerId, String customerEmail, String customerPassword, 
                   String customerFirstName, String customerLastName, String customerPhone, 
                   String customerRole, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.customerId = customerId;
        this.customerEmail = customerEmail;
        this.customerPassword = customerPassword;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerPhone = customerPhone;
        this.customerRole = customerRole;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerRole() {
        return customerRole;
    }

    public void setCustomerRole(String customerRole) {
        this.customerRole = customerRole;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}