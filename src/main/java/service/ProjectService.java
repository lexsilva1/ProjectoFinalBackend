package service;

import bean.*;
import dto.*;
import entities.ProjectEntity;
import entities.ProjectUserEntity;
import entities.TaskEntity;
import entities.UserEntity;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * The service class for the projects.
 */
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
    @EJB
    ResourceBean resourceBean;
    private static final Logger logger = LogManager.getLogger(ProjectService.class);

    /**
     *  The method to find all projects.
     * @param token
     * @param projectName
     * @param projectLab
     * @param projectSkill
     * @param projectInterest
     * @param projectStatus
     * @param projectUser
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @GET
    @Path("")
    @Produces("application/json")
    public Response findAllProjects(@HeaderParam("token") String token,@QueryParam("projectName") String projectName,@QueryParam("projectLab") String projectLab,@QueryParam("projectSkill") String projectSkill,@QueryParam("projectInterest") String projectInterest,@QueryParam("projectStatus") int projectStatus,@QueryParam("projectUser") int projectUser,@Context HttpServletRequest request) throws UnsupportedEncodingException {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to find projects", ipAddress);
            if(userBean.findUserByToken(token) != null && tokenBean.isTokenValid(token)) {
                logger.error("User with IP address {} and token {} set token last activity", ipAddress, token);
                userBean.setLastActivity(token);
            }
            logger.info("User with IP address {} and token {} found projects", ipAddress, token);

        return Response.status(200).entity(projectBean.findProjects( projectName,projectLab,projectSkill,projectInterest,projectStatus,projectUser,token)).build();
    }
    /**
     * The method to find all project users.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param request The HTTP request.
     * @return The response.
     */
    @GET
    @Path("/projectUsers")
    @Produces("application/json")
    public Response findAllProjectUsers(@HeaderParam("token") String token,@QueryParam("projectName") String projectName,@Context HttpServletRequest request){
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to find project users", ipAddress);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to find project users", ipAddress, token);
            return Response.status(403).entity("not allowed").build();
        }
        ProjectEntity project = projectBean.findProjectByName(projectName);
        if(project == null) {
            logger.error("User with IP address {} and token {} failed to find project users", ipAddress, token);
            return Response.status(404).entity("project not found").build();
        }else{
            logger.info("User with IP address {} and token {} found project users for project {}", ipAddress, token, projectName);
            return Response.status(200).entity(projectBean.findProjectUsers(project)).build();

        }

    }
    /**
     * The method to find all project tasks.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param request The HTTP request.
     * @return The response.
     */
    @GET
    @Path("/projectSkills")
    @Produces("application/json")
    public Response findAllProjectSkills(@HeaderParam("token") String token,@QueryParam("projectName") String projectName,@Context HttpServletRequest request){
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to find project skills", ipAddress);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to find project skills", ipAddress, token);
            return Response.status(403).entity("not allowed").build();
        }
        ProjectEntity project = projectBean.findProjectByName(projectName);
        if(project == null) {
            logger.error("User with IP address {} and token {} failed to find project skills for project {}", ipAddress, token, projectName);
            return Response.status(404).entity("project not found").build();
        }else{
            logger.info("User with IP address {} and token {} found project skills for project {}", ipAddress, token, projectName);
            return Response.status(200).entity(projectBean.findProjectSkills(project)).build();
        }

    }
    /**
     * The method to find all project interests.
     * @param token The token of the user.
     * @param projectDto The name of the project.
     * @param request The HTTP request.
     * @return The response.
     */
    @POST
    @Path("/")
    @Produces("application/json")
    public Response createProject(@HeaderParam("token") String token, CreateProjectDto projectDto, @Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to create a project", ipAddress);
        if(userBean.findUserByToken(token) == null ||  !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to create a project", ipAddress, token);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);

        boolean created = projectBean.createProject(projectDto,token);
        if(!created) {
            logger.error("User with IP address {} and token {} failed to create a project", ipAddress, token);
            return Response.status(404).entity("project not created").build();
        }
        logger.info("User with IP address {} and token {} created project {}", ipAddress, token, projectDto.getName());
        return Response.status(201).entity("created").build();
    }
    /**
     * The method to find all project interests.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param request The HTTP request.
     * @return The response.
     */
    @GET
    @Path("/allStatus")
    @Produces ("application/json")
    public Response findAllStatus(){
        return Response.status(200).entity(projectBean.findAllStatus()).build();
    }
/**
     * The method to find all project interests.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param request The HTTP request.
     * @return The response.
     */
    @POST
    @Path("/{projectName}/tasks")
    @Produces("application/json")
    public Response createTask(@HeaderParam("token") String token, @PathParam("projectName") String projectName, TaskDto taskDto,@Context HttpServletRequest request){
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to create a task", ipAddress);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to create a task", ipAddress, token);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        if(taskDto.getResponsibleId() == 0) {
            logger.error("User with IP address {} and token {} was set as responsible for this task because the task had no responsible User ", ipAddress, token);
            taskDto.setResponsibleId(userBean.findUserByToken(token).getId());
        }
        if(taskDto.getUsers() == null) {
            Set<Integer> users = new HashSet<>();
            users.add(userBean.findUserByToken(token).getId());
            taskDto.setUsers(users);
            logger.error("User with IP address {} and token {} was added as a user for this task because the task had no executors ", ipAddress, token);
        }
        if(taskBean.createTask(token,projectName,taskDto)) {
            TaskDto taskDto1 = taskBean.getTaskById(taskDto.getId());
            logger.info("User with IP address {} and token {} created task {} for project {}", ipAddress, token, taskDto.getTitle(), projectName);
            return Response.status(201).entity(taskDto1).build();
        }else {
            logger.error("User with IP address {} and token {} failed to create task for project {}", ipAddress, token, projectName);
            return Response.status(400).entity("something went wrong").build();
        }

    }
    /**
     * The method to find all project interests.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param request The HTTP request.
     * @return The response.
     */
    @GET
    @Path("/{projectName}")
    @Produces("application/json")
    public Response findProject(@HeaderParam("token") String token, @PathParam("projectName") String projectName,@Context HttpServletRequest request) throws UnsupportedEncodingException {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to find project {}", ipAddress, projectName);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to find project {}", ipAddress, token, projectName);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        ProjectEntity project = projectBean.findProjectByName(projectName);
        if(project == null) {
            logger.error("User with IP address {} and token {} failed to find project because {} it does not exist}", ipAddress, token, projectName);
            return Response.status(404).entity("project not found").build();
        }else{
            ProjectDto projectDto = projectBean.convertToDto(project);
            logger.info("User with IP address {} and token {} found project {}", ipAddress, token, projectName);
            return Response.status(200).entity(projectDto).build();
        }
    }
    /**
     * The method to find all project interests.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param request The HTTP request.
     * @return The response.
     */
    @PUT
    @Path("/{projectName}/status")
    @Produces("application/json")
    public Response updateProjectStatus(@HeaderParam("token") String token, @PathParam("projectName") String projectName, @QueryParam("status") String status,@Context HttpServletRequest request){
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to update project status", ipAddress);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to update project status", ipAddress, token);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        if(projectBean.updateProjectStatus(token,projectName,status)) {
            logger.info("User with IP address {} and token {} updated project {} status to {}", ipAddress, token, projectName, status);

            return Response.status(200).entity("status updated").build();
        }else{
            logger.error("User with IP address {} and token {} failed to update project {} status to {}", ipAddress, token, projectName, status);
            return Response.status(400).entity("something went wrong").build();
        }
    }
