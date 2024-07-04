package service;

import bean.*;
import dto.*;
import entities.ProjectEntity;
import entities.TaskEntity;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;

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
    @EJB
    GroupChatBean groupChatBean;
    @EJB
    ProjectLogBean projectLogBean;


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
    @PUT
    @Path("/{projectName}/status")
    @Produces("application/json")
    public Response updateProjectStatus(@HeaderParam("token") String token, @PathParam("projectName") String projectName, @QueryParam("status") String status){
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        if(projectBean.updateProjectStatus(token,projectName,status)) {
            return Response.status(200).entity("status updated").build();
        }else{
            return Response.status(400).entity("something went wrong").build();
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
        if(projectBean.applyToProject(token,projectName)) {
            return Response.status(200).entity("applied").build();
        }else {
            return Response.status(405).entity("you cannot apply to this project").build();
        }
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
        if(projectBean.inviteToProject(token,projectName,userId)) {
            notificationBean.sendNotification(new NotificationDto("INVITE",userId,projectName,false,LocalDateTime.now()));
            return Response.status(200).entity("invited").build();
        }else {
            return Response.status(405).entity("you cannot invite to this project").build();
        }
    }

    @POST
    @Path("/{projectName}/accept")
    @Produces("application/json")
    public Response acceptUser(@HeaderParam("token") String token, @PathParam("projectName") String projectName, @QueryParam("userId") int userId,@QueryParam("operationType") String operationType, @QueryParam("notificationId")int notificationId) {
        if (userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        ProjectEntity project = projectBean.findProjectByName(projectName);


        String operationTypeString = operationType;
        ProjectBean.OperationType operationTypeEnum = ProjectBean.OperationType.valueOf(operationTypeString);
        if (operationType.equals("ACCEPT_INVITATION")) {
            if (projectBean.acceptRequest(token, projectName, userId, operationTypeEnum)) {
                NotificationDto notification = notificationBean.convertToDto(notificationBean.findNotificationById(notificationId));
                if (notification == null) {
                    return Response.status(404).entity("notification not found").build();

                }
                NotificationDto updatedNotification = notificationBean.updateNotificationMessage(notificationId, "ACCEPTED");
                return Response.status(201).entity(updatedNotification).build();
            }
        } else if (projectBean.acceptRequest(token, projectName, userId, operationTypeEnum)) {
            NotificationDto acceptedNotification = new NotificationDto("ACCEPTED", userId, projectName, false, LocalDateTime.now());
            notificationBean.sendNotification(acceptedNotification);
            return Response.status(200).entity(projectBean.findTeamMembers(project)).build();
        }
        return Response.status(405).entity("not allowed").build();
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
            ProjectLogDto projectLogDto = new ProjectLogDto(userBean.findUserByToken(token),projectBean.findProjectByName(projectName), "User rejected: " + userBean.findUserById(userId).getFirstName());
            projectLogDto.setType("REJECT_USER");
            projectLogBean.createProjectLog(projectLogDto);
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
        if(projectBean.promoteUserToProjectManager(token,projectName,userId)) {
            notificationBean.sendNotification(new NotificationDto("PROMOTED",userId,projectName,false,LocalDateTime.now()));
            return Response.status(200).entity("promoted").build();
        }
        else {
            return Response.status(400).entity("something went wrong").build();
        }
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
        notificationBean.sendNotification(new NotificationDto("DEMOTE",userId,projectName,false,LocalDateTime.now()));
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
        ProjectLogDto projectLogDto = new ProjectLogDto(userBean.findUserByToken(token),projectBean.findProjectByName(projectDto.getName()), "Project updated");
        projectLogDto.setType("UPDATE_PROJECT_DETAILS");
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
            ProjectLogDto projectLogDto = new ProjectLogDto(userBean.findUserByToken(token),projectBean.findProjectByName(projectName), "Task updated: " + taskDto.getTitle());
            projectLogDto.setType("UPDATE_TASK");
            projectLogBean.createProjectLog(projectLogDto);
            return Response.status(200).entity(taskDto1).build();
        }else {
            return Response.status(400).entity("something went wrong").build();
        }
    }
    @GET
    @Path("/{projectName}/projectUsers")
    @Produces("application/json")
    public Response fetchProjectUsers(@HeaderParam("token") String token, @PathParam("projectName") String projectName){
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        ProjectEntity project = projectBean.findProjectByName(projectName);
        return Response.status(200).entity(projectBean.findProjectUsers(project)).build();
    }
    @DELETE
    @Path("/{projectName}/ProjectUser")
    @Produces("application/json")
    public Response removeProjectUser(@HeaderParam("token") String token, @PathParam("projectName") String projectName, @QueryParam("userId") int userId){
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token) || !projectBean.isProjectManager(token,projectName)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        if(projectBean.removeProjectUser(token,projectName,userId)) {
            return Response.status(200).entity("user removed").build();
        }else {
            return Response.status(400).entity("something went wrong").build();
        }
    }
    @GET
    @Path("/{projectName}/chat")
    @Produces("application/json")
    public Response fetchProjectChat(@HeaderParam("token") String token, @PathParam("projectName") String projectName){
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        return Response.status(200).entity(groupChatBean.fetchProjectChat(projectName)).build();
    }
    @GET
    @Path("/{projectName}/logs")
    @Produces("application/json")
    public Response fetchProjectLogs(@HeaderParam("token") String token, @PathParam("projectName") String projectName){
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        return Response.status(200).entity(projectBean.getProjectLogs(projectName)).build();
    }
    @POST
    @Path("/{projectName}/logs")
    @Produces("application/json")
    public Response createProjectLog(@HeaderParam("token") String token, @PathParam("projectName") String projectName, ProjectLogDto projectLogDto){
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        projectLogDto.setProject(projectName);
        projectLogDto.setUserId(userBean.findUserByToken(token).getId());
        projectLogDto.setType("OTHER");
        projectLogDto = projectBean.addProjectLog(projectLogDto);
        if(projectLogDto != null){
            return Response.status(201).entity(projectLogDto).build();
        }else {
            return Response.status(400).entity("something went wrong").build();
        }
    }
    @GET
    @Path("/{projectName}/ProjectCreatorByTask")
    @Produces("application/json")
    public Response findProjectCreatorByTask(@HeaderParam("token") String token, @PathParam("projectName") String projectName, @QueryParam("taskId") int taskId){
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        TaskEntity task = taskBean.findTaskById(taskId);
        return Response.status(200).entity(projectBean.findProjectCreatorByTask(task)).build();
    }
    @POST
    @Path("/{projectName}/resources")
    @Produces("application/json")
    public Response addResourceToProject(@HeaderParam("token") String token, @PathParam("projectName") String projectName, @QueryParam("resourceId") int resourceId, @QueryParam("quantity") int quantity){
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token) || !projectBean.isProjectManager(token,projectName)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        if(projectBean.addResourceToProject(token,projectName,resourceId,quantity)) {
            return Response.status(200).entity("resource added").build();
        }else {
            return Response.status(400).entity("something went wrong").build();
        }
    }
    @DELETE
    @Path("/{projectName}/resources")
    @Produces("application/json")
    public Response removeResourceFromProject(@HeaderParam("token") String token, @PathParam("projectName") String projectName, @QueryParam("resourceId") int resourceId, @QueryParam("quantity") int quantity){
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token) || !projectBean.isProjectManager(token,projectName)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        if(projectBean.removeResourceFromProject(token,projectName,resourceId)) {
            return Response.status(200).entity("resource removed").build();
        }else {
            return Response.status(400).entity("something went wrong").build();
        }
    }
    @PUT
    @Path("/{projectName}/resources")
    @Produces("application/json")
    public Response updateResourceInProject(@HeaderParam("token") String token, @PathParam("projectName") String projectName, @QueryParam("resourceId") int resourceId, @QueryParam("quantity") int quantity){
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token) || !projectBean.isProjectManager(token,projectName)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        if(projectBean.updateResourceQuantity(token,projectName,resourceId,quantity)) {
            return Response.status(200).entity("resource updated").build();
        }else {
            return Response.status(400).entity("something went wrong").build();
        }
    }
}
