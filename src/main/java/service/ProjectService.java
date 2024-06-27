package service;

import bean.*;
import dto.CreateProjectDto;
import dto.NotificationDto;
import dto.ProjectDto;
import dto.TaskDto;
import entities.ProjectEntity;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
    @EJB
    TokenBean tokenBean;
    @EJB
    NotificationBean notificationBean;



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
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
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
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
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
    public Response createProject(@HeaderParam("token") String token, CreateProjectDto projectDto){
        System.out.println("create project 1 - entrei no endpoint");
        if(userBean.findUserByToken(token) == null ||  !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        boolean created = projectBean.createProject(projectDto,token);
        if(!created) {
            return Response.status(404).entity("project not created").build();
        }
        return Response.status(201).entity("created").build();
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
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        if(taskBean.createTask(token,projectName,taskDto)) {
            TaskDto taskDto1 = taskBean.getTaskById(taskDto.getId());
            return Response.status(201).entity(taskDto1).build();
        }else {
            return Response.status(400).entity("something went wrong").build();
        }

    }
    @GET
    @Path("/{projectName}")
    @Produces("application/json")
    public Response findProject(@HeaderParam("token") String token, @PathParam("projectName") String projectName){
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        ProjectEntity project = projectBean.findProjectByName(projectName);
        if(project == null) {
            return Response.status(404).entity("project not found").build();
        }else{
            ProjectDto projectDto = projectBean.convertToDto(project);
            return Response.status(200).entity(projectDto).build();
        }
    }
    @POST
    @Path("/{projectName}/apply")
    @Produces("application/json")
    public Response applyProject(@HeaderParam("token") String token, @PathParam("projectName") String projectName){
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        projectBean.applyToProject(token,projectName);
        return Response.status(200).entity("applied").build();
    }
    @POST
    @Path("/{projectName}/invite")
    @Produces("application/json")
    public Response inviteUser(@HeaderParam("token") String token, @PathParam("projectName") String projectName, @QueryParam("userId") int userId){
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        projectBean.inviteToProject(token,projectName,userId);
        return Response.status(200).entity("invited").build();
    }

    @POST
    @Path("/{projectName}/accept")
    @Produces("application/json")
    public Response acceptUser(@HeaderParam("token") String token, @PathParam("projectName") String projectName, @QueryParam("userId") int userId,@QueryParam("operationType") String operationType, @QueryParam("notificationId")int notificationId){
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        NotificationDto notification = notificationBean.convertToDto(notificationBean.findNotificationById(notificationId));
        if(notification == null) {
            return Response.status(404).entity("notification not found").build();
        }

        String operationTypeString = operationType;
        ProjectBean.OperationType operationTypeEnum = ProjectBean.OperationType.valueOf(operationTypeString);
        if(projectBean.acceptRequest(token,projectName,userId,operationTypeEnum)) {
            NotificationDto updatedNotification = notificationBean.updateNotificationMessage(notificationId,"ACCEPT");
            return Response.status(200).entity(updatedNotification).build();
        }else{
            return Response.status(405).entity("not accepted").build();
        }
    }
    @DELETE
    @Path("/{projectName}/reject")
    @Produces("application/json")
    public Response rejectUser(@HeaderParam("token") String token, @PathParam("projectName") String projectName, @QueryParam("userId") int userId,@QueryParam("operationType") String operationType, @QueryParam("notificationId") int notificationId){
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        String operationTypeString = operationType; // rename the string variable
        ProjectBean.OperationType operationTypeEnum = ProjectBean.OperationType.valueOf(operationTypeString);
        if(projectBean.rejectRequest(token,projectName,userId,operationTypeEnum)){
            NotificationDto updatedNotification = notificationBean.updateNotificationMessage(notificationId,"REJECT");
            return Response.status(200).entity(updatedNotification).build();
        }else{
            return Response.status(405).entity("not rejected").build();
        }

    }
    @PUT
    @Path("/{projectName}/promote")
    @Produces("application/json")
    public Response promoteUser(@HeaderParam("token") String token, @PathParam("projectName") String projectName, @QueryParam("userId") int userId){
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        projectBean.promoteUserToProjectManager(token,projectName,userId);
        return Response.status(200).entity("promoted").build();
    }
    @PUT
    @Path("/{projectName}/demote")
    @Produces("application/json")
    public Response demoteUser(@HeaderParam("token") String token, @PathParam("projectName") String projectName, @QueryParam("userId") int userId){
    if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        projectBean.demoteUserFromProjectManager(token,projectName,userId);
        return Response.status(200).entity("demoted").build();
    }
    @POST
    @Path("/{projectName}/leave")
    @Produces("application/json")
    public Response leaveProject(@HeaderParam("token") String token, @PathParam("projectName") String projectName){
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        projectBean.leaveProject(token,projectName);
        return Response.status(200).entity("left").build();
    }
    @PUT
    @Path("/")
    @Produces("application/json")
    public Response updateProject(@HeaderParam("token") String token, CreateProjectDto projectDto){
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectBean.createProject(projectDto,token);
        return Response.status(200).entity("project updated").build();
    }
    @GET
    @Path("statistics")
    @Produces("application/json")
    public Response getStatistics(@HeaderParam("token") String token){
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        return Response.status(200).entity(projectBean.getProjectStatistics()).build();
    }


    @GET
    @Path("/{projectName}/tasks")
    @Produces("application/json")
    public Response findProjectTasks(@HeaderParam("token") String token, @PathParam("projectName") String projectName){
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        ProjectEntity project = projectBean.findProjectByName(projectName);
        if(project == null) {
            return Response.status(404).entity("project not found").build();
        }else{
            return Response.status(200).entity(projectBean.findProjectTasks(projectName)).build();
        }
    }
    @PUT
    @Path("/{projectName}/tasks")
    @Produces("application/json")
    public Response updateTask(@HeaderParam("token") String token, @PathParam("projectName") String projectName, TaskDto taskDto){
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        if(taskBean.updateTask(token,projectName,taskDto)) {
            TaskDto taskDto1 = taskBean.getTaskById(taskDto.getId());
            return Response.status(200).entity(taskDto1).build();
        }else {
            return Response.status(400).entity("something went wrong").build();
        }
    }
}
