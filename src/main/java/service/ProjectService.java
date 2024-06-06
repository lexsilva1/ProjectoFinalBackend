package service;

import bean.ProjectBean;
import bean.TaskBean;
import bean.UserBean;
import dto.ProjectDto;
import dto.TaskDto;
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
    @EJB
    TaskBean taskBean;



    @GET
    @Path("")
    @Produces("application/json")
    public Response findAllProjects(@HeaderParam("token") String token,@QueryParam("projectName") String projectName,@QueryParam("projectLab") String projectLab,@QueryParam("projectSkill") String projectSkill,@QueryParam("projectInterest") String projectInterest,@QueryParam("projectStatus") int projectStatus,@QueryParam("projectUser") int projectUser){

        return Response.status(200).entity(projectBean.findProjects( projectName,projectLab,projectSkill,projectInterest,projectStatus,projectUser,token)).build();
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
    @GET
    @Path("/allStatus")
    @Produces ("application/json")
    public Response findAllStatus(){
        return Response.status(200).entity(projectBean.findAllStatus()).build();
    }
    @POST
    @Path("/{projectName}/tasks")
    @Produces("application/json")
    public Response createTask(@HeaderParam("token") String token, @PathParam("projectName") String projectName, TaskDto taskDto){
        if(userBean.findUserByToken(token) == null) {
            return Response.status(403).entity("not allowed").build();
        }
        taskBean.createTask(token,projectName,taskDto);
        return Response.status(200).entity("task created").build();
    }
    @GET
    @Path("/{projectName}")
    @Produces("application/json")
    public Response findProject(@HeaderParam("token") String token, @PathParam("projectName") String projectName){
        if(userBean.findUserByToken(token) == null) {
            return Response.status(403).entity("not allowed").build();
        }
        ProjectEntity project = projectBean.findProjectByName(projectName);
        if(project == null) {
            return Response.status(404).entity("project not found").build();
        }else{
            ProjectDto projectDto = projectBean.convertToDto(project);
            return Response.status(200).entity(projectDto).build();
        }
    }
}