/**
     * The method to find all project interests.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param request The HTTP request.
     * @return The response.
     */
    @POST
    @Path("/{projectName}/apply")
    @Produces("application/json")
    public Response applyProject(@HeaderParam("token") String token, @PathParam("projectName") String projectName,@Context HttpServletRequest request) throws UnsupportedEncodingException {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to apply to project {}", ipAddress,token, projectName);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to apply to project {}", ipAddress, token, projectName);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        if(projectBean.applyToProject(token,projectName)) {
            logger.info("User with IP address {} and token {} applied to project {}", ipAddress, token, projectName);
            return Response.status(200).entity("applied").build();
        }else {
            logger.error("User with IP address {} and token {} failed to apply to project {}", ipAddress, token, projectName);
            return Response.status(405).entity("you cannot apply to this project").build();
        }
    }
    /**
     * The method to find all project interests.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param request The HTTP request.
     * @return The response.
     */
    @POST
    @Path("/{projectName}/invite")
    @Produces("application/json")
    public Response inviteUser(@HeaderParam("token") String token, @PathParam("projectName") String projectName, @QueryParam("userId") int userId,@Context HttpServletRequest request) throws UnsupportedEncodingException {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to invite user {} to project {}", ipAddress,token, userId, projectName);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        if(projectBean.inviteToProject(token,projectName,userId)) {
            logger.info("User with IP address {} and token {} invited user {} to project {}", ipAddress,token, userId, projectName);
            notificationBean.sendNotification(new NotificationDto("INVITE",userId,projectName,false,LocalDateTime.now()));
            logger.info("User with IP address {} and token {} sent notification to user {} for project {}", ipAddress,token, userId, projectName);
            return Response.status(200).entity("invited").build();
        }else {
            logger.error("User with IP address {} and token {} failed to invite user {} to project {}", ipAddress,token, userId, projectName);
            return Response.status(405).entity("you cannot invite to this project").build();
        }
    }
