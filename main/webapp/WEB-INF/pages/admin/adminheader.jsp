<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style>
        /* Main navigation styles */
        ul.nav {
            list-style: none;
            padding: 0;
            margin: 0;
            /* background-color: #333; */
            display: flex;
        }

        ul.nav > li {
            position: relative;
        }

        ul.nav > li > a {
            display: block;
            padding: 10px 20px;
            color: white;
            text-decoration: none;
        }

        ul.nav > li:hover {
            background-color: #ff6b6b;
        }

        /* Submenu styles */
        .submenu {
            display: none !important;
            position: absolute;
            top: 55px;
            left: 0;
            width: 200px;
            background-color: #444;
            list-style: none;
            margin: 0;
            padding:0;
            min-width: 180px;
            border-radius: 8px;
        }

        .submenu li a {
            /* display: block; */
            padding: 16px !important;
            color: white;
            text-decoration: none;
            margin: 8px;
        }

        .submenu li a:hover {
            /* background-color: #ff6b6b; */
        }

        /* Show submenu on hover */
        li:hover .submenu {
            display: block !important;
        }
    </style>

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
                 <ul class = "nav">
                     <%-- Main site navigation links --%>
                     <li><a href="${pageContext.request.contextPath}/shop">Shop</a></li>
                     <%-- <li>
                     <a href="${pageContext.request.contextPath}/product">Products</a>
                     </li> --%>
                     
                     <!-- editing -->
                     <li class="dropdown">
				        <a href="${pageContext.request.contextPath}/product">Products</a>
				        <ul class="submenu">
				            <li><a href="${pageContext.request.contextPath}/addNewProduct">Add Product</a></li>
				            <li><a href="${pageContext.request.contextPath}/product/edit">Edit Product</a></li>
				            <li><a href="${pageContext.request.contextPath}/product/delete">Delete Product</a></li>
				            <li><a href="${pageContext.request.contextPath}/product/list">Show All Products</a></li>
				        </ul>
				    </li>
				                     
                     <li><a href="${pageContext.request.contextPath}/about">About</a></li>
                 </ul>
             </nav>
        </div>

        <div class="header-right">
             <nav class="secondary-nav">
                 <ul>
                     <%-- Secondary actions/links --%>
                     <li><a href="${pageContext.request.contextPath}/portfolio">Portfolio</a></li>
                     <li><a href="${pageContext.request.contextPath}/dashboard">Dashboard</a></li>
                     <li><a href="${pageContext.request.contextPath}/login">Log In</a></li>
                 </ul>
             </nav>
             <a href="${pageContext.request.contextPath}/register" class="btn btn-signup">Sign Up</a> <%-- Distinct Sign Up button --%>
        </div>
    </div>
</header>



