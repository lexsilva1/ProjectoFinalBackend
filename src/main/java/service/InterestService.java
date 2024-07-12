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
    private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(InterestService.class);
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllInterests(@Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to find all interests", ipAddress);
        return Response.status(200).entity(interestBean.findAllInterests()).build();
    }
    @POST
    @Path("")
    @Produces("application/json")
    public Response createInterest(@HeaderParam("token") String token, InterestDto interestDto, @Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to create an interest", ipAddress);
        userBean.setLastActivity(token);
        if(interestBean.findInterestByName(interestDto.getName()) != null) {
            logger.error("User with IP address {} and token {} failed to create an interest named {}", ipAddress, token, interestDto.getName());
            if(interestDto.getProjectName() == null || interestDto.getProjectName().isEmpty()) {
                InterestDto interest = interestBean.toInterestDto(interestBean.findInterestByName(interestDto.getName()));
                if(interestBean.addInterestToUser(token, interestDto.getName())) {
                    logger.info("User with IP address {} and token {} created the {} interest", ipAddress, token, interestDto.getName());
                    return Response.status(200).entity(interest).build();
                } else {
                    logger.error("User with IP address {} and token {} failed to create the {} interest", ipAddress, token, interestDto.getName());
                    return Response.status(404).entity("interest not added").build();
                }
            } else {
                logger.info("User with IP address {} and token {} is tryng to create the {} interest for a project", ipAddress, token, interestDto.getName());
                InterestDto interest = interestBean.toInterestDto(interestBean.findInterestByName(interestDto.getName()));
                if(interestBean.addInterestToProject(token, interestDto.getProjectName(), interestDto.getName())) {
                    logger.info("User with IP address {} and token {} created the {} interest for the {} project", ipAddress, token, interestDto.getName(), interestDto.getProjectName());
                    return Response.status(200).entity(interest).build();
                } else {
                    logger.error("User with IP address {} and token {} failed to create the {} interest for the {} project", ipAddress, token, interestDto.getName(), interestDto.getProjectName());
                    return Response.status(404).entity("interest not added").build();
                }
            }
        }

        if(interestDto.getProjectName() == null || interestDto.getProjectName().isEmpty()) {
            interestBean.createInterest(interestDto);
            InterestDto interest = interestBean.toInterestDto(interestBean.findInterestByName(interestDto.getName()));
           if( interestBean.addInterestToUser(token, interestDto.getName())) {
                logger.info("User with IP address {} and token {} created the {} interest", ipAddress, token, interestDto.getName());
               return Response.status(200).entity(interest).build();
              } else {
                logger.error("User with IP address {} and token {} failed to create the {} interest", ipAddress, token, interestDto.getName());
                  return Response.status(404).entity("interest not added").build();
              }


        } else {
            interestBean.createInterest(interestDto);
            InterestDto interest = interestBean.toInterestDto(interestBean.findInterestByName(interestDto.getName()));
            if(interestBean.addInterestToProject(token, interestDto.getProjectName(), interestDto.getName())) {
                logger.info("User with IP address {} and token {} created the {} interest for the {} project", ipAddress, token, interestDto.getName(), interestDto.getProjectName());
                return Response.status(200).entity(interest).build();
            } else {
                logger.error("User with IP address {} and token {} failed to create the {} interest for the {} project", ipAddress, token, interestDto.getName(), interestDto.getProjectName());
                return Response.status(404).entity("keyword not added").build();
            }

        }


    }
    @DELETE
    @Path("/removeInterest")
    @Produces("application/json")
    public Response deleteInterest(@HeaderParam("token") String token, InterestDto interestDto, @Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to remove an interest", ipAddress);
        if(userBean.findUserByToken(token) == null) {
            logger.error("User with IP address {} and token {} is not allowed to remove an interest", ipAddress, token);
            return Response.status(403).entity("not allowed").build();
        }

        userBean.setLastActivity(token);
        if(interestDto.getProjectName() == null || interestDto.getProjectName().isEmpty()) {
            if(interestBean.removeInterestFromUser(token, interestDto.getName())) {
                logger.info("User with IP address {} and token {} removed the {} interest", ipAddress, token, interestDto.getName());
                return Response.status(200).entity("interest removed from your list").build();
            } else {
                logger.error("User with IP address {} and token {} failed to remove the {} interest", ipAddress, token, interestDto.getName());
                return Response.status(404).entity("interest not removed").build();
            }
        } else {
            if(interestBean.removeInterestFromProject(token, interestDto.getProjectName(), interestDto.getName())) {
                logger.info("User with IP address {} and token {} removed the {} interest from the {} project", ipAddress, token, interestDto.getName(), interestDto.getProjectName());
                return Response.status(200).entity("interest removed from project").build();
            } else {
                logger.error("User with IP address {} and token {} failed to remove the {} interest from the {} project", ipAddress, token, interestDto.getName(), interestDto.getProjectName());
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
    @POST
    @Path("/createKeyword")
    @Produces("application/json")
    public Response createKeyword(@HeaderParam("token") String token, InterestDto interestDto, @Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to create a keyword", ipAddress);
        if(userBean.findUserByToken(token) == null) {
            logger.error("User with IP address {} and token {} is not allowed to create a keyword", ipAddress, token);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        if(interestBean.findInterestByName(interestDto.getName()) != null) {
            logger.error("User with IP address {} and token {} failed to create a keyword", ipAddress, token);
            return Response.status(404).entity("keyword already exists").build();
        }
        boolean added = interestBean.createInterest(interestDto);
        if(!added) {
            logger.error("User with IP address {} and token {} failed to create a keyword", ipAddress, token);
            return Response.status(405).entity("keyword not added").build();
        }else {
            logger.info("User with IP address {} and token {} created a keyword", ipAddress, token);
            return Response.status(201).entity(interestBean.toInterestDto(interestBean.findInterestByName(interestDto.getName()))).build();
        }

    }
}
