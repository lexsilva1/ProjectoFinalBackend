package bean;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * The image servlet.

 */
@WebServlet("/ProjectoFinalImages/*")
public class ImageServlet extends HttpServlet {

    private static final String SERVER_IMAGE_DIRECTORY = "C:/Users/xanos/OneDrive/Desktop/AoR/PAJ/wildfly/wildfly-30.0.1.Final/bin/ProjectoFinalImages"; // Change this to your server directory path

    /**
     * Process the HTTP GET request.
     * @param req an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     *
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     *
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String imagePath = SERVER_IMAGE_DIRECTORY + pathInfo;

        // Basic validation for imagePath to prevent path traversal attacks
        if (pathInfo == null || pathInfo.contains("..")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid image path");
            return;
        }

        File file = new File(imagePath);

        if (!file.exists() || !file.isFile()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
            return;
        }

        String mimeType = Files.probeContentType(file.toPath());
        if (mimeType == null || !mimeType.startsWith("image")) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Unsupported image type");
            return;
        }

        resp.setContentType(mimeType);
        resp.setHeader("Content-Disposition", "inline");
        resp.setContentLength((int) file.length());

        try (FileInputStream in = new FileInputStream(file);
             OutputStream out = resp.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }
}
