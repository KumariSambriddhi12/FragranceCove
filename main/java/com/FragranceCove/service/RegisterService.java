package com.FragranceCove.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.FragranceCove.config.DbConfig;
import com.FragranceCove.model.UserModel;
import com.FragranceCove.model.RoleModel;


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
     * Ensures the database connection is valid and active.
     * If not, attempts to reconnect.
     * 
     * @return true if connection is valid, false otherwise
     */
    private boolean ensureConnection() {
        try {
            if (dbConn == null || dbConn.isClosed()) {
                System.out.println("Database connection is closed or null. Attempting to reconnect...");
                try {
                    this.dbConn = DbConfig.getDbConnection();
                    return dbConn != null && !dbConn.isClosed();
                } catch (SQLException | ClassNotFoundException ex) {
                    System.err.println("Failed to reconnect to database: " + ex.getMessage());
                    ex.printStackTrace();
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error checking database connection: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
	public List<RoleModel> getRoles() {
		if (dbConn == null) {
			System.err.println("Database connection is not available!");
			return null;
		}

		String query = "Select * from roles";

		try {
			PreparedStatement roleStmt = dbConn.prepareStatement(query);
			ResultSet result = roleStmt.executeQuery();

			List<RoleModel> role = new ArrayList<RoleModel>();

			while (result.next()) {
				role.add(new RoleModel(result.getInt("role_id"), result.getString("role_name")));
			}

			return role;
		} catch (SQLException e) {
			System.err.println("Error during student registration: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

    /**
     * Registers a new user in the database.
     *
     * @param userModel the user details to be registered
     * @return Boolean indicating the success of the operation
     * 
     */
	
	
    public Boolean addUser(UserModel userModel) {
        // Validate user model data
        if (userModel == null || 
            userModel.getUsername() == null || 
            userModel.getEmail() == null || 
            userModel.getPassword() == null) {
            System.err.println("Invalid user model data - missing required fields");
            return false;
        }

        // Ensure database connection is active
        if (!ensureConnection()) {
            System.err.println("Database connection is not available.");
            return null;
        }

        String insertQuery = "INSERT INTO users (first_name, last_name, username, password, email, phone, birth_date, image_url,role_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
        boolean autoCommitOriginal = false;
        try {
            // Begin transaction
            autoCommitOriginal = dbConn.getAutoCommit();
            dbConn.setAutoCommit(false);
            
            try (PreparedStatement insertStmt = dbConn.prepareStatement(insertQuery)) {
                // Insert user details directly using the UserModel
                insertStmt.setString(1, userModel.getFirst_name());
                insertStmt.setString(2, userModel.getLast_name());
                insertStmt.setString(3, userModel.getUsername());
                insertStmt.setString(4, userModel.getPassword());
                insertStmt.setString(5, userModel.getEmail());
                insertStmt.setString(6, userModel.getPhone());
                insertStmt.setDate(7, userModel.getBirth_date() != null ? 
                                 java.sql.Date.valueOf(userModel.getBirth_date()) : null);
                insertStmt.setString(8, userModel.getImage_url());
                insertStmt.setInt(9, userModel.getRole_id());
                
                // Enhanced debug logging
                System.out.println("Attempting to insert user with username: " + userModel.getUsername());

                int rowsAffected = insertStmt.executeUpdate();
                System.out.println("Rows affected: " + rowsAffected);
                
                if (rowsAffected > 0) {
                    dbConn.commit();
                    return true;
                } else {
                    dbConn.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {
            // Rollback transaction in case of error
            try {
                if (dbConn != null) {
                    dbConn.rollback();
                }
            } catch (SQLException rollbackEx) {
                System.err.println("Error during rollback: " + rollbackEx.getMessage());
                rollbackEx.printStackTrace();
            }
            
            // Enhanced error logging
            System.err.println("Error during user registration: " + e.getMessage());
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
            return null;
        } finally {
            // Reset auto-commit to original state
            try {
                if (dbConn != null) {
                    dbConn.setAutoCommit(autoCommitOriginal);
                }
            } catch (SQLException e) {
                System.err.println("Error resetting auto-commit: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}