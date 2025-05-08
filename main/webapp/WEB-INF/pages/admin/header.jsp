<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header id="main-header">
    <div class="container uber-header-container"> <%-- Added specific class --%>
        <div class="header-left">
             <div class="logo-container">
                 <a href="${pageContext.request.contextPath}/home" class="logo-link">
                     <%-- <img src="${pageContext.request.contextPath}/images/uber-style-logo.png" alt="Aromance Logo"> --%>
                     <span class="logo-text">Fragrance Cove</span> <%-- Keep your logo text --%>
                 </a>
             </div>
             <nav class="primary-nav">
                 <ul>
                     <%-- Main site navigation links --%>
                     <li><a href="${pageContext.request.contextPath}/shop">Shop</a></li>
                     <li><a href="${pageContext.request.contextPath}/product">Products</a></li>
                     <li><a href="${pageContext.request.contextPath}/about">About</a></li>
                 </ul>
             </nav>
        </div>

        <div class="header-right">
             <nav class="secondary-nav">
                 <ul>
                     <%-- Secondary actions/links --%>
                     <li><a href="${pageContext.request.contextPath}/cart">Cart</a></li>
                     <li><a href="${pageContext.request.contextPath}/dashboard">Dashboard</a></li>
                     <li><a href="${pageContext.request.contextPath}/login">Log In</a></li>
                 </ul>
             </nav>
             <a href="${pageContext.request.contextPath}/register" class="btn btn-signup">Sign Up</a> <%-- Distinct Sign Up button --%>
        </div>
    </div>
</header>