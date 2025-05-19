package com.FragranceCove.util;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RedirectionUtil {
	private static final String baseUrl = "/WEB-INF/pages/";
	public static final String registerUrl = baseUrl + "register.jsp";
	public static final String loginUrl = baseUrl + "register.jsp";
	public static final String homeUrl = baseUrl + "home.jsp";
	public static final String aboutUrl = baseUrl + "aboutUs.jsp";
	public static final String productUrl = baseUrl + "product.jsp";
	public static final String portfolioUrl = baseUrl + "portfolio.jsp";
	public static final String dashboardUrl = baseUrl + "admin/dashboard.jsp";
	public static final String perfumeFormUrl = baseUrl + "admin/perfumeform.jsp";
	public static final String perfumeListUrl = baseUrl + "admin/perfumelist.jsp";
	public static final String addNewProductUrl = baseUrl + "addNewProduct.jsp";

	

	public void setMsgAttribute(HttpServletRequest req, String msgType, String msg) {
		req.setAttribute(msgType, msg);
	}

	public void redirectToPage(HttpServletRequest req, HttpServletResponse resp, String page)
			throws ServletException, IOException {
		req.getRequestDispatcher(page).forward(req, resp);
	}

	public void setMsgAndRedirect(HttpServletRequest req, HttpServletResponse resp, String msgType, String msg,
			String page) throws ServletException, IOException {
		setMsgAttribute(req, msgType, msg);
		redirectToPage(req, resp, page);
	}

}
