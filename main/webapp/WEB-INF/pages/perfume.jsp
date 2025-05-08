<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Perfumes</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/perfume.css">
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 font-inter">
    <div class="container mx-auto p-6 sm:p-10 md:p-12 lg:p-16">
        <h1 class="text-2xl font-semibold text-gray-800 mb-6 text-center">Manage Perfumes</h1>

        <div id="messageBox" class="message-box" role="alert">
            <span id="messageText"></span>
        </div>

        <div class="bg-white shadow-md rounded-lg overflow-x-auto">
            <table class="min-w-full leading-normal">
                <thead class="bg-gray-200 text-gray-700">
                    <tr>
                        <th class="px-5 py-3 border-b-2 border-gray-200 text-left text-xs font-semibold uppercase tracking-wider">
                            ID
                        </th>
                        <th class="px-5 py-3 border-b-2 border-gray-200 text-left text-xs font-semibold uppercase tracking-wider">
                            Brand
                        </th>
                        <th class="px-5 py-3 border-b-2 border-gray-200 text-left text-xs font-semibold uppercase tracking-wider">
                            Item Form
                        </th>
                        <th class="px-5 py-3 border-b-2 border-gray-200 text-left text-xs font-semibold uppercase tracking-wider">
                            Volume
                        </th>
                        <th class="px-5 py-3 border-b-2 border-gray-200 text-left text-xs font-semibold uppercase tracking-wider">
                            Scent
                        </th>
                        <th class="px-5 py-3 border-b-2 border-gray-200 text-left text-xs font-semibold uppercase tracking-wider">
                            Price
                        </th>
                        <th class="px-5 py-3 border-b-2 border-gray-200 text-left text-xs font-semibold uppercase tracking-wider">
                            Actions
                        </th>
                    </tr>
                </thead>
                <tbody class="bg-white">
                    <c:forEach var="perfume" items="${perfumeList}">
                        <tr>
                            <td class="px-5 py-5 border-b border-gray-200 text-sm">
                                <span class="text-gray-900">${perfume.id}</span>
                            </td>
                            <td class="px-5 py-5 border-b border-gray-200 text-sm">
                                <span class="text-gray-900">${perfume.brand}</span>
                            </td>
                            <td class="px-5 py-5 border-b border-gray-200 text-sm">
                                <span class="text-gray-900">${perfume.itemForm}</span>
                            </td>
                            <td class="px-5 py-5 border-b border-gray-200 text-sm">
                                <span class="text-gray-900">${perfume.volume}</span>
                            </td>
                             <td class="px-5 py-5 border-b border-gray-200 text-sm">
                                <span class="text-gray-900">${perfume.scent}</span>
                            </td>
                            <td class="px-5 py-5 border-b border-gray-200 text-sm">
                                 <span class="text-gray-900">${perfume.price}</span>
                            </td>
                            <td class="px-5 py-5 border-b border-gray-200 text-sm">
                                <div class="flex space-x-2">
                                    <button
                                        onclick="location.href='${pageContext.request.contextPath}/managePerfumes?action=updateForm&perfumeId=${perfume.id}'"
                                        class="px-3 py-1.5 bg-blue-500 hover:bg-blue-700 text-white rounded-md text-xs font-semibold"
                                    >
                                        Update
                                    </button>
                                    <button
                                        onclick="deletePerfume(${perfume.id})"
                                        class="px-3 py-1.5 bg-red-500 hover:bg-red-700 text-white rounded-md text-xs font-semibold"
                                    >
                                        Delete
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <script>
        function deletePerfume(perfumeId) {
            if (confirm("Are you sure you want to delete this perfume?")) {
                const deleteUrl = `${window.location.href}?action=delete&perfumeId=${perfumeId}`;
                const form = document.createElement('form');
                form.method = 'POST';
                form.action = deleteUrl;
                const perfumeIdInput = document.createElement('input');
                perfumeIdInput.type = 'hidden';
                perfumeIdInput.name = 'perfumeId';
                perfumeIdInput.value = perfumeId;
                form.appendChild(perfumeIdInput);
                document.body.appendChild(form);
                form.submit();
            }
        }

        function showMessage(message, type = 'success') {
            const messageBox = document.getElementById('messageBox');
            const messageText = document.getElementById('messageText');
            messageText.textContent = message;
            messageBox.className = `message-box ${type}`;
            messageBox.classList.add('show');
            setTimeout(() => {
                messageBox.classList.remove('show');
            }, 3000);
        }

        window.onload = function() {
            const successMessage = "${success}";
            const errorMessage = "${error}";
            if (successMessage) {
                showMessage(successMessage, 'success');
            } else if (errorMessage) {
                showMessage(errorMessage, 'error');
            }
        };
    </script>
</body>
</html>
