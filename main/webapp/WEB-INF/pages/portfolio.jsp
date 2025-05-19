<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>My Profile | Fragrance Cove</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/portfolio.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@400;500;600&display=swap" rel="stylesheet">
</head>
<body>
	    <%@ include file="header.jsp" %>

    <c:if test="${not empty error}">
        <p class="error-message">${error}</p>
    </c:if>

    <c:if test="${not empty success}">
        <p class="success-message">${success}</p>
    </c:if>
    <div class="profile-container">
        <div class="sidebar">
            <h2 class="sidebar-title">Fragrance Cove</h2>
            <ul class="nav-menu">
                <li class="nav-item active">My profile</li>
                <li class="nav-item">Cards & Wallets</li>
                <li class="nav-item">Exchange currency</li>
                <li class="nav-item">Deposit funds</li>
            </ul>

            <div class="verification-banner">
                <span class="verification-icon">!</span>
                Account verification pending
            </div>
        </div>

        <div class="main-content">
            <h1 class="profile-title">Personal details</h1>

            <c:if test="${not empty user}">
                <div class="profile-details">
                    <div class="detail-group">
                        <label>FIRST name</label>
                        <div class="detail-value">${user.firstName}</div>
                    </div>

                    <div class="detail-group">
                        <label>Date of birth</label>
                        <div class="birth-date">
                            <div class="date-part">
                                <div class="date-label">Date</div>
                                <div class="date-value"><fmt:formatDate value="${user.birthDate}" pattern="dd" /></div>
                            </div>
                            <div class="date-part">
                                <div class="date-label">Month</div>
                                <div class="date-value"><fmt:formatDate value="${user.birthDate}" pattern="MMMM" /></div>
                            </div>
                            <div class="date-part">
                                <div class="date-label">Year</div>
                                <div class="date-value"><fmt:formatDate value="${user.birthDate}" pattern="yyyy" /></div>
                            </div>
                        </div>
                    </div>

                    <div class="detail-group">
                        <label>Last name</label>
                        <div class="detail-value">${user.lastName}</div>
                    </div>

                    <div class="detail-group">
                        <label>Phone number</label>
                        <div class="detail-value">${user.phone}</div>
                    </div>

                    <%-- Assuming you have a 'country' attribute in your UserModel --%>
                    <c:if test="${not empty user.country}">
                        <div class="detail-group">
                            <label>Country</label>
                            <div class="detail-value">${user.country}</div>
                        </div>
                    </c:if>

                    <%-- Display Profile Image if available --%>
                    <c:if test="${not empty user.imageUrl}">
                        <div class="detail-group">
                            <label>Profile Image</label>
                            <div class="detail-value">
                                <img src="${pageContext.request.contextPath}/${user.imageUrl}" alt="Profile Picture" style="max-width: 150px; max-height: 150px;">
                            </div>
                        </div>
                    </c:if>
                </div>
            </c:if>
            <c:if test="${empty user}">
                <p>No user information found. Please ensure you are logged in.</p>
            </c:if>

            <div class="copyright-notice">
                Images may be subject to copyright. <a href="#" class="learn-more">Learn More</a>
            </div>
        </div>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>