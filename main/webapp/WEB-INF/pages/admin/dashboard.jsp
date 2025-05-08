<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard Summary</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="dashboard-container">
        <h1>Dashboard Summary of your App</h1>
        
        <div class="stats-container">
            <div class="stat-card">
                <div class="stat-value">${totalUsers != null ? totalUsers : "0"}</div>
                <div class="stat-label">Total Users</div>
            </div>
            <div class="stat-card">
                <div class="stat-value">${totalProducts != null ? totalProducts : "0"}</div>
                <div class="stat-label">Total Products</div>
            </div>
            <div class="stat-card">
                <div class="stat-value">${pendingOrders != null ? pendingOrders : "0"}</div>
                <div class="stat-label">Pending Orders</div>
            </div>
            <div class="stat-card">
                <div class="stat-value">${recentSales != null ? recentSales : "0.00"}</div>
                <div class="stat-label">Recent Sales (24h)</div>
            </div>
             <div class="stat-card">
                <div class="stat-value">${lowStockProducts != null ? lowStockProducts: "0"}</div>
                <div class="stat-label">Low Stock Products</div>
            </div>
        </div>
        
        <div class="charts-container">
            <div class="chart-card">
                <h3>Area Chart</h3>
                <div class="chart-area">
                    <div class="chart-bar" style="height: 30.00%;"></div>
                    <div class="chart-bar" style="height: 22.50%;"></div>
                    <div class="chart-bar" style="height: 10.00%;"></div>
                    <div class="chart-bar" style="height: 7.50%;"></div>
                </div>
            </div>
            
            <div class="chart-card">
                <h3>Line Chart</h3>
                <div class="chart-line">
                    <div class="chart-point" style="bottom: 300px;"></div>
                    <div class="chart-point" style="bottom: 150px;"></div>
                    <div class="chart-point" style="bottom: 100px;"></div>
                    <div class="chart-point" style="bottom: 50px;"></div>
                    <div class="chart-line-connector"></div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="footer.jsp" %>
</body>
</html>
