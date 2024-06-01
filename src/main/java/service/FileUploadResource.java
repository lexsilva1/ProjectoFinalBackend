package service;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import utilities.GoogleCloudStorageService;

import java.io.IOException;
import java.io.InputStream;

@Path("/upload")
public class FileUploadResource {

    private GoogleCloudStorageService storageService;

    public FileUploadResource() throws IOException {
        storageService = new GoogleCloudStorageService();
    }

    @POST
    @Path("/file")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail,
            @FormDataParam("file") FormDataBodyPart bodyPart,
            @HeaderParam ("token") String token){

        String fileName = fileDetail.getFileName();
        String contentType = bodyPart.getMediaType().toString();

        // Save the file
        try {
            String fileUrl = storageService.uploadFile(uploadedInputStream, fileName, contentType);
            return Response.status(Response.Status.OK).entity(fileUrl).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("File upload failed").build();
        }
    }
}