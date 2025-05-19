package com.FragranceCove.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.FragranceCove.config.DbConfig;
import com.FragranceCove.model.UserModel;
import com.FragranceCove.util.PasswordUtil;

/**
 * Service class for handling login operations. Connects to the database,
 * verifies user credentials, and returns login status.
 */
public class LoginService {

	private Connection dbConn;
	private boolean isConnectionError = false;

	/**
	 * Constructor initializes the database connection. Sets the connection error
	 * flag if the connection fails.
	 */
	public LoginService() {
		try {
			dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			isConnectionError = true;
		}
	}

	/**
	 * Validates the user credentials against the database records.
	 *
	 * @param userModel the StudentModel object containing user credentials
	 * @return true if the user credentials are valid, false otherwise; null if a
	 *         connection error occurs
	 */
	public UserModel authenticateUser(String username, String password) {
	    if (dbConn == null) {
	        System.err.println("Database connection is not available!");
	        return null;
	    }

	    String query = "SELECT u.*, r.role_name FROM users u " +
	                   "JOIN roles r ON u.role_id = r.role_id " +
	                   "WHERE u.username = ? AND u.password = ? AND u.status = 'active'";  
	                   // Check active status and use hashed password comparison in production

	    try {
	        PreparedStatement stmt = dbConn.prepareStatement(query);
	        stmt.setString(1, username);
	        stmt.setString(2, password);  // In production, compare hashed passwords
	        ResultSet result = stmt.executeQuery();

	        if (result.next()) {
	            UserModel user = new UserModel();
	            user.setUser_id(result.getInt("user_id"));
	            user.setUsername(result.getString("username"));
	            user.setFirst_name(result.getString("first_name"));
	            user.setLast_name(result.getString("last_name"));
	            user.setEmail(result.getString("email"));
	            user.setRole_id(result.getInt("role_id"));
	            user.setImage_url(result.getString("image_url"));
	            return user;
	        }
	    } catch (SQLException e) {
	        System.err.println("Error during user authentication: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return null;
	}
	public Boolean loginUser(UserModel userModel) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT email, password FROM users WHERE email = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, userModel.getEmail());
			ResultSet result = stmt.executeQuery();

			if (result.next()) {
				return validatePassword(result, userModel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return false;
	}
	
	
	public String getRole(UserModel userModel) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT role_name from roles join users on users.role_id = roles.role_id where users.email = ? ";	
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, userModel.getEmail());
			ResultSet result = stmt.executeQuery();

			if (result.next()) {
				return result.getString("role_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}


	/**
	 * Validates the password retrieved from the database.
	 *
	 * @param result       the ResultSet containing the username and password from
	 *                     the database
	 * @param userModel the UserModel object containing user credentials
	 * @return true if the passwords match, false otherwise
	 * @throws SQLException if a database access error occurs
	 */
	private boolean validatePassword(ResultSet result, UserModel userModel) throws SQLException {
		String dbEmail = result.getString("email");
		String dbPassword = result.getString("password");

		return dbEmail.equals(userModel.getEmail())
				&& PasswordUtil.decrypt(dbEmail,dbPassword).equals(userModel.getPassword());
	}
}