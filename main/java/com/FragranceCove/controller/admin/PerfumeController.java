package com.FragranceCove.controller.admin;

import com.FragranceCove.model.BrandModel;
import com.FragranceCove.model.CategoryModel;
import com.FragranceCove.model.PerfumeModel;
import com.FragranceCove.service.BrandService;
import com.FragranceCove.service.CategoryService;
import com.FragranceCove.service.PerfumeService;
// import com.FragranceCove.util.SessionUtil; // For admin auth checks

import java.io.File; // For file operations
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/admin/perfumes/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,    // 1 MB
    maxFileSize = 1024 * 1024 * 10,     // 10 MB
    maxRequestSize = 1024 * 1024 * 50   // 50 MB
)
public class PerfumeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private PerfumeService perfumeService;
    private BrandService brandService;
    private CategoryService categoryService;

    @Override
    public void init() {
        perfumeService = new PerfumeService();
        brandService = new BrandService();
        categoryService = new CategoryService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // --- BEGIN Admin Authentication/Authorization Check (Example) ---
        // HttpSession session = request.getSession(false);
        // if (session == null || session.getAttribute("adminUser") == null) { // Assuming "adminUser" is set on login
        //     response.sendRedirect(request.getContextPath() + "/admin/login?error=Please login");
        //     return;
        // }
        // --- END Admin Check ---

        String pathInfo = request.getPathInfo();
        String action = pathInfo == null ? "/list" : pathInfo;

        try {
            switch (action) {
                case "/add":
                    showAddPerfumeForm(request, response);
                    break;
                case "/edit":
                    showEditPerfumeForm(request, response);
                    break;
                case "/delete":
                    deletePerfumeAction(request, response);
                    break;
                case "/list":
                default:
                    listPerfumes(request, response);
                    break;
            }
        } catch (NumberFormatException e) {
            System.err.println("Admin Perfume Controller - Invalid ID format: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/perfumes?error=invalid_id");
        }
        // Removed ClassNotFoundException catch here as services handle it and log
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // --- BEGIN Admin Authentication/Authorization Check (Example) ---
        // HttpSession session = request.getSession(false);
        // if (session == null || session.getAttribute("adminUser") == null) {
        //     response.sendRedirect(request.getContextPath() + "/admin/login?error=Please login");
        //     return;
        // }
        // --- END Admin Check ---

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing action path for POST.");
            return;
        }

        try {
            switch (pathInfo) {
                case "/add":
                    addPerfumeAction(request, response);
                    break;
                case "/edit":
                    updatePerfumeAction(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unknown POST action.");
                    break;
            }
        } catch (NumberFormatException e) {
            System.err.println("Admin Perfume Controller POST - Invalid number format: " + e.getMessage());
            // Handle specific redirection based on context if possible, or a generic error
            if ("/add".equals(pathInfo)) {
                 repopulateAddFormOnError(request, response, "Invalid number format for price, stock, or IDs.");
            } else if ("/edit".equals(pathInfo)) {
                 String idParam = request.getParameter("id");
                 int id = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : 0;
                 repopulateEditFormOnError(request, response, id, "Invalid number format for price, stock, or IDs.");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/perfumes?error=invalid_input_format");
            }
        }
    }

    private void listPerfumes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String search = request.getParameter("search");
        List<PerfumeModel> perfumes;
        if (search != null && !search.trim().isEmpty()) {
            perfumes = perfumeService.searchPerfumes(search);
            request.setAttribute("searchKeyword", search); // Use a distinct attribute name
        } else {
            perfumes = perfumeService.getAllPerfumes();
        }
        request.setAttribute("perfumesList", perfumes); // Use a distinct attribute name
        request.getRequestDispatcher("/WEB-INF/views/admin/perfumes.jsp").forward(request, response);
    }

    private void showAddPerfumeForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<BrandModel> brands = brandService.getAllBrands();
        List<CategoryModel> categories = categoryService.getAllCategories();
        request.setAttribute("brandsList", brands);
        request.setAttribute("categoriesList", categories);
        request.getRequestDispatcher("/WEB-INF/views/admin/add-perfume.jsp").forward(request, response);
    }

    private void showEditPerfumeForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NumberFormatException {
        int id = Integer.parseInt(request.getParameter("id"));
        PerfumeModel perfume = perfumeService.getPerfumeById(id);
        if (perfume != null) {
            List<BrandModel> brands = brandService.getAllBrands();
            List<CategoryModel> categories = categoryService.getAllCategories();
            request.setAttribute("perfumeToEdit", perfume); // Use a distinct attribute name
            request.setAttribute("brandsList", brands);
            request.setAttribute("categoriesList", categories);
            request.getRequestDispatcher("/WEB-INF/views/admin/edit-perfume.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/perfumes?error=perfume_not_found");
        }
    }

    private void addPerfumeAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NumberFormatException {
        PerfumeModel perfume = new PerfumeModel();
        perfume.setName(request.getParameter("name"));
        perfume.setBrandId(Integer.parseInt(request.getParameter("brandId")));
        perfume.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
        perfume.setDescription(request.getParameter("description"));
        perfume.setPrice(Double.parseDouble(request.getParameter("price")));
        perfume.setStockQuantity(Integer.parseInt(request.getParameter("stockQuantity")));
        perfume.setDateAdded(new Timestamp(System.currentTimeMillis())); // Set creation date

        Part filePart = request.getPart("image");
        String imageUrl = processImageUpload(filePart, null, request); // Pass null for oldImageUrl on add
        perfume.setImageUrl(imageUrl != null ? imageUrl : "uploads/perfumes/default-perfume.jpg");


        if (perfumeService.createPerfume(perfume)) {
            response.sendRedirect(request.getContextPath() + "/admin/perfumes?message=add_success");
        } else {
            repopulateAddFormOnError(request, response, "Failed to add perfume. Database error or validation issue.");
        }
    }
    
    private void repopulateAddFormOnError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        request.setAttribute("errorMessage", errorMessage);
        // Repopulate form fields with submitted values
        PerfumeModel submittedPerfume = new PerfumeModel();
        submittedPerfume.setName(request.getParameter("name"));
        try { submittedPerfume.setBrandId(Integer.parseInt(request.getParameter("brandId"))); } catch (Exception e) {/* ignore */}
        try { submittedPerfume.setCategoryId(Integer.parseInt(request.getParameter("categoryId"))); } catch (Exception e) {/* ignore */}
        submittedPerfume.setDescription(request.getParameter("description"));
        try { submittedPerfume.setPrice(Double.parseDouble(request.getParameter("price"))); } catch (Exception e) {/* ignore */}
        try { submittedPerfume.setStockQuantity(Integer.parseInt(request.getParameter("stockQuantity"))); } catch (Exception e) {/* ignore */}
        // Note: Image cannot be easily repopulated for file input
        request.setAttribute("submittedPerfumeData", submittedPerfume);
        showAddPerfumeForm(request, response); // Re-use the method that sets up brands/categories
    }


    private void updatePerfumeAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NumberFormatException {
        int id = Integer.parseInt(request.getParameter("id"));
        PerfumeModel perfume = perfumeService.getPerfumeById(id);
        if (perfume == null) {
            response.sendRedirect(request.getContextPath() + "/admin/perfumes?error=perfume_not_found_for_update");
            return;
        }

        String oldImageUrl = perfume.getImageUrl(); // Get old image URL before updating object

        perfume.setName(request.getParameter("name"));
        perfume.setBrandId(Integer.parseInt(request.getParameter("brandId")));
        perfume.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
        perfume.setDescription(request.getParameter("description"));
        perfume.setPrice(Double.parseDouble(request.getParameter("price")));
        perfume.setStockQuantity(Integer.parseInt(request.getParameter("stockQuantity")));

        Part filePart = request.getPart("image");
        String newImageUrl = processImageUpload(filePart, oldImageUrl, request);
        if (newImageUrl != null) { // Only update if a new image was successfully processed
            perfume.setImageUrl(newImageUrl);
        } // Else, imageUrl on perfume object remains the old one

        if (perfumeService.updatePerfume(perfume)) {
            response.sendRedirect(request.getContextPath() + "/admin/perfumes?message=update_success");
        } else {
             repopulateEditFormOnError(request, response, id, "Failed to update perfume. Database error or validation issue.");
        }
    }

    private void repopulateEditFormOnError(HttpServletRequest request, HttpServletResponse response, int perfumeId, String errorMessage)
        throws ServletException, IOException {
        request.setAttribute("errorMessage", errorMessage);
        // Get the perfume data again, or use what was attempted to be saved
        PerfumeModel perfumeToEdit = perfumeService.getPerfumeById(perfumeId); 
        if (perfumeToEdit == null) { // Should not happen if update started with a valid perfume
             response.sendRedirect(request.getContextPath() + "/admin/perfumes?error=perfume_not_found_on_error");
             return;
        }
        // Overwrite with submitted values for repopulation
        perfumeToEdit.setName(request.getParameter("name"));
        try { perfumeToEdit.setBrandId(Integer.parseInt(request.getParameter("brandId"))); } catch (Exception e) {/* ignore */}
        try { perfumeToEdit.setCategoryId(Integer.parseInt(request.getParameter("categoryId"))); } catch (Exception e) {/* ignore */}
        perfumeToEdit.setDescription(request.getParameter("description"));
        try { perfumeToEdit.setPrice(Double.parseDouble(request.getParameter("price"))); } catch (Exception e) {/* ignore */}
        try { perfumeToEdit.setStockQuantity(Integer.parseInt(request.getParameter("stockQuantity"))); } catch (Exception e) {/* ignore */}
        // Image URL remains the original one unless successfully changed by processImageUpload before this error
        
        request.setAttribute("perfumeToEdit", perfumeToEdit);
        showEditPerfumeForm(request, response);
    }


    private void deletePerfumeAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NumberFormatException {
        int id = Integer.parseInt(request.getParameter("id"));
        PerfumeModel perfumeToDelete = perfumeService.getPerfumeById(id); // Get details before deleting for image cleanup

        if (perfumeService.deletePerfume(id)) {
            // If perfume had an image (and it's not the default), try to delete it from filesystem
            if (perfumeToDelete != null && perfumeToDelete.getImageUrl() != null &&
                !perfumeToDelete.getImageUrl().endsWith("default-perfume.jpg")) {
                deleteOldImage(perfumeToDelete.getImageUrl(), request);
            }
            response.sendRedirect(request.getContextPath() + "/admin/perfumes?message=delete_success");
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/perfumes?error=delete_failed");
        }
    }

    private String processImageUpload(Part filePart, String oldImageUrl, HttpServletRequest request) throws IOException {
        String uniqueFileName = null;
        if (filePart != null && filePart.getSize() > 0) {
            String originalFileName = getSubmittedFileName(filePart);
            if (originalFileName != null && !originalFileName.trim().isEmpty()) {
                // Sanitize filename and make it unique
                String sanitizedFileName = originalFileName.replaceAll("[^a-zA-Z0-9._-]", "_");
                uniqueFileName = System.currentTimeMillis() + "_" + sanitizedFileName;

                String uploadDirRealPath = request.getServletContext().getRealPath("/uploads/perfumes");
                File uploadDirFile = new File(uploadDirRealPath);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();
                }
                File imageFile = new File(uploadDirFile, uniqueFileName);
                filePart.write(imageFile.getAbsolutePath());

                // Delete old image if this is an update and old image was not default
                if (oldImageUrl != null && !oldImageUrl.endsWith("default-perfume.jpg") && !oldImageUrl.equals("uploads/perfumes/" + uniqueFileName)) {
                    deleteOldImage(oldImageUrl, request);
                }
                return "uploads/perfumes/" + uniqueFileName; // Return relative path for web access
            }
        }
        return null; // Return null if no new file was uploaded or processed
    }
    
    private void deleteOldImage(String imageUrl, HttpServletRequest request) {
        if (imageUrl == null || imageUrl.trim().isEmpty() || imageUrl.endsWith("default-perfume.jpg")) {
            return;
        }
        String imagePath = request.getServletContext().getRealPath("/") + imageUrl;
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            if (imageFile.delete()) {
                System.out.println("Successfully deleted old image: " + imageUrl);
            } else {
                System.err.println("Failed to delete old image: " + imageUrl);
            }
        }
    }


    private String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1);
            }
        }
        return null;
    }
}