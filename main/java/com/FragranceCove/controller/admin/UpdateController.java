package com.FragranceCove.controller.admin; // Adjust your package name

import java.io.IOException;

import com.FragranceCove.model.PerfumeModel; // Adjust your model name
import com.FragranceCove.service.UpdateService; // You'll need this service
import com.FragranceCove.util.SessionUtil; // Assuming you have this utility

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation for handling perfume update operations.
 *
 * This servlet processes HTTP requests for updating perfume information.
 * It interacts with the UpdateService to perform database operations and
 * forwards requests to the appropriate JSP page for user interaction.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/perfumeUpdate" }) // Changed URL pattern
public class UpdateController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Service for updating perfume information
    private UpdateService updateService; // Assuming you'll create this service

    /**
     * Default constructor initializes the UpdateService instance.
     */
    public UpdateController() {
        this.updateService = new UpdateService(); // Initialize your service
    }

    /**
     * Handles HTTP GET requests by retrieving perfume information from the session
     * and forwarding the request to the update JSP page.
     *
     * @param req The HttpServletRequest object containing the request data.
     * @param resp The HttpServletResponse object used to return the response.
     * @throws ServletException If an error occurs during request processing.
     * @throws IOException If an input or output error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Retrieve and set perfume information from the session if available
        if (req.getSession().getAttribute("perfume") != null) { // Changed attribute name
            PerfumeModel perfume = (PerfumeModel) SessionUtil.getAttribute(req, "perfume"); // Changed model name
            SessionUtil.removeAttribute(req, "perfume"); // Changed attribute name
            req.setAttribute("perfume", perfume); // Changed attribute name
        }

        // Forward to the update JSP page (you'll need to create this)
        req.getRequestDispatcher("/WEB-INF/pages/admin/updatePerfume.jsp").forward(req, resp); // Changed JSP path
    }

    /**
     * Handles HTTP POST requests for updating perfume information.
     * Retrieves perfume data from the request parameters, updates the perfume record
     * in the database using UpdateService, and redirects to the dashboard or
     * handles update failure.
     *
     * @param req The HttpServletRequest object containing the request data.
     * @param resp The HttpServletResponse object used to return the response.
     * @throws ServletException If an error occurs during request processing.
     * @throws IOException If an input or output error occurs.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Retrieve perfume data from request parameters (adjust parameter names)
        int perfumeId = Integer.parseInt(req.getParameter("perfumeId")); // Assuming you have a perfumeId
        String brand = req.getParameter("brand"); // Adjust parameter name
        String itemForm = req.getParameter("itemForm"); // Adjust parameter name
        String volume = req.getParameter("volume"); // Adjust parameter name
        String scent = req.getParameter("scent"); // Adjust parameter name
        String specialFeature = req.getParameter("specialFeature"); // Adjust parameter name
        int price = Integer.parseInt(req.getParameter("price")); // Adjust parameter name

        // Create PerfumeModel object with updated data
        PerfumeModel perfume = new PerfumeModel(perfumeId, brand, itemForm, volume, scent, specialFeature, price, 0); // Assuming stockQuantity is handled elsewhere or not updated here

        // Attempt to update perfume information in the database
        Boolean result = updateService.updatePerfumeInfo(perfume); // Assuming you'll create this method
        if (result != null && result) {
            resp.sendRedirect(req.getContextPath() + "/managePerfumes"); // Redirect to manage perfumes page
        } else {
            req.getSession().setAttribute("perfume", perfume); // Changed attribute name
            handleUpdateFailure(req, resp, result); // Handle failure
        }
    }

    /**
     * Handles update failures by setting an error message and forwarding the request
     * back to the update page.
     *
     * @param req The HttpServletRequest object containing the request data.
     * @param resp The HttpServletResponse object used to return the response.
     * @param loginStatus Indicates the result of the update operation.
     * @throws ServletException If an error occurs during request processing.
     * @throws IOException If an input or output error occurs.
     */
    private void handleUpdateFailure(HttpServletRequest req, HttpServletResponse resp, Boolean loginStatus)
            throws ServletException, IOException {
        // Determine error message based on update result
        String errorMessage;
        if (loginStatus == null) {
            errorMessage = "Our server is under maintenance. Please try again later!";
        } else {
            errorMessage = "Update Failed. Please try again!";
        }
        req.setAttribute("error", errorMessage);
        req.getRequestDispatcher(req.getContextPath() + "/perfumeUpdate").forward(req, resp); // Changed URL
    }
}