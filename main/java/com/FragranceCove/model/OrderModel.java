package com.FragranceCove.model;

import java.sql.Timestamp;
import java.util.List;

public class OrderModel {
    private Integer orderId;
    private Integer customerId;
    private Timestamp orderDate;
    private double totalAmount;
    private String status;

    // Default constructor
    public OrderModel() {
    }

    // Constructor with basic order information
    public OrderModel(Integer orderId, Integer customerId, Timestamp orderDate, double totalAmount, String status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    // Getter methods
    public Integer getOrderId() {
        return orderId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }


    // Setter methods
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
               "orderId=" + orderId +
               ", customerId=" + customerId +
               ", orderDate=" + orderDate +
               ", totalAmount=" + totalAmount +
               ", status='" + status + '\'' +
               '}';
    }
}
