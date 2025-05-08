<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<footer id="main-footer">
    <div class="container footer-container">
        <div class="footer-column">
            <h4>Shop</h4>
            <ul>
                <li><a href="#">Perfumes</a></li>
                <li><a href="#">Body Care</a></li>
                <li><a href="#">Home Fragrance</a></li>
                <li><a href="#">Gifts</a></li>
            </ul>
        </div>
        <div class="footer-column">
            <h4>About Us</h4>
            <ul>
                <li><a href="#">Our Story</a></li>
                <li><a href="#">Ingredients</a></li>
                <li><a href="#">Sustainability</a></li>
                <li><a href="#">Careers</a></li>
            </ul>
        </div>
        <div class="footer-column">
            <h4>Customer Care</h4>
            <ul>
                <li><a href="#">Contact Us</a></li>
                <li><a href="#">Shipping & Returns</a></li>
                <li><a href="#">FAQ</a></li>
                <li><a href="#">Track Order</a></li>
            </ul>
        </div>
        <div class="footer-column">
            <h4>Follow Us</h4>
             <div class="social-icons">
                 <%-- Placeholders - replace with actual icons/links --%>
                 <a href="#" aria-label="Instagram"><span class="icon-placeholder">📷</span></a>
                 <a href="#" aria-label="Facebook"><span class="icon-placeholder">👍</span></a>
                 <a href="#" aria-label="Pinterest"><span class="icon-placeholder">📌</span></a>
             </div>
             <h4>Newsletter</h4>
             <form action="#" method="post" class="newsletter-form">
                 <input type="email" placeholder="Enter your email" required>
                 <button type="submit">Subscribe</button>
             </form>
        </div>
    </div>
    <div class="footer-bottom">
        <div class="container">
            <p>© <%= new java.util.Date().getYear() + 1900 %> Aromance. All Rights Reserved.</p>
            <%-- Add payment icons or other info if needed --%>
        </div>
    </div>
</footer>