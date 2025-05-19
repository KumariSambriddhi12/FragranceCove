package com.FragranceCove.controller;

import java.io.IOException;


import com.FragranceCove.service.PortfolioService;
import com.FragranceCove.util.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HomeController
 */
@WebServlet(asyncSupported = true, urlPatterns =  "/portfolio")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
public class PortfolioController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private PortfolioService portfolioService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PortfolioController() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init() throws ServletException {
		// Initializing dependencies in init()
		this.portfolioService = new PortfolioService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// Using SessionUtil to get the username
	    String email = (String) SessionUtil.getAttribute(req, "email");

		req.setAttribute("user", portfolioService.getUserInfo(email));
		
		
		
		
		
		req.getRequestDispatcher("WEB-INF/pages/portfolio.jsp").forward(req, resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
