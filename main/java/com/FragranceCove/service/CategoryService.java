package com.FragranceCove.service;

import com.FragranceCove.model.CategoryModel;
import com.FragranceCove.config.DbConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {

    public List<CategoryModel> getAllCategories() {
        List<CategoryModel> categories = new ArrayList<>();
        String query = "SELECT category_id, category_name FROM categories ORDER BY category_name ASC";

        try (Connection conn = DbConfig.getDbConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                CategoryModel category = new CategoryModel();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                // category.setDescription(rs.getString("description")); // If you have a description column
                categories.add(category);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error fetching all categories: " + e.getMessage());
            e.printStackTrace();
        }
        return categories;
    }

    public CategoryModel getCategoryById(int categoryId) {
        String query = "SELECT category_id, category_name FROM categories WHERE category_id = ?";
        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, categoryId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    CategoryModel category = new CategoryModel();
                    category.setCategoryId(rs.getInt("category_id"));
                    category.setCategoryName(rs.getString("category_name"));
                    // category.setDescription(rs.getString("description"));
                    return category;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Error fetching category by ID " + categoryId + ": " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    // Add, Update, Delete methods for categories if needed
}