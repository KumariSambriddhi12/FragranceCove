<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>FRAGRANCE COVE | Premium Fragrances</title>

<%-- Link the CSS files --%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/home.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/footer.css">

<%-- Optional: Add Google Fonts --%>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap"
	rel="stylesheet">
</head>
<body>
	<%-- Include Header --%>
	<jsp:include page="header.jsp" />

	<main>
		<!-- Hero Section -->
		<section class="hero-section">
			<%-- Background image is set via CSS --%>
			<div class="container hero-content">
				<h1>Timeless Scents, Modern Elegance</h1>
				<p>Explore our curated collection of exquisite fragrances
					crafted with the finest ingredients.</p>
				<a href="#" class="btn btn-primary">Shop New Arrivals</a>
			</div>
		</section>

		<!-- Featured Products Grid -->
		<section class="featured-products section-padding">
			<div class="container">
				<h2 class="section-title">Featured Fragrances</h2>
				<div class="product-grid">
					<%-- Repeat this block for each featured product --%>
					<div class="product-card">
						<a href="#"> <%-- Link to product page --%> <img
							src="${pageContext.request.contextPath}/images/homepage/Eau_da_perfume.png"
							alt="Product Name 1">
							<h3>Eau De perfume</h3>
							<p class="product-price">$110.00</p>
						</a>
						<button class="btn btn-secondary btn-add-cart">Add to
							Cart</button>
					</div>
					<div class="product-card">
						<a href="#"> <img
							src="${pageContext.request.contextPath}/images/homepage/grove candle.png"
							alt="Product Name 2">
							<h3>Citrus Grove Candle</h3>
							<p class="product-price">$65.00</p>
						</a>
						<button class="btn btn-secondary btn-add-cart">Add to
							Cart</button>
					</div>
					<div class="product-card">
						<a href="#"> <img
							src="${pageContext.request.contextPath}/images/product-placeholder-3.jpg"
							alt="Product Name 3">
							<h3>Velvet Rose Hand Cream</h3>
							<p class="product-price">$40.00</p>
						</a>
						<button class="btn btn-secondary btn-add-cart">Add to
							Cart</button>
					</div>
					<div class="product-card">
						<a href="#"> <img
							src="${pageContext.request.contextPath}/images/product-placeholder-4.jpg"
							alt="Product Name 4">
							<h3>Midnight Oud Diffuser</h3>
							<p class="product-price">$85.00</p>
						</a>
						<button class="btn btn-secondary btn-add-cart">Add to
							Cart</button>
					</div>
					<%-- End repeatable block --%>
				</div>
			</div>
		</section>

		<!-- Shop by Category -->
		<section class="category-showcase section-padding bg-light">
			<div class="container">
				<h2 class="section-title">Shop by Category</h2>
				<div class="category-grid">
					<a href="#" class="category-card"> <img
						src="${pageContext.request.contextPath}/images/category-perfume.jpg"
						alt="Perfumes Category"> <span>Perfumes</span>
					</a> <a href="#" class="category-card"> <img
						src="${pageContext.request.contextPath}/images/category-candles.jpg"
						alt="Home Fragrance Category"> <span>Home Fragrance</span>
					</a> <a href="#" class="category-card"> <img
						src="${pageContext.request.contextPath}/images/category-bodycare.jpg"
						alt="Body Care Category"> <span>Body Care</span>
					</a>
				</div>
			</div>
		</section>

		<!-- Image/Text Feature Section -->
		<section class="content-feature section-padding">
			<div class="container feature-container">
				<div class="feature-image">
					<img
						src="${pageContext.request.contextPath}/images/feature-image.jpg"
						alt="Feature description">
				</div>
				<div class="feature-text">
					<h2>The Art of Perfumery</h2>
					<p>We believe in the power of scent to evoke memories and
						emotions. Our master perfumers blend traditional techniques with
						contemporary artistry to create unique and captivating fragrances.</p>
					<a href="#" class="btn btn-secondary">Learn More</a>
				</div>
			</div>
		</section>

	</main>

	<%-- Include Footer --%>
	<jsp:include page="footer.jsp" />

</body>
</html>