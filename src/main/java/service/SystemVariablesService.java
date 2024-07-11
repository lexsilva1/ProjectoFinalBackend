package service;

import bean.ProjectBean;
import bean.SystemVariablesBean;
import bean.UserBean;
import dto.SystemVariablesDto;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final Logger logger = LogManager.getLogger(SystemVariablesService.class);
    @POST
    @Path("/timeout")
    @Produces("application/json")
    public Response setSessionTimeout(@HeaderParam("token") String token, int timeout,@Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to set session timeout to {}", ipAddress, timeout);
        if(userBean.findUserByToken(token).getRole().getValue() >= 1) {
            logger.error("User with IP address {} is not allowed to set session timeout", ipAddress);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        if(timeout < 1 ) {
            return Response.status(405).entity("timeout must be greater than 0").build();
        }
        systemVariablesBean.setSessionTimeout(timeout);
        logger.info("User with IP address {} set session timeout to {}", ipAddress, timeout);
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
    @POST
    @Path("/")
    @Produces("application/json")
    public Response setSystemVariables(@HeaderParam("token") String token, SystemVariablesDto systemVariables, @Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to set system variables", ipAddress);
        if (userBean.findUserByToken(token).getRole().getValue() > 0) {
            logger.error("User with IP address {} and token {} is not allowed to set system variables", ipAddress,token);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        if(systemVariables.getMaxUsers() > userBean.getAllUsers().size()) {
            logger.error("User with IP address {} and token {} failed to set max users to {}", ipAddress,token,systemVariables.getMaxUsers());
            return Response.status(405).entity("max users exceeded").build();
        }
        if(systemVariables.getTimeout() < 1) {
            logger.error("User with IP address {} and token {} failed to set timeout to {}", ipAddress,token,systemVariables.getTimeout());
            return Response.status(405).entity("timeout must be greater than 0").build();
        }
        systemVariablesBean.setSessionTimeout(systemVariables.getTimeout());
        systemVariablesBean.setMaxUsers(systemVariables.getMaxUsers());
        logger.info("User with IP address {} and token {} set system variables to {} users and {} timeout", ipAddress,token,systemVariables.getMaxUsers(),systemVariables.getTimeout());
        return Response.status(200).entity("Systems variables updated.").build();
    }
    @GET
    @Path("")
    @Produces("application/json")
    public Response getSystemVariables(@HeaderParam("token") String token, @Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to get system variables", ipAddress);
        if (userBean.findUserByToken(token).getRole().getValue() >= 1) {
            logger.error("User with IP address {} and token {} is not allowed to get system variables", ipAddress,token);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        logger.info("User with IP address {} and token {} got system variables", ipAddress,token);
        return Response.status(200).entity(systemVariablesBean.getSystemVariables()).build();
    }
}

