package service;

import bean.SkillBean;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Response;

@Path("/skills")
public class SkillService {
    @Context
    private HttpServletRequest request;
    @Inject
    private SkillBean skillBean;

    @GET
    @Path("")
    public Response findAllSkills() {
        return Response.status(200).entity(skillBean.findAllSkills()).build();
    }
}
