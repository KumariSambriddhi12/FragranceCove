<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> <%-- For formatting price --%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin - Manage Perfumes</title>
    <%-- Link to base styles (optional) --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin-base-style.css">
    <%-- Link to specific styles for this page --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/list-perfumes-style.css">
    <script>
        function confirmDelete(perfumeId) {
            if (confirm("Are you sure you want to delete this perfume? This action cannot be undone.")) {
                window.location.href = "${pageContext.request.contextPath}/admin/perfumes/delete?id=" + perfumeId;
            }
            return false; // Prevent default link behavior if not confirmed
        }
    </script>
</head>
<body>
    <div class="admin-content-container list-perfumes-container"> <%-- Added specific class --%>
        <h1>Manage Perfumes</h1>

        <c:if test="${not empty param.message}">
            <p class="success-message">
                <c:choose>
                    <c:when test="${param.message == 'add_success'}">Perfume added successfully!</c:when>
                    <c:when test="${param.message == 'update_success'}">Perfume updated successfully!</c:when>
                    <c:when test="${param.message == 'delete_success'}">Perfume deleted successfully!</c:when>
                    <c:otherwise>Operation successful!</c:otherwise>
                </c:choose>
            </p>
        </c:if>
        <c:if test="${not empty param.error}">
            <p class="error-message">
                 <c:choose>
                    <c:when test="${param.error == 'not_found'}">Perfume not found.</c:when>
                    <c:when test="${param.error == 'delete_failed'}">Failed to delete perfume. It might be in use.</c:when>
                    <c:when test="${param.error == 'add_failed'}">Failed to add perfume. Please check your input.</c:when>
                    <c:when test="${param.error == 'update_failed'}">Failed to update perfume. Please check your input.</c:when>
                    <c:otherwise>An error occurred: <c:out value="${param.error}"/></c:otherwise>
                </c:choose>
            </p>
        </c:if>

        <div class="actions-bar">
            <a href="${pageContext.request.contextPath}/admin/perfumes/add" class="button-link">Add New Perfume</a>
            <form action="${pageContext.request.contextPath}/admin/perfumes" method="get" class="search-form">
                <input type="text" name="search" placeholder="Search by name or brand..." value="<c:out value="${search}"/>">
                <button type="submit" class="button">Search</button>
                <c:if test="${not empty search}">
                     <a href="${pageContext.request.contextPath}/admin/perfumes" class="button-link" style="background-color: #6c757d;">Clear</a>
                </c:if>
            </form>
        </div>

        <c:if test="${empty perfumes}">
            <p class="no-perfumes-message">No perfumes found matching your criteria.</p>
        </c:if>

        <c:if test="${not empty perfumes}">
            <table class="admin-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Image</th>
                        <th>Name</th>
                        <th>Brand</th>
                        <th>Category</th>
                        <th>Price</th>
                        <th>Stock</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="perfume" items="${perfumes}">
                        <tr>
                            <td>${perfume.id}</td>
                            <td>
                                <c:if test="${not empty perfume.imageUrl && perfume.imageUrl ne 'uploads/perfumes/default-perfume.jpg'}">
                                    <img src="${pageContext.request.contextPath}/${perfume.imageUrl}" alt="<c:out value="${perfume.name}"/>" class="thumbnail">
                                </c:if>
                                <c:if test="${empty perfume.imageUrl || perfume.imageUrl eq 'uploads/perfumes/default-perfume.jpg'}">
                                    <img src="${pageContext.request.contextPath}/uploads/perfumes/default-perfume.jpg" alt="Default" class="thumbnail">
                                </c:if>
                            </td>
                            <td><c:out value="${perfume.name}"/></td>
                            <td><c:out value="${perfume.brand}"/></td>
                            <td><c:out value="${perfume.category}"/></td>
                            <td><fmt:formatNumber value="${perfume.price}" type="currency" currencySymbol="$"/></td> <%-- Example currency formatting --%>
                            <td><c:out value="${perfume.stockQuantity}"/></td>
                            <td class="actions">
                                <a href="${pageContext.request.contextPath}/admin/perfumes/edit?id=${perfume.id}" class="button-link edit-link">Edit</a>
                                <a href="#" onclick="return confirmDelete(${perfume.id});" class="button-link delete-link">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</body>
</html>