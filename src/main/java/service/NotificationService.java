package service;

import bean.NotificationBean;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/notifications")
public class NotificationService {
    @EJB
    NotificationBean notificationBean;

    @GET
    @Path("")
    @Produces("application/json")
    public Response findAllNotifications(@HeaderParam("token") String token, @QueryParam("projectName") String projectName, @QueryParam("User") int User, @QueryParam("isRead") Boolean isRead){
        if(token == null) {
            return Response.status(403).entity("not allowed").build();
        }
        return Response.status(200).entity(notificationBean.findNotifications(projectName, User,isRead)).build();

    }
}
