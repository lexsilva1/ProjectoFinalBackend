package service;

import bean.ProjectBean;
import bean.SkillBean;
import bean.UserBean;
import dto.SkillDto;
import entities.SkillEntity;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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

    @GET
    @Path("")
    public Response findAllSkills() {
        return Response.status(200).entity(skillBean.findAllSkills()).build();
    }
    @POST
    @Path("")
    @Produces("application/json")
    public Response createSkill(@HeaderParam("token") String token, SkillDto skillDto) {
        if(userBean.findUserByToken(token) == null) {
            return Response.status(404).entity("user not found").build();
        }
        userBean.setLastActivity(token);
        if(skillBean.findSkillByName(skillDto.getName()) != null) {
            return Response.status(404).entity("skill already exists").build();
        }
        if(skillDto.getProjetcId() == 0) {
            skillBean.createSkill(skillDto);
            skillBean.addSkillToUser(token, skillDto.getName());
        } else {
            skillBean.createSkill(skillDto);
            skillBean.addSkilltoProject(token, skillDto.getProjetcId(), skillDto.getName());
        }
        return Response.status(200).entity("skill created").build();
    }
    @DELETE
    @Path("/removeSkill")
    @Produces("application/json")
    public Response deleteSkill(@HeaderParam("token") String token, SkillDto skillDto) {
        if(userBean.findUserByToken(token) == null) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        SkillEntity skill = skillBean.findSkillByName(skillDto.getName());
        if(skill == null) {
            return Response.status(404).entity("skill not found").build();
        }
        if(skillDto.getProjetcId() == 0) {

            userBean.removeSkillFromUser(token, skill);
            return Response.status(200).entity("skill removed from your profile").build();
        } else {
            projectBean.removeSkillFromProject(token, skillDto.getProjetcId(), skill);
            return Response.status(200).entity("skill removed from project").build();
        }

    }


}
