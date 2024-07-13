package service;

import bean.NotificationBean;
import bean.TokenBean;
import bean.UserBean;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * The service class for the notifications.
 */
@Path("/notifications")
public class NotificationService {
    @Context
    private HttpServletRequest request;
    @EJB
    NotificationBean notificationBean;
    @EJB
    UserBean userBean;
    @EJB
    TokenBean tokenBean;
    private static final Logger logger = LogManager.getLogger(NotificationService.class);
    /**
     * The method to find all notifications.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param isRead The status of the notification.
     * @param request The HTTP request.
     * @return The response.
     */
    @GET
    @Path("")
    @Produces("application/json")
    public Response findAllNotifications(@HeaderParam("token") String token, @QueryParam("projectName") String projectName, @QueryParam("isRead") Boolean isRead, @Context HttpServletRequest request){
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to find notifications", ipAddress);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to find notifications", ipAddress, token);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        logger.info("User with IP address {} and token {} found notifications", ipAddress, token);
        return Response.status(200).entity(notificationBean.findNotifications(projectName, token,isRead)).build();

    }
    /**
     * The method to mark a notification as read.
     * @param token The token of the user.
     * @param notificationId The id of the notification.
     * @param request The HTTP request.
     * @return The response.
     */
    @PUT
    @Path("/read/{notificationId}")
    @Produces("application/json")
    public Response markAsRead(@HeaderParam("token") String token, @PathParam("notificationId") int notificationId, @Context HttpServletRequest request){
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to mark a notification as read", ipAddress);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to mark a notification as read", ipAddress, token);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        return Response.status(200).entity(notificationBean.markAsRead(notificationId)).build();
    }
    /**
     * The method to mark all notifications as seen.
     * @param token The token of the user.
     * @param request The HTTP request.
     * @return The response.
     */
    @PUT
    @Path("/seen")
    @Produces("application/json")
    public Response markAsSeen(@HeaderParam("token") String token, @Context HttpServletRequest request){
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to mark all notifications as seen", ipAddress);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to mark all notifications as seen", ipAddress, token);
            return Response.status(403).entity("not allowed").build();
        }
        if(notificationBean.markAllAsSeen(token)){
            logger.info("User with IP address {} and token {} marked all notifications as seen", ipAddress, token);
            return Response.status(200).entity("seen").build();
        }else {
            logger.error("User with IP address {} and token {} failed to mark all notifications as seen", ipAddress, token);
            return Response.status(404).entity("not seen").build();
        }

    }
}
