package com.FragranceCove.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.FragranceCove.model.UserModel;
import com.FragranceCove.model.RoleModel;
import com.FragranceCove.service.DashboardService;

/**
 * Servlet implementation for handling dashboard-related HTTP requests.
 * 
 * This servlet manages interactions with the DashboardService to fetch student
 * information, handle updates, and manage student data. It forwards requests to
 * appropriate JSP pages or handles POST actions based on the request
 * parameters.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/dashboard", "/productManagement" })
public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// user listing
	//product management

	// Instance of DashboardService for handling business logic
	private DashboardService dashboardService;

	/**
	 * Default constructor initializes the DashboardService instance.
	 */
	public DashboardController() {
		this.dashboardService = new DashboardService();
	}

	/**
	 * Handles HTTP GET requests by retrieving student information and forwarding
	 * the request to the dashboard JSP page.
	 * 
	 * @param request  The HttpServletRequest object containing the request data.
	 * @param response The HttpServletResponse object used to return the response.
	 * @throws ServletException If an error occurs during request processing.
	 * @throws IOException      If an input or output error occurs.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve all student information from the DashboardService
		request.setAttribute("userlist", dashboardService.getTotalRegisteredUser());

		request.setAttribute("total", dashboardService.getTotalProductsInInventory());
		request.setAttribute("pendingOrders", dashboardService.getNumberOfPendingOrders());
		request.setAttribute("recentsales", dashboardService.getRecentSalesLast24Hours());

		// Forward the request to the dashboard JSP for rendering
		request.getRequestDispatcher("/WEB-INF/pages/admin/dashboard.jsp").forward(request, response);
	}

}
