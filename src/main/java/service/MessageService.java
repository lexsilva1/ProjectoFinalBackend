package service;

import bean.MessageBean;
import bean.TokenBean;
import bean.UserBean;
import dto.MessageDto;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * The service class for the messages.
 */
@Path("/messages")
public class MessageService {
    @Context
    private HttpServletRequest request;
    @EJB
    private MessageBean messageBean;
    @EJB
    private UserBean userBean;
    @EJB
    private TokenBean tokenBean;
    private static final Logger logger = LogManager.getLogger(MessageService.class);

    /**
     * The method to create a message.
     * @param token The token of the user.
     * @param message The message to create.
     * @param request The HTTP request.
     * @return The response.
     */
    @POST
    @Path("")
    @Produces("application/json")
    public Response createMessage(@HeaderParam ("token") String token, MessageDto message, @Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to create a message", ipAddress);
        userBean.setLastActivity(token);
        if(userBean.findUserByToken(token) == null) {
            logger.error("User with IP address {} and token {} is not allowed to create a message", ipAddress, token);
            return Response.status(404).entity("user not found").build();
        }
        messageBean.createMessage(message);
        logger.info("User with IP address {} and token {} created a message", ipAddress, token);
        return Response.status(200).build();
    }
    /**
     * The method to find all messages.
     * @param token The token of the user.
     * @param id The id of the user.
     * @param request The HTTP request.
     * @return The response.
     */
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response findAllMessages(@HeaderParam("token") String token, @PathParam("id") int id, @Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to find all messages", ipAddress);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to find all messages", ipAddress, token);
            return Response.status(404).entity("user not found").build();
        }
        userBean.setLastActivity(token);
        logger.info("User with IP address {} and token {} found all messages", ipAddress, token);
        return Response.status(200).entity(messageBean.findUserMessages(token,id)).build();
    }
/**
     * The method to find last messages.
     * @param token The token of the user.
     * @param request The HTTP request.
     * @return The response.
     */
    @GET
    @Path("")
    @Produces("application/json")
    public Response findLastMessages(@HeaderParam("token") String token, @Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to find last messages", ipAddress);
        userBean.setLastActivity(token);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to find last messages", ipAddress, token);
            return Response.status(404).entity("user not found").build();
        }
        logger.info("User with IP address {} and token {} found last messages", ipAddress, token);
        return Response.status(200).entity(messageBean.findLastMessages(token)).build();
    }

}
