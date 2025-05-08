package com.FragranceCove.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.FragranceCove.config.DbConfig;
import com.FragranceCove.model.UserModel;

/**
 * RegisterService handles the registration of new users. It manages database
 * interactions for user registration.
 */
public class RegisterService {

    private Connection dbConn;

    /**
     * Constructor initializes the database connection.
     */
    public RegisterService() {
        try {
            this.dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println("Database connection error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Registers a new user in the database.
     *
     * @param userModel the user details to be registered
     * @return Boolean indicating the success of the operation
     */
    public Boolean addUser(UserModel userModel) {
        if (dbConn == null) {
            System.err.println("Database connection is not available.");
            return null;
        }

        String insertQuery = "INSERT INTO users (first_name, last_name, username, password, email, status, role_id) "
                + "VALUES ( ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement insertStmt = dbConn.prepareStatement(insertQuery)) {

            // Insert user details directly using the UserModel
            insertStmt.setString(1, userModel.getFirstName());
            insertStmt.setString(2, userModel.getLastName());
            insertStmt.setString(3, userModel.getUsername());
            insertStmt.setString(4, userModel.getPassword());
            insertStmt.setString(5, userModel.getEmail());
            insertStmt.setString(6, userModel.getStatus());
            insertStmt.setInt(7, userModel.getRoleId());

            return insertStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error during user registration: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}