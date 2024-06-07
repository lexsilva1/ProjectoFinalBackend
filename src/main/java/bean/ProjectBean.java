package bean;

import dao.*;
import dto.*;
import entities.*;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.*;

@Stateless
public class ProjectBean {
    @Inject
    private ProjectDao projectDao;
    @Inject
    UserBean userBean;
    @Inject
    LabDao labDao;
    @Inject
    SkillDao skillDao;
    @Inject
    ProjectUserDao projectUserDao;
    @Inject
    InterestDao interestDao;
    @Inject
    UserDao UserDao;
    @Inject
    ResourceDao resourceDao;
    @Inject
    TaskBean taskBean;
    @Inject
    TaskDao taskDao;
    @Inject
    ProjectTaskDao projectTaskDao;
    @Inject
    ProjectResourceDao projectResourceDao;
    @Inject
    ResourceBean resourceBean;



    public void createDefaultProjects() {
        if(projectDao.findProjectByName("Forge X") == null) {
            ProjectEntity defaultProject = new ProjectEntity();
            defaultProject.setName("Forge X");
            defaultProject.setLab(labDao.findLabByLocation(LabEntity.Lab.Coimbra));
            List<SkillEntity> skills = skillDao.findSkillByType(SkillEntity.SkillType.SOFTWARE);
            if (skills != null) {
                defaultProject.setSkills(new LinkedHashSet<>(skills));
            }
            defaultProject.setStatus((ProjectEntity.Status.Planning));
            defaultProject.setDescription("Forge X is a project that aims to create a new software that will revolutionize the way we interact with technology.");
            defaultProject.setMaxMembers(5);
            List<InterestEntity> interests = interestDao.findInterestByType(InterestEntity.InterestType.KNOWLEDGE);
            if (interests != null) {
                defaultProject.setInterests(new LinkedHashSet<>(interests));
            }
            defaultProject.setCreator(userBean.findUserByEmail("admin@admin.com"));
            ProjectUserEntity defaultProjectUser = new ProjectUserEntity();
            defaultProjectUser.setProject(defaultProject);
            defaultProjectUser.setUser(userBean.findUserByEmail("admin@admin.com"));
            defaultProjectUser.setProjectManager(true);
            defaultProjectUser.setApprovalStatus(ProjectUserEntity.ApprovalStatus.MEMBER);

            defaultProject.setProjectUsers(new LinkedHashSet<>(List.of(defaultProjectUser)));
            defaultProject.setStartDate(java.time.LocalDateTime.now().minusDays(5));
            defaultProject.setEndDate(java.time.LocalDateTime.now().plusDays(30));
            defaultProject.setCreatedAt(java.time.LocalDateTime.now());
            TaskEntity task = new TaskEntity();
            task.setTitle("Create the database");
            task.setDescription("Create the database for the project");
            task.setProject(defaultProject);
            task.setResponsibleUser(userBean.findUserByEmail("admin@admin.com"));
            task.setStatus(TaskEntity.Status.NOT_STARTED);
            task.setStartDate(java.time.LocalDateTime.now().minusDays(5));
            task.setEndDate(java.time.LocalDateTime.now().plusDays(30));
            task.setCreationDate(java.time.LocalDateTime.now());
            taskDao.persist(task);
            projectDao.persist(defaultProject);
            ProjectTaskEntity projectTask = new ProjectTaskEntity();
            projectTask.setProject_id(defaultProject.getName());
            projectTask.setTask_id(task.getId());
            projectTaskDao.persist(projectTask);
            projectUserDao.persist(defaultProjectUser);
            ProjectResourceEntity projectResource = new ProjectResourceEntity();
            projectResource.setProject_id(defaultProject.getId());
            projectResource.setResource_id(resourceDao.findResourceByName("CPU").getId());
            projectResource.setQuantity(5);
            projectResourceDao.persist(projectResource);



        }
    }
    public ProjectEntity findProjectByName(String name) {
        return projectDao.findProjectByName(name);
    }

