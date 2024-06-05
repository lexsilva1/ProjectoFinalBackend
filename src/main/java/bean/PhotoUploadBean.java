package bean;

import entities.ProjectEntity;
import entities.UserEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import net.coobird.thumbnailator.Thumbnails;
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

    public void ensureDirectoryExists() {
        Path path = Paths.get(System.getProperty("user.dir"), RELATIVE_PATH);
        ensureDirectoryExists(path);
    }

    private void ensureDirectoryExists(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void resizeImage(Path originalImagePath, Path resizedImagePath) throws IOException {
        BufferedImage originalImage = ImageIO.read(originalImagePath.toFile());
        BufferedImage resizedImage = Thumbnails.of(originalImage)
                .size(AVATAR_WIDTH, AVATAR_HEIGHT)
                .asBufferedImage();
        ImageIO.write(resizedImage, "jpg", resizedImagePath.toFile());
    }

    public boolean isImageValid(Path imagePath) throws IOException {
        String mimeType = Files.probeContentType(imagePath);
        return "image/jpeg".equals(mimeType) || "image/png".equals(mimeType);
    }

    public String uploadPhoto(Path filePath) {
        try {
            Path resizedPath = Paths.get(System.getProperty("user.dir"), RELATIVE_PATH, filePath.getFileName().toString());
            ensureDirectoryExists(resizedPath.getParent());

            if (!isImageValid(filePath)) {
                throw new IOException("Invalid image format");
            }

            resizeImage(filePath, resizedPath);
            return resizedPath.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public UserEntity confirmUserByToken(String token) {
        UserEntity user = userBean.findUserByAuxToken(token);
        if (user != null) {
         return user;
        }else {
        return userBean.findUserByToken(token);
        }
    }
    public ProjectEntity findProjectByName(String name) {
        return projectBean.findProjectByName(name);
    }
}
