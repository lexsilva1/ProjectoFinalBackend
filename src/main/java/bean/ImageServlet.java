package bean;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageServlet extends HttpServlet {

    private PhotoUploadBean photoUploadBean = new PhotoUploadBean();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String imageName = req.getParameter("imageName");
        String filePath = System.getProperty("user.dir") + File.separator + photoUploadBean.RELATIVE_PATH + File.separator + imageName;
        File file = new File(filePath);
        resp.setContentType("image/jpeg");
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