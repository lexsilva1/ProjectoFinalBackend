package service;
import bean.InterestBean;
import bean.UserBean;
import dto.InterestDto;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;

@Path("/interests")
public class InterestService {
    @Context
    private HttpServletRequest request;
    @EJB
    InterestBean interestBean;
    @EJB
    UserBean userBean;

    @GET
    @Path("")
    public Response findAllInterests() {
        return Response.status(200).entity(interestBean.findAllInterests()).build();
    }
    @POST
    @Path("")
    @Produces("application/json")
    public Response createInterest(@HeaderParam("token") String token, InterestDto interestDto) {
        if(interestBean.findInterestByName(interestDto.getName()) != null) {
            return Response.status(404).entity("interest already exists").build();
        }
        userBean.setLastActivity(token);
        interestBean.createInterest(interestDto);
        return Response.status(200).entity("interest created").build();
    }
}
