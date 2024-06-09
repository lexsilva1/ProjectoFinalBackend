package service;

import bean.SkillBean;
import bean.UserBean;
import dto.SkillDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Response;

@Path("/skills")
public class SkillService {
    @Context
    private HttpServletRequest request;
    @Inject
    private SkillBean skillBean;
    @Inject
    private UserBean userBean;

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
        skillBean.createSkill(skillDto);
        return Response.status(200).entity("skill created").build();
    }
}
