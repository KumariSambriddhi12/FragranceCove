package com.FragranceCove.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths; // For robust path joining and getting file name
import java.util.UUID;    // For generating unique file names

import jakarta.servlet.http.Part;

public class ImageUtil {

    /**
     * Extracts the original submitted file name from the Part.
     *
     * @param part The Part object.
     * @return The original file name, or a default if not found.
     */
    public String getOriginalImageNameFromPart(Part part) {
        if (part == null) {
            return "default_image.png"; // Or throw an IllegalArgumentException
        }
        // A more robust way to get the submitted file name from the Part
        String submittedFileName = part.getSubmittedFileName();
        if (submittedFileName != null && !submittedFileName.trim().isEmpty()) {
            // Sanitize the filename (e.g., get only the file name part, not the full client path)
            return Paths.get(submittedFileName).getFileName().toString();
        }

        // Fallback to your existing header parsing if part.getSubmittedFileName() is not reliable
        // for some reason (though it generally should be with modern servlet containers)
        String contentDisp = part.getHeader("content-disposition");
        if (contentDisp != null) {
            String[] items = contentDisp.split(";");
            for (String s : items) {
                if (s.trim().startsWith("filename")) {
                    String name = s.substring(s.indexOf("=") + 2, s.length() - 1);
                    // Ensure it's just the filename, not a path
                    return Paths.get(name).getFileName().toString();
                }
            }
        }
        return "default_image.png"; // Default if no name found
    }

    /**
     * Uploads the image file from the given {@link Part} object to a specified
     * directory on the server. This version will generate a UNIQUE name for the
     * file to prevent overwrites and use the provided rootPath for dynamic deployment.
     *
     * @param part       the {@link Part} object representing the uploaded image file.
     * @param rootPath   The absolute path to the web application's root directory (e.g., servletContext.getRealPath("/")).
     * @param saveFolder The subdirectory within the webapp's images folder (e.g., "user", "product_images").
     *                   The final path will be something like rootPath/images/[saveFolder]/unique_name.jpg
     * @return The relative path of the saved image (e.g., "images/user/unique_name.jpg") if successful, null otherwise.
     */
    public String uploadImage(Part part, String rootPath, String saveFolder) {
        if (part == null || part.getSize() == 0) {
            System.out.println("No image part provided or image is empty.");
            return null; // No image to upload
        }

        // Get the original file name to extract its extension
        String originalFileName = getOriginalImageNameFromPart(part);
        String fileExtension = "";
        int i = originalFileName.lastIndexOf('.');
        if (i > 0 && i < originalFileName.length() - 1) {
            fileExtension = originalFileName.substring(i); // e.g., ".jpg", ".png"
        } else {
            // Handle cases with no extension or invalid names if necessary,
            // or assign a default extension like ".dat" or reject the file.
            // For simplicity, we'll proceed, but this could be a validation point.
             System.err.println("Warning: Uploaded file has no or an unusual extension: " + originalFileName);
        }

        // Generate a unique file name using UUID
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        // Construct the save path dynamically using rootPath
        // The target structure is typically: webapp_root/images/[saveFolder]/filename.ext
        // Example: if rootPath is "/var/lib/tomcat/webapps/FragranceCove", saveFolder is "user"
        // Then path would be "/var/lib/tomcat/webapps/FragranceCove/images/user/"
        File targetSaveDir = Paths.get(rootPath, "images", saveFolder).toFile();

        // Ensure the directory exists
        if (!targetSaveDir.exists()) {
            if (!targetSaveDir.mkdirs()) {
                System.err.println("Failed to create the save directory: " + targetSaveDir.getAbsolutePath());
                return null; // Failed to create the directory
            }
        }

        try {
            // Create the full file path for the new unique file
            File uniqueFile = new File(targetSaveDir, uniqueFileName);

            // Write the file to the server
            part.write(uniqueFile.getAbsolutePath());

            // Return the relative path for storage in the database
            // e.g., "images/user/123e4567-e89b-12d3-a456-426614174000.jpg"
            String relativePath = Paths.get("images", saveFolder, uniqueFileName).toString()
                                      .replace("\\", "/"); // Ensure forward slashes for web
            System.out.println("Image uploaded to: " + uniqueFile.getAbsolutePath());
            System.out.println("Relative path for DB: " + relativePath);
            return relativePath;

        } catch (IOException e) {
            e.printStackTrace(); // Log the exception
            return null; // Upload failed
        }
    }


    /**
     * Deletes an image file from the server.
     *
     * @param imageRelativePath The relative path to the image file (e.g., "images/product_images/image.jpg")
     *                          as stored in the database. This path is relative to the webapp root.
     * @param rootPath          The absolute path to the web application's root directory (e.g., servletContext.getRealPath("/")).
     * @return true if the image was deleted successfully or if it didn't exist, false if deletion failed.
     */
    public boolean deleteImage(String imageRelativePath, String rootPath) {
        if (imageRelativePath == null || imageRelativePath.trim().isEmpty() ||
            rootPath == null || rootPath.trim().isEmpty()) {
            System.err.println("Image relative path or root path is null or empty for deletion.");
            return false;
        }

        File imageFileToDelete = Paths.get(rootPath, imageRelativePath).toFile();

        if (imageFileToDelete.exists()) {
            if (imageFileToDelete.isFile()) {
                if (imageFileToDelete.delete()) {
                    System.out.println("Successfully deleted image: " + imageFileToDelete.getAbsolutePath());
                    return true;
                } else {
                    System.err.println("Failed to delete image: " + imageFileToDelete.getAbsolutePath() + ". Check file permissions.");
                    return false;
                }
            } else {
                System.err.println("Path points to a directory, not a file, cannot delete: " + imageFileToDelete.getAbsolutePath());
                return false;
            }
        } else {
            System.out.println("Image not found, nothing to delete: " + imageFileToDelete.getAbsolutePath());
            return true; // File is not there, so deletion goal is met.
        }
    }

    // The getSavePath method is no longer needed if paths are constructed dynamically within uploadImage.
    // If you still want it for some other purpose, it should also be made dynamic.
    /*
    public String getSavePath(String rootPath, String saveFolder) {
        // This would construct something like: rootPath + File.separator + "images" + File.separator + saveFolder
        // Example:
        File saveDir = new File(new File(rootPath, "images"), saveFolder);
        return saveDir.getAbsolutePath();
    }
    */
}