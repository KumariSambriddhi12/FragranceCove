<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register | Luxury Fragrances</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    
    <!-- Display error message if available -->
		<c:if test="${not empty error}">
			<p class="error-message">${error}</p>
		</c:if>

		<!-- Display success message if available -->
		<c:if test="${not empty success}">
			<p class="success-message">${success}</p>
		</c:if>
    

    <main class="registration-main">
        <section class="registration-section">
            <div class="registration-container">
            
                <h2 class="form-title">Create Your Account</h2>
                <form class="registration-form" action="${pageContext.request.contextPath}/register" method="post">

                    <div class="form-group">
                        <label for="firstName">First Name*</label>
                        <div class="input-with-icon">
                            <i class="fas fa-user"></i>
                            <input type="text" id="firstName" name="FirstName" class="form-control" required>
                        </div>
                        <% if (request.getAttribute("errorFirstName") != null) { %>
                            <p class="error-message"><%= request.getAttribute("errorFirstName") %></p>
                        <% } %>
                    </div>

                    <div class="form-group">
                        <label for="lastName">Last Name*</label>
                        <div class="input-with-icon">
                            <i class="fas fa-user"></i>
                            <input type="text" id="lastName" name="LastName" class="form-control" required>
                        </div>
                        <% if (request.getAttribute("errorLastName") != null) { %>
                            <p class="error-message"><%= request.getAttribute("errorLastName") %></p>
                        <% } %>
                    </div>

                    <div class="form-group">
                        <label for="username">Username*</label>
                        <div class="input-with-icon">
                            <i class="fas fa-id-card"></i>
                            <input type="text" id="username" name="username" class="form-control" required>
                        </div>
                        <% if (request.getAttribute("errorUsername") != null) { %>
                            <p class="error-message"><%= request.getAttribute("errorUsername") %></p>
                        <% } %>
                    </div>

                    <div class="form-group">
                        <label for="email">Email Address*</label>
                        <div class="input-with-icon">
                            <i class="fas fa-envelope"></i>
                            <input type="email" id="email" name="email" class="form-control" required>
                        </div>
                        <% if (request.getAttribute("errorEmail") != null) { %>
                            <p class="error-message"><%= request.getAttribute("errorEmail") %></p>
                        <% } %>
                    </div>

                    <div class="form-group">
                        <label for="password">Password*</label>
                        <div class="input-with-icon">
                            <i class="fas fa-lock"></i>
                            <input type="password" id="password" name="password" class="form-control" required>
                        </div>
                        <% if (request.getAttribute("errorPassword") != null) { %>
                            <p class="error-message"><%= request.getAttribute("errorPassword") %></p>
                        <% } %>
                    </div>

                    <div class="form-group">
                        <label for="confirmPassword">Confirm Password*</label>
                        <div class="input-with-icon">
                            <i class="fas fa-lock"></i>
                            <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required>
                        </div>
                        <% if (request.getAttribute("errorConfirmPassword") != null) { %>
                            <p class="error-message"><%= request.getAttribute("errorConfirmPassword") %></p>
                        <% } %>
                    </div>

                    <div class="form-group">
                        <label for="roleId">Account Type*</label>
                        <div class="input-with-icon">
                            <i class="fas fa-briefcase"></i>
                            <select id="roleId" name="RoleId" class="form-control" required>
                                <option value="2" selected>Customer</option>
                                <option value="1">Admin</option>
                                </select>
                        </div>
                        <% if (request.getAttribute("errorRoleId") != null) { %>
                            <p class="error-message"><%= request.getAttribute("errorRoleId") %></p>
                        <% } %>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary btn-block">
                            <i class="fas fa-user-plus"></i> Register
                        </button>
                        <p class="login-link">Already have an account? <a href="${pageContext.request.contextPath}/login">Sign in</a></p>
                    </div>
                </form>
            </div>
        </section>
    </main>

    <%@ include file="footer.jsp" %>
</body>
</html>