/**
     * The method to find all project interests.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param request The HTTP request.
     * @return The response.
     */
    @POST
    @Path("/{projectName}/accept")
    @Produces("application/json")
    public Response acceptUser(@HeaderParam("token") String token, @PathParam("projectName") String projectName, @QueryParam("userId") int userId,@QueryParam("operationType") String operationType, @QueryParam("notificationId")int notificationId,@Context HttpServletRequest request) throws UnsupportedEncodingException {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to accept user {} to project {}", ipAddress,token, userId, projectName);
        if (userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to accept user {} to project {}", ipAddress,token, userId, projectName);
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
                    logger.error("User with IP address {} and token {} failed to accept user {} to project {} because notification not found", ipAddress,token, userId, projectName);
                    return Response.status(404).entity("notification not found").build();

                }
                NotificationDto updatedNotification = notificationBean.updateNotificationMessage(notificationId, "ACCEPTED");
                logger.info("User with IP address {} and token {} accepted user {} to project {}", ipAddress,token, userId, projectName);
                return Response.status(201).entity(updatedNotification).build();
            }
        } else if (projectBean.acceptRequest(token, projectName, userId, operationTypeEnum)) {
            NotificationDto acceptedNotification = new NotificationDto("ACCEPTED", userId, projectName, false, LocalDateTime.now());
            notificationBean.sendNotification(acceptedNotification);
            logger.info("User with IP address {} and token {} accepted user {} to project {}", ipAddress,token, userId, projectName);
            return Response.status(200).entity(projectBean.findTeamMembers(project)).build();
        }
        logger.error("User with IP address {} and token {} failed to accept user {} to project {}", ipAddress,token, userId, projectName);
        return Response.status(405).entity("not allowed").build();
    }


