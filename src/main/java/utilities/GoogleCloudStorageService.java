package utilities;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GoogleCloudStorageService {

    private static final String BUCKET_NAME = "imagestoragefinal";
    private Storage storage;

    public GoogleCloudStorageService() throws IOException {
        // Authenticate using the service account JSON key
        GoogleCredentials credentials = GoogleCredentials.fromStream(
                Files.newInputStream(Paths.get("C:\\Users\\xanos\\OneDrive\\Documents\\GitHub\\ProjectoFinal\\jsonkey.json"))
        );
        storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }

    // Upload a file
    public String uploadFile(InputStream content, String fileName, String contentType) throws IOException {
        // Create a BlobId with desired object name and bucket
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(contentType) // Set content type for proper display
                .build();

        // Upload the file
        storage.create(blobInfo, content);
        System.out.println("File " + fileName + " uploaded to bucket " + BUCKET_NAME);

        // Construct and return the public URL of the uploaded image
        return "https://storage.googleapis.com/" + BUCKET_NAME + "/" + fileName;
    }

    // Additional methods for listing files, deleting files, etc. can be added here
}
