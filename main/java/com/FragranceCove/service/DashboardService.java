package com.FragranceCove.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.FragranceCove.config.DbConfig;
import com.FragranceCove.model.PerfumeModel;

public class DashboardService {

    // No constructor needed if we fetch connection per method
    // No dbConn field or isConnectionError flag needed

    public List<PerfumeModel> getAllPerfumes() {
        List<PerfumeModel> perfumes = new ArrayList<>();
        String sql = "SELECT p.id, p.name, p.description, p.price, p.stock_quantity, p.image_url, " +
                       "c.category_name AS category_name, b.brand_name AS brand_name, " +
                       "p.category_id, p.brand_id " +
                       "FROM perfumes p " +
                       "LEFT JOIN categories c ON p.category_id = c.category_id " +
                       "LEFT JOIN brands b ON p.brand_id = b.brand_id " +
                       "ORDER BY p.name ASC"; // Added an order by for consistency

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                PerfumeModel perfume = new PerfumeModel();
                perfume.setId(rs.getInt("id"));
                perfume.setName(rs.getString("name"));
                perfume.setBrand(rs.getString("brand_name")); // Use alias from JOIN
                perfume.setDescription(rs.getString("description"));
                perfume.setPrice(rs.getDouble("price"));
                perfume.setStockQuantity(rs.getInt("stock_quantity"));
                perfume.setImageUrl(rs.getString("image_url"));
                perfume.setCategory(rs.getString("category_name")); // Use alias from JOIN

                // Optionally set IDs if your PerfumeModel needs them
                perfume.setBrandId(rs.getInt("brand_id"));
                perfume.setCategoryId(rs.getInt("category_id"));

                perfumes.add(perfume);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error fetching all perfumes for dashboard: " + e.getMessage());
            e.printStackTrace();
            // Depending on how critical this is, you might throw a custom exception
            // or simply return an empty list as done here.
        }
        return perfumes;
    }

    public PerfumeModel getPerfumeById(int perfumeId) {
        PerfumeModel perfume = null;
        String sql = "SELECT p.id, p.name, p.description, p.price, p.stock_quantity, p.image_url, " +
                       "c.category_name AS category_name, b.brand_name AS brand_name, " +
                       "p.category_id, p.brand_id " +
                       "FROM perfumes p " +
                       "LEFT JOIN categories c ON p.category_id = c.category_id " +
                       "LEFT JOIN brands b ON p.brand_id = b.brand_id " +
                       "WHERE p.id = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, perfumeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    perfume = new PerfumeModel();
                    perfume.setId(rs.getInt("id"));
                    perfume.setName(rs.getString("name"));
                    perfume.setBrand(rs.getString("brand_name"));
                    perfume.setDescription(rs.getString("description"));
                    perfume.setPrice(rs.getDouble("price")); // Assuming price is double
                    perfume.setStockQuantity(rs.getInt("stock_quantity"));
                    perfume.setImageUrl(rs.getString("image_url"));
                    perfume.setCategory(rs.getString("category_name"));

                    perfume.setBrandId(rs.getInt("brand_id"));
                    perfume.setCategoryId(rs.getInt("category_id"));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error fetching perfume by ID for dashboard: " + e.getMessage());
            e.printStackTrace();
        }
        return perfume;
    }

    public String getTotalRegisteredUsers() { // Renamed for clarity
        String query = "SELECT COUNT(*) AS total FROM users"; // Assuming your user table is 'users'
        return executeCountQuery(query, "Error fetching total registered users: ");
    }

    public String getTotalProductsInInventory() {
        String query = "SELECT SUM(stock_quantity) AS total FROM perfumes"; // More accurate for "inventory"
        // Or if you mean distinct product types: "SELECT COUNT(*) AS total FROM perfumes";
        return executeCountQuery(query, "Error fetching total products in inventory: ");
    }

    public String getNumberOfPendingOrders() {
        // Ensure 'orders' table exists and 'status' column is correct
        String query = "SELECT COUNT(*) AS total FROM orders WHERE status IN ('Pending', 'Processing')";
        return executeCountQuery(query, "Error fetching number of pending orders: ");
    }

    public String getRecentSalesLast24Hours() {
        // Ensure 'orders' table exists and 'total_amount', 'order_date' columns are correct
        String query = "SELECT COALESCE(SUM(total_amount), 0) AS total_sales FROM orders WHERE order_date >= NOW() - INTERVAL 1 DAY"; // INTERVAL 1 DAY is often clearer
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet result = stmt.executeQuery()) {
            return result.next() ? String.format("%.2f", result.getDouble("total_sales")) : "0.00";
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error fetching recent sales: " + e.getMessage());
            e.printStackTrace();
            return "0.00"; // Return a default value on error
        }
    }

    public String getLowStockProductsCount(int threshold) {
        String query = "SELECT COUNT(*) AS total FROM perfumes WHERE stock_quantity <= ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, threshold);
            try (ResultSet result = stmt.executeQuery()) {
                return result.next() ? result.getString("total") : "0";
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error fetching low stock products count: " + e.getMessage());
            e.printStackTrace();
            return "0"; // Return a default value on error
        }
    }

    // Helper method to execute count queries, now with error message prefix
    private String executeCountQuery(String query, String errorMessagePrefix) {
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet result = stmt.executeQuery()) {
            return result.next() ? result.getString("total") : "0";
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(errorMessagePrefix + e.getMessage());
            e.printStackTrace();
            return "0"; // Return a default value on error
        }
    }

    public boolean updatePerfumeInfo(PerfumeModel perfumeModel) {
        // This method might be better suited for PerfumeService.java
        // as DashboardService is typically for read-only summary data.
        // But if it's specifically for a quick dashboard update, ensure it's scoped correctly.

        // Corrected SQL and parameter indexing
        String sql = "UPDATE perfumes SET name = ?, brand_id = ?, category_id = ?, description = ?, price = ?, stock_quantity = ?, image_url = ? WHERE id = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, perfumeModel.getName());
            pstmt.setInt(2, perfumeModel.getBrandId()); // Assuming PerfumeModel has getBrandId()
            pstmt.setInt(3, perfumeModel.getCategoryId()); // Assuming PerfumeModel has getCategoryId()
            pstmt.setString(4, perfumeModel.getDescription());
            pstmt.setDouble(5, perfumeModel.getPrice());
            pstmt.setInt(6, perfumeModel.getStockQuantity());
            pstmt.setString(7, perfumeModel.getImageUrl());
            pstmt.setInt(8, perfumeModel.getId()); // For the WHERE clause

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error updating perfume info from dashboard: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePerfume(int perfumeId) {
        // This method is also more typical for PerfumeService.java.
        String sql = "DELETE FROM perfumes WHERE id = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, perfumeId);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error deleting perfume from dashboard context: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}