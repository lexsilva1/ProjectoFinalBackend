package service;

import bean.ProjectBean;
import bean.ResourceBean;
import bean.TokenBean;
import bean.UserBean;
import dto.ResourceDto;
import entities.ResourceEntity;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * The service class for the resources.
 */
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
    private static final Logger logger = LogManager.getLogger(ResourceService.class);

    /**
     * The method to find all resources.
     * @param token
     * @param resourceName
     * @param resourceType
     * @param resourceIdentifier
     * @param supplier
     * @param request
     * @return
     */
    @GET
    @Path("")
    @Produces("application/json")
    public Response findResources(@HeaderParam("token") String token, @QueryParam("resourceName") String resourceName, @QueryParam("resourceType") String resourceType, @QueryParam("resourceIdentifier") String resourceIdentifier, @QueryParam("supplier") String supplier,@Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to find resources", ipAddress);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to find resources", ipAddress, token);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        logger.info("User with IP address {} and token {} found resources", ipAddress, token);
        return Response.status(200).entity(resourceBean.findAllResources(resourceName,resourceIdentifier,supplier,resourceType)).build();
    }
    /**
     * The method to create a resource.
     * @param token The token of the user.
     * @param resourceDto The resource to create.
     * @param request The HTTP request.
     * @return The response.
     */
    @POST
    @Path("")
    @Produces("application/json")
    public Response createResource(@HeaderParam("token") String token, ResourceDto resourceDto,@Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to create a resource", ipAddress);
        if (userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to create a resource", ipAddress, token);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        if (resourceBean.findResourceByNameAndSupplier(resourceDto.getName(),resourceDto.getSupplier()) != null) {
            logger.error("User with IP address {} and token {} failed to create a resource", ipAddress, token);
            return Response.status(404).entity("resource already exists").build();
        }
        resourceBean.createResource(resourceDto);
        ResourceEntity resourceEntity = resourceBean.findResourceByNameAndSupplier(resourceDto.getName(),resourceDto.getSupplier());
        ResourceDto resourceDtoToSend = resourceBean.convertCreatedResourceToDto(resourceEntity);
        resourceDtoToSend.setQuantity(resourceDto.getQuantity());
        logger.info("User with IP address {} and token {} created a resource", ipAddress, token);
        return Response.status(200).entity(resourceDtoToSend).build();
    }
    /**
     * The method to get resource statistics.
     * @param token The token of the user.
     * @param request The HTTP request.
     * @return The response.
     */
    @GET
    @Path("/statistics")
    @Produces("application/json")
    public Response getResourceStatistics(@HeaderParam("token") String token,@Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to get resource statistics", ipAddress);
        if (userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to get resource statistics", ipAddress, token);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        logger.info("User with IP address {} and token {} got resource statistics", ipAddress, token);
        return Response.status(200).entity(resourceBean.getResourceStatistics()).build();
    }
    /**
     * The method to update a resource.
     * @param token The token of the user.
     * @param resourceId The id of the resource.
     * @param resourceDto The resource to update.
     * @param request The HTTP request.
     * @return The response.
     */
    @PUT
    @Path("/{resourceId}")
    @Consumes("application/json")
    public Response updateResource(@HeaderParam("token") String token, @PathParam("resourceId") int resourceId, ResourceDto resourceDto,@Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to update a resource", ipAddress);
        if (userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to update a resource", ipAddress, token);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        if (resourceBean.findResourceById(resourceId) == null) {
            logger.error("User with IP address {} and token {} failed to update a resource", ipAddress, token);
            return Response.status(404).entity("resource not found").build();
        }
        if(resourceBean.updateResource(resourceId, resourceDto)){
            logger.info("User with IP address {} and token {} updated a resource {}", ipAddress, token, resourceId);
            return Response.status(200).entity("resource updated").build();
        } else {
            logger.error("User with IP address {} and token {} failed to update a resource {}", ipAddress, token,resourceId);
            return Response.status(404).entity("resource not updated").build();
        }

    }
}
