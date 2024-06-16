package service;

import bean.ProjectBean;
import bean.ResourceBean;
import bean.TokenBean;
import bean.UserBean;
import dto.ResourceDto;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;

@Path("/resources")
public class ResourceService {
    @Context
    private HttpServletRequest request;
    @Inject
    private ResourceBean resourceBean;
    @Inject
    private ProjectBean projectBean;
    @Inject
    private UserBean userBean;
    @Inject
    private TokenBean tokenBean;


    @GET
    @Path("")
    @Produces("application/json")
    public Response findResources(@HeaderParam("token") String token, @QueryParam("resourceName") String resourceName, @QueryParam("resourceType") String resourceType, @QueryParam("resourceIdentifier") String resourceIdentifier, @QueryParam("supplier") String supplier) {
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        return Response.status(200).entity(resourceBean.findAllResources(resourceName,resourceIdentifier,supplier,resourceType)).build();
    }
    @POST
    @Path("")
    @Consumes("application/json")
    public Response createResource(@HeaderParam("token") String token, ResourceDto resourceDto) {
        if (userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        if (resourceBean.findResourceByNameAndSupplier(resourceDto.getName(),resourceDto.getSupplier()) != null) {
            return Response.status(404).entity("resource already exists").build();
        }
        resourceBean.createResource(resourceDto);
        return Response.status(200).entity("resource created").build();
    }
}
