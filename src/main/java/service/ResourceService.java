package service;

import bean.ProjectBean;
import bean.ResourceBean;
import bean.UserBean;
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


    @GET
    @Path("")
    @Produces("application/json")
    public Response findResources(@HeaderParam("token") String token, @QueryParam("resourceName") String resourceName, @QueryParam("resourceType") String resourceType, @QueryParam("resourceIdentifier") String resourceIdentifier, @QueryParam("supplier") String supplier) {
        if(userBean.findUserByToken(token) == null) {
            return Response.status(403).entity("not allowed").build();
        }
        return Response.status(200).entity(resourceBean.findAllResources(resourceName,resourceIdentifier,supplier,resourceType)).build();
    }
}
