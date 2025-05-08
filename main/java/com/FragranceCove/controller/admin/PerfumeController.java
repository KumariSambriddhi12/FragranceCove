package com.FragranceCove.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.FragranceCove.model.PerfumeModel; // Adjust your model name
import com.FragranceCove.service.DashboardService; // Adjust your service name
import com.FragranceCove.util.ValidationUtil; // Assuming you have this utility

/**
 * Servlet implementation class PerfumeController for managing perfume data.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/managePerfumes" }) // Changed URL pattern
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class PerfumeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Instance of AdminDashboardService for handling business logic (renamed)
    private DashboardService DashboardService;

    /**
     * Default constructor initializes the AdminDashboardService instance.
     */
    public PerfumeController() {
        this.DashboardService = new DashboardService(); // Initialize your service
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve all perfume information (you'll need a method in your service)
        // request.setAttribute("perfumeList", adminDashboardService.getAllPerfumesInfo());

        // You might have different dashboard stats for perfumes
        // request.setAttribute("totalPerfumes", adminDashboardService.getTotalPerfumes());
        // request.setAttribute("lowStock", adminDashboardService.getLowStockProductsCount(5));

        // Forward the request to the perfumes JSP for rendering (you'll need to create this)
        request.getRequestDispatcher("/WEB-INF/pages/admin/perfumes.jsp").forward(request, response); // Changed JSP path
    }

    /**
     * Handles HTTP POST requests for various actions such as update, delete, or
     * redirecting to the update form for perfumes. Processes the request parameters
     * based on the specified action.
     *
     * @param request  The HttpServletRequest object containing the request data.
     * @param response The HttpServletResponse object used to return the response.
     * @throws ServletException If an error occurs during request processing.
     * @throws IOException     If an input or output error occurs.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String perfumeIdParam = request.getParameter("perfumeId"); // Assuming you have a perfumeId

        if (perfumeIdParam != null && !perfumeIdParam.isEmpty()) {
            int perfumeId = Integer.parseInt(perfumeIdParam);

            switch (action) {
                case "updateForm":
                    handleUpdateForm(request, response, perfumeId);
                    break;

                case "update":
                    handleUpdate(request, response, perfumeId);
                    break;

                case "delete":
                    handleDelete(request, response, perfumeId);
                    break;

                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Perfume ID is missing.");
        }
    }

    /**
     * Handles the update form action by setting perfume data in the session and
     * redirecting to the update page.
     *
     * @param request    The HttpServletRequest object containing the request data.
     * @param response   The HttpServletResponse object used to return the response.
     * @param perfumeId The ID of the perfume to be updated.
     * @throws IOException If an input or output error occurs.
     */
    private void handleUpdateForm(HttpServletRequest request, HttpServletResponse response, int perfumeId)
            throws ServletException, IOException {
        // Retrieve the perfume information from the service (you'll need this method)
        // PerfumeModel perfume = adminDashboardService.getSpecificPerfumeInfo(perfumeId);
        PerfumeModel perfume = new PerfumeModel(); // Placeholder - replace with actual service call
        perfume.setId(perfumeId); // Assuming your model has setId

        if (perfume != null) {
            // Store the perfume object in the session
            request.getSession().setAttribute("perfume", perfume); // Changed attribute name

            // Redirect to the update URL
            response.sendRedirect(request.getContextPath() + "/perfumeUpdate"); // Changed URL
        } else {
            // Handle case where perfume info is not found
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Perfume not found with ID: " + perfumeId);
        }
    }

    /**
     * Handles the update action by processing perfume data and updating it through
     * the AdminDashboardService (or a dedicated PerfumeService). Redirects to the
     * manage perfumes page upon completion.
     *
     * @param request    The HttpServletRequest object containing the request data.
     * @param response   The HttpServletResponse object used to return the response.
     * @param perfumeId The ID of the perfume to be updated.
     * @throws ServletException If an error occurs during request processing.
     * @throws IOException     If an input or output error occurs.
     */
    private void handleUpdate(HttpServletRequest request, HttpServletResponse response, int perfumeId)
            throws ServletException, IOException {
        // Validate form parameters (you'll need to adapt this)
        String validationMessage = validatePerfumeForm(request);
        if (validationMessage != null) {
            request.setAttribute("error", validationMessage);
            doGet(request, response); // Reload the page with the error message
            return;
        }

        // Retrieve form parameters (adjust parameter names to match your form)
        String brand = request.getParameter("brand");
        String itemForm = request.getParameter("itemForm");
        String volume = request.getParameter("volume");
        String scent = request.getParameter("scent");
        String specialFeature = request.getParameter("specialFeature");
        String priceStr = request.getParameter("price");

        int price = 0;
        if (priceStr != null && !priceStr.isEmpty()) {
            try {
                price = Integer.parseInt(priceStr);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid price format.");
                doGet(request, response);
                return;
            }
        }

        // Create a PerfumeModel object
        PerfumeModel perfume = new PerfumeModel(perfumeId, brand, itemForm, volume, scent, specialFeature, price, 0); // Assuming stockQuantity is not updated here

        // Update the perfume using a service (you might need a dedicated PerfumeService)
        boolean success = DashboardService.updatePerfumeInfo(perfume); // Assuming this method exists

        // Handle the result of the update operation
        if (success) {
            request.setAttribute("success", "Perfume information updated successfully.");
        } else {
            request.setAttribute("error", "Failed to update perfume information.");
        }

        // Forward to the manage perfumes page to reflect changes
        doGet(request, response);
    }

    /**
     * Validates the form fields for the perfume update operation (adapt this).
     *
     * @param request The HttpServletRequest object containing the request data.
     * @return A validation error message, or null if all validations pass.
     */
    private String validatePerfumeForm(HttpServletRequest request) {
        String brand = request.getParameter("brand");
        String itemForm = request.getParameter("itemForm");
        String volume = request.getParameter("volume");
        String scent = request.getParameter("scent");
        String priceStr = request.getParameter("price");

        if (ValidationUtil.isNullOrEmpty(brand))
            return "Brand is required.";
        if (ValidationUtil.isNullOrEmpty(itemForm))
            return "Item Form is required.";
        if (ValidationUtil.isNullOrEmpty(volume))
            return "Volume is required.";
        if (ValidationUtil.isNullOrEmpty(scent))
            return "Scent is required.";
        if (ValidationUtil.isNullOrEmpty(priceStr))
            return "Price is required.";

        try {
            Integer.parseInt(priceStr);
        } catch (NumberFormatException e) {
            return "Invalid price format.";
        }

        // Add other validation rules as needed (e.g., for volume format, etc.)

        return null; // Return null if all validations pass
    }

    /**
     * Handles the delete action by removing a perfume from the database and
     * forwarding to the manage perfumes page.
     *
     * @param request    The HttpServletRequest object containing the request data.
     * @param response   The HttpServletResponse object used to return the response.
     * @param perfumeId The ID of the perfume to be deleted.
     * @throws ServletException If an error occurs during request processing.
     * @throws IOException     If an input or output error occurs.
     */
    private void handleDelete(HttpServletRequest request, HttpServletResponse response, int perfumeId)
            throws ServletException, IOException {
        // Assuming you have a deletePerfume method in your service
        boolean success = DashboardService.deletePerfume(perfumeId); // You'll need to create this

        if (success) {
            System.out.println("Perfume deletion successful");
        } else {
            System.out.println("Perfume deletion failed");
        }

        // Forward to the manage perfumes page to reflect changes
        doGet(request, response);
    }
}