    public ProjectDto convertToDto(ProjectEntity project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setName(project.getName());
        projectDto.setDescription(project.getDescription());
        projectDto.setImage(project.getImage());
        projectDto.setStatus(project.getStatus().name());
        projectDto.setLab(project.getLab().getLocation().name());
        Set<String> skills = new LinkedHashSet<>();
        for (SkillEntity skill : project.getSkills()) {
            skills.add(skill.getName());
        }
        projectDto.setSkills(skills.toArray(new String[0]));
        Set<String> interests = new LinkedHashSet<>();
        for (InterestEntity interest : project.getInterests()) {
            interests.add(interest.getName());
        }
        projectDto.setInterests(interests.toArray(new String[0]));
        List<ProjectUserDto> teamMembers = new ArrayList<>();
        for (ProjectUserEntity projectUser : project.getProjectUsers()) {
            teamMembers.add(userBean.convertToProjectUserDto(projectUser.getUser()));
        }
        projectDto.setTeamMembers(teamMembers);
        Set<ResourceEntity> resources = project.getResources();
        List<ResourceDto> resourceDtos = new ArrayList<>();
        for (ResourceEntity resource : resources) {
            resourceDtos.add(resourceBean.convertToDto(resource));
        }
        projectDto.setBillOfMaterials(resourceDtos);
        projectDto.setMaxTeamMembers(project.getMaxMembers());
        projectDto.setStartDate(project.getStartDate());
        projectDto.setEndDate(project.getEndDate());
        return projectDto;
    }



    public List<UserDto> findProjectUsers(ProjectEntity project) {
        List<UserEntity> projectUsers = projectUserDao.findAllProjectUsers(project);
        List<UserDto> users = new ArrayList<>();
        for (UserEntity user : projectUsers) {
            users.add(userBean.convertToDto(user));
        }

        return users;
    }
    public List<SkillDto> findProjectSkills(ProjectEntity project) {
        List<SkillEntity> projectSkills = projectDao.findProjectSkills(project);
        List<SkillDto> skills = new ArrayList<>();
        for (SkillEntity skill : projectSkills) {
            skills.add(convertToSkillDto(skill));
        }
        return skills;
    }
    public SkillDto convertToSkillDto(SkillEntity skill) {
        SkillDto skillDto = new SkillDto();
        skillDto.setName(skill.getName());
        skillDto.setSkillType(skill.getSkillType().getDeclaringClass().getTypeName());
        skillDto.setId(skill.getId()); // Correct usage
        return skillDto;
    }
    public List<ProjectDto> findProjects(String projectName, String projectLab, String projectSkill, String projectInterest, int projectStatus, int projectUser, String token) {
        List<ProjectEntity> projectEntities = projectDao.findProjects(projectName, projectLab, projectSkill, projectInterest, projectStatus, projectUser);

        List<ProjectDto> projectDtos = new ArrayList<>();
        for (ProjectEntity projectEntity : projectEntities) {
            projectDtos.add(convertToDto(projectEntity));
        }
        return projectDtos;
    }

