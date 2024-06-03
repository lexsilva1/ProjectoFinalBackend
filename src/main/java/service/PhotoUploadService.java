package service;

import bean.PhotoUploadBean;
import bean.UserBean;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.apache.commons.io.IOUtils;
import entities.UserEntity;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

import java.util.List;
import java.util.Map;


@Path("/upload")
public class PhotoUploadService {
    @Context
    private HttpServletRequest request;
    @EJB
    UserBean userBean;
    @EJB
    PhotoUploadBean photoBean;

    @POST
    @Path("/file")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPhoto(@HeaderParam("token") String token, MultipartFormDataInput input) {

        UserEntity user2 = userBean.findUserByAuxToken(token);

        if (user2 == null) {
            return Response.status(403).entity("not allowed").build();
        }

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("input");

        for (InputPart inputPart : inputParts) {
            try {
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(inputStream);

                String fileName = user2.getId() + ".jpg";
                String uploadDirectory = System.getProperty("user.dir") + File.separator + "bin" + File.separator + "ProjectoFinalImages" + File.separator + user2.getId();
                java.nio.file.Path userDirectoryPath = java.nio.file.Paths.get(uploadDirectory);
                if (!Files.exists(userDirectoryPath)) {
                    Files.createDirectories(userDirectoryPath);
                }

                java.nio.file.Path filePath = java.nio.file.Paths.get(uploadDirectory, fileName);
                Files.write(filePath, bytes);

                // Use the photoBean to handle the photo upload
                String photoPath = photoBean.uploadPhoto(filePath);

                if (photoPath == null) {
                    return Response.status(500).entity("Error uploading photo1").build();
                }

                return Response.status(200).entity(photoPath).build();

            } catch (Exception e) {
                e.printStackTrace();
                return Response.status(500).entity("Error uploading photo2").build();
            }
        }

        return Response.status(400).entity("No file found").build();
    }
}
