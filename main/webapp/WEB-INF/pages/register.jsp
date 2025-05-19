<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Your Account</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/register.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/footer.css">
</head>
<body>
	<%@ include file="header.jsp"%>

	<div class="registration-page">
		<div class="hero-section">
			<div class="hero-content">
				<h1>Join Our Community!</h1>
				<p>Sign up to connect with fellow enthusiasts, stay updated on
					the latest news, and unlock exclusive content.</p>
				<button class="sign-in-button">Already have an account?
					Sign in</button>
			</div>
		</div>
		<div class="signup-section">
			<div class="signup-form-container">
				<h2>Create Your Account</h2>
				<p class="signup-subtitle">It's quick and easy!</p>
				<c:if test="${not empty error}">
					<p class="error-message">${error}</p>
				</c:if>
				<form class="signup-form"
					action="${pageContext.request.contextPath}/register" method="post"
					enctype="multipart/form-data">
					<div class="form-group">
						<label for="first_name">First Name</label> <input type="text"
							id="first_name" name="first_name" value="${first_name}" required>
					</div>
					<div class="form-group">
						<label for="last_name">Last Name</label> <input type="text"
							id="last_name" name="last_name" value="${last_name}" required>
					</div>
					<div class="form-group">
						<label for="username">Username</label> <input type="text"
							id="username" name="username" value="${username}" required>
					</div>
					<div class="form-group">
						    <label for="birth_date">Birth Date</label>
						    <input type="date" id="birth_date" name="birth_date" value="${birth_date}">
					</div>
					<div class="form-group">
						<label for="email">Email Address</label> <input type="email"
							id="email" name="email" value="${email}" required>
					</div>
					<div class="form-group">
						<label for="phone">Phone Number</label> <input type="tel"
							id="phone" name="phone" value="${phone}">
					</div>
					<div class="form-group">
						<label for="password">Password</label> <input type="password"
							id="password" name="password" required>
						<p class="form-hint">At least 8 characters, 1 uppercase, 1
							number, 1 symbol</p>
					</div>
					<div class="form-group">
						<label for="role_id">User Role</label> <select id="role_id"
							name="role_id">
							<c:forEach var="role" items="${roles}">
								<option value="${role.roleId}">${role.roleName}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label for="image">Profile Image (Optional)</label> <input
							type="file" id="image" name="image" accept="image/*">
						<p class="form-hint">Upload a profile picture (optional).</p>
					</div>
					<button type="submit" class="btn-signup">Sign Up</button>
				</form>
			</div>
		</div>
	</div>

	<%@ include file="footer.jsp"%>
	<c:remove var="error" scope="request" />
</body>
</html>