<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin - Add New Perfume</title>
    <%-- Link to base styles (optional) --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin-base-style.css">
    <%-- Link to specific styles for this page --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/add-perfume-style.css">
</head>
<body>
    <div class="admin-content-container add-perfume-form-container"> <%-- Added specific class --%>
        <h1>Add New Perfume</h1>

        <c:if test="${not empty errorMessage}">
            <p class="error-message">${errorMessage}</p>
        </c:if>
        <c:if test="${not empty param.error}">
            <p class="error-message">Error: ${param.error}</p>
        </c:if>

        <c:set var="nameValue" value="${not empty submittedPerfume ? submittedPerfume.name : ''}" />
        <c:set var="brandIdValue" value="${not empty submittedPerfume ? submittedPerfume.brandId : ''}" />
        <c:set var="categoryIdValue" value="${not empty submittedPerfume ? submittedPerfume.categoryId : ''}" />
        <c:set var="descriptionValue" value="${not empty submittedPerfume ? submittedPerfume.description : ''}" />
        <c:set var="priceValue" value="${not empty submittedPerfume ? submittedPerfume.price : ''}" />
        <c:set var="stockValue" value="${not empty submittedPerfume ? submittedPerfume.stockQuantity : ''}" />

        <form method="POST" action="${pageContext.request.contextPath}/admin/perfumes/add" enctype="multipart/form-data">
            <div class="form-group">
                <label for="name">Perfume Name:</label>
                <input type="text" id="name" name="name" value="${nameValue}" required>
            </div>

            <div class="form-group">
                <label for="brandId">Brand:</label>
                <select id="brandId" name="brandId" required>
                    <option value="">-- Select Brand --</option>
                    <c:forEach var="brand" items="${brandsList}">
                        <option value="${brand.brandId}" ${brand.brandId == brandIdValue ? 'selected' : ''}>
                            <c:out value="${brand.brandName}"/>
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="categoryId">Category:</label>
                <select id="categoryId" name="categoryId" required>
                    <option value="">-- Select Category --</option>
                    <c:forEach var="category" items="${categoriesList}">
                        <option value="${category.categoryId}" ${category.categoryId == categoryIdValue ? 'selected' : ''}>
                            <c:out value="${category.categoryName}"/>
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="description">Description:</label>
                <textarea id="description" name="description" required><c:out value="${descriptionValue}"/></textarea>
            </div>

            <div class="form-group">
                <label for="price">Price:</label>
                <input type="number" id="price" name="price" step="0.01" value="${priceValue}" required>
            </div>

            <div class="form-group">
                <label for="stockQuantity">Stock Quantity:</label>
                <input type="number" id="stockQuantity" name="stockQuantity" value="${stockValue}" required>
            </div>

            <div class="form-group">
                <label for="image">Image:</label>
                <input type="file" id="image" name="image" accept="image/*">
                <small>Upload an image for the perfume.</small>
            </div>

            <div class="form-actions">
                <button type="submit">Add Perfume</button>
                 <a href="${pageContext.request.contextPath}/admin/perfumes" class="button-link" style="background-color: #6c757d; margin-left:10px;">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>