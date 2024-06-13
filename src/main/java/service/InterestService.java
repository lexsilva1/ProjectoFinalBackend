package service;
import bean.InterestBean;
import bean.UserBean;
import dto.InterestDto;
import dto.SkillDto;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllInterests() {
        return Response.status(200).entity(interestBean.findAllInterests()).build();
    }
    @POST
    @Path("")
    @Produces("application/json")
    public Response createInterest(@HeaderParam("token") String token, InterestDto interestDto) {
        userBean.setLastActivity(token);
        if(interestBean.findInterestByName(interestDto.getName()) != null) {
            if(interestDto.getProjectId() == 0) {
                InterestDto interest = interestBean.toInterestDto(interestBean.findInterestByName(interestDto.getName()));
                if(interestBean.addInterestToUser(token, interestDto.getName())) {
                    return Response.status(200).entity(interest).build();
                } else {
                    return Response.status(404).entity("interest not added").build();
                }
            } else {
                InterestDto interest = interestBean.toInterestDto(interestBean.findInterestByName(interestDto.getName()));
                if(interestBean.addInterestToProject(token, interestDto.getProjectId(), interestDto.getName())) {
                    return Response.status(200).entity(interest).build();
                } else {
                    return Response.status(404).entity("interest not added").build();
                }
            }
        }

        if(interestDto.getProjectId() == 0) {
            interestBean.createInterest(interestDto);
            InterestDto interest = interestBean.toInterestDto(interestBean.findInterestByName(interestDto.getName()));
           if( interestBean.addInterestToUser(token, interestDto.getName())) {
               return Response.status(200).entity(interest).build();
              } else {
                  return Response.status(404).entity("interest not added").build();
              }


        } else {
            interestBean.createInterest(interestDto);
            InterestDto interest = interestBean.toInterestDto(interestBean.findInterestByName(interestDto.getName()));
            if(interestBean.addInterestToProject(token, interestDto.getProjectId(), interestDto.getName())) {
                return Response.status(200).entity(interest).build();
            } else {
                return Response.status(404).entity("keyword not added").build();
            }

        }


    }
    @DELETE
    @Path("/removeInterest")
    @Produces("application/json")
    public Response deleteInterest(@HeaderParam("token") String token, InterestDto interestDto) {
        if(userBean.findUserByToken(token) == null) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        if(interestDto.getProjectId() == 0) {
            if(interestBean.removeInterestFromUser(token, interestDto.getName())) {
                return Response.status(200).entity("interest removed from your list").build();
            } else {
                return Response.status(404).entity("interest not removed").build();
            }
        } else {
            if(interestBean.removeInterestFromProject(token, interestDto.getProjectId(), interestDto.getName())) {
                return Response.status(200).entity("interest removed from project").build();
            } else {
                return Response.status(404).entity("interest not removed").build();
            }
        }
    }
    @GET
    @Path("/types")
    @Produces("application/json")
    public Response findAllInterestTypes() {
        return Response.status(200).entity(interestBean.findAllInterestTypes()).build();
    }

}
