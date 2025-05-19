<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin - Edit Perfume</title>
    <%-- Link to base styles (optional) --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin-base-style.css">
    <%-- Link to specific styles for this page --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/edit-perfume-style.css">
</head>
<body>
    <div class="admin-content-container edit-perfume-form-container"> <%-- Added specific class --%>
        <h1>Edit Perfume: <c:out value="${perfume.name}"/></h1>

        <c:if test="${not empty errorMessage}">
            <p class="error-message">${errorMessage}</p>
        </c:if>
        <c:if test="${not empty param.error}">
            <p class="error-message">Error: ${param.error}</p>
        </c:if>

        <form method="POST" action="${pageContext.request.contextPath}/admin/perfumes/edit" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${perfume.id}">

            <div class="form-group">
                <label for="name">Perfume Name:</label>
                <input type="text" id="name" name="name" value="<c:out value="${perfume.name}"/>" required>
            </div>

            <div class="form-group">
                <label for="brandId">Brand:</label>
                <select id="brandId" name="brandId" required>
                    <option value="">-- Select Brand --</option>
                    <c:forEach var="brand" items="${brandsList}">
                        <option value="${brand.brandId}" ${brand.brandId == perfume.brandId ? 'selected' : ''}>
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
                        <option value="${category.categoryId}" ${category.categoryId == perfume.categoryId ? 'selected' : ''}>
                            <c:out value="${category.categoryName}"/>
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="description">Description:</label>
                <textarea id="description" name="description" required><c:out value="${perfume.description}"/></textarea>
            </div>

            <div class="form-group">
                <label for="price">Price:</label>
                <input type="number" id="price" name="price" step="0.01" value="${perfume.price}" required>
            </div>

            <div class="form-group">
                <label for="stockQuantity">Stock Quantity:</label>
                <input type="number" id="stockQuantity" name="stockQuantity" value="${perfume.stockQuantity}" required>
            </div>

            <div class="form-group current-image-container">
                <span class="current-image-label">Current Image:</span>
                <c:if test="${not empty perfume.imageUrl && perfume.imageUrl ne 'uploads/perfumes/default-perfume.jpg'}">
                    <img src="${pageContext.request.contextPath}/${perfume.imageUrl}" alt="<c:out value="${perfume.name}"/>" class="current-image">
                </c:if>
                <c:if test="${empty perfume.imageUrl || perfume.imageUrl eq 'uploads/perfumes/default-perfume.jpg'}">
                    <p>No image available or using default.</p>
                </c:if>
            </div>

            <div class="form-group">
                <label for="image">Upload New Image (optional):</label>
                <input type="file" id="image" name="image" accept="image/*">
                <small>Leave empty to keep the current image.</small>
            </div>

            <div class="form-actions">
                <button type="submit">Update Perfume</button>
                 <a href="${pageContext.request.contextPath}/admin/perfumes" class="button-link" style="background-color: #6c757d; margin-left:10px;">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>