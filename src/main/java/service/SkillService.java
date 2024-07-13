package service;

import bean.ProjectBean;
import bean.SkillBean;
import bean.TokenBean;
import bean.UserBean;
import dto.SkillDto;
import entities.SkillEntity;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
/**
 * The service class for the skills.
 */
@Path("/skills")
public class SkillService {
    @Context
    private HttpServletRequest request;
    @Inject
    private SkillBean skillBean;
    @Inject
    private UserBean userBean;
    @Inject
    private ProjectBean projectBean;
    @Inject
    private TokenBean tokenBean;
    private static final Logger logger = LogManager.getLogger(SkillService.class);
    /**
     * The method to find all skills.
     * @return The response.
     */
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllSkills() {
        return Response.status(200).entity(skillBean.findAllSkills()).build();
    }
    /**
     * The method to create a skill.
     * @param token The token of the user.
     * @param skillDto The skill to create.
     * @param request The HTTP request.
     * @return The response.
     */
    @POST
    @Path("")
    @Produces("application/json")
    public Response createSkill(@HeaderParam("token") String token, SkillDto skillDto, @Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to create a skill", ipAddress);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to create a skill", ipAddress, token);
            return Response.status(404).entity("user not found").build();
        }

        userBean.setLastActivity(token);
        if(skillBean.findSkillByName(skillDto.getName()) != null) {
            if (skillDto.getProjectName() == null || skillDto.getProjectName().isEmpty()) {
                boolean added = skillBean.addSkillToUser(token, skillDto.getName());
                if (added) {
                    logger.info("User with IP address {} and token {} added the {} skill to the user", ipAddress, token, skillDto.getName());
                    return Response.status(200).entity("skill added to your profile").build();
                } else {
                    logger.error("User with IP address {} and token {} failed add the {} skill to the user", ipAddress, token, skillDto.getName());
                    return Response.status(404).entity("skill not added").build();
                }
            } else {
                boolean added = skillBean.addSkilltoProject(token, skillDto.getProjectName(), skillDto.getName());
                if (!added) {
                    logger.error("User with IP address {} and token {} failed to add the {} skill to the project{}", ipAddress, token, skillDto.getName(), skillDto.getProjectName());
                    return Response.status(404).entity("skill not added").build();
                } else {
                    logger.info("User with IP address {} and token {} added the {} skill to the project {}", ipAddress, token, skillDto.getName(), skillDto.getProjectName());
                    return Response.status(200).entity("skill added to project").build();
                }
            }
        }
        if(skillDto.getProjectName() == null || skillDto.getProjectName().isEmpty()){
             skillBean.createSkill(skillDto);
             SkillDto skill = skillBean.toSkillDtos(skillBean.findSkillByName(skillDto.getName()));
            boolean added = skillBean.addSkillToUser(token, skillDto.getName());
            if(added) {
                logger.info("User with IP address {} and token {} created and added the {} skill to the user", ipAddress, token, skillDto.getName());
                return Response.status(201).entity(skill).build();
            } else {
                logger.error("User with IP address {} and token {} failed to create and add the {} skill to the user", ipAddress, token, skillDto.getName());
                return Response.status(404).entity("skill not added").build();
            }
        } else {
            skillBean.createSkill(skillDto);
            SkillDto skill = skillBean.toSkillDtos(skillBean.findSkillByName(skillDto.getName()));
            boolean added = skillBean.addSkilltoProject(token, skillDto.getProjectName(), skillDto.getName());
            if(!added) {
                logger.error("User with IP address {} and token {} failed to create and add the {} skill to the project {}", ipAddress, token, skillDto.getName(), skillDto.getProjectName());
                return Response.status(404).entity("skill not added").build();
            } else {
                logger.info("User with IP address {} and token {} created and added the {} skill to the project {}", ipAddress, token, skillDto.getName(), skillDto.getProjectName());
                return Response.status(201).entity(skill).build();
            }
        }
    }
    /**
     * The method to remove a skill.
     * @param token The token of the user.
     * @param skillDto The skill to remove.
     * @param request The HTTP request.
     * @return The response.
     */
    @DELETE
    @Path("/removeSkill")
    @Produces("application/json")
    public Response deleteSkill(@HeaderParam("token") String token, SkillDto skillDto, @Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to remove a skill", ipAddress);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to remove a skill", ipAddress, token);
            return Response.status(403).entity("not allowed").build();
        }

        userBean.setLastActivity(token);
        SkillEntity skill = skillBean.findSkillByName(skillDto.getName());
        if(skill == null) {
            logger.error("User with IP address {} and token {} failed to remove a skill", ipAddress, token);
            return Response.status(404).entity("skill not found").build();
        }
        if(skillDto.getProjectName() == null || skillDto.getProjectName().isEmpty()) {
            logger.info("User with IP address {} and token {} removed the {} skill from the user", ipAddress, token, skillDto.getName());
            userBean.removeSkillFromUser(token, skill);
            return Response.status(200).entity("skill removed from your profile").build();
        } else {
            if(skillBean.removeSkillFromProject(token, skillDto.getProjectName(), skill.getName())){
                logger.info("User with IP address {} and token {} removed the {} skill from the project {}", ipAddress, token, skillDto.getName(), skillDto.getProjectName());
                return Response.status(200).entity("skill removed from project").build();
            } else {
                logger.error("User with IP address {} and token {} failed to remove the {} skill from the project {}", ipAddress, token, skillDto.getName(), skillDto.getProjectName());
                return Response.status(404).entity("skill not removed").build();
            }
        }

    }
    /**
     * The method to find all skill types.
     * @param request The HTTP request.
     * @return The response.
     */
    @GET
    @Path("/types")
    @Produces("application/json")
    public Response findAllSkillTypes(@Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to get all skill types", ipAddress);
        return Response.status(200).entity(skillBean.findAllSkilltypes()).build();
    }
    /**
     * The method to find all skills by name.
     * @param names The names of the skills.
     * @return The response.
     */
    @GET
    @Path("/tests")
    @Produces("application/json")
    public Response test(List<String> names) {
        return Response.status(200).entity(skillBean.findSkillsByName(names)).build();

    }/**
     * The method to create a skill for a project.
     * @param token The token of the user.
     * @param skillDto The skill to create.
     * @param request The HTTP request.
     * @return The response.
     */
    @POST
    @Path("/createSkill")
    @Produces("application/json")
    public Response createSkillForProject(@HeaderParam("token") String token, SkillDto skillDto, @Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to create a skill for a project", ipAddress);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to create a skill for a project", ipAddress, token);
            return Response.status(404).entity("user not found").build();
        }
        userBean.setLastActivity(token);
        if(skillBean.findSkillByName(skillDto.getName()) != null) {
            logger.error("User with IP address {} and token {} failed to create a skill for project {} because it already exists", ipAddress, token, skillDto.getProjectName());
            return Response.status(404).entity("skill already exists").build();
        }
        boolean added = skillBean.createSkill(skillDto);
        SkillDto skill = skillBean.toSkillDtos(skillBean.findSkillByName(skillDto.getName()));
        if(!added) {
            logger.error("User with IP address {} and token {} failed to create a skill for project {}", ipAddress, token, skillDto.getProjectName());
            return Response.status(404).entity("skill not added").build();
        } else {
            logger.info("User with IP address {} and token {} created the {} skill for project {}", ipAddress, token, skillDto.getName(), skillDto.getProjectName());
            return Response.status(201).entity(skill).build();
        }
    }

}
