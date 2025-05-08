package com.FragranceCove.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.FragranceCove.config.DbConfig; // Adjust your DbConfig path
import com.FragranceCove.model.PerfumeModel; // Adjust your model name

/**
 * Service class for updating perfume information in the database.
 *
 * This class provides methods to update perfume details.
 * It manages database connections and handles SQL exceptions.
 */
public class UpdateService {
    private Connection dbConn;
    private boolean isConnectionError = false;

    /**
     * Constructor initializes the database connection. Sets the connection error
     * flag if the connection fails.
     */
    public UpdateService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            // Log and handle exceptions related to database connection
            ex.printStackTrace();
            isConnectionError = true;
        }
    }

    /**
     * Updates perfume information in the database.
     *
     * @param perfume The PerfumeModel object containing the updated perfume data.
     * @return Boolean indicating the success of the update operation. Returns null
     * if there is a connection error or an exception occurs.
     */
    public Boolean updatePerfumeInfo(PerfumeModel perfume) {
        if (isConnectionError) {
            return null;
        }

        String updateSQL = "UPDATE perfumes SET brand = ?, item_form = ?, volume = ?, "
                + "scent = ?, special_feature = ?, price = ? WHERE id = ?"; // Adjust table and column names

        try (PreparedStatement preparedStatement = dbConn.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, perfume.getBrand());
            preparedStatement.setString(2, perfume.getItemForm());
            preparedStatement.setString(3, perfume.getVolume());
            preparedStatement.setString(4, perfume.getScent());
            preparedStatement.setString(5, perfume.getSpecialFeature());
            preparedStatement.setInt(6, perfume.getPrice());
            preparedStatement.setInt(7, perfume.getId()); // Assuming 'id' is the primary key

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            // Log and handle SQL exceptions
            e.printStackTrace();
            return null;
        }
    }

    // You might not need a separate getProgramId-like method for perfumes
    // unless you have a related table for perfume categories or brands.
    // If you do, you would implement a similar method here.
}

