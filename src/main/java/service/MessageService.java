package service;

import bean.MessageBean;
import bean.UserBean;
import dto.MessageDto;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;

@Path("/messages")
public class MessageService {
    @Context
    private HttpServletRequest request;
    @EJB
    private MessageBean messageBean;
    @EJB
    private UserBean userBean;

    @POST
    @Path("")
    @Produces("application/json")
    public Response createMessage(@HeaderParam ("token") String token, MessageDto message) {
        System.out.println("create message");
        if(userBean.findUserByToken(token) == null) {
            return Response.status(404).entity("user not found").build();
        }
        messageBean.createMessage(message);
        return Response.status(200).build();
    }
}
