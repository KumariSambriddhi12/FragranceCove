package com.FragranceCove.model; // Adjust the package name to your project's structure

import java.sql.Timestamp;

public class UserModel {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Timestamp registrationDate;
    private String status;
    private Integer roleId;

    // Default constructor
    public UserModel() {
    }
    
	public UserModel(String email, String pasword) {
		this.email = email;
		this.password = pasword;
	}

    // Constructor with essential registration info (excluding auto-generated and default values)
    public UserModel(int userId, String firstName, String lastName,String username, String password, String email, Integer roleId) {
    	this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.username = username;
        this.email = email;
        this.roleId = roleId;
    }

    // Constructor with all fields (excluding Role object initially)
    public UserModel(Integer userId, String firstName, String lastName, String username, String password, String email, Timestamp registrationDate, String status, Integer roleId) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
        this.status = status;
        this.roleId = roleId;
    }

    // Getter methods
    public Integer getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    
    public String getUsername() {
    	return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public String getStatus() {
        return status;
    }

    public Integer getRoleId() {
        return roleId;
    }

    // Setter methods
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void setUsername(String username) {
    	this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }


    @Override
    public String toString() {
        return "UserModel{" +
               "userId=" + userId +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", password='" + password + '\'' +
               ", email='" + email + '\'' +
               ", registrationDate=" + registrationDate +
               ", status='" + status + '\'' +
               ", roleId=" + roleId +
               '}';
    }
}