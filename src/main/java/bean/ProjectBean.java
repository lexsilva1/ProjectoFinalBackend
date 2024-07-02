package bean;

import dao.*;
import dto.*;
import entities.*;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
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
    ProjectResourceDao projectResourceDao;
    @Inject
    ResourceBean resourceBean;
    @Inject
    SkillBean skillBean;
    @Inject
    InterestBean interestBean;
    @Inject
    NotificationBean notificationBean;


    public void createDefaultProjects() {
        if (projectDao.findProjectByName("Forge X") == null) {
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
            task.setResponsibleUser(userBean.findUserByEmail("admin@admin.com"));
            task.setStatus(TaskEntity.Status.NOT_STARTED);
            task.setStartDate(java.time.LocalDateTime.now().minusDays(5));
            task.setEndDate(java.time.LocalDateTime.now().plusDays(30));
            task.setCreationDate(java.time.LocalDateTime.now());
            taskDao.persist(task);
            TaskEntity finaltask = new TaskEntity();
            finaltask.setTitle("Final Presentation");
            finaltask.setDescription("Final presentation of the finalized project");

            finaltask.setResponsibleUser(userBean.findUserById(1));
            finaltask.setStatus(TaskEntity.Status.NOT_STARTED);
            finaltask.setStartDate(defaultProject.getEndDate().minusDays(1));
            finaltask.setEndDate(defaultProject.getEndDate());
            finaltask.setCreationDate(LocalDateTime.now());
            finaltask.setCreatedBy(userBean.findUserById(1));
            taskDao.persist(finaltask);
            defaultProject.setTasks(new LinkedHashSet<>(List.of(finaltask,task)));
            projectDao.persist(defaultProject);
            projectUserDao.persist(defaultProjectUser);
            ProjectResourceEntity projectResource = new ProjectResourceEntity();
            projectResource.setProject_id(defaultProject.getId());
            projectResource.setResource_id(resourceDao.findResourceByName("CPU").getId());
            projectResource.setQuantity(3);
            projectResourceDao.persist(projectResource);


        }
        if(projectDao.findProjectByName("UserInterface") == null) {
            ProjectEntity defaultProject = new ProjectEntity();
            defaultProject.setName("UserInterface");
            defaultProject.setLab(labDao.findLabByLocation(LabEntity.Lab.Vila_Real));
            List<SkillEntity> skills = skillDao.findSkillByType(SkillEntity.SkillType.HARDWARE);
            if (skills != null) {
                defaultProject.setSkills(new LinkedHashSet<>(skills));
            }
            defaultProject.setStatus((ProjectEntity.Status.Approved));
            defaultProject.setDescription("UserInterface is a project that aims to create a new hardware that will revolutionize the way we interact with technology.");
            defaultProject.setMaxMembers(5);
            List<InterestEntity> interests = interestDao.findInterestByType(InterestEntity.InterestType.CAUSES);
            if (interests != null) {
                defaultProject.setInterests(new LinkedHashSet<>(interests));
            }

            ProjectUserEntity defaultProjectUser = new ProjectUserEntity();
            defaultProjectUser.setProject(defaultProject);
            defaultProjectUser.setUser(userBean.findUserByEmail("tozemarreco@gmail.com"));
            defaultProjectUser.setProjectManager(true);
            defaultProjectUser.setApprovalStatus(ProjectUserEntity.ApprovalStatus.MEMBER);
            ProjectUserEntity defaultProjectUser2 = new ProjectUserEntity();
            defaultProjectUser2.setProject(defaultProject);
            defaultProjectUser2.setUser(userBean.findUserByEmail("mariamacaca@gmail.com"));
            defaultProjectUser2.setProjectManager(false);
            defaultProjectUser2.setApprovalStatus(ProjectUserEntity.ApprovalStatus.MEMBER);
            ProjectUserEntity defaultProjectUser3 = new ProjectUserEntity();
            defaultProjectUser3.setProject(defaultProject);
            defaultProjectUser3.setUser(userBean.findUserByEmail("zetamplario@gmail.com"));
            defaultProjectUser3.setProjectManager(false);
            defaultProjectUser3.setApprovalStatus(ProjectUserEntity.ApprovalStatus.INVITED);
            defaultProject.setProjectUsers(new LinkedHashSet<>(List.of(defaultProjectUser, defaultProjectUser2, defaultProjectUser3)));
            defaultProject.setStartDate(java.time.LocalDateTime.now().minusDays(10));
            defaultProject.setEndDate(java.time.LocalDateTime.now().plusDays(15));
            defaultProject.setCreatedAt(java.time.LocalDateTime.now().minusDays(15));
            projectDao.persist(defaultProject);
            TaskEntity task = new TaskEntity();
            task.setTitle("Create the UserInterface");
            task.setDescription("Create the UserInterface for the project");

            task.setResponsibleUser(userBean.findUserByEmail("mariamacaca@gmail.com"));
            task.setStatus(TaskEntity.Status.NOT_STARTED);
            task.setStartDate(java.time.LocalDateTime.now().minusDays(10));
            task.setEndDate(java.time.LocalDateTime.now().plusDays(15));
            task.setCreationDate(java.time.LocalDateTime.now());
            taskDao.persist(task);
            TaskEntity task2 = new TaskEntity();
            task2.setTitle("get the ball rolling");
            task2.setDescription("get the ball rolling for the project");

            task2.setResponsibleUser(userBean.findUserByEmail("tozemarreco@gmail.com"));
            task2.setStatus(TaskEntity.Status.IN_PROGRESS);
            task2.setStartDate(java.time.LocalDateTime.now().minusDays(10));
            task2.setEndDate(java.time.LocalDateTime.now().plusDays(15));
            task2.setCreationDate(java.time.LocalDateTime.now().minusDays(11));
            task2.setCreatedBy(userBean.findUserByEmail("mariamacaca@gmail.com"));
            task2.setTaskUsers(new LinkedHashSet<>(List.of(userBean.findUserByEmail("mariamacaca@gmail.com"), userBean.findUserByEmail("tozemarreco@gmail.com"))));
            task2.setExternalExecutors("Canalizadores Lda");
            taskDao.persist(task2);
            TaskEntity task3 = new TaskEntity();
            task3.setTitle("do something else");
            task3.setDescription("do something else for the project");

            task3.setResponsibleUser(userBean.findUserByEmail("tozemarreco@gmail.com"));
            task3.setStatus(TaskEntity.Status.NOT_STARTED);
            task3.setStartDate(java.time.LocalDateTime.now().plusDays(16));
            task3.setEndDate(java.time.LocalDateTime.now().plusDays(30));
            task3.setCreationDate(java.time.LocalDateTime.now());
            task3.setCreatedBy(userBean.findUserByEmail("tozemarreco@gmail.com"));
            task3.setDependencies(new LinkedHashSet<>(List.of(task2)));
            task3.setTaskUsers(new LinkedHashSet<>(List.of(userBean.findUserByEmail("mariamacaca@gmail.com"))));
            taskDao.persist(task3);
            defaultProject.setTasks(new LinkedHashSet<>(List.of(task, task2, task3)));
            projectDao.merge(defaultProject);


            projectUserDao.persist(defaultProjectUser);
            projectUserDao.persist(defaultProjectUser2);
            projectUserDao.persist(defaultProjectUser3);
            ProjectResourceEntity projectResource = new ProjectResourceEntity();
            projectResource.setProject_id(defaultProject.getId());
            projectResource.setResource_id(resourceDao.findResourceByName("RAM").getId());
            projectResource.setQuantity(3);
            projectResourceDao.persist(projectResource);
            notificationBean.createNotification(new NotificationDto("INVITE",userBean.findUserByEmail("zetamplario@gmail.com").getId(), "UserInterface",  false, LocalDateTime.now()));
        }
        if(projectDao.findProjectByName("Project X") == null) {
            ProjectEntity defaultProject = new ProjectEntity();
            defaultProject.setName("Project X");
            defaultProject.setLab(labDao.findLabByLocation(LabEntity.Lab.Coimbra));
            List<SkillEntity> skills = skillDao.findSkillByType(SkillEntity.SkillType.TOOLS);
            if (skills != null) {
                defaultProject.setSkills(new LinkedHashSet<>(skills));
            }
            defaultProject.setStatus((ProjectEntity.Status.Planning));
            defaultProject.setDescription("Project X is a project that aims to create a new software that will revolutionize the way we interact with technology.");
            defaultProject.setMaxMembers(5);
            List<InterestEntity> interests = interestDao.findInterestByType(InterestEntity.InterestType.THEMES);
            if (interests != null) {
                defaultProject.setInterests(new LinkedHashSet<>(interests));
            }

            ProjectUserEntity defaultProjectUser = new ProjectUserEntity();
            defaultProjectUser.setProject(defaultProject);
            defaultProjectUser.setUser(userBean.findUserByEmail("zetamplario@gmail.com"));
            defaultProjectUser.setProjectManager(true);
            defaultProjectUser.setApprovalStatus(ProjectUserEntity.ApprovalStatus.MEMBER);
            ProjectUserEntity defaultProjectUser2 = new ProjectUserEntity();
            defaultProjectUser2.setProject(defaultProject);
            defaultProjectUser2.setUser(userBean.findUserByEmail("mariamacaca@gmail.com"));
            defaultProjectUser2.setProjectManager(false);
            defaultProjectUser2.setApprovalStatus(ProjectUserEntity.ApprovalStatus.APPLIED);
            ProjectUserEntity defaultProjectUser3 = new ProjectUserEntity();
            defaultProjectUser3.setProject(defaultProject);
            defaultProjectUser3.setUser(userBean.findUserByEmail("tozemarreco@gmail.com"));
            defaultProjectUser3.setProjectManager(false);
            defaultProjectUser3.setApprovalStatus(ProjectUserEntity.ApprovalStatus.INVITED);

            defaultProject.setProjectUsers(new LinkedHashSet<>(List.of(defaultProjectUser, defaultProjectUser2, defaultProjectUser3)));
            defaultProject.setStartDate(java.time.LocalDateTime.now().minusDays(5));
            defaultProject.setEndDate(java.time.LocalDateTime.now().plusDays(30));
            defaultProject.setCreatedAt(java.time.LocalDateTime.now());
            TaskEntity task = new TaskEntity();
            task.setTitle("Create the database");
            task.setDescription("Create the database for the project");
            task.setResponsibleUser(userBean.findUserByEmail("zetamplario@gmail.com"));
            task.setStatus(TaskEntity.Status.IN_PROGRESS);
            task.setStartDate(java.time.LocalDateTime.now().minusDays(5));
            task.setEndDate(java.time.LocalDateTime.now().plusDays(2));
            task.setCreationDate(java.time.LocalDateTime.now().minusDays(6));
            taskDao.persist(task);
            projectDao.persist(defaultProject);

            projectUserDao.persist(defaultProjectUser);
            projectUserDao.persist(defaultProjectUser2);
            projectUserDao.persist(defaultProjectUser3);
            ProjectResourceEntity projectResource = new ProjectResourceEntity();
            projectResource.setProject_id(defaultProject.getId());
            projectResource.setResource_id(resourceDao.findResourceByName("Windows 10 License").getId());
            projectResource.setQuantity(3);
            projectResourceDao.persist(projectResource);
            notificationBean.createNotification(new NotificationDto("INVITE",userBean.findUserByEmail("tozemarreco@gmail.com").getId(), "Project X",  false, LocalDateTime.now()));
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
        projectDto.setCreationDate(project.getCreatedAt());
        projectDto.setSkills(skillBean.entityToString(project.getSkills()));

        projectDto.setInterests(interestBean.entityToName(project.getInterests()));
        List<ProjectUserDto> teamMembers = new ArrayList<>();
        for (ProjectUserEntity projectUser : project.getProjectUsers()) {
            teamMembers.add(userBean.convertToProjectUserDto(projectUser));
        }
        projectDto.setTeamMembers(teamMembers);
        List<ProjectResourceEntity> resources = getProjectResources(project.getName());
        Set<ResourceEntity> resourceSet = project.getResources();
        List<ResourceDto> resourceDtos = new ArrayList<>();
        for (ResourceEntity resource : resourceSet) {
            ResourceDto resourceDto = resourceBean.convertToDto(resource);
            for (ProjectResourceEntity projectResource : resources) {
                if (projectResource.getResource_id() == resource.getId()) {
                    resourceDto.setQuantity(projectResource.getQuantity());
                }
            }
            resourceDtos.add(resourceDto);
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
    public List<ProjectUserDto> findTeamMembers(ProjectEntity project) {
        List<ProjectUserEntity> projectUsers = projectUserDao.findTeamMembers(project);
        List<ProjectUserDto> users = new ArrayList<>();
        for (ProjectUserEntity projectUser : projectUsers) {
            users.add(userBean.convertToProjectUserDto(projectUser));
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

    public boolean createProject(CreateProjectDto projectDto, String token) {
        System.out.println("Creating project");
        if (projectDao.findProjectByName(projectDto.getName()) != null) {
            return false;
        }

        ProjectEntity project = new ProjectEntity();
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setImage(projectDto.getImage());
        project.setStatus(ProjectEntity.Status.Planning);
        project.setLab(labDao.findLabByLocation(LabEntity.Lab.valueOf(projectDto.getLab())));
        TaskEntity lastTask = taskBean.createLastTask(token, projectDto, userBean.findUserByToken(token), List.of(userBean.findUserByToken(token).getId()));
        project.setTasks(new LinkedHashSet<>(List.of(lastTask)));
        project.setMaxMembers(projectDto.getSlots());
        project.setCreatedAt(java.time.LocalDateTime.now());
        if (projectDto.getStartDate() == null || projectDto.getEndDate() == null) {
            project.setStartDate(java.time.LocalDateTime.now());
            project.setEndDate(java.time.LocalDateTime.now().plusDays(30));
        }
        project.setStartDate(projectDto.getStartDate());
        project.setEndDate(projectDto.getEndDate());
        project.setSkills(skillBean.listDtoToEntity(new HashSet<>((projectDto.getSkills()))));
        project.setInterests(interestBean.listDtoToEntity(new HashSet<>(projectDto.getInterests())));

        projectDao.persist(project);
        if(projectDto.getTeamMembers().size() > project.getMaxMembers()){
            return false;
        }
            for (ProjectUserDto projectUserDto : projectDto.getTeamMembers()) {
                ProjectUserEntity projectUser = new ProjectUserEntity();
                projectUser.setProject(project);
                projectUser.setUser(UserDao.findUserById(projectUserDto.getUserId()));
                projectUser.setProjectManager(projectUserDto.getIsProjectManager());
                projectUser.setApprovalStatus(ProjectUserEntity.ApprovalStatus.valueOf(projectUserDto.getApprovalStatus()));
                projectUserDao.persist(projectUser);
                if(projectUserDto.getApprovalStatus().equals("INVITED")){
                   NotificationDto notificationDto = new NotificationDto();
                     notificationDto.setProjectName(project.getName());
                        notificationDto.setUserId(projectUserDto.getUserId());
                        notificationDto.setType("INVITE");
                        notificationDto.setRead(false);
                        if(notificationBean.createNotification(notificationDto)){
                            notificationBean.sendNotification(notificationDto);
                        }


                }
            }




        Set resourceSet = new LinkedHashSet<>();
        for (ResourceDto resourceDto : projectDto.getBillOfMaterials()) {
            ResourceEntity resource = resourceDao.findResourceByIdentifier(resourceDto.getIdentifier());
            ProjectResourceEntity projectResource = new ProjectResourceEntity();
            projectResource.setProject_id(project.getId());
            projectResource.setResource_id(resource.getId());
            projectResource.setQuantity(resourceDto.getQuantity());
            projectResourceDao.persist(projectResource);
            resourceSet.add(resource);
        }
        project.setResources(resourceSet);




        return true;
    }

    public List<String> findAllStatus() {
        List<String> status = new ArrayList<>();
        for (ProjectEntity.Status s : ProjectEntity.Status.values()) {
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
        if (projectUser != null) {
            return false;
        }
        projectUser = new ProjectUserEntity();
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
        if (projectUser != null) {
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
        UserEntity targetUser = operationType == OperationType.ACCEPT_INVITATION ? user : UserDao.findUserById(userId);

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
        projectUserDao.findProjectManagers(project).forEach(projectManager -> {
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setProjectName(project.getName());
            notificationDto.setUserId(projectManager.getUser().getId());
            notificationDto.setType("ACCEPT_APPLICATION");
            notificationDto.setRead(false);
            notificationDto.setOtherUserId(targetUser.getId());
            notificationBean.sendNotification(notificationDto);
        });
        return true;
    }


    public boolean rejectRequest(String token, String projectName, Integer userId, OperationType operationType) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        UserEntity targetUser = operationType == OperationType.ACCEPT_INVITATION ? user : UserDao.findUserById(userId);

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

        projectUserDao.remove(projectUser);
        projectUserDao.findProjectManagers(project).forEach(projectManager -> {
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setProjectName(project.getName());
            notificationDto.setUserId(projectManager.getUser().getId());
            notificationDto.setType("REJECT");
            notificationDto.setRead(false);
            notificationDto.setOtherUserId(targetUser.getId());
            notificationBean.sendNotification(notificationDto);
        });
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

    public String decodeProjectName(String projectName) {
        try {
            return java.net.URLDecoder.decode(projectName, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addSkillToProject(String token, int projectid, SkillEntity skill) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectById(projectid);
        if (user == null || project == null || skill == null) {
            return false;
        }
        project.getSkills().add(skill);
        projectDao.persist(project);
        return true;
    }

    public boolean removeSkillFromProject(String token, int projectid, SkillEntity skill) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectById(projectid);
        if (user == null || project == null || skill == null) {
            return false;
        }
        project.getSkills().remove(skill);
        projectDao.persist(project);
        return true;
    }
    public boolean addInterestToProject(String token, int projectid, InterestEntity interest) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectById(projectid);
        if (user == null || project == null || interest == null) {
            return false;
        }
        project.getInterests().add(interest);
        projectDao.persist(project);
        return true;
    }
    public boolean removeInterestFromProject(String token, int projectid, InterestEntity interest) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectById(projectid);
        if (user == null || project == null || interest == null) {
            return false;
        }
        project.getInterests().remove(interest);
        projectDao.persist(project);
        return true;
    }
    public ProjectStatistics getProjectStatistics() {
        ProjectStatistics projectStatistics = new ProjectStatistics();
        projectStatistics.setTotalProjects(projectDao.findAllProjects().size());
        projectStatistics.setProjectsByLab(projectDao.getProjectsByLab());
        projectStatistics.setTotalApprovedProjects(projectDao.getApprovedProjectsByLab());
        projectStatistics.setTotalCompletedProjects(projectDao.getCompletedProjectsByLab());
        projectStatistics.setTotalCancelledProjects(projectDao.getCancelledProjectsByLab());
        projectStatistics.setTotalPlanningProjects(projectDao.getPlanningProjectsByLab());
        projectStatistics.setTotalInProgressProjects(projectDao.getInProgressProjectsByLab());
        projectStatistics.setTotalReadyProjects(projectDao.getReadyProjectsByLab());
        projectStatistics.setAverageMembersPerProject(projectDao.getAverageMembersPerProject());
        projectStatistics.setAverageExecutionTime(projectDao.getAverageExecutionTime());
        return projectStatistics;
    }
    public ProjectTasksDto findProjectTasks(String projectName) {
        ProjectEntity project = projectDao.findProjectByName(projectName);
        if (project == null) {
            return null;
        }
        ProjectTasksDto projectTasksDto = new ProjectTasksDto();
        projectTasksDto.setProjectName(project.getName());
        Set<TaskDto> tasks = new HashSet<>();
        for (TaskEntity task : project.getTasks()) {
            tasks.add(taskBean.toTasktoDto(task));
        }
        projectTasksDto.setTasks(tasks);
        return projectTasksDto;
    }
    public void addTaskToProject( String projectName, TaskEntity task) {
        ProjectEntity project = projectDao.findProjectByName(projectName);
        if (project == null) {
            return;
        }
        project.getTasks().add(task);
        projectDao.persist(project);
    }
    public int convertStatus(String status){
        int newStatus;
        switch (status){
            case "Planning":
                newStatus = 100;
                break;
            case "Ready":
                newStatus = 200;
                break;
            case "Approved":
                newStatus = 300;
                break;
            case "In_Progress":
                newStatus = 400;
                break;
            case "Finished":
                newStatus = 500;
                break;
            case "Cancelled":
                newStatus = 0;
                break;
            default:
                newStatus = -1;
        }

      return newStatus;
    }
    public boolean updateProjectStatus(String token, String projectName, String status) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        ProjectUserEntity projectUser = projectUserDao.findProjectUserByProjectAndUser(projectDao.findProjectByName(projectName), user);
        int newStatus = convertStatus(status);
        if(newStatus == -1){
            return false;
        }
        if(newStatus == 300 && (user.getRole().getValue() > 1 || project.getStatus().getValue() != 200)){
            return false;
        }
        if (newStatus == 200 && (!projectUser.isProjectManager() || project.getStatus().getValue() != 100)) {
            return false;
        }
        if(newStatus == 0 && (user.getRole().getValue() > 1 || !projectUser.getApprovalStatus().equals("CREATOR"))){
            return false;
        }
        if(newStatus == 400 && (!projectUser.isProjectManager() || project.getStatus().getValue() != 300)){
            return false;
        }
        if(newStatus == 500 && (!projectUser.isProjectManager() || project.getStatus().getValue() != 400)){
            return false;
        }
        if(newStatus == 100 && (user.getRole().getValue() > 1 || project.getStatus().getValue() >= 200)){
            return false;
        }

        project.setStatus(ProjectEntity.Status.fromValue(newStatus));
        projectDao.persist(project);
        return true;
    }
    public boolean removeProjectUser(String token, String projectName, int userId) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        ProjectUserEntity projectUser = projectUserDao.findProjectUserByProjectAndUser(project, UserDao.findUserById(userId));
        if (user == null || project == null || projectUser == null) {
            return false;
        }
        projectUserDao.remove(projectUser);
        return true;
    }
    public boolean isProjectManager(String token, String projectName){
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        ProjectUserEntity projectUser = projectUserDao.findProjectUserByProjectAndUser(project, user);
        if (user == null || project == null || projectUser == null) {
            return false;
        }
        return projectUser.isProjectManager();
    }
    public List<ProjectResourceEntity> getProjectResources(String projectName){
        ProjectEntity project = projectDao.findProjectByName(projectName);
        if (project == null) {
            return null;
        }
        return projectResourceDao.findProjectResources(project.getId());
    }
    public List<ResourceDto> convertToDto(Set<ResourceEntity> resources, List<ProjectResourceEntity> projectResources){
        List<ResourceDto> resourceDtos = new ArrayList<>();
        for (ResourceEntity resource : resources) {
            resourceDtos.add(resourceBean.convertToDto(resource));
        }
        for (ProjectResourceEntity projectResource : projectResources) {
            ResourceDto resourceDto = resourceBean.convertToDto(resourceDao.findResourceById(projectResource.getResource_id()));
            resourceDto.setQuantity(projectResource.getQuantity());
            resourceDtos.add(resourceDto);
        }
        return resourceDtos;
    }
}