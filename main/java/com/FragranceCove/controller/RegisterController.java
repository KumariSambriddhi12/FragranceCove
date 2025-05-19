package com.FragranceCove.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.FragranceCove.model.RoleModel;
import com.FragranceCove.model.UserModel;
import com.FragranceCove.service.RegisterService;
import com.FragranceCove.util.ImageUtil;
import com.FragranceCove.util.PasswordUtil;
import com.FragranceCove.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet(asyncSupported = true, urlPatterns = { "/register" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ImageUtil imageUtil = new ImageUtil();
    private final RegisterService registerService = new RegisterService();
    // DateTimeFormatter is not used in the current code, but kept if planned for future use
    // private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final String USER_IMAGE_SUB_FOLDER = "user"; // Subfolder for user images within "images"

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<RoleModel> roles = registerService.getRoles();
            // System.out.println("Retrieved roles: " + roles);
            req.setAttribute("roles", roles);
        } catch (Exception e) { // Catch potential exceptions from service layer
            e.printStackTrace();
            req.setAttribute("errorMessage", "Could not load roles. Please try again later.");
            // You might want to handle this more gracefully, e.g. not show role selection
        }
        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // System.out.println("DEBUG: RegisterController doPost CALLED");
        req.setCharacterEncoding("UTF-8"); // Ensure correct encoding for parameters

        try {
            // Validate registration form data
            String validationMessage = validateRegistrationForm(req);
            if (validationMessage != null) {
                handleError(req, resp, validationMessage); // handleError will forward
                return;
            }

            // Step 1: Upload image (if provided) and get its relative path
            String uploadedImageRelativePath = null; // This will be like "images/user/uuid.jpg"
            Part imagePart = req.getPart("imagePath"); // Name of your <input type="file">

            if (imagePart != null && imagePart.getSize() > 0) {
                String rootPath = req.getServletContext().getRealPath("/");
                // imageUtil.uploadImage now returns the relative path or null
                uploadedImageRelativePath = imageUtil.uploadImage(imagePart, rootPath, USER_IMAGE_SUB_FOLDER);

                if (uploadedImageRelativePath == null) { // Upload failed
                    handleError(req, resp, "Could not upload your profile image. Please try again later!");
                    return;
                }
            }
            // If image is optional and not provided, uploadedImageRelativePath will remain null.

            // Step 2: Extract user model, passing the new relative image path
            UserModel userModel = extractUserModel(req, uploadedImageRelativePath);

            // Step 3: Add user to database
            Boolean isAdded = registerService.addUser(userModel);

            if (isAdded == null) {
                // This case might indicate a server-side issue not caught by an exception earlier
                handleError(req, resp, "Our server encountered an issue. Please try again later!");
            } else if (isAdded) {
                // Redirect to login page on success
                req.getSession().setAttribute("successMessage", "Your account has been successfully created! Please login.");
                resp.sendRedirect(req.getContextPath() + "/login"); // Assuming /login is your login page URL
            } else {
                // This usually means a non-exception failure from addUser, e.g., username/email taken if not validated earlier
                handleError(req, resp, "Could not register your account. The username or email might already be in use, or an error occurred.");
            }

        } catch (ServletException e) { // Specifically catch ServletException (e.g., from getPart)
            e.printStackTrace();
            handleError(req, resp, "Error processing your request: " + e.getMessage());
        }
        catch (Exception e) { // Generic catch-all for other unexpected errors
            e.printStackTrace();
            handleError(req, resp, "An unexpected error occurred. Please try again later!");
        }
    }

    private String validateRegistrationForm(HttpServletRequest req) {
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String username = req.getParameter("username");
        String birthDateStr = req.getParameter("birth_date");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");
        String roleIdStr = req.getParameter("role_id"); // Also validate role selection
        LocalDate birthdate = null;

        if (ValidationUtil.isNullOrEmpty(firstName)) return "First name is required.";
        if (ValidationUtil.isNullOrEmpty(lastName)) return "Last name is required.";
        if (ValidationUtil.isNullOrEmpty(username)) return "Username is required.";
        if (ValidationUtil.isNullOrEmpty(birthDateStr)) return "Date of birth is required.";
        if (ValidationUtil.isNullOrEmpty(email)) return "Email is required.";
        if (ValidationUtil.isNullOrEmpty(phone)) return "Phone number is required.";
        if (ValidationUtil.isNullOrEmpty(password)) return "Password is required.";
        if (ValidationUtil.isNullOrEmpty(roleIdStr)) return "Please select a role.";


        try {
            birthdate = LocalDate.parse(birthDateStr, DateTimeFormatter.ISO_LOCAL_DATE); // Standard yyyy-MM-dd
        } catch (Exception e) {
            return "Invalid date of birth format. Please use YYYY-MM-DD.";
        }
        
        try {
            Integer.parseInt(roleIdStr); // Check if role_id is a valid number
        } catch (NumberFormatException e) {
            return "Invalid role selected.";
        }


        if (!ValidationUtil.isAlphanumericStartingWithLetter(username)) return "Username must start with a letter and contain only letters and numbers.";
        if (!ValidationUtil.isValidEmail(email)) return "Invalid email format.";
        if (!ValidationUtil.isValidPhoneNumber(phone)) return "Phone number must be 10 digits and start with 98 (e.g., 98XXXXXXXX).";
        if (!ValidationUtil.isValidPassword(password)) return "Password must be at least 8 characters long, with 1 uppercase letter, 1 number, and 1 symbol.";
        if (birthdate != null && !ValidationUtil.isAgeAtLeast16(birthdate)) return "You must be at least 16 years old to register.";

        // Image validation can be done here or rely on ImageUtil's internal checks if preferred
        try {
            Part image = req.getPart("imagePath");
            if (image != null && image.getSize() > 0) {
                if (!ValidationUtil.isValidImageExtension(image)) { // Assuming you have this in ValidationUtil
                    return "Invalid image format. Only jpg, jpeg, png, and gif are allowed.";
                }
            }
        } catch (IOException | ServletException e) {
            // This exception might occur if the request is not multipart or exceeds limits
            // It's often better handled in the main doPost catch block.
            System.err.println("Error getting image part during validation: " + e.getMessage());
            // return "Error processing image file. Please ensure the file is valid and within size limits.";
        }

        // Check for existing username or email (optional here, can also be handled by DB constraints and service layer)
        // if (registerService.isUsernameTaken(username)) return "Username is already taken.";
        // if (registerService.isEmailTaken(email)) return "Email is already registered.";

        return null; // All validations passed
    }

    private UserModel extractUserModel(HttpServletRequest req, String imageRelativePath) throws Exception {
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String username = req.getParameter("username");
        String birthDateStr = req.getParameter("birth_date");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");
        // String imageUrlFromForm = req.getParameter("image_url"); // This field is not typically on a registration form
        int roleId = Integer.parseInt(req.getParameter("role_id"));
        LocalDate birthDate = null;

        if (birthDateStr != null && !birthDateStr.isEmpty()) {
            birthDate = LocalDate.parse(birthDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        }

        String encryptedPassword = PasswordUtil.encrypt(password, email); // Assuming encrypt uses password and salt (like email)

        // The imageRelativePath is the one from uploadImage (e.g., "images/user/uuid.jpg")
        // If no image was uploaded, imageRelativePath will be null.
        UserModel userModel = new UserModel(
                firstName,
                lastName,
                username,
                birthDate,
                email,
                phone,
                encryptedPassword,
                imageRelativePath, // Use the path from upload or null
                roleId
        );
        return userModel;
    }

    // The controller's internal uploadImage method is no longer needed as ImageUtil.uploadImage is used directly.
    // private boolean uploadImage(HttpServletRequest req) throws IOException, ServletException { ... REMOVE ... }


    private void handleSuccess(HttpServletRequest req, HttpServletResponse resp, String message, String redirectPage)
            throws ServletException, IOException {
        // For success that redirects, it's better to put message in session
        // and then redirect, so it's available after the redirect.
        // If forwarding (like to login.jsp), request attribute is fine.
        req.getSession().setAttribute("successMessage", message); // Use session for redirect
        resp.sendRedirect(req.getContextPath() + redirectPage); // Assuming redirectPage is context-relative
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
            throws ServletException, IOException {
        // Preserve form data for re-display
        req.setAttribute("errorMessage", message); // Changed from "error" to "errorMessage" for consistency
        req.setAttribute("firstName", req.getParameter("first_name"));
        req.setAttribute("lastName", req.getParameter("last_name"));
        req.setAttribute("username", req.getParameter("username"));
        req.setAttribute("birthDate", req.getParameter("birth_date"));
        req.setAttribute("email", req.getParameter("email"));
        req.setAttribute("phone", req.getParameter("phone"));
        // Do NOT set password back to the form for security reasons.
        // req.setAttribute("password", req.getParameter("password"));
        req.setAttribute("role_id_selected", req.getParameter("role_id")); // Preserve selected role ID

        // Reload roles for the dropdown in case of error
        try {
            List<RoleModel> roles = registerService.getRoles();
            req.setAttribute("roles", roles);
        } catch (Exception e) {
            e.printStackTrace();
            // If roles can't be loaded, the form might be broken. Add to error.
            req.setAttribute("errorMessage", message + " (Additionally, roles could not be reloaded.)");
        }

        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }
}