/**
     * The method to find all project interests.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param request The HTTP request.
     * @return The response.
     */

    @DELETE
    @Path("/{projectName}/reject")
    @Produces("application/json")
    public Response rejectUser(@HeaderParam("token") String token, @PathParam("projectName") String projectName, @QueryParam("userId") int userId,@QueryParam("operationType") String operationType, @QueryParam("notificationId") int notificationId,@Context HttpServletRequest request) throws UnsupportedEncodingException {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to reject user {} from project {}", ipAddress,token, userId, projectName);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to reject user {} from project {}", ipAddress,token, userId, projectName);
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
            logger.info("User with IP address {} and token {} rejected user {} from project {}", ipAddress,token, userId, projectName);
            return Response.status(200).entity(updatedNotification).build();
        }else{
            logger.error("User with IP address {} and token {} failed to reject user {} from project {}", ipAddress,token, userId, projectName);
            return Response.status(405).entity("not rejected").build();
        }

    }
    /**
     * The method to find all project interests.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param request The HTTP request.
     * @return The response.
     */
    @PUT
    @Path("/{projectName}/promote")
    @Produces("application/json")
    public Response promoteUser(@HeaderParam("token") String token, @PathParam("projectName") String projectName, @QueryParam("userId") int userId,@Context HttpServletRequest request){
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to promote user {} to project manager in project {}", ipAddress,token, userId, projectName);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to promote user {} to project manager in project {}", ipAddress,token, userId, projectName);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        if(projectBean.promoteUserToProjectManager(token,projectName,userId)) {
            notificationBean.sendNotification(new NotificationDto("PROMOTED",userId,projectName,false,LocalDateTime.now()));
            logger.info("User with IP address {} and token {} promoted user {} to project manager in project {}", ipAddress,token, userId, projectName);
            return Response.status(200).entity("promoted").build();
        }
        else {
            logger.error("User with IP address {} and token {} failed to promote user {} to project manager in project {}", ipAddress,token, userId, projectName);
            return Response.status(400).entity("something went wrong").build();
        }
    }
    /**
     * The method to find all project interests.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param request The HTTP request.
     * @return The response.
     */
    @PUT
    @Path("/{projectName}/demote")
    @Produces("application/json")
    public Response demoteUser(@HeaderParam("token") String token, @PathParam("projectName") String projectName, @QueryParam("userId") int userId,@Context HttpServletRequest request){
        String ipAdress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to demote user {} from project manager in project {}", ipAdress,token, userId, projectName);
    if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to demote user {} from project manager in project {}", ipAdress,token, userId, projectName);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        projectBean.demoteUserFromProjectManager(token,projectName,userId);
        notificationBean.sendNotification(new NotificationDto("DEMOTED",userId,projectName,false,LocalDateTime.now()));
        logger.info("User with IP address {} and token {} demoted user {} from project manager in project {}", ipAdress,token, userId, projectName);
        return Response.status(200).entity("demoted").build();
    }
    /**
     * The method to find all project interests.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param request The HTTP request.
     * @return The response.
     */
    @POST
    @Path("/{projectName}/leave")
    @Produces("application/json")
    public Response leaveProject(@HeaderParam("token") String token, @PathParam("projectName") String projectName,@Context HttpServletRequest request) throws UnsupportedEncodingException {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to leave project {}", ipAddress,token, projectName);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to leave project {}", ipAddress,token, projectName);

            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        projectBean.leaveProject(token,projectName);
        logger.info("User with IP address {} and token {} left project {}", ipAddress,token, projectName);
        return Response.status(200).entity("left").build();
    }
    /**
     * The method to find all project interests.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param request The HTTP request.
     * @return The response.
     */
    @PUT
    @Path("/{projectName}")
    @Produces("application/json")
    public Response updateProject(@HeaderParam("token") String token,@PathParam("projectName") String projectName, UpdateProjectDto projectDto,@Context HttpServletRequest request) throws UnsupportedEncodingException {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to update project {}", ipAddress,token, projectName);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to update project {}", ipAddress,token, projectName);
            return Response.status(403).entity("not allowed").build();
        }
        ProjectUserEntity projectUser = projectBean.findUserByTokenAndProject(projectName,token);
        if(projectUser == null || !projectUser.isProjectManager()) {
            logger.error("User with IP address {} and token {} is not allowed to update project {} because he is not a Project Manager", ipAddress,token, projectName);
            return Response.status(403).entity("not a manager").build();
        }
        userBean.setLastActivity(token);
        if(projectBean.updateProject(projectDto,projectName)) {
            ProjectLogDto projectLogDto = new ProjectLogDto(userBean.findUserByToken(token), projectBean.findProjectByName(projectName), "Project updated");
            projectLogDto.setType("UPDATE_PROJECT_DETAILS");
            projectLogBean.createProjectLog(projectLogDto);
            logger.info("User with IP address {} and token {} updated project {}", ipAddress,token, projectName);
            return Response.status(200).entity("project updated").build();
        }else {
            logger.error("User with IP address {} and token {} failed to update project {}", ipAddress,token, projectName);
            return Response.status(400).entity("something went wrong").build();
        }
    }
    /**
     * The method to find all project interests.
     * @param token The token of the user.
     * @param request The HTTP request.
     * @return The response.
     */
    @GET
    @Path("statistics")
    @Produces("application/json")
    public Response getStatistics(@HeaderParam("token") String token,@Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to get project statistics", ipAddress,token);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to get project statistics", ipAddress,token);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        logger.info("User with IP address {} and token {} got project statistics", ipAddress,token);
        return Response.status(200).entity(projectBean.getProjectStatistics()).build();
    }

