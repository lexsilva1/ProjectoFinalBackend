package service;

import bean.PhotoUploadBean;
import bean.TokenBean;
import bean.UserBean;
import entities.ProjectEntity;
import entities.UserEntity;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
/**
 * The service class for the photo uploads.
 */
@Path("/upload")
public class PhotoUploadService {
    @Context
    private HttpServletRequest request;
    @EJB
    UserBean userBean;
    @EJB
    PhotoUploadBean photoBean;
    @EJB
    TokenBean tokenBean;
    private static final Logger logger = LogManager.getLogger(PhotoUploadService.class);

    /**
     *  The method to upload a photo.
     * @param token
     * @param input
     * @param request
     * @return
     */
    @POST
    @Path("/userPhoto")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPhoto(@HeaderParam("token") String token, MultipartFormDataInput input, @Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to upload a photo", ipAddress);
        UserEntity user = photoBean.confirmUserByToken(token);

        if (user == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to upload a photo", ipAddress, token);
            return Response.status(403).entity("not allowed").build();
        }

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("input");

        for (InputPart inputPart : inputParts) {
            try {
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(inputStream);

                String fileName = user.getId() + ".jpg";
                String uploadDirectory = System.getProperty("user.dir") + File.separator + "bin" + File.separator + "ProjectoFinalImages" + File.separator + user.getId();
                java.nio.file.Path userDirectoryPath = java.nio.file.Paths.get(uploadDirectory);
                if (!Files.exists(userDirectoryPath)) {
                    Files.createDirectories(userDirectoryPath);
                }

                java.nio.file.Path filePath = java.nio.file.Paths.get(uploadDirectory, fileName);
                Files.write(filePath, bytes);

                // Use the photoBean to handle the photo upload
                String photoPath = photoBean.uploadPhoto(filePath);

                if (photoPath == null) {
                    logger.error("User with IP address {} and token {} failed to upload a photo", ipAddress, token);
                    return Response.status(500).entity("Error uploading photo").build();
                }
                String uniqueTime = "?t=" + System.currentTimeMillis();

                // Construct the URL to return to the client
                String photoUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/ProjectoFinalImages/" + fileName+uniqueTime;
                logger.info("User with IP address {} and token {} uploaded a photo", ipAddress, token);
                return Response.status(200).entity(photoUrl).build();

            } catch (Exception e) {
                e.printStackTrace();
                logger.error("User with IP address {} and token {} failed to upload a photo", ipAddress, token);
                return Response.status(500).entity("Error uploading photo").build();
            }
        }
        logger.error("User with IP address {} and token {} failed to upload a photo", ipAddress, token);
        return Response.status(400).entity("No file found").build();
    }
/**
     * The method to upload a photo to a project.
     * @param token The token of the user.
     * @param name The name of the project.
     * @param input The input.
     * @param request The HTTP request.
     * @return The response.
     */
    @POST
    @Path("/projectPhoto")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadProjectPhoto(@HeaderParam("token") String token,@HeaderParam("name") String name, MultipartFormDataInput input, @Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to upload a photo to project", ipAddress);
        if(!tokenBean.isTokenValid(token)){
            logger.error("User with IP address {} and token {} is not allowed to upload a photo to project", ipAddress, token);
            return Response.status(403).entity("not allowed").build();
        }
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("input");

        for (InputPart inputPart : inputParts) {
            try {
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(inputStream);

                String fileName = name + ".jpg";
                String uploadDirectory = System.getProperty("user.dir") + File.separator + "bin" + File.separator + "ProjectoFinalImages" + File.separator + name;
                java.nio.file.Path userDirectoryPath = java.nio.file.Paths.get(uploadDirectory);
                if (!Files.exists(userDirectoryPath)) {
                    Files.createDirectories(userDirectoryPath);
                }

                java.nio.file.Path filePath = java.nio.file.Paths.get(uploadDirectory, fileName);
                Files.write(filePath, bytes);

                // Use the photoBean to handle the photo upload
                String photoPath = photoBean.uploadPhoto(filePath);

                if (photoPath == null) {
                    logger.error("User with IP address {} and token {} failed to upload a photo to project", ipAddress, token);
                    return Response.status(500).entity("Error uploading photo").build();
                }
                String uniqueTime = "?t=" + System.currentTimeMillis();
                // Construct the URL to return to the client
                String photoUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/ProjectoFinalImages/" +  fileName+uniqueTime;


                logger.info("User with IP address {} and token {} uploaded a photo to project", ipAddress, token);
                return Response.status(200).entity(photoUrl).build();

            } catch (Exception e) {
                e.printStackTrace();
                logger.error("User with IP address {} and token {} failed to upload a photo to project", ipAddress, token);
                return Response.status(500).entity("Error uploading photo").build();
            }
        }
        logger.error("User with IP address {} and token {} failed to upload a photo to project", ipAddress, token);
        return Response.status(400).entity("No file found").build();
    }
}

