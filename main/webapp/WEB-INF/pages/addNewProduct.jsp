 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<%--  <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> --%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add New Perfume</title>
    <style>
        .perfume-form {
            width: 400px;
            margin: 50px auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 8px;
            background-color: #f9f9f9;
            font-family: Arial, sans-serif;
        }
        .perfume-form h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        .perfume-form label {
            display: block;
            margin: 10px 0 5px;
            font-weight: bold;
        }
        .perfume-form input[type="text"],
        .perfume-form input[type="number"],
        .perfume-form textarea,
        .perfume-form select {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .perfume-form button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            margin-top: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .perfume-form button:hover {
            background-color: #45a049;
        }
        .perfume-form .error {
            color: red;
            font-size: 0.9em;
        }
    </style>
</head>
<body>
    <div class="perfume-form">
        <h2>Add New Perfume</h2>
        <!-- General validation message -->
        <% String errorMsg = (String) request.getAttribute("errorMessage");
           if (errorMsg != null) { %>
            <div class="error"><%= errorMsg %></div>
        <% } %>
        <form action="addNewProduct" method="post">
            <input type="hidden" name="action" value="addPerfume" />
            
            <label for="name">Name:</label>
            <input type="text" id="name" name="name"
                   value="<%= request.getParameter("name") != null ? request.getParameter("name") : "" %>" required />
            <span class="error"><%= request.getAttribute("nameError") != null ? request.getAttribute("nameError") : "" %></span>
            
            <label for="description">Description:</label>
            <textarea id="description" name="description" rows="3"><%= request.getParameter("description") != null ? request.getParameter("description") : "" %></textarea>
            <span class="error"><%= request.getAttribute("descriptionError") != null ? request.getAttribute("descriptionError") : "" %></span>
            
            <label for="price">Price:</label>
            <input type="number" id="price" name="price" step="0.01"
                   value="<%= request.getParameter("price") != null ? request.getParameter("price") : "" %>" required />
            <span class="error"><%= request.getAttribute("priceError") != null ? request.getAttribute("priceError") : "" %></span>
            
            <label for="stock_quantity">Stock Quantity:</label>
            <input type="number" id="stock_quantity" name="stock_quantity"
                   value="<%= request.getParameter("stock_quantity") != null ? request.getParameter("stock_quantity") : "" %>" />
            <span class="error"><%= request.getAttribute("stockError") != null ? request.getAttribute("stockError") : "" %></span>
            
            <label for="image_url">Image URL:</label>
            <input type="text" id="image_url" name="image_url"
                   value="<%= request.getParameter("image_url") != null ? request.getParameter("image_url") : "" %>" />
            <span class="error"><%= request.getAttribute("imageError") != null ? request.getAttribute("imageError") : "" %></span>
            
            <label for="brand_id">Brand ID:</label>
            <input type="number" id="brand_id" name="brand_id"
                   value="<%= request.getParameter("brand_id") != null ? request.getParameter("brand_id") : "" %>" />
            <span class="error"><%= request.getAttribute("brandError") != null ? request.getAttribute("brandError") : "" %></span>
            
            <label for="category_id">Category ID:</label>
            <input type="number" id="category_id" name="category_id"
                   value="<%= request.getParameter("category_id") != null ? request.getParameter("category_id") : "" %>" />
            <span class="error"><%= request.getAttribute("categoryError") != null ? request.getAttribute("categoryError") : "" %></span>
            
            <button type="submit">Add Perfume</button>
        </form>
    </div>
</body>
</html>
 