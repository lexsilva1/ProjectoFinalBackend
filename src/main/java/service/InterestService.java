package service;
import bean.InterestBean;
import bean.UserBean;
import dto.InterestDto;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
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
    public Response createInterest(InterestDto interestDto) {
        if(interestBean.findInterestByName(interestDto.getName()) != null) {
            return Response.status(404).entity("interest already exists").build();
        }
        interestBean.createInterest(interestDto);
        return Response.status(200).entity("interest created").build();
    }
}
