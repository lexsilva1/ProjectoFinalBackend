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
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response findAllMessages(@HeaderParam("token") String token, @PathParam("id") int id) {
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(404).entity("user not found").build();
        }
        return Response.status(200).entity(messageBean.findUserMessages(token,id)).build();
    }

    @GET
    @Path("")
    @Produces("application/json")
    public Response findLastMessages(@HeaderParam("token") String token) {
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(404).entity("user not found").build();
        }
        return Response.status(200).entity(messageBean.findLastMessages(token)).build();
    }
}
