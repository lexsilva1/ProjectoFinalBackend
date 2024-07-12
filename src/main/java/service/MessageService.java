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
