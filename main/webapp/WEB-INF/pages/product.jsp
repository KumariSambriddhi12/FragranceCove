<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Our Collection | Luxury Fragrances</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/products.css">
</head>
<body>
    <%@ include file="header.jsp" %>

    <main class="products-main">
        <section class="product-filters">
            <div class="container">
                <h1>Perfume Collection</h1>
                <div class="filter-options">
                    <div class="filter-group">
                        <label>Category:</label>
                        <select>
                            <option>All Fragrances</option>
                            <option>Women's Perfume</option>
                            <option>Men's Cologne</option>
                            <option>Unisex</option>
                        </select>
                    </div>
                    <div class="filter-group">
                        <label>Sort By:</label>
                        <select>
                            <option>Best Sellers</option>
                            <option>New Arrivals</option>
                            <option>Price: Low to High</option>
                        </select>
                    </div>
                </div>
            </div>
        </section>

        <section class="product-grid-section">
            <div class="container">
                <div class="product-grid">
                    <!-- Product 1 -->
                    <div class="product-card">
                        <div class="product-badge">Best Seller</div>
                        <img src="${pageContext.request.contextPath}/images/products/perfume1.jpg" alt="Elegance Noir">
                        <div class="product-info">
                            <h3>Elegance Noir</h3>
                            <div class="scent-notes">
                                <span>Floral</span>
                                <span>Woody</span>
                                <span>Vanilla</span>
                            </div>
                            <span class="price">$129.99</span>
                            <a href="product.jsp?id=1" class="btn btn-sm">View Details</a>
                        </div>
                    </div>
                    
                    <!-- Repeat product cards... -->
                </div>
            </div>
        </section>
    </main>

    <%@ include file="footer.jsp" %>
</body>
</html>