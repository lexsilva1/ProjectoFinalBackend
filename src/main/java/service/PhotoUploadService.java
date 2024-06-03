package service;

import jakarta.ejb.Stateless;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Stateless
public class PhotoUploadService {
    /**
     * Uploads a photo for a user.
     *

     * @return A response indicating the result of the operation.
     */
    /*@POST
    @Path("/photo")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadPhoto(HeaderParam("token") String token, MultipartFormDataInput input, @Context HttpServletRequest request) {
        Response response;

        try {
            // Upload the photo
            input.getFormDataMap().forEach((key, value) -> {
                System.out.println("Key: " + key + ", Value: " + value);
            });
            response = Response.status(200).entity("Photo uploaded successfully").build();
        } catch (Exception e) {
            response = Response.status(400).entity("Error uploading photo").build();
        }

        return response;
    }*/
}
