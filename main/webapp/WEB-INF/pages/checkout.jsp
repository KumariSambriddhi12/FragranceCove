<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout | Luxury Fragrances</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/checkout.css">
</head>
<body>
    <%@ include file="header.jsp" %>

    <main class="checkout-main">
        <div class="container">
            <h1>Checkout</h1>
            
            <form action="processOrder" method="post" class="checkout-form">
                <section class="checkout-section">
                    <h2>Shipping Information</h2>
                    <div class="form-group">
                        <label for="fullName">Full Name</label>
                        <input type="text" id="fullName" name="fullName" required>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" id="email" name="email" required>
                        </div>
                        <div class="form-group">
                            <label for="phone">Phone</label>
                            <input type="tel" id="phone" name="phone" required>
                        </div>
                    </div>
                    <!-- More address fields -->
                </section>
                
                <section class="checkout-section">
                    <h2>Payment Method</h2>
                    <div class="payment-methods">
                        <label class="payment-option">
                            <input type="radio" name="payment" value="credit" checked>
                            Credit Card
                        </label>
                        <label class="payment-option">
                            <input type="radio" name="payment" value="paypal">
                            PayPal
                        </label>
                    </div>
                </section>
                
                <button type="submit" class="btn btn-primary">Place Order</button>
            </form>
        </div>
    </main>

    <%@ include file="footer.jsp" %>
</body>
</html>
