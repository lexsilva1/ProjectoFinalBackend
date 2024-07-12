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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Path("/labs")
public class LabService {
    @Context
    private HttpServletRequest request;
    @EJB
    LabBean labBean;
    @EJB
    UserBean userBean;
    private static final Logger logger = LogManager.getLogger(LabService.class);

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllLabs(@HeaderParam("token") String token, @Context HttpServletRequest request){
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to find all labs", ipAddress);
        if(token == null) {
            logger.error("User with IP address {} is not allowed to find all labs", ipAddress);
            return Response.status(403).entity("not allowed").build();
        }else{
            logger.info("User with IP address {} found all labs", ipAddress);
            UserEntity user = userBean.findUserByToken(token);

            if(user == null ){
                logger.error("User with IP address {} is not allowed to find all labs", ipAddress);
                return Response.status(404).entity("not found").build();
            }
        }
        logger.info("User with IP address {} found all labs", ipAddress);
        return Response .status(200).entity(labBean.findAllLabs()).build();
    }
}
