package service;

import bean.NotificationBean;
import bean.TokenBean;
import bean.UserBean;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/notifications")
public class NotificationService {
    @EJB
    NotificationBean notificationBean;
    @EJB
    UserBean userBean;
    @EJB
    TokenBean tokenBean;

    @GET
    @Path("")
    @Produces("application/json")
    public Response findAllNotifications(@HeaderParam("token") String token, @QueryParam("projectName") String projectName, @QueryParam("isRead") Boolean isRead){
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        return Response.status(200).entity(notificationBean.findNotifications(projectName, token,isRead)).build();

    }
    @PUT
    @Path("/read/{notificationId}")
    @Produces("application/json")
    public Response markAsRead(@HeaderParam("token") String token, @PathParam("notificationId") int notificationId){
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        return Response.status(200).entity(notificationBean.markAsRead(notificationId)).build();
    }
    @PUT
    @Path("/seen")
    @Produces("application/json")
    public Response markAsSeen(@HeaderParam("token") String token){
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        if(notificationBean.markAllAsSeen(token)){
            return Response.status(200).entity("seen").build();
        }else {
            return Response.status(404).entity("not seen").build();
        }

    }
}
