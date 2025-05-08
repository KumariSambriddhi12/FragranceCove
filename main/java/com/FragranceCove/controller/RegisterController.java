package com.FragranceCove.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.FragranceCove.model.UserModel; // Import your UserModel
import com.FragranceCove.service.RegisterService;
import com.FragranceCove.util.PasswordUtil;
import com.FragranceCove.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * RegisterController handles user registration requests and processes form
 * submissions. It also manages file uploads and account creation.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/register" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final RegisterService registerService = new RegisterService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Validate and extract user model
            String validationMessage = validateRegistrationForm(req);
            if (validationMessage != null) {
                handleError(req, resp, validationMessage);
                return;
            }

            UserModel userModel = extractUserModel(req);
            Boolean isAdded = registerService.addUser(userModel); // Assuming you have an addUser method

            if (isAdded == null) {
                handleError(req, resp, "Our server is under maintenance. Please try again later!");
            } else if (isAdded) {
                handleSuccess(req, resp, "Your account is successfully created!", "/WEB-INF/pages/login.jsp");
            } else {
                handleError(req, resp, "Could not register your account. Please try again later!");
            }
        } catch (Exception e) {
            handleError(req, resp, "An unexpected error occurred. Please try again later!");
            e.printStackTrace(); // Log the exception
        }
    }

    private String validateRegistrationForm(HttpServletRequest req) {
        String firstName = req.getParameter("FirstName");
        String lastName = req.getParameter("LastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String retypePassword = req.getParameter("confirmPassword");
        String roleIdStr = req.getParameter("RoleId"); // Assuming "RoleId" is your role ID field

        // Check for null or empty fields first
        if (ValidationUtil.isNullOrEmpty(firstName))
            return "First name is required.";
        if (ValidationUtil.isNullOrEmpty(lastName))
            return "Last name is required.";
        if (ValidationUtil.isNullOrEmpty(email))
            return "Email is required.";
        if (ValidationUtil.isNullOrEmpty(password))
            return "Password is required.";
        if (ValidationUtil.isNullOrEmpty(retypePassword))
            return "Please retype the password.";
        if (ValidationUtil.isNullOrEmpty(roleIdStr))
            return "User role is required.";

        // Validate fields
        if (!ValidationUtil.isValidEmail(email))
            return "Invalid email format.";
        if (!ValidationUtil.isValidPassword(password))
            return "Password must be at least 8 characters long, with 1 uppercase letter, 1 number, and 1 symbol.";
        if (!ValidationUtil.doPasswordsMatch(password, retypePassword))
            return "Passwords do not match.";

        try {
            Integer.parseInt(roleIdStr); // Just check if it's a valid integer
        } catch (NumberFormatException e) {
            return "Invalid user role.";
        }

        return null; // All basic validations passed
    }

    private UserModel extractUserModel(HttpServletRequest req) throws Exception {
        String firstName = req.getParameter("FirstName");
        String lastName = req.getParameter("LastName");
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String roleIdStr = req.getParameter("RoleId");

        // Assuming password validation is already done
        String encryptedPassword = PasswordUtil.encrypt(email, password); // Using email as salt

        UserModel userModel = new UserModel();
        userModel.setFirstName(firstName);
        userModel.setLastName(lastName);
        userModel.setUsername(username);
        userModel.setEmail(email);
        userModel.setPassword(encryptedPassword);
        try {
            userModel.setRoleId(Integer.parseInt(roleIdStr));
        } catch (NumberFormatException e) {
            // Handle invalid role ID, maybe set a default or log an error
            userModel.setRoleId(2); // Default customer role, adjust as needed
        }
        userModel.setRegistrationDate(Timestamp.valueOf(LocalDateTime.now()));
        userModel.setStatus("active"); // Default status

        return userModel;
    }

    private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message, String redirectPage)
            throws ServletException, IOException {
        req.setAttribute("success", message);
        req.getRequestDispatcher(redirectPage).forward(req, resp);
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
            throws ServletException, IOException {
        req.setAttribute("error", message);
        req.setAttribute("firstName", req.getParameter("FirstName"));
        req.setAttribute("lastName", req.getParameter("LastName"));
        req.setAttribute("username", req.getParameter("username"));
        req.setAttribute("email", req.getParameter("email"));
        req.setAttribute("RoleId", req.getParameter("RoleId"));
        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }
}