package com.FragranceCove.controller;

import java.io.IOException;
import java.util.List;

import com.FragranceCove.model.PerfumeModel; // Use PerfumeModel
import com.FragranceCove.service.PerfumeService; // Use the actual service class
import com.FragranceCove.util.RedirectionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(asyncSupported = true, urlPatterns = { "/product" })
public class productController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Instantiate your PerfumeService
    private PerfumeService perfumeService = new PerfumeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Retrieve the list of all perfumes from the service
		List<PerfumeModel> perfumeList = null;
		perfumeList = perfumeService.getAllPerfumes();

		// 2. Add the list to the request attributes with the name "perfumeList"
		req.setAttribute("perfumeList", perfumeList);

		// 3. Forward the request to the product.jsp page
		req.getRequestDispatcher(RedirectionUtil.productUrl).forward(req, resp);
    }
}