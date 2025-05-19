package com.FragranceCove.service;

import com.FragranceCove.model.BrandModel;
import com.FragranceCove.config.DbConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BrandService {

    public List<BrandModel> getAllBrands() {
        List<BrandModel> brands = new ArrayList<>();
        String query = "SELECT brand_id, brand_name FROM brands ORDER BY brand_name ASC";

        try (Connection conn = DbConfig.getDbConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                BrandModel brand = new BrandModel();
                brand.setBrandId(rs.getInt("brand_id"));
                brand.setBrandName(rs.getString("brand_name"));
                brands.add(brand);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error fetching all brands: " + e.getMessage());
            e.printStackTrace();
        }
        return brands;
    }

    public BrandModel getBrandById(int brandId) {
        String query = "SELECT brand_id, brand_name FROM brands WHERE brand_id = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, brandId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    BrandModel brand = new BrandModel();
                    brand.setBrandId(rs.getInt("brand_id"));
                    brand.setBrandName(rs.getString("brand_name"));
                    return brand;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error fetching brand by ID " + brandId + ": " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Add, Update, Delete methods for brands if you need to manage brands themselves
    // Example:
    public boolean addBrand(BrandModel brand) {
        String query = "INSERT INTO brands (brand_name) VALUES (?)";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, brand.getBrandName());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        brand.setBrandId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error adding brand: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}