/**
     * The method to find all project interests.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param request The HTTP request.
     * @return The response.
     */
    @GET
    @Path("/{projectName}/tasks")
    @Produces("application/json")
    public Response findProjectTasks(@HeaderParam("token") String token, @PathParam("projectName") String projectName,@Context HttpServletRequest request) throws UnsupportedEncodingException {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to find tasks for project {}", ipAddress,token, projectName);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        ProjectEntity project = projectBean.findProjectByName(projectName);
        if(project == null) {
            logger.error("User with IP address {} and token {} failed to find tasks for project {} because project not found", ipAddress,token, projectName);
            return Response.status(404).entity("project not found").build();
        }else{
            logger.info("User with IP address {} and token {} found tasks for project {}", ipAddress,token, projectName);
            return Response.status(200).entity(projectBean.findProjectTasks(projectName)).build();
        }
    }
    /**
     * The method to find all project interests.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param request The HTTP request.
     * @return The response.
     */
    @PUT
    @Path("/{projectName}/tasks")
    @Produces("application/json")
    public Response updateTask(@HeaderParam("token") String token, @PathParam("projectName") String projectName, TaskDto taskDto,@Context HttpServletRequest request) throws UnsupportedEncodingException {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to update task", ipAddress,token);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to update task", ipAddress,token);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        if(taskBean.updateTask(token,projectName,taskDto)) {
            TaskDto taskDto1 = taskBean.getTaskById(taskDto.getId());
            ProjectLogDto projectLogDto = new ProjectLogDto(userBean.findUserByToken(token),projectBean.findProjectByName(projectName), "Task updated: " + taskDto.getTitle());
            if(taskDto1.getStatus().equals("COMPLETED"))
                projectLogDto.setType("COMPLETE_TASK");
            else if(taskDto1.getStatus().equals(("CANCELLED")))
                projectLogDto.setType("DELETE_TASK");
            else {
                projectLogDto.setType("UPDATE_TASK");
            }
            projectLogBean.createProjectLog(projectLogDto);
            logger.info("User with IP address {} and token {} updated task for project {}", ipAddress,token, projectName);
            return Response.status(200).entity(taskDto1).build();
        }else {
            logger.error("User with IP address {} and token {} failed to update task for project {}", ipAddress,token, projectName);
            return Response.status(400).entity("something went wrong").build();
        }
    }
    /**
     * The method to find all project interests.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param request The HTTP request.
     * @return The response.
     */
    @GET
    @Path("/{projectName}/projectUsers")
    @Produces("application/json")
    public Response fetchProjectUsers(@HeaderParam("token") String token, @PathParam("projectName") String projectName,@Context HttpServletRequest request) throws UnsupportedEncodingException {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to find project users for project {}", ipAddress,token, projectName);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to find project users for project {}", ipAddress,token, projectName);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        ProjectEntity project = projectBean.findProjectByName(projectName);
        if(project == null) {
            logger.error("User with IP address {} and token {} failed to find project users for project {} because project not found", ipAddress,token, projectName);
            return Response.status(404).entity("project not found").build();
        }
        logger.info("User with IP address {} and token {} found project users for project {}", ipAddress,token, projectName);
        return Response.status(200).entity(projectBean.findProjectUsersByProject(project)).build();
    }
    /**
     * The method to find all project interests.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param request The HTTP request.
     * @return The response.
     */
    @DELETE
    @Path("/{projectName}/ProjectUser")
    @Produces("application/json")
    public Response removeProjectUser(@HeaderParam("token") String token, @PathParam("projectName") String projectName, @QueryParam("userId") int userId,@Context HttpServletRequest request){
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to remove user {} from project {}", ipAddress,token, userId, projectName);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token) || !projectBean.isProjectManager(token,projectName)) {
            logger.error("User with IP address {} and token {} is not allowed to remove user {} from project {}", ipAddress,token, userId, projectName);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        if(projectBean.removeProjectUser(token,projectName,userId)) {
            ProjectLogDto projectLogDto = new ProjectLogDto(userBean.findUserByToken(token),projectBean.findProjectByName(projectName), "User removed: " + userBean.findUserById(userId).getFirstName());
            projectLogDto.setType("REMOVE_USER");
            projectLogBean.createProjectLog(projectLogDto);
            logger.info("User with IP address {} and token {} removed user {} from project {}", ipAddress,token, userId, projectName);
            return Response.status(200).entity("user removed").build();
        }else {
            logger.error("User with IP address {} and token {} failed to remove user {} from project {}", ipAddress,token, userId, projectName);
            return Response.status(400).entity("something went wrong").build();
        }
    }
    /**
     * The method to find all project interests.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param request The HTTP request.
     * @return The response.
     */
    @GET
    @Path("/{projectName}/chat")
    @Produces("application/json")
    public Response fetchProjectChat(@HeaderParam("token") String token, @PathParam("projectName") String projectName,@Context HttpServletRequest request){
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to find project chat for project {}", ipAddress,token, projectName);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to find project chat for project {}", ipAddress,token, projectName);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        logger.info("User with IP address {} and token {} found project chat for project {}", ipAddress,token, projectName);
        return Response.status(200).entity(groupChatBean.fetchProjectChat(projectName)).build();
    }
    /**
     * The method to find all project interests.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param request The HTTP request.
     * @return The response.
     */
    @GET
    @Path("/{projectName}/logs")
    @Produces("application/json")
    public Response fetchProjectLogs(@HeaderParam("token") String token, @PathParam("projectName") String projectName,@Context HttpServletRequest request){
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to find project logs for project {}", ipAddress,token, projectName);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to find project logs for project {}", ipAddress,token, projectName);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        logger.info("User with IP address {} and token {} found project logs for project {}", ipAddress,token, projectName);
        return Response.status(200).entity(projectBean.getProjectLogs(projectName)).build();
    }
    /**
     * The method to find all project interests.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param request The HTTP request.
     * @return The response.
     */
    @POST
    @Path("/{projectName}/logs")
    @Produces("application/json")
    public Response createProjectLog(@HeaderParam("token") String token, @PathParam("projectName") String projectName, ProjectLogDto projectLogDto,@Context HttpServletRequest request){
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to create project log for project {}", ipAddress,token, projectName);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to create project log for project {}", ipAddress,token, projectName);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        projectLogDto.setProject(projectName);
        projectLogDto.setUserId(userBean.findUserByToken(token).getId());
        projectLogDto.setType("OTHER");
        projectLogDto = projectBean.addProjectLog(projectLogDto);
        if(projectLogDto != null){
            logger.info("User with IP address {} and token {} created project log for project {}", ipAddress,token, projectName);
            return Response.status(201).entity(projectLogDto).build();
        }else {
            logger.error("User with IP address {} and token {} failed to create project log for project {}", ipAddress,token, projectName);
            return Response.status(400).entity("something went wrong").build();
        }
    }
    /**
     * The method to find all project interests.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param taskId The id of the task.
     * @param request The HTTP request.
     * @return The response.
     */
    @GET
    @Path("/{projectName}/ProjectCreatorByTask")
    @Produces("application/json")
    public Response findProjectCreatorByTask(@HeaderParam("token") String token, @PathParam("projectName") String projectName, @QueryParam("taskId") int taskId,@Context HttpServletRequest request){
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to find project creator by task", ipAddress,token);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to find project creator by task", ipAddress,token);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        TaskEntity task = taskBean.findTaskById(taskId);
        if(task == null) {
            logger.error("User with IP address {} and token {} failed to find project creator by task because task not found", ipAddress,token);
            return Response.status(404).entity("task not found").build();
        }
        logger.info("User with IP address {} and token {} found project {} creator by task {}", ipAddress,token, projectName, taskId);
        return Response.status(200).entity(projectBean.findProjectCreatorByTask(task)).build();
    }
    /**
     * the method to add Resource to a project.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param  resourceId The id of the resource.
     * @param request The HTTP request.
     * @return The response.
     */
    @POST
    @Path("/{projectName}/resources")
    @Produces("application/json")
    public Response addResourceToProject(@HeaderParam("token") String token, @PathParam("projectName") String projectName, @QueryParam("resourceId") int resourceId, @QueryParam("quantity") int quantity,@Context HttpServletRequest request){
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to add resource to project", ipAddress,token);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to add resource to project", ipAddress,token);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        if(projectBean.addResourceToProject(token,projectName,resourceId,quantity)) {
            ProjectLogDto projectLogDto = new ProjectLogDto(userBean.findUserByToken(token),projectBean.findProjectByName(projectName), "Resource added: " + resourceBean.findResourceById(resourceId).getName());
            projectLogDto.setType("PROJECT_RESOURCE_ADDED");
            projectLogBean.createProjectLog(projectLogDto);
            logger.info("User with IP address {} and token {} added resource to project {}", ipAddress,token, projectName);
            return Response.status(200).entity(resourceBean.findResourceById(resourceId)).build();
        }else {
            logger.error("User with IP address {} and token {} failed to add resource to project{}", ipAddress,token, projectName);
            return Response.status(400).entity("something went wrong").build();
        }
    }
    /**
     * the method to remove Resource from a project.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param  resourceId The id of the resource.
     * @param request The HTTP request.
     * @return The response.
     */
    @DELETE
    @Path("/{projectName}/resources")
    @Produces("application/json")
    public Response removeResourceFromProject(@HeaderParam("token") String token, @PathParam("projectName") String projectName, @QueryParam("resourceId") int resourceId, @QueryParam("quantity") int quantity,@Context HttpServletRequest request){
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to remove resource from project", ipAddress,token);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} is not allowed to remove resource from project", ipAddress,token);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        if(projectBean.removeResourceFromProject(token,projectName,resourceId)) {
            ProjectLogDto projectLogDto = new ProjectLogDto(userBean.findUserByToken(token),projectBean.findProjectByName(projectName), "Resource removed: " + resourceBean.findResourceById(resourceId).getName());
            projectLogDto.setType("PROJECT_RESOURCE_REMOVED");
            projectLogBean.createProjectLog(projectLogDto);
            logger.info("User with IP address {} and token {} removed resource from project", ipAddress,token);
            return Response.status(200).entity("resource removed").build();
        }else {
            logger.error("User with IP address {} and token {} failed to remove resource from project", ipAddress,token);
            return Response.status(400).entity("something went wrong").build();
        }
    }
    /**
     * the method to update Resource in a project.
     * @param token The token of the user.
     * @param projectName The name of the project.
     * @param  resourceId The id of the resource.
     * @param request The HTTP request.
     * @return The response.
     */
    @PUT
    @Path("/{projectName}/resources")
    @Produces("application/json")
    public Response updateResourceInProject(@HeaderParam("token") String token, @PathParam("projectName") String projectName, @QueryParam("resourceId") int resourceId, @QueryParam("quantity") int quantity,@Context HttpServletRequest request){
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to update resource in project", ipAddress,token);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token) ) {
            logger.error("User with IP address {} and token {} is not allowed to update resource in project", ipAddress,token);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        projectName = projectBean.decodeProjectName(projectName);
        if(projectBean.updateResourceQuantity(token,projectName,resourceId,quantity)) {
           ProjectLogDto projectLogDto = new ProjectLogDto(userBean.findUserByToken(token),projectBean.findProjectByName(projectName), "Resource updated: " + resourceBean.findResourceById(resourceId).getName());
           projectLogDto.setType("PROJECT_RESOURCE_UPDATED");
              projectLogBean.createProjectLog(projectLogDto);
            logger.info("User with IP address {} and token {} updated resource in project", ipAddress,token);
            return Response.status(200).entity(resourceBean.findResourceById(resourceId)).build();
        }else {
            logger.error("User with IP address {} and token {} failed to update resource in project", ipAddress,token);
            return Response.status(400).entity("something went wrong").build();
        }
    }
}
