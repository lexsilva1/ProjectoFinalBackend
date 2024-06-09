package service;

import bean.LabBean;
import bean.UserBean;
import dao.UserDao;
import entities.UserEntity;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/labs")
public class LabService {
    @Context
    private HttpServletRequest request;
    @EJB
    LabBean labBean;
    @EJB
    UserBean userBean;


    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllLabs(@HeaderParam("token") String token){
        if(token == null) {
            return Response.status(403).entity("not allowed").build();
        }else{
            UserEntity user = userBean.findUserByToken(token);
            UserEntity user2 = userBean.findUserByAuxToken(token);
            if(user == null && user2 == null){
                return Response.status(404).entity("not found").build();
            }
        }

        return Response .status(200).entity(labBean.findAllLabs()).build();
    }
}
