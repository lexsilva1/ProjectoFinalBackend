package bean;

import entities.ProjectEntity;
import entities.UserEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Stateless
public class PhotoUploadBean {
    @EJB
    private UserBean userBean;
    @EJB
    private ProjectBean projectBean;

    static final String RELATIVE_PATH = "ProjectoFinalImages";
    private static final int AVATAR_WIDTH = 300;
    private static final int AVATAR_HEIGHT = 300;
    private static final Logger logger = LogManager.getLogger(PhotoUploadBean.class);

    public PhotoUploadBean() {
    }

    private void ensureDirectoryExists(Path path) {
        logger.info("Ensuring directory exists: {}", path);
        if (!Files.exists(path)) {
            logger.info("Creating directory: {}", path);
            try {
                logger.info("Creating directory: {}", path);
                Files.createDirectories(path);
            } catch (IOException e) {
                logger.error("Error creating directory: {}", path);
                e.printStackTrace();
            }
        }
    }

    public void resizeImage(Path originalImagePath, Path resizedImagePath) throws IOException {
        logger.info("Resizing image: {} to {}", originalImagePath, resizedImagePath);
        BufferedImage originalImage = ImageIO.read(originalImagePath.toFile());
        BufferedImage resizedImage = Thumbnails.of(originalImage)
                .size(AVATAR_WIDTH, AVATAR_HEIGHT)
                .asBufferedImage();
        ImageIO.write(resizedImage, "jpg", resizedImagePath.toFile());
    }

    public boolean isImageValid(Path imagePath) throws IOException {
        logger.info("Checking if image is valid: {}", imagePath);
        String mimeType = Files.probeContentType(imagePath);
        logger.info("Mime type: {}", mimeType);
        return "image/jpeg".equals(mimeType) || "image/png".equals(mimeType);
    }

    public String uploadPhoto(Path filePath) {
        logger.info("Uploading photo: {}", filePath);
        try {
            logger.info("Uploading photo: {}", filePath);
            Path resizedPath = Paths.get(System.getProperty("user.dir"), RELATIVE_PATH, filePath.getFileName().toString());
            ensureDirectoryExists(resizedPath.getParent());
            logger.info("Ensuring directory exists: {}", resizedPath.getParent());
            if (!isImageValid(filePath)) {
                logger.error("Invalid image format");
                throw new IOException("Invalid image format");
            }
            logger.info("Resizing image: {} to {}", filePath, resizedPath);
            resizeImage(filePath, resizedPath);
            logger.info("Photo uploaded successfully: {}", resizedPath);
            return resizedPath.toString();
        } catch (Exception e) {
            logger.error("Error uploading photo: {}", filePath);
            e.printStackTrace();
            return null;
        }
    }
    public UserEntity confirmUserByToken(String token) {
        logger.info("Confirming user by token: {}", token);
        UserEntity user = userBean.findUserByToken(token);
        if (user != null) {
            logger.info("User confirmed by token: {}", token);
         return user;
        }else {
            logger.error("User not found by token: {}", token);
        return userBean.findUserByToken(token);
        }
    }
    public ProjectEntity findProjectByName(String name) {
        logger.info("Project {} found",name); return projectBean.findProjectByName(name);
    }
}
