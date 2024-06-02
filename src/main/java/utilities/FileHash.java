package utilities;

import java.nio.file.*;
import java.security.*;
import javax.xml.bind.DatatypeConverter;

public class FileHash {
    public static void main(String[] args) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(Files.readAllBytes(Paths.get("C:/Users/xanos/OneDrive/Documents/GitHub/ProjectoFinal/projectofinalfrontend/src/multimedia/Images/avatarProject.png")));
        byte[] digest = md.digest();
        String hashValue = DatatypeConverter.printHexBinary(digest);
        System.out.println(hashValue);
    }
}
