package com.FragranceCove.service; // Adjust your package name

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.FragranceCove.config.DbConfig; // Adjust your DbConfig path
import com.FragranceCove.model.PerfumeModel; // Assuming you have this model

public class DashboardService {

    private Connection dbConn;
    private boolean isConnectionError = false;

    public DashboardService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            isConnectionError = true;
        }
    }

    public String getTotalRegisteredUser() {
        if (isConnectionError) {
        	System.out.println("Connection Error");
        	return null;
        }
        String query = "SELECT COUNT(*) AS total FROM users";
        return executeCountQuery(query);
    }

    public String getTotalProductsInInventory() {
        if (isConnectionError) return null;
        String query = "SELECT COUNT(*) AS total FROM perfumes";
        return executeCountQuery(query);
    }

    public String getNumberOfPendingOrders() {
        if (isConnectionError) return null;
        String query = "SELECT COUNT(*) AS total FROM orders WHERE status IN ('Pending', 'Processing')"; // Adjust status values
        return executeCountQuery(query);
    }

    public String getRecentSalesLast24Hours() {
        if (isConnectionError) return null;
        String query = "SELECT COALESCE(SUM(total_amount), 0) AS total_sales FROM orders WHERE order_date >= NOW() - INTERVAL 24 HOUR"; // Adjust column name
        try (PreparedStatement stmt = dbConn.prepareStatement(query);
             ResultSet result = stmt.executeQuery()) {
            return result.next() ? String.valueOf(result.getDouble("total_sales")) : "0.00";
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getLowStockProductsCount(int threshold) {
        if (isConnectionError) return null;
        String query = "SELECT COUNT(*) AS total FROM products WHERE stock_quantity <= ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setInt(1, threshold);
            try (ResultSet result = stmt.executeQuery()) {
                return result.next() ? result.getString("total") : "0";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Helper method to execute count queries
    private String executeCountQuery(String query) {
        try (PreparedStatement stmt = dbConn.prepareStatement(query);
             ResultSet result = stmt.executeQuery()) {
            return result.next() ? result.getString("total") : "0";
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean updatePerfumeInfo(PerfumeModel perfumeModel) {
        if (isConnectionError) {
            System.out.println("Database connection error");
            return false;
        }

        String sql = "UPDATE products SET id = ?, brand = ?, itemform = ?, volume = ?, scent = ?, special_feature = ?, price = ?, stock_quantity=?  WHERE id = ?"; // Added stock_quantity
        try (PreparedStatement pstmt = dbConn.prepareStatement(sql)) {
            pstmt.setInt(1, perfumeModel.getId());
            pstmt.setString(2, perfumeModel.getBrand());
            pstmt.setString(3, perfumeModel.getItemForm());
            pstmt.setString(4, perfumeModel.getVolume());
            pstmt.setString(5, perfumeModel.getScent());
            pstmt.setString(6, perfumeModel.getSpecialFeature());
            pstmt.setInt(7, perfumeModel.getPrice());
            pstmt.setInt(8, perfumeModel.getStockQuantity()); // Set the stock quantity
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Deletes a perfume from the database.
     *
     * @param perfumeId The ID of the perfume to delete.
     * @return true if the deletion was successful, false otherwise.
     */
    public boolean deletePerfume(int perfumeId) {
        if (isConnectionError) {
            System.out.println("Database connection error");
            return false;
        }

        String sql = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement pstmt = dbConn.prepareStatement(sql)) {
            pstmt.setInt(1, perfumeId);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
 }
}