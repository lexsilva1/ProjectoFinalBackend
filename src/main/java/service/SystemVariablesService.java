package service;

import bean.ProjectBean;
import bean.SystemVariablesBean;
import bean.UserBean;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;

@Path("/systemVariables")
public class SystemVariablesService {
    @Context
    private HttpServletRequest request;
    @EJB
    private SystemVariablesBean systemVariablesBean;
    @EJB
    private UserBean userBean;
    @EJB
    private ProjectBean projectBean;

    @POST
    @Path("/timeout")
    @Produces("application/json")
    public Response setSessionTimeout(@HeaderParam("token") String token, int timeout) {
        if(userBean.findUserByToken(token).getRole().getValue() >= 1) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);

        systemVariablesBean.setSessionTimeout(timeout);
        return Response.status(200).entity("timeout set").build();
    }
    @POST
    @Path("/maxUsers")
    @Produces("application/json")
    public Response setMaxUsers(@HeaderParam("token") String token, int maxUsers) {
        if (userBean.findUserByToken(token).getRole().getValue() >= 1) {
            return Response.status(403).entity("not allowed").build();
        }
        if (systemVariablesBean.setMaxUsers(maxUsers)) {
            projectBean.checkMaxMembers(systemVariablesBean.getMaxUsers());
            return Response.status(200).entity("max users set").build();
        }
        return Response.status(405).entity("max users exceeded").build();
    }
    @GET
    @Path("")
    @Produces("application/json")
    public Response getSystemVariables(@HeaderParam("token") String token) {
        if (userBean.findUserByToken(token).getRole().getValue() >= 1) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        return Response.status(200).entity(systemVariablesBean.getSystemVariables()).build();
    }
}

