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
    @Produces(MediaType.APPLICATION_JSON)
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
            if (skillDto.getProjetcId() == 0) {
                boolean added = skillBean.addSkillToUser(token, skillDto.getName());
                if (added) {
                    return Response.status(200).entity("skill added to your profile").build();
                } else {
                    return Response.status(404).entity("skill not added").build();
                }
            } else {
                boolean added = skillBean.addSkilltoProject(token, skillDto.getProjetcId(), skillDto.getName());
                if (!added) {
                    return Response.status(404).entity("skill not added").build();
                } else {
                    return Response.status(200).entity("skill added to project").build();
                }
            }
        }
        if(skillDto.getProjetcId() == 0) {
             skillBean.createSkill(skillDto);
            boolean added = skillBean.addSkillToUser(token, skillDto.getName());
            if(added) {
                return Response.status(201).entity("skill added to your profile").build();
            } else {
                return Response.status(404).entity("skill not added").build();
            }
        } else {
            skillBean.createSkill(skillDto);
            boolean added = skillBean.addSkilltoProject(token, skillDto.getProjetcId(), skillDto.getName());
            if(!added) {
                return Response.status(404).entity("skill not added").build();
            } else {
                return Response.status(201).entity("skill added to project").build();
            }
        }
    }
    @DELETE
    @Path("/removeSkill")
    @Produces("application/json")
    public Response deleteSkill(@HeaderParam("token") String token, SkillDto skillDto) {
        if(userBean.findUserByToken(token) == null) {
            return Response.status(403).entity("not allowed").build();
        }
        System.out.println(skillDto.getName()+" "+skillDto.getProjetcId()+" "+skillDto.getSkillType());
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
    @GET
    @Path("/types")
    @Produces("application/json")
    public Response findAllSkillTypes() {
        return Response.status(200).entity(skillBean.findAllSkilltypes()).build();
    }


}
