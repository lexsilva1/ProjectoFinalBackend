package service;

import bean.LabBean;
import jakarta.ejb.EJB;
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


    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllLabs(@HeaderParam("token") String token){
        if(token == null) {
            return Response.status(403).entity("not allowed").build();
        }
        return Response .status(200).entity(labBean.findAllLabs()).build();
    }
}
