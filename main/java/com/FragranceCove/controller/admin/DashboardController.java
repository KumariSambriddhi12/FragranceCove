package com.FragranceCove.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.FragranceCove.model.PerfumeModel;
import com.FragranceCove.service.DashboardService;

/**
 * Servlet implementation for handling dashboard-related HTTP requests for perfumes.
 *
 * This servlet manages interactions with the DashboardService to fetch dashboard
 * information and manage perfume data. It forwards requests to the dashboard
 * JSP page or handles POST actions for perfumes.
 *
 * @author Prithivi
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/dashboard" })
public class DashboardController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private DashboardService dashboardService;

    public DashboardController() {
        this.dashboardService = new DashboardService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Dashboard statistics
        request.setAttribute("totalUsers", dashboardService.getTotalRegisteredUsers());
        request.setAttribute("totalProducts", dashboardService.getTotalProductsInInventory());
        request.setAttribute("pendingOrders", dashboardService.getNumberOfPendingOrders());
        request.setAttribute("recentSales", dashboardService.getRecentSalesLast24Hours());
        request.setAttribute("lowStockProducts", dashboardService.getLowStockProductsCount(5));

        // Fetch the list of perfumes
        List<PerfumeModel> perfumeList = dashboardService.getAllPerfumes();
        request.setAttribute("perfumeList", perfumeList);

        request.getRequestDispatcher("/WEB-INF/pages/admin/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("updateFormPerfume".equals(action)) {
            String perfumeIdParam = request.getParameter("id");
            if (perfumeIdParam != null && !perfumeIdParam.isEmpty()) {
                try {
                    int perfumeId = Integer.parseInt(perfumeIdParam);
                    handleUpdateFormPerfume(request, response, perfumeId);
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid perfumeId: " + perfumeIdParam);
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Perfume ID is required for update form.");
            }
        } else if ("updatePerfume".equals(action)) {
            String perfumeIdParam = request.getParameter("perfumeId");
            if (perfumeIdParam != null && !perfumeIdParam.isEmpty()) {
                try {
                    int perfumeId = Integer.parseInt(perfumeIdParam);
                    handleUpdatePerfume(request, response, perfumeId);
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid perfumeId: " + perfumeIdParam);
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Perfume ID is required for update.");
            }
        } else if ("deletePerfume".equals(action)) {
            String perfumeIdParam = request.getParameter("perfumeId");
            if (perfumeIdParam != null && !perfumeIdParam.isEmpty()) {
                try {
                    int perfumeId = Integer.parseInt(perfumeIdParam);
                    handleDeletePerfume(request, response, perfumeId);
                } catch (NumberFormatException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid perfumeId: " + perfumeIdParam);
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Perfume ID is required for delete.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
        }
    }

    private void handleUpdateFormPerfume(HttpServletRequest request, HttpServletResponse response, int perfumeId)
            throws IOException {
        PerfumeModel perfume = dashboardService.getPerfumeById(perfumeId);
        if (perfume != null) {
            request.getSession().setAttribute("perfume", perfume);
            response.sendRedirect(request.getContextPath() + "/admin/updatePerfume"); // Adjust URL as needed
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Perfume with ID " + perfumeId + " not found.");
        }
    }

    private void handleUpdatePerfume(HttpServletRequest request, HttpServletResponse response, int perfumeId)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String brand = request.getParameter("brand");
        String brandIdParam = request.getParameter("brandId");
        String description = request.getParameter("description");
        String priceParam = request.getParameter("price");
        String stockQuantityParam = request.getParameter("stockQuantity");
        String imageUrl = request.getParameter("imageUrl");
        String category = request.getParameter("category");
        String categoryIdParam = request.getParameter("categoryId");

        // Handle parsing of numeric values
        double price = 0;
        int stockQuantity = 0;
        int brandId = 0;
        int categoryId = 0;

        try {
            if (priceParam != null && !priceParam.isEmpty()) {
                price = Double.parseDouble(priceParam);
            }
            
            if (stockQuantityParam != null && !stockQuantityParam.isEmpty()) {
                stockQuantity = Integer.parseInt(stockQuantityParam);
            }
            
            if (brandIdParam != null && !brandIdParam.isEmpty()) {
                brandId = Integer.parseInt(brandIdParam);
            }
            
            if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
                categoryId = Integer.parseInt(categoryIdParam);
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid numeric value in form submission.");
            return;
        }

        // Create a PerfumeModel with the updated values
        PerfumeModel updatedPerfume = new PerfumeModel();
        updatedPerfume.setId(perfumeId);
        updatedPerfume.setName(name);
        updatedPerfume.setBrand(brand);
        updatedPerfume.setBrandId(brandId);
        updatedPerfume.setDescription(description);
        updatedPerfume.setPrice(price);
        updatedPerfume.setStockQuantity(stockQuantity);
        updatedPerfume.setImageUrl(imageUrl);
        updatedPerfume.setCategory(category);
        updatedPerfume.setCategoryId(categoryId);
        // Note: dateAdded is typically not updated during edit

        boolean success = dashboardService.updatePerfumeInfo(updatedPerfume);
        if (success) {
            System.out.println("Perfume update successful");
        } else {
            System.out.println("Perfume update failed");
        }
        doGet(request, response);
    }

    private void handleDeletePerfume(HttpServletRequest request, HttpServletResponse response, int perfumeId)
            throws ServletException, IOException {
        boolean success = dashboardService.deletePerfume(perfumeId);
        if (success) {
            System.out.println("Perfume deletion successful");
        } else {
            System.out.println("Perfume deletion failed");
        }
        doGet(request, response);
    }
}