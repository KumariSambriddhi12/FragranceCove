<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Our Perfumes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
</head>
<body>
	<%-- Include Header --%>
	<jsp:include page="header.jsp" />

    <div class="products-container">
        <h2 class="products-title">Our Exquisite Perfumes</h2>
        <p class="products-subtitle">Discover your signature scent.</p>

        <div class="products-filter">
            <button class="filter-button active">All Perfumes</button>
            <button class="filter-button">Floral</button>
            <button class="filter-button">Woody</button>
            <button class="filter-button">Citrus</button>
            <button class="filter-button">Oriental</button>
            <button class="filter-button">Fresh</button>
            </div>

        <div class="products-grid">
            <c:if test="${empty perfumeList}">
                <p class="debug-info">No perfumes found in the database.</p>
            </c:if>

            <c:forEach var="perfume" items="${perfumeList}">
                <div class="product-card" data-category="${perfume.category}">
                    <div class="product-image">
                        <img src="${not empty perfume.imageUrl ? perfume.imageUrl : 'images/placeholder.jpg'}" alt="${perfume.name}">
                    </div>
                    <div class="product-details">
                        <h3 class="product-name">${perfume.name}</h3>
                        <p class="product-brand">${perfume.brand}</p>
                        <p class="product-category">Category: ${perfume.category}</p>
                        <p class="product-price">$${perfume.price}</p>
                        <%-- <p class="product-description">${perfume.description}</p> --%>
                        <form action="${pageContext.request.contextPath}/cart" method="post">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="productId" value="${perfume.id}">
                            <button type="submit" class="add-to-cart">Buy Now</button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="pagination">
            <button class="pagination-button prev">&larr;</button>
            <div class="pagination-dots">
                <span class="dot active"></span>
                <span class="dot"></span>
                <span class="dot"></span>
            </div>
            <button class="pagination-button next">&rarr;</button>
        </div>
    </div>

	<%-- Include Footer --%>
	<jsp:include page="footer.jsp" />
</body>
</html>