package bean;

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

    static final String RELATIVE_PATH = "ProjectoFinalImages";
    private static final int AVATAR_WIDTH = 100;
    private static final int AVATAR_HEIGHT = 100;

    public void ensureDirectoryExists() {
        Path path = Paths.get(System.getProperty("user.dir"), "bin", RELATIVE_PATH);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (Exception e) {
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
        return mimeType.equals("image/jpeg") || mimeType.equals("image/png");
    }
    public void ensureDirectoryExists(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public String uploadPhoto(Path filePath) {
        try {
            Path resizedPath = Paths.get(System.getProperty("user.dir"), "bin", RELATIVE_PATH, filePath.getFileName().toString());
            ensureDirectoryExists(resizedPath.getParent());
            resizeImage(filePath, resizedPath);
            return resizedPath.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}