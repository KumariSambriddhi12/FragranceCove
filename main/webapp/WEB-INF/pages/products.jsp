<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Detail | Luxury Fragrances</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/products.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
</head>
<body>
    <%@ include file="header.jsp" %>

    <main class="product-main">
        <section class="product-detail">
            <div class="container">
                <h1>Our Products</h1>
                <div class="product-grid">
                    <div class="product-card">
                        <img src="${pageContext.request.contextPath}/images/products/elegance Noir.png" alt="Elegance Noir">
                        <h2>Elegance Noir</h2>
                        <div class="price">$129.99</div>
                        <form action="addToCart" method="post" class="product-form">
                            <button type="submit" class="btn btn-primary">Add to Cart</button>
                        </form>
                    </div>

                    <div class="product-card">
                        <img src="${pageContext.request.contextPath}/images/products/floral bliss.png" alt="Floral Bliss">
                        <h2>Floral Bliss</h2>
                        <div class="price">$99.99</div>
                        <form action="addToCart" method="post" class="product-form">
                            <button type="submit" class="btn btn-primary">Add to Cart</button>
                        </form>
                    </div>

                    <div class="product-card">
                        <img src="${pageContext.request.contextPath}/images/products/citrus fresh.png" alt="Citrus Fresh">
                        <h2>Citrus Fresh</h2>
                        <div class="price">$89.99</div>
                        <form action="addToCart" method="post" class="product-form">
                            <button type="submit" class="btn btn-primary">Add to Cart</button>
                        </form>
                    </div>

                    <div class="product-card">
                        <img src="${pageContext.request.contextPath}/images/products/woody essence.png" alt="Woody Essence">
                        <h2>Woody Essence</h2>
                        <div class="price">$109.99</div>
                        <form action="addToCart" method="post" class="product-form">
                            <button type="submit" class="btn btn-primary">Add to Cart</button>
                        </form>
                    </div>

                    <div class="product-card">
                        <img src="${pageContext.request.contextPath}/images/products/ocean bridge.png" alt="Ocean Breeze">
                        <h2>Ocean Breeze</h2>
                        <div class="price">$79.99</div>
                        <form action="addToCart" method="post" class="product-form">
                            <button type="submit" class="btn btn-primary">Add to Cart</button>
                        </form>
                    </div>

                    <div class="product-card">
                        <img src="${pageContext.request.contextPath}/images/products/spicy wood.png" alt="Spicy Wood">
                        <h2>Spicy Wood</h2>
                        <div class="price">$119.99</div>
                        <form action="addToCart" method="post" class="product-form">
                            <button type="submit" class="btn btn-primary">Add to Cart</button>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    </main>

    <%@ include file="footer.jsp" %>
</body>
</html>
