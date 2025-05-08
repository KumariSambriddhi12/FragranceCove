<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Account | Luxury Fragrances</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
    <%@ include file="header.jsp" %>

    <main class="user-container">
        <h1 class="user-greeting">Welcome, ${user.name}!</h1>
        
        <div class="user-card">
            <h2><i class="fas fa-user-circle"></i> My Profile</h2>
            <form class="user-form" action="updateProfile" method="post">
                <!-- Form fields same as before -->
            </form>
        </div>

        <div class="user-card">
            <h2><i class="fas fa-box-open"></i> Recent Orders</h2>
            <div class="order-list">
                <!-- Static order listing -->
            </div>
            <a href="orders.jsp" class="btn btn-view-all">View All Orders</a>
        </div>
    </main>

    <%@ include file="footer.jsp" %>
</body>
</html>
