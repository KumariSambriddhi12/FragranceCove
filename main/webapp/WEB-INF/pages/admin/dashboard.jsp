<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard Summary</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
</head>
<body>
    <%@ include file="adminheader.jsp" %>
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

        <div>
            <h3>Product Inventory</h3>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Stock Quantity</th>
                        <th>Image URL</th>
                        <th>Brand ID</th>
                        <th>Category ID</th>
                        <th>Date Added</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%-- Ensure your servlet passes 'productList' to the JSP --%>
                    <c:forEach var="product" items="${productList}">
                        <tr>
                            <td>${product.id}</td>
                            <td><c:out value="${product.name}"/></td>
                            <td><c:out value="${product.description}"/></td>
                            <td>${product.price}</td>
                            <td>${product.stockQuantity}</td>
                            <td><c:out value="${product.imageUrl}"/></td>
                            <td>${product.brandId}</td>
                            <td>${product.categoryId}</td>
                            <td>${product.dateAdded}</td> {/* Consider formatting this date/timestamp if needed */}
                            <td>
                                <form action="${pageContext.request.contextPath}/dashboard" method="post" style="display: inline;">
                                    <input type="hidden" name="productId" value="${product.id}">
                                    <input type="hidden" name="action" value="updateFormProduct"> {/* Changed from updateFormPerfume */}
                                    <button type="submit">
                                        <img src="${pageContext.request.contextPath}/resources/images/icons/edit.png" alt="Edit" title="Edit" />
                                    </button>
                                </form>
                                <form action="${pageContext.request.contextPath}/dashboard" method="post" style="display: inline;">
                                    <input type="hidden" name="productId" value="${product.id}">
                                    <input type="hidden" name="action" value="deleteProduct"> {/* Changed from deletePerfume */}
                                    <button type="submit">
                                        <img src="${pageContext.request.contextPath}/resources/images/icons/delete.png" alt="Delete" title="Delete" />
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <%@ include file="adminfooter.jsp" %>
</body>
</html>