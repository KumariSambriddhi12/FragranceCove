package com.FragranceCove.service;

import com.FragranceCove.model.PerfumeModel;
import com.FragranceCove.config.DbConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp; // For date_added
import java.util.ArrayList;
import java.util.List;

public class PerfumeService {

    public boolean createPerfume(PerfumeModel perfume) {
        String query = "INSERT INTO perfumes (name, brand_id, category_id, description, price, stock_quantity, image_url, date_added) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, perfume.getName());
            pstmt.setInt(2, perfume.getBrandId());
            pstmt.setInt(3, perfume.getCategoryId());
            pstmt.setString(4, perfume.getDescription());
            pstmt.setDouble(5, perfume.getPrice());
            pstmt.setInt(6, perfume.getStockQuantity());
            pstmt.setString(7, perfume.getImageUrl());
            pstmt.setTimestamp(8, perfume.getDateAdded() != null ? perfume.getDateAdded() : new Timestamp(System.currentTimeMillis())); // Default to now if null

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error creating perfume: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<PerfumeModel> getAllPerfumes() {
        List<PerfumeModel> perfumes = new ArrayList<>();
        String query = "SELECT p.id, p.name, p.brand_id, b.brand_name, p.category_id, c.category_name, " +
                       "p.description, p.price, p.stock_quantity, p.image_url, p.date_added " +
                       "FROM perfumes p " +
                       "LEFT JOIN brands b ON p.brand_id = b.brand_id " +
                       "LEFT JOIN categories c ON p.category_id = c.category_id " +
                       "ORDER BY p.name ASC";

        try (Connection conn = DbConfig.getDbConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                PerfumeModel perfume = new PerfumeModel();
                perfume.setId(rs.getInt("id"));
                perfume.setName(rs.getString("name"));
                perfume.setBrandId(rs.getInt("brand_id"));
                perfume.setBrand(rs.getString("brand_name")); // Set brand name from JOIN
                perfume.setCategoryId(rs.getInt("category_id"));
                perfume.setCategory(rs.getString("category_name")); // Set category name from JOIN
                perfume.setDescription(rs.getString("description"));
                perfume.setPrice(rs.getDouble("price"));
                perfume.setStockQuantity(rs.getInt("stock_quantity"));
                perfume.setImageUrl(rs.getString("image_url"));
                perfume.setDateAdded(rs.getTimestamp("date_added"));
                perfumes.add(perfume);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error fetching all perfumes: " + e.getMessage());
            e.printStackTrace();
        }
        return perfumes;
    }

    public PerfumeModel getPerfumeById(int id) {
        String query = "SELECT p.id, p.name, p.brand_id, b.brand_name, p.category_id, c.category_name, " +
                       "p.description, p.price, p.stock_quantity, p.image_url, p.date_added " +
                       "FROM perfumes p " +
                       "LEFT JOIN brands b ON p.brand_id = b.brand_id " +
                       "LEFT JOIN categories c ON p.category_id = c.category_id " +
                       "WHERE p.id = ?";
        PerfumeModel perfume = null;
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    perfume = new PerfumeModel();
                    perfume.setId(rs.getInt("id"));
                    perfume.setName(rs.getString("name"));
                    perfume.setBrandId(rs.getInt("brand_id"));
                    perfume.setBrand(rs.getString("brand_name"));
                    perfume.setCategoryId(rs.getInt("category_id"));
                    perfume.setCategory(rs.getString("category_name"));
                    perfume.setDescription(rs.getString("description"));
                    perfume.setPrice(rs.getDouble("price"));
                    perfume.setStockQuantity(rs.getInt("stock_quantity"));
                    perfume.setImageUrl(rs.getString("image_url"));
                    perfume.setDateAdded(rs.getTimestamp("date_added"));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error fetching perfume by ID " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
        return perfume;
    }

    public boolean updatePerfume(PerfumeModel perfume) {
        String query = "UPDATE perfumes SET name = ?, brand_id = ?, category_id = ?, description = ?, " +
                       "price = ?, stock_quantity = ?, image_url = ? " + // Removed date_added from update, usually set on creation
                       "WHERE id = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, perfume.getName());
            pstmt.setInt(2, perfume.getBrandId());
            pstmt.setInt(3, perfume.getCategoryId());
            pstmt.setString(4, perfume.getDescription());
            pstmt.setDouble(5, perfume.getPrice());
            pstmt.setInt(6, perfume.getStockQuantity());
            pstmt.setString(7, perfume.getImageUrl());
            pstmt.setInt(8, perfume.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error updating perfume: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePerfume(int id) {
        String query = "DELETE FROM perfumes WHERE id = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error deleting perfume ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<PerfumeModel> searchPerfumes(String keyword) {
        List<PerfumeModel> perfumes = new ArrayList<>();
        String query = "SELECT p.id, p.name, p.brand_id, b.brand_name, p.category_id, c.category_name, " +
                       "p.description, p.price, p.stock_quantity, p.image_url, p.date_added " +
                       "FROM perfumes p " +
                       "LEFT JOIN brands b ON p.brand_id = b.brand_id " +
                       "LEFT JOIN categories c ON p.category_id = c.category_id " +
                       "WHERE p.name LIKE ? OR b.brand_name LIKE ? OR c.category_name LIKE ? " +
                       "ORDER BY p.name ASC";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            String searchTerm = "%" + keyword + "%";
            pstmt.setString(1, searchTerm);
            pstmt.setString(2, searchTerm);
            pstmt.setString(3, searchTerm); // Search by category name too

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PerfumeModel perfume = new PerfumeModel();
                    perfume.setId(rs.getInt("id"));
                    perfume.setName(rs.getString("name"));
                    perfume.setBrandId(rs.getInt("brand_id"));
                    perfume.setBrand(rs.getString("brand_name"));
                    perfume.setCategoryId(rs.getInt("category_id"));
                    perfume.setCategory(rs.getString("category_name"));
                    perfume.setDescription(rs.getString("description"));
                    perfume.setPrice(rs.getDouble("price"));
                    perfume.setStockQuantity(rs.getInt("stock_quantity"));
                    perfume.setImageUrl(rs.getString("image_url"));
                    perfume.setDateAdded(rs.getTimestamp("date_added"));
                    perfumes.add(perfume);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error searching perfumes: " + e.getMessage());
            e.printStackTrace();
        }
        return perfumes;
    }
}