    public boolean createProject(ProjectDto projectDto, String token) {
        if(projectDao.findProjectByName(projectDto.getName()) != null) {
            return false;
        }

        ProjectEntity project = new ProjectEntity();
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setImage(projectDto.getImage());
        project.setStatus(ProjectEntity.Status.Planning);
        project.setLab(labDao.findLabByLocation(LabEntity.Lab.valueOf(projectDto.getLab())));
        project.setCreator(userBean.findUserByToken(token));
        project.setMaxMembers(projectDto.getMaxTeamMembers());
        project.setCreatedAt(java.time.LocalDateTime.now());
        if(projectDto.getStartDate() == null || projectDto.getEndDate() == null) {
            project.setStartDate(java.time.LocalDateTime.now());
            project.setEndDate(java.time.LocalDateTime.now().plusDays(30));
        }
        project.setStartDate(projectDto.getStartDate());
        project.setEndDate(projectDto.getEndDate());
        List<SkillEntity> skills = new ArrayList<>();
        for (String skillName : projectDto.getSkills()) {
            SkillEntity skill = skillDao.findSkillByName(skillName);
            if (skill != null) {
                skills.add(skill);
            }else{
                skill = new SkillEntity();
                skill.setName(skillName);
                skillDao.persist(skill);
                skills.add(skill);

            }
        }
        project.setSkills(new LinkedHashSet<>(skills));
        List<InterestEntity> interests = new ArrayList<>();
        for (String interestName : projectDto.getInterests()) {
            InterestEntity interest = interestDao.findInterestByName(interestName);
            if (interest != null) {
                interests.add(interest);
            }else {
                interest = new InterestEntity();
                interest.setName(interestName);
                interestDao.persist(interest);
                interests.add(interest);
            }
        }
        project.setInterests(new LinkedHashSet<>(interests));


        for(int i = 0; i < projectDto.getTeamMembers().size(); i++) {
            for (ProjectUserDto projectUserDto : projectDto.getTeamMembers()) {
                ProjectUserEntity projectUser = new ProjectUserEntity();
                projectUser.setProject(project);
                projectUser.setUser(UserDao.findUserById(projectUserDto.getUserId()));
                projectUser.setProjectManager(projectUserDto.isProjectManager());
                projectUser.setApprovalStatus(ProjectUserEntity.ApprovalStatus.MEMBER);
                projectUser.setProjectManager(projectUserDto.isProjectManager());
                projectUserDao.persist(projectUser);
            }
        }

        Set resourceSet = new LinkedHashSet<>();
        for (ResourceDto resourceDto : projectDto.getBillOfMaterials()) {
            ResourceEntity resource = resourceDao.findResourceByName(resourceDto.getName());
            if (resource == null) {
                resource = new ResourceEntity();
                resource.setName(resourceDto.getName());
                resourceDao.persist(resource);
            }
            resourceSet.add(resource);
        }
        project.setResources(resourceSet);
        TaskEntity lastTask = taskBean.createLastTask(token, project, userBean.findUserByToken(token), project.getStartDate(), project.getEndDate(), List.of(userBean.findUserByToken(token).getId()));
        ProjectTaskEntity projectTask = new ProjectTaskEntity();
        projectTask.setProject_id(project.getName());
        projectTask.setTask_id(lastTask.getId());
        projectTaskDao.persist(projectTask);
        projectDao.persist(project);
        return true;
       }
       public List<String> findAllStatus(){
        List<String> status = new ArrayList<>();
        for(ProjectEntity.Status s : ProjectEntity.Status.values()){
            status.add(s.name());
        }
        return status;
       }
    public boolean applyToProject(String token, String projectName) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        ProjectUserEntity projectUser = projectUserDao.findProjectUserByProjectAndUser(project, user);
        if (user == null || project == null) {
            return false;
        }
        if(projectUser != null) {
            return false;
        }
        projectUser.setProject(project);
        projectUser.setUser(user);
        projectUser.setApprovalStatus(ProjectUserEntity.ApprovalStatus.APPLIED);
        projectUser.setProjectManager(false);
        projectUserDao.persist(projectUser);
        return true;
    }

    public boolean inviteToProject(String token, String projectName, int userId) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        UserEntity invitedUser = UserDao.findUserById(userId);
        ProjectUserEntity projectUser = projectUserDao.findProjectUserByProjectAndUser(project, invitedUser);
        if (user == null || project == null || invitedUser == null) {
            return false;
        }
        if(projectUser != null) {
            return false;
        }
        projectUser.setProject(project);
        projectUser.setUser(invitedUser);
        projectUser.setApprovalStatus(ProjectUserEntity.ApprovalStatus.INVITED);
        projectUser.setProjectManager(false);
        projectUserDao.persist(projectUser);
        return true;
    }
    public enum OperationType {
        ACCEPT_INVITATION,
        ACCEPT_APPLICATION
    }

    public boolean acceptRequest(String token, String projectName, Integer userId, OperationType operationType) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        UserEntity targetUser;

        if (operationType == OperationType.ACCEPT_INVITATION) {
            targetUser = user;
        } else {
            targetUser = UserDao.findUserById(userId);
        }

        ProjectUserEntity projectUser = projectUserDao.findProjectUserByProjectAndUser(project, targetUser);
        if (user == null || project == null || targetUser == null || projectUser == null) {
            return false;
        }

        if (operationType == OperationType.ACCEPT_INVITATION) {
            if (projectUser.getApprovalStatus() != ProjectUserEntity.ApprovalStatus.INVITED) {
                return false;
            }
        } else if (operationType == OperationType.ACCEPT_APPLICATION) {
            if (projectUser.getApprovalStatus() != ProjectUserEntity.ApprovalStatus.APPLIED) {
                return false;
            }
        }

        projectUser.setApprovalStatus(ProjectUserEntity.ApprovalStatus.MEMBER);
        projectUserDao.persist(projectUser);
        return true;
    }

    public boolean rejectInvitation(String token, String projectName) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        ProjectUserEntity projectUser = projectUserDao.findProjectUserByProjectAndUser(project, user);
        if (user == null || project == null || projectUser == null) {
            return false;
        }
        if(projectUser.getApprovalStatus() != ProjectUserEntity.ApprovalStatus.INVITED) {
            return false;
        }
        projectUserDao.remove(projectUser);
        return true;
    }

    public boolean leaveProject(String token, String projectName) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        ProjectUserEntity projectUser = projectUserDao.findProjectUserByProjectAndUser(project, user);
        if (user == null || project == null || projectUser == null) {
            return false;
        }
        projectUserDao.remove(projectUser);
        return true;
    }
    public boolean removeUserFromProject(String token, String projectName, int userId) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        UserEntity removedUser = UserDao.findUserById(userId);
        ProjectUserEntity projectUser = projectUserDao.findProjectUserByProjectAndUser(project, removedUser);
        if (user == null || project == null || removedUser == null || projectUser == null) {
            return false;
        }
        projectUserDao.remove(projectUser);
        return true;
    }
    public boolean promoteUserToProjectManager(String token, String projectName, int userId) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        UserEntity promotedUser = UserDao.findUserById(userId);
        ProjectUserEntity projectUser = projectUserDao.findProjectUserByProjectAndUser(project, promotedUser);
        if (user == null || project == null || promotedUser == null || projectUser == null) {
            return false;
        }
        projectUser.setProjectManager(true);
        projectUserDao.persist(projectUser);
        return true;
    }
    public boolean demoteUserFromProjectManager(String token, String projectName, int userId) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        UserEntity demotedUser = UserDao.findUserById(userId);
        ProjectUserEntity projectUser = projectUserDao.findProjectUserByProjectAndUser(project, demotedUser);
        if (user == null || project == null || demotedUser == null || projectUser == null) {
            return false;
        }
        projectUser.setProjectManager(false);
        projectUserDao.persist(projectUser);
        return true;
    }
    public boolean changeProjectStatus(String token, String projectName, String status) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        if (user == null || project == null) {
            return false;
        }
        project.setStatus(ProjectEntity.Status.valueOf(status));
        projectDao.persist(project);
        return true;
    }
    public boolean addResourceToProject(String token, String projectName, String resourceName, int quantity) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        ResourceEntity resource = resourceDao.findResourceByName(resourceName);
        if (user == null || project == null || resource == null) {
            return false;
        }
        ProjectResourceEntity projectResource = new ProjectResourceEntity();
        projectResource.setProject_id(project.getId());
        projectResource.setResource_id(resource.getId());
        projectResource.setQuantity(quantity);
        projectResourceDao.persist(projectResource);
        return true;
    }
}
