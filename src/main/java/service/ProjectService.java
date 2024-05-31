package service;

import bean.ProjectBean;
import bean.UserBean;
import dto.ProjectDto;
import entities.ProjectEntity;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;

@Path("/projects")
public class ProjectService {
    @Context
    private HttpServletRequest request;
    @EJB
    ProjectBean projectBean;
    @EJB
    UserBean userBean;



    @GET
    @Path("")
    @Produces("application/json")
    public Response findAllProjects(@HeaderParam("token") String token,@QueryParam("projectName") String projectName,@QueryParam("projectLab") String projectLab,@QueryParam("projectSkill") String projectSkill,@QueryParam("projectInterest") String projectInterest,@QueryParam("projectStatus") int projectStatus,@QueryParam("projectUser") int projectUser){
        if(userBean.findUserByToken(token) == null) {
            return Response.status(404).entity("user not found").build();
        }
        return Response.status(200).entity(projectBean.findProjects(projectName,projectLab,projectSkill,projectInterest,projectStatus,projectUser)).build();
    }
    @GET
    @Path("/projectUsers")
    @Produces("application/json")
    public Response findAllProjectUsers(@HeaderParam("token") String token,@QueryParam("projectName") String projectName){
        if(userBean.findUserByToken(token) == null) {
            return Response.status(403).entity("not allowed").build();
        }
        ProjectEntity project = projectBean.findProjectByName(projectName);
        if(project == null) {
            return Response.status(404).entity("project not found").build();
        }else{
            return Response.status(200).entity(projectBean.findProjectUsers(project)).build();
        }

    }
    @GET
    @Path("/projectSkills")
    @Produces("application/json")
    public Response findAllProjectSkills(@HeaderParam("token") String token,@QueryParam("projectName") String projectName){
        if(userBean.findUserByToken(token) == null) {
            return Response.status(403).entity("not allowed").build();
        }
        ProjectEntity project = projectBean.findProjectByName(projectName);
        if(project == null) {
            return Response.status(404).entity("project not found").build();
        }else{
            return Response.status(200).entity(projectBean.findProjectSkills(project)).build();
        }

    }
    @POST
    @Path("/")
    @Produces("application/json")
    public Response createProject(@HeaderParam("token") String token,ProjectDto projectDto){
        if(userBean.findUserByToken(token) == null) {
            return Response.status(403).entity("not allowed").build();
        }
        projectBean.createProject(projectDto,token);
        return Response.status(200).entity("project created").build();
    }
}
