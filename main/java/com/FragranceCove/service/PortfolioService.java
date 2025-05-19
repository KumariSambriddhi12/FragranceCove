package com.FragranceCove.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.FragranceCove.config.DbConfig;
import com.FragranceCove.model.UserModel;

public class PortfolioService {
    private Connection dbConn;
    boolean isConnectionError = false;

    public PortfolioService() {
        try {
            this.dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Database connection error: " + ex.getMessage());
            ex.printStackTrace();
            isConnectionError = true;
        }
    }

    public UserModel getUserInfo(String email) {
        /*
         * Checking for database connection; in case of connection error returning null
         */
        if (isConnectionError) {
            System.err.println("Database connection is not available.");
            return null;
        }

        /*
         * Getting the user's username from Session username is used to fetch user
         * details as each username is unique
         */

        // SQL query to fetch the user's details - updated column names to match DB schema
        String query = "SELECT user_id, first_name, last_name, username, birth_date, email, phone, " +
                "image_url, password, status, role_id FROM users WHERE email =?";

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet result = stmt.executeQuery();
            UserModel user = null;

            if (result.next()) {
                // Extracting user details
                int user_id = result.getInt("user_id");
                String first_name = result.getString("first_name");
                String last_name = result.getString("last_name");
                String username = result.getString("username");
                LocalDate birth_date = result.getDate("birth_date") != null ?
                        result.getDate("birth_date").toLocalDate() : null;
                String phone = result.getString("phone");
                String image_url = result.getString("image_url");
                String password = result.getString("password");
                String status = result.getString("status");
                int role_id = result.getInt("role_id");

                // Creating UserModel Instance
                user = new UserModel();
                user.setUser_id(user_id);
                user.setFirst_name(first_name);
                user.setLast_name(last_name);
                user.setUsername(username);
                user.setBirth_date(birth_date);
                user.setEmail(email);
                user.setPhone(phone);
                user.setImage_url(image_url);
                user.setPassword(password);
                user.setStatus(status);
                user.setRole_id(role_id);
                
                System.out.println("Image URL: " + (user != null ? user.getImage_url() : "null"));
            }

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}