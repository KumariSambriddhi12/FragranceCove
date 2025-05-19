package com.FragranceCove.model;
import java.time.LocalDate;

public class UserModel {
    private int user_id;
    private String first_name;
    private String last_name;
    private String username;
    private LocalDate birth_date;
    private String email;
    private String phone;
    private String password;
    private String image_url;
    private String status; // "active", "inactive", "deleted", "locked"
    private int role_id;
    
    // Default Constructor
    public UserModel() {
    }
    
    // Parameterized Constructor with all fields
    public UserModel(int user_id, String first_name, String last_name, String username, 
                    LocalDate birth_date, String email, String phone, String password,
                    String image_url, String status, int role_id) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.birth_date = birth_date;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.image_url = image_url;
        this.status = status;
        this.role_id = role_id;
    }
    
    // Constructor without ID (for new user creation)
    public UserModel(String first_name, String last_name, String username, 
                    LocalDate birth_date, String email, String phone, String password,
                    String image_url, int role_id) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.birth_date = birth_date;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.image_url = image_url;
        this.role_id = role_id;
    }
    
    // Constructor for login
    public UserModel(String email, String password) {
        this.email = email;
        this.password = password;
        
    }
    
    // Constructor for profile display
    public UserModel(int user_id, String first_name, String last_name, String username, 
                    LocalDate birth_date, String email, String phone, String image_url) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.birth_date = birth_date;
        this.email = email;
        this.phone = phone;
        this.image_url = image_url;
    }
    
    // Getters and Setters
    public int getUser_id() {
        return user_id;
    }
    
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    
    public String getFirst_name() {
        return first_name;
    }
    
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    
    public String getLast_name() {
        return last_name;
    }
    
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public LocalDate getBirth_date() {
        return birth_date;
    }
    
    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getImage_url() {
        return image_url;
    }
    
    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public int getRole_id() {
        return role_id;
    }
    
    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
}