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
    ProjectLogBean projectLogBean;
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
    @Inject
    SystemVariablesBean systemVariablesBean;


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
            defaultProject.setMaxMembers(4);
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
            defaultProject.setStartDate(LocalDateTime.now().minusDays(5));
            defaultProject.setEndDate(LocalDateTime.now().plusDays(30));
            defaultProject.setCreatedAt(LocalDateTime.now());
            TaskEntity task = new TaskEntity();
            task.setTitle("Create the database");
            task.setDescription("Create the database for the project");
            task.setResponsibleUser(userBean.findUserByEmail("admin@admin.com"));
            task.setStatus(TaskEntity.Status.NOT_STARTED);
            task.setStartDate(LocalDateTime.now().minusDays(5));
            task.setEndDate(LocalDateTime.now().plusDays(30));
            task.setCreationDate(LocalDateTime.now());
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
            projectResource.setProject(defaultProject);
            projectResource.setResource(resourceDao.findResourceByName("CPU"));
            projectResource.setQuantity(5);
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
            defaultProject.setMaxMembers(4);
            List<InterestEntity> interests = interestDao.findInterestByType(InterestEntity.InterestType.CAUSES);
            if (interests != null) {
                defaultProject.setInterests(new LinkedHashSet<>(interests));
            }

            ProjectUserEntity defaultProjectUser = new ProjectUserEntity();
            defaultProjectUser.setProject(defaultProject);
            defaultProjectUser.setUser(userBean.findUserByEmail("tozemarreco@gmail.com"));
            defaultProjectUser.setProjectManager(true);
            defaultProjectUser.setApprovalStatus(ProjectUserEntity.ApprovalStatus.CREATOR);
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
            defaultProject.setStartDate(LocalDateTime.now().minusDays(10));
            defaultProject.setEndDate(LocalDateTime.now().plusDays(15));
            defaultProject.setCreatedAt(LocalDateTime.now().minusDays(15));
            projectDao.persist(defaultProject);
            TaskEntity task = new TaskEntity();
            task.setTitle("Create the UserInterface");
            task.setDescription("Create the UserInterface for the project");

            task.setResponsibleUser(userBean.findUserByEmail("mariamacaca@gmail.com"));
            task.setStatus(TaskEntity.Status.NOT_STARTED);
            task.setStartDate(LocalDateTime.now().minusDays(10));
            task.setEndDate(LocalDateTime.now().plusDays(15));
            task.setCreationDate(LocalDateTime.now());
            taskDao.persist(task);
            TaskEntity task2 = new TaskEntity();
            task2.setTitle("get the ball rolling");
            task2.setDescription("get the ball rolling for the project");

            task2.setResponsibleUser(userBean.findUserByEmail("tozemarreco@gmail.com"));
            task2.setStatus(TaskEntity.Status.IN_PROGRESS);
            task2.setStartDate(LocalDateTime.now().minusDays(10));
            task2.setEndDate(LocalDateTime.now().plusDays(15));
            task2.setCreationDate(LocalDateTime.now().minusDays(11));
            task2.setCreatedBy(userBean.findUserByEmail("mariamacaca@gmail.com"));
            task2.setTaskUsers(new LinkedHashSet<>(List.of(userBean.findUserByEmail("mariamacaca@gmail.com"), userBean.findUserByEmail("tozemarreco@gmail.com"))));
            task2.setExternalExecutors("Canalizadores Lda");
            taskDao.persist(task2);
            TaskEntity task3 = new TaskEntity();
            task3.setTitle("do something else");
            task3.setDescription("do something else for the project");

            task3.setResponsibleUser(userBean.findUserByEmail("tozemarreco@gmail.com"));
            task3.setStatus(TaskEntity.Status.NOT_STARTED);
            task3.setStartDate(LocalDateTime.now().plusDays(16));
            task3.setEndDate(LocalDateTime.now().plusDays(30));
            task3.setCreationDate(LocalDateTime.now());
            task3.setCreatedBy(userBean.findUserByEmail("tozemarreco@gmail.com"));
            task3.setDependencies(new LinkedHashSet<>(List.of(task2)));
            task3.setTaskUsers(new LinkedHashSet<>(List.of(userBean.findUserByEmail("mariamacaca@gmail.com"))));
            taskDao.persist(task3);
            TaskEntity finaltask = new TaskEntity();
            finaltask.setTitle("Final Presentation");
            finaltask.setDescription("Final presentation of the finalized project");

            finaltask.setResponsibleUser(userBean.findUserById(defaultProjectUser.getUser().getId()));
            finaltask.setStatus(TaskEntity.Status.NOT_STARTED);
            finaltask.setStartDate(defaultProject.getEndDate().minusDays(1));
            finaltask.setEndDate(defaultProject.getEndDate());
            finaltask.setCreationDate(LocalDateTime.now());
            finaltask.setCreatedBy(userBean.findUserById(1));
            taskDao.persist(finaltask);
            defaultProject.setTasks(new LinkedHashSet<>(List.of(task, task2, task3, finaltask)));
            projectDao.merge(defaultProject);


            projectUserDao.persist(defaultProjectUser);
            projectUserDao.persist(defaultProjectUser2);
            projectUserDao.persist(defaultProjectUser3);
            ProjectResourceEntity projectResource = new ProjectResourceEntity();
            projectResource.setProject(defaultProject);
            projectResource.setResource(resourceDao.findResourceByName("RAM"));
            projectResource.setQuantity(4);
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
            defaultProject.setMaxMembers(4);
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
            defaultProject.setStartDate(LocalDateTime.now().minusDays(5));
            defaultProject.setEndDate(LocalDateTime.now().plusDays(30));
            defaultProject.setCreatedAt(LocalDateTime.now());
            TaskEntity task = new TaskEntity();
            task.setTitle("Create the database");
            task.setDescription("Create the database for the project");
            task.setResponsibleUser(userBean.findUserByEmail("zetamplario@gmail.com"));
            task.setStatus(TaskEntity.Status.IN_PROGRESS);
            task.setStartDate(LocalDateTime.now().minusDays(5));
            task.setEndDate(LocalDateTime.now().plusDays(2));
            task.setCreationDate(LocalDateTime.now().minusDays(6));
            taskDao.persist(task);
            TaskEntity finaltask = new TaskEntity();
            finaltask.setTitle("Final Presentation");
            finaltask.setDescription("Final presentation of the finalized project");

            finaltask.setResponsibleUser(userBean.findUserById(defaultProjectUser.getUser().getId()));
            finaltask.setStatus(TaskEntity.Status.NOT_STARTED);
            finaltask.setStartDate(defaultProject.getEndDate().minusDays(1));
            finaltask.setEndDate(defaultProject.getEndDate());
            finaltask.setCreationDate(LocalDateTime.now());
            finaltask.setCreatedBy(userBean.findUserById(1));
            taskDao.persist(finaltask);
            defaultProject.setTasks(new LinkedHashSet<>(List.of(task, finaltask)));
            projectDao.persist(defaultProject);

            projectUserDao.persist(defaultProjectUser);
            projectUserDao.persist(defaultProjectUser2);
            projectUserDao.persist(defaultProjectUser3);
            ProjectResourceEntity projectResource = new ProjectResourceEntity();
            projectResource.setProject(defaultProject);
            projectResource.setResource(resourceDao.findResourceByName("Windows 10 License"));
            projectResource.setQuantity(3);
            projectResourceDao.persist(projectResource);
            notificationBean.createNotification(new NotificationDto("INVITE",userBean.findUserByEmail("tozemarreco@gmail.com").getId(), "Project X",  false, LocalDateTime.now()));
        }
        if(projectDao.findProjectByName("Build Deathstar") == null) {
            ProjectEntity defaultProject = new ProjectEntity();
            defaultProject.setName("Build Deathstar");
            defaultProject.setLab(labDao.findLabByLocation(LabEntity.Lab.Coimbra));
            List<SkillEntity> skills = skillDao.findSkillByType(SkillEntity.SkillType.TOOLS);
            if (skills != null) {
                defaultProject.setSkills(new LinkedHashSet<>(skills));
            }
            defaultProject.setStatus((ProjectEntity.Status.Planning));
            defaultProject.setDescription("Build Deathstar is a project that aims to create a new and fully operational Battlestation.");
            defaultProject.setMaxMembers(4);
            List<InterestEntity> interests = interestDao.findInterestByType(InterestEntity.InterestType.THEMES);
            if (interests != null) {
                defaultProject.setInterests(new LinkedHashSet<>(interests));
            }

            ProjectUserEntity defaultProjectUser = new ProjectUserEntity();
            defaultProjectUser.setProject(defaultProject);
            defaultProjectUser.setUser(userBean.findUserByEmail("palpatine@gmail.com"));
            defaultProjectUser.setProjectManager(true);
            defaultProjectUser.setApprovalStatus(ProjectUserEntity.ApprovalStatus.CREATOR);
            ProjectUserEntity defaultProjectUser2 = new ProjectUserEntity();
            defaultProjectUser2.setProject(defaultProject);
            defaultProjectUser2.setUser(userBean.findUserByEmail("darthvader@gmail.com"));
            defaultProjectUser2.setProjectManager(false);
            defaultProjectUser2.setApprovalStatus(ProjectUserEntity.ApprovalStatus.MEMBER);
            defaultProject.setCreatedAt(LocalDateTime.now());
            defaultProject.setStartDate(LocalDateTime.now().minusDays(5));
            defaultProject.setEndDate(LocalDateTime.now().plusDays(30));
            projectDao.persist(defaultProject);
            TaskEntity task = new TaskEntity();
            task.setTitle("Create the Deathstar");
            task.setDescription("Create the Deathstar for the project");
            task.setResponsibleUser(userBean.findUserByEmail("palpatine@gmail.com"));
            task.setStatus(TaskEntity.Status.IN_PROGRESS);
            task.setStartDate(LocalDateTime.now().minusDays(5));
            task.setEndDate(LocalDateTime.now().plusDays(30));
            task.setCreationDate(LocalDateTime.now());
            taskDao.persist(task);
            TaskEntity finaltask = new TaskEntity();
            finaltask.setTitle("Final Presentation");
            finaltask.setDescription("Final presentation of the finalized project");
            finaltask.setResponsibleUser(userBean.findUserById(defaultProjectUser.getUser().getId()));
            finaltask.setStatus(TaskEntity.Status.NOT_STARTED);
            finaltask.setStartDate(defaultProject.getEndDate().minusDays(1));
            finaltask.setEndDate(defaultProject.getEndDate());
            finaltask.setCreationDate(LocalDateTime.now());
            finaltask.setCreatedBy(userBean.findUserById(5));
            taskDao.persist(finaltask);
            defaultProject.setTasks(new LinkedHashSet<>(List.of(task, finaltask)));
            projectDao.merge(defaultProject);
            projectUserDao.persist(defaultProjectUser);
            projectUserDao.persist(defaultProjectUser2);
            ProjectResourceEntity projectResource = new ProjectResourceEntity();
            projectResource.setProject(defaultProject);
            projectResource.setResource(resourceDao.findResourceByIdentifier("L15-001"));
            projectResource.setQuantity(2);
            ProjectResourceEntity projectResource2 = new ProjectResourceEntity();
            projectResource2.setProject(defaultProject);
            projectResource2.setResource(resourceDao.findResourceByIdentifier("D17-001"));
            projectResource2.setQuantity(1);
            projectResourceDao.persist(projectResource2);
            projectResourceDao.persist(projectResource);
            notificationBean.createNotification(new NotificationDto("INVITE", userBean.findUserByEmail("luke@gmail.com").getId(), "Build Deathstar", false, LocalDateTime.now()));
        }
        if(projectDao.findProjectByName("Yavin 4") == null){
            ProjectEntity defaultProject = new ProjectEntity();
            defaultProject.setName("Yavin 4");
            defaultProject.setLab(labDao.findLabByLocation(LabEntity.Lab.Coimbra));
            List<SkillEntity> skills = skillDao.findSkillByType(SkillEntity.SkillType.TOOLS);
            if (skills != null) {
                defaultProject.setSkills(new LinkedHashSet<>(skills));
            }
            defaultProject.setStatus((ProjectEntity.Status.Planning));
            defaultProject.setDescription("Yavin 4 is a project that aims to create a new and fully operational Battlestation.");
            defaultProject.setMaxMembers(4);
            List<InterestEntity> interests = interestDao.findInterestByType(InterestEntity.InterestType.THEMES);
            if (interests != null) {
                defaultProject.setInterests(new LinkedHashSet<>(interests));
            }

            ProjectUserEntity defaultProjectUser = new ProjectUserEntity();
            defaultProjectUser.setProject(defaultProject);
            defaultProjectUser.setUser(userBean.findUserByEmail("princessleia@gmail.com"));
            defaultProjectUser.setProjectManager(true);
            defaultProjectUser.setApprovalStatus(ProjectUserEntity.ApprovalStatus.CREATOR);
            ProjectUserEntity defaultProjectUser2 = new ProjectUserEntity();
            defaultProjectUser2.setProject(defaultProject);
            defaultProjectUser2.setUser(userBean.findUserByEmail("luke@gmail.com"));
            defaultProjectUser2.setProjectManager(false);
            defaultProjectUser2.setApprovalStatus(ProjectUserEntity.ApprovalStatus.MEMBER);
            ProjectUserEntity defaultProjectUser3 = new ProjectUserEntity();
            defaultProjectUser3.setProject(defaultProject);
            defaultProjectUser3.setUser(userBean.findUserByEmail("obiwan@gmail.com"));
            defaultProjectUser3.setProjectManager(false);
            defaultProjectUser3.setApprovalStatus(ProjectUserEntity.ApprovalStatus.MEMBER);
            defaultProject.setProjectUsers(new LinkedHashSet<>(List.of(defaultProjectUser, defaultProjectUser2, defaultProjectUser3)));
            defaultProject.setCreatedAt(LocalDateTime.now());
            defaultProject.setStartDate(LocalDateTime.now().minusDays(1));
            defaultProject.setEndDate(LocalDateTime.now().plusDays(40));
            projectDao.persist(defaultProject);
            projectUserDao.persist(defaultProjectUser);
            projectUserDao.persist(defaultProjectUser2);
            projectUserDao.persist(defaultProjectUser3);
            TaskEntity task = new TaskEntity();
            task.setTitle("Defend the base");
            task.setDescription("Defend the base from the Empire");
            task.setResponsibleUser(userBean.findUserByEmail("obiwan@gmail.com"));
            task.setStatus(TaskEntity.Status.IN_PROGRESS);
            task.setStartDate(LocalDateTime.now().minusDays(1));
            task.setEndDate(LocalDateTime.now().plusDays(30));
            task.setCreatedBy(userBean.findUserByEmail("obiwan@gmail.com"));
            task.setCreationDate(LocalDateTime.now());
            taskDao.persist(task);
            TaskEntity task2 = new TaskEntity();
            task2.setTitle("Destroy the Deathstar");
            task2.setDescription("Destroy the Deathstar");
            task2.setResponsibleUser(userBean.findUserByEmail("luke@gmail.com"));
            task2.setStatus(TaskEntity.Status.NOT_STARTED);
            task2.setStartDate(LocalDateTime.now().plusDays(31));
            task2.setEndDate(LocalDateTime.now().plusDays(40));
            task2.setCreationDate(LocalDateTime.now());
            task2.setCreatedBy(userBean.findUserByEmail("princessleia@gmail.com"));
            task2.setDependencies(new LinkedHashSet<>(List.of(task)));
            taskDao.persist(task2);
            TaskEntity finaltask = new TaskEntity();
            finaltask.setTitle("Final Presentation");
            finaltask.setDescription("Final presentation of the finalized project");
            finaltask.setResponsibleUser(userBean.findUserByEmail("princessleia@gmail.com"));
            finaltask.setStatus(TaskEntity.Status.NOT_STARTED);
            finaltask.setStartDate(defaultProject.getEndDate().minusDays(1));
            finaltask.setEndDate(defaultProject.getEndDate());
            finaltask.setCreationDate(LocalDateTime.now());
            finaltask.setCreatedBy(userBean.findUserByEmail("luke@gmail.com"));
            taskDao.persist(finaltask);
            defaultProject.setTasks(new LinkedHashSet<>(List.of(task, task2, finaltask)));
            projectDao.merge(defaultProject);
            ProjectResourceEntity projectResource = new ProjectResourceEntity();
            projectResource.setProject(defaultProject);
            projectResource.setResource(resourceDao.findResourceByIdentifier("L12-001"));
            projectResource.setQuantity(2);
            projectResourceDao.persist(projectResource);
            ProjectResourceEntity projectResource2 = new ProjectResourceEntity();
            projectResource2.setResource(resourceDao.findResourceByIdentifier("XW-001"));
            projectResource2.setQuantity(1);
            projectResource2.setProject(defaultProject);
            projectResourceDao.persist(projectResource2);
            notificationBean.createNotification(new NotificationDto("INVITE", userBean.findUserByEmail("hansolo@gmail.com").getId(), "Yavin 4", false, LocalDateTime.now()));

        }
        if(projectDao.findProjectByName("Kessel Run") == null) {
            ProjectEntity defaultProject = new ProjectEntity();
            defaultProject.setName("Kessel Run");
            defaultProject.setLab(labDao.findLabByLocation(LabEntity.Lab.Coimbra));
            List<SkillEntity> skills = skillDao.findSkillByType(SkillEntity.SkillType.TOOLS);
            if (skills != null) {
                defaultProject.setSkills(new LinkedHashSet<>(skills));
            }
            defaultProject.setStatus((ProjectEntity.Status.Planning));
            defaultProject.setDescription("Kessel Run is a spice smuggling job that will take you to the edge of the galaxy.");
            defaultProject.setMaxMembers(4);
            List<InterestEntity> interests = interestDao.findInterestByType(InterestEntity.InterestType.THEMES);
            if (interests != null) {
                defaultProject.setInterests(new LinkedHashSet<>(interests));
            }

            ProjectUserEntity defaultProjectUser = new ProjectUserEntity();
            defaultProjectUser.setProject(defaultProject);
            defaultProjectUser.setUser(userBean.findUserByEmail("hansolo@gmail.com"));
            defaultProjectUser.setProjectManager(true);
            defaultProjectUser.setApprovalStatus(ProjectUserEntity.ApprovalStatus.CREATOR);
            ProjectUserEntity defaultProjectUser2 = new ProjectUserEntity();
            defaultProjectUser2.setProject(defaultProject);
            defaultProjectUser2.setUser(userBean.findUserByEmail("chewbacca@gmail.com"));
            defaultProjectUser2.setProjectManager(false);
            defaultProjectUser2.setApprovalStatus(ProjectUserEntity.ApprovalStatus.MEMBER);
            ProjectUserEntity defaultProjectUser3 = new ProjectUserEntity();
            defaultProjectUser3.setProject(defaultProject);
            defaultProjectUser3.setUser(userBean.findUserByEmail("lando@gmail.com"));
            defaultProjectUser3.setProjectManager(false);
            defaultProjectUser3.setApprovalStatus(ProjectUserEntity.ApprovalStatus.MEMBER);

            defaultProject.setProjectUsers(new LinkedHashSet<>(List.of(defaultProjectUser, defaultProjectUser2, defaultProjectUser3)));
            defaultProject.setCreatedAt(LocalDateTime.now());
            defaultProject.setStartDate(LocalDateTime.now().minusDays(3));
            defaultProject.setEndDate(LocalDateTime.now().plusDays(10));
            projectDao.persist(defaultProject);
            projectUserDao.persist(defaultProjectUser);
            projectUserDao.persist(defaultProjectUser2);
            projectUserDao.persist(defaultProjectUser3);
            TaskEntity task = new TaskEntity();
            task.setTitle("Smuggle the spice");
            task.setDescription("Smuggle the spice from Kessel to Tatooine");
            task.setResponsibleUser(userBean.findUserByEmail("lando@gamil.com"));
            task.setStatus(TaskEntity.Status.IN_PROGRESS);
            task.setStartDate(LocalDateTime.now().minusDays(3));
            task.setEndDate(LocalDateTime.now().plusDays(9));
            task.setCreationDate(LocalDateTime.now());
            taskDao.persist(task);
            TaskEntity finaltask = new TaskEntity();
            finaltask.setTitle("Final Presentation");
            finaltask.setDescription("Final presentation of the finalized project");
            finaltask.setResponsibleUser(userBean.findUserById(defaultProjectUser.getUser().getId()));
            finaltask.setStatus(TaskEntity.Status.NOT_STARTED);
            finaltask.setStartDate(defaultProject.getEndDate().minusDays(1));
            finaltask.setEndDate(defaultProject.getEndDate());
            finaltask.setCreationDate(LocalDateTime.now());
            finaltask.setCreatedBy(userBean.findUserById(defaultProjectUser.getUser().getId()));
            taskDao.persist(finaltask);
            TaskEntity task2 = new TaskEntity();
            task2.setTitle("Avoid the Empire");
            task2.setDescription("Avoid the Empire while smuggling the spice");
            task2.setResponsibleUser(userBean.findUserByEmail("chewbacca@gmail.com"));
            task2.setStatus(TaskEntity.Status.NOT_STARTED);
            task2.setStartDate(LocalDateTime.now().plusDays(5));
            task2.setEndDate(LocalDateTime.now().plusDays(10));
            task2.setCreationDate(LocalDateTime.now());
            task2.setCreatedBy(userBean.findUserById(defaultProjectUser.getUser().getId()));
            task2.setDependencies(new LinkedHashSet<>(List.of(task)));
            task2.setTaskUsers(new LinkedHashSet<>(List.of(userBean.findUserByEmail("lando@gmail.com"))));
            taskDao.persist(task2);
            defaultProject.setTasks(new LinkedHashSet<>(List.of(task, task2, finaltask)));
            projectDao.merge(defaultProject);
            ProjectResourceEntity projectResource = new ProjectResourceEntity();
            projectResource.setProject(defaultProject);
            projectResource.setResource(resourceDao.findResourceByIdentifier("YT-1700"));
            projectResource.setQuantity(1);
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
        projectDto.setCreationDate(project.getCreatedAt());
        Set<SkillDto> skill = new HashSet<>();
        for (SkillEntity s : project.getSkills()) {
            skill.add(skillBean.toSkillDtos(s));
        }
        projectDto.setSkills(skill);

        Set<InterestDto> interest = new HashSet<>();
        for (InterestEntity i : project.getInterests()) {
            interest.add(interestBean.toInterestDto(i));
        }
        projectDto.setInterests(interest);
        List<ProjectUserDto> teamMembers = new ArrayList<>();
        for (ProjectUserEntity projectUser : project.getProjectUsers()) {
            teamMembers.add(userBean.convertToProjectUserDto(projectUser));
        }
        projectDto.setTeamMembers(teamMembers);
        List<ProjectResourceEntity> resources = getProjectResources(project.getName());
        List<ResourceDto> billOfMaterials = new ArrayList<>();
            for (ProjectResourceEntity projectResource : resources) {
                ResourceDto resourceDto = resourceBean.convertToDto(projectResource.getResource());
                resourceDto.setQuantity(projectResource.getQuantity());
                billOfMaterials.add(resourceDto);
            }
        projectDto.setBillOfMaterials(billOfMaterials);
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
        if(projectDto.getName() == null || projectDto.getName().equals("")){
            return false;
        }
        project.setName(projectDto.getName());
        if(projectDto.getDescription() == null || projectDto.getDescription().equals("")){
            return false;
        }
        project.setDescription(projectDto.getDescription());
        project.setImage(projectDto.getImage());
        project.setStatus(ProjectEntity.Status.Planning);
        if(projectDto.getLab() == null || projectDto.getLab().equals("")){
            UserEntity user = userBean.findUserByToken(token);
            projectDto.setLab(user.getLocation().toString());
        }
        project.setLab(labDao.findLabByLocation(LabEntity.Lab.valueOf(projectDto.getLab())));
        TaskEntity lastTask = taskBean.createLastTask(token, projectDto, userBean.findUserByToken(token), List.of(userBean.findUserByToken(token).getId()));
        project.setTasks(new LinkedHashSet<>(List.of(lastTask)));
        if(projectDto.getSlots() == 0){
            projectDto.setSlots(4);
        }
        project.setMaxMembers(projectDto.getSlots());
        project.setCreatedAt(LocalDateTime.now());
        if (projectDto.getStartDate() == null ) {
            project.setStartDate(LocalDateTime.now());
        }else {
            project.setStartDate(projectDto.getStartDate());
        }
        if(projectDto.getEndDate() == null || projectDto.getEndDate().isBefore(LocalDateTime.now())) { // Correct usage
            project.setEndDate(project.getStartDate().plusDays(30));
        }else{
            project.setEndDate(projectDto.getEndDate());
        }
        project.setSkills(skillBean.listDtoToEntity(new HashSet<>((projectDto.getSkills()))));
        project.setInterests(interestBean.listDtoToEntity(new HashSet<>(projectDto.getInterests())));
        projectDao.persist(project);
        if(projectDto.getTeamMembers() != null){
            for (ProjectUserDto projectUserDto : projectDto.getTeamMembers()) {
                ProjectUserEntity projectUser = new ProjectUserEntity();
                projectUser.setProject(project);
                projectUser.setUser(UserDao.findUserById(projectUserDto.getUserId()));
                projectUser.setProjectManager(projectUserDto.getIsProjectManager());
                projectUser.setApprovalStatus(ProjectUserEntity.ApprovalStatus.valueOf(projectUserDto.getApprovalStatus()));
                projectUserDao.persist(projectUser);
                if(projectUserDto.getApprovalStatus().equals("INVITED")) {
                    NotificationDto notificationDto = new NotificationDto();
                    notificationDto.setProjectName(project.getName());
                    notificationDto.setUserId(projectUserDto.getUserId());
                    notificationDto.setType("INVITE");
                    notificationDto.setRead(false);
                    notificationBean.sendNotification(notificationDto);

                    }
                }
            }
        for (ResourceDto resourceDto : projectDto.getBillOfMaterials()) {
            ResourceEntity resource = resourceDao.findResourceByIdentifier(resourceDto.getIdentifier());
            ProjectResourceEntity projectResource = new ProjectResourceEntity();
            projectResource.setProject(project);
            projectResource.setResource(resource);
            projectResource.setQuantity(resourceDto.getQuantity());
            projectResourceDao.persist(projectResource);
        }
        projectDao.merge(project);
        ProjectLogDto projectLogDto = new ProjectLogDto(userBean.findUserByToken(token), project, "Project created");
        projectLogDto.setType("PROJECT_CREATED");
        projectLogBean.createProjectLog(projectLogDto);
        if(project.getMaxMembers()> systemVariablesBean.getMaxUsers()){
            project.setMaxMembers(systemVariablesBean.getMaxUsers());
            projectDao.persist(project);
            NotificationDto notificationDto = new NotificationDto("PROJECT_USERS_EXCEEDED", userBean.findUserByToken(token).getId(), project.getName(), false, LocalDateTime.now());
            if(notificationBean.createNotification(notificationDto)){
                notificationBean.sendNotification(notificationDto);
            }
        }
        return true;
    }
    public boolean updateProject(UpdateProjectDto projectDto,String projectName){
        ProjectEntity project = projectDao.findProjectByName(projectName);
        if(project == null){
            return false;
        }
        if(projectDto.getLab()!= null){
            project.setLab(labDao.findLabByLocation(LabEntity.Lab.valueOf(projectDto.getLab())));
        }
        if(projectDto.getDescription() != null){
            project.setDescription(projectDto.getDescription());
        }
        projectDao.persist(project);
        return true;
    }

    public List<String> findAllStatus() {
        List<String> status = new ArrayList<>();
        for (ProjectEntity.Status s : ProjectEntity.Status.values()) {
            status.add(s.name());
        }
        return status;
    }
    public ProjectUserEntity findUserByTokenAndProject(String projectName, String token) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        return projectUserDao.findProjectUserByProjectAndUser(project, user);
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
        ProjectLogDto projectLogDto = new ProjectLogDto(user, project, "User " + user.getFirstName() + " applied to project");
        projectLogDto.setType("APPLY_USER");
        projectLogBean.createProjectLog(projectLogDto);
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
        projectUser = new ProjectUserEntity();
        projectUser.setProject(project);
        projectUser.setUser(invitedUser);
        projectUser.setApprovalStatus(ProjectUserEntity.ApprovalStatus.INVITED);
        projectUser.setProjectManager(false);
        projectUserDao.persist(projectUser);
        ProjectLogDto projectLogDto = new ProjectLogDto(user, project, "User " + invitedUser.getFirstName() + " invited to project");
        projectLogDto.setType("INVITE_USER");
        projectLogDto.setOtherUserId(userId);
        projectLogBean.createProjectLog(projectLogDto);
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
        ProjectLogDto projectLogDto = new ProjectLogDto(targetUser, project, "User " + targetUser.getFirstName() + " accepted invitation to project");
        projectLogDto.setType("ACCEPT_USER");
        projectLogBean.createProjectLog(projectLogDto);
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
        ProjectLogDto projectLogDto = new ProjectLogDto(targetUser, project, "User " + targetUser.getFirstName() + " rejected invitation from project");
        projectLogDto.setType("REJECT_USER");
        projectLogBean.createProjectLog(projectLogDto);
        return true;
    }
public boolean userBelongsToProject(String token, String projectName) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        if (user == null || project == null) {
            return false;
        }
        ProjectUserEntity projectUser = projectUserDao.findProjectUserByProjectAndUser(project, user);
        return projectUser != null;
    }
    public boolean leaveProject(String token, String projectName) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        ProjectUserEntity projectUser = projectUserDao.findProjectUserByProjectAndUser(project, user);
        if (user == null || project == null || projectUser == null) {
            return false;
        }
        List <TaskEntity> tasks = taskDao.findTasksByResponsibleUser(user);
        List<TaskEntity> filteredTasks = new ArrayList<>();
        for (TaskEntity task : tasks) {
            if (project.getTasks().contains(task)) {
                filteredTasks.add(task);
            }
        }
        if(filteredTasks != null) {
            for (TaskEntity task : filteredTasks) {
                ProjectEntity projectTask = projectDao.findProjectByTask(task);
                UserEntity projectTaskCreator = projectUserDao.findProjectCreator(projectTask).getUser();
                task.setResponsibleUser(projectUserDao.findProjectCreator(project).getUser());
                taskDao.persist(task);
                notificationBean.sendNotification(new NotificationDto("TASK_ASSIGN", projectTaskCreator.getId(), projectTask.getName(), false, LocalDateTime.now()));
                ProjectLogDto projectLogDto = new ProjectLogDto(projectTaskCreator, projectTask, "User " + user.getFirstName() + " left project, task reassigned to project creator");
                projectLogDto.setType("UPDATE_TASK");
                projectLogDto.setTaskId(task.getId());
                projectLogDto.setOtherUserId(user.getId());
                projectLogBean.createProjectLog(projectLogDto);

            }
        }
        projectUserDao.remove(projectUser);
        ProjectLogDto projectLogDto = new ProjectLogDto(user, project, "User " + user.getFirstName() + " left project");
        projectLogDto.setType("USER_LEFT");
        projectLogBean.createProjectLog(projectLogDto);
        notificationBean.sendNotification(new NotificationDto("USER_LEFT", projectUserDao.findProjectCreator(project).getUser().getId(), projectName, false, LocalDateTime.now()));
        return true;
    }
 public UserDto findProjectCreatorByTask(TaskEntity task) {
        ProjectEntity project = projectDao.findProjectByTask(task);
        ProjectUserEntity projectUser = projectUserDao.findProjectCreator(project);
        return userBean.convertToDto(projectUser.getUser());

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
        ProjectLogDto projectLogDto = new ProjectLogDto(userBean.findUserById(userId), project, "User " + userBean.findUserById(userId).getFirstName() + " promoted to project manager");
        projectLogDto.setType("PROMOTE_USER");
        projectLogDto.setOtherUserId(userId);
        projectLogBean.createProjectLog(projectLogDto);
        projectUserDao.merge(projectUser);
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
        ProjectLogDto projectLogDto = new ProjectLogDto(userBean.findUserById(userId), project, "User " + userBean.findUserById(userId).getFirstName() + " demoted from project manager");
        projectLogDto.setType("DEMOTE_USER");
        projectLogDto.setOtherUserId(userId);
        projectLogBean.createProjectLog(projectLogDto);
        projectUserDao.persist(projectUser);
        return true;
    }

    public boolean addResourceToProject(String token, String projectName, int resourceId, int quantity) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        ResourceEntity resource = resourceDao.findResourceById(resourceId);
        if (user == null || project == null || resource == null) {
            return false;
        }
        ProjectResourceEntity projectResource = new ProjectResourceEntity();
        projectResource.setProject(project);
        projectResource.setResource(resource);
        projectResource.setQuantity(quantity);
        projectResourceDao.persist(projectResource);
        return true;
    }
    public boolean removeResourceFromProject(String token, String projectName, int resourceId) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        ResourceEntity resource = resourceDao.findResourceById(resourceId);
        if (user == null || project == null || resource == null) {
            return false;
        }
        ProjectResourceEntity projectResource = projectResourceDao.findByProjectAndResource(project, resource);
        projectResourceDao.remove(projectResource);
        return true;
    }
    public boolean updateResourceQuantity(String token, String projectName, int resourceId, int quantity) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        ResourceEntity resource = resourceDao.findResourceById(resourceId);
        if (user == null || project == null || resource == null || quantity <= 0) {
            return false;
        }
        ProjectResourceEntity projectResource = projectResourceDao.findByProjectAndResource(project, resource);
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

    public boolean addSkillToProject(String token, String projectName, SkillEntity skill) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        if (user == null || project == null || skill == null) {
            return false;
        }

        project.getSkills().add(skill);
        skill.getProjects().add(project);
        skillDao.merge(skill);
        projectDao.merge(project);
        return true;
    }

    public boolean removeSkillFromProject(String token, String projectName, SkillEntity skill) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        if (user == null || project == null || skill == null) {
            return false;
        }
        skill.getProjects().remove(project);
        project.getSkills().remove(skill);
        skillDao.merge(skill);
        projectDao.merge(project);
        return true;
        }

    public boolean addInterestToProject(String token, String projectName, InterestEntity interest) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        if (user == null || project == null || interest == null) {
            return false;
        }
        project.getInterests().add(interest);
        interest.getProjects().add(project);
        interestDao.merge(interest);
        projectDao.merge(project);
        return true;
    }
    public boolean removeInterestFromProject(String token, String projectName, InterestEntity interest) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        if (user == null || project == null || interest == null) {
            return false;
        }
        project.getInterests().remove(interest);
        interest.getProjects().remove(project);
        interestDao.merge(interest);
        projectDao.merge(project);
        return true;
    }
    public HashMap<String,HashMap<String,Integer>> getResourceQuantitiesByLab(){
        List<LabEntity> labs = labDao.findAllLabs();
        List<ProjectEntity> projects = projectDao.findAllProjects();
        HashMap<String,HashMap<String,Integer>> resources = new HashMap<>();
        for(LabEntity lab : labs){
            HashMap<String,Integer> resourceQuantities = new HashMap<>();
            for(ProjectEntity project : projects){
                if(project.getLab().equals(lab)){
                    for(ResourceEntity resource : project.getResources()){
                        ProjectResourceEntity projectResource = projectResourceDao.findByProjectAndResource(project,resource);
                        if(resourceQuantities.containsKey(resource.getName())){
                            resourceQuantities.put(resource.getName(),resourceQuantities.get(resource.getName()) + projectResource.getQuantity());
                        }else{
                            resourceQuantities.put(resource.getName(),projectResource.getQuantity());
                        }
                    }
                }
            }
            resources.put(lab.getLocation().name(),resourceQuantities);
        }
        return resources;
    }
    public HashMap<String,HashMap<String,Integer>> resourceQuantitiesByProject(){
        List<ProjectEntity> projects = projectDao.findAllProjects();
        HashMap<String,HashMap<String,Integer>> resourcesPerProject = new HashMap<>();
        for(ProjectEntity project : projects){
            HashMap<String,Integer> resourceQuantities = new HashMap<>();
            for(ResourceEntity resource : project.getResources()){
                ProjectResourceEntity projectResource = projectResourceDao.findByProjectAndResource(project,resource);
                resourceQuantities.put(resource.getName(),projectResource.getQuantity());
            }
            resourcesPerProject.put(project.getName(),resourceQuantities);
        }
        return resourcesPerProject;
    }
    public HashMap<String,Integer> findResourceQuantities(){
        List<ProjectResourceEntity> projectResources = projectResourceDao.findAllProjectResources();
        HashMap<String,Integer> resourceQuantities = new HashMap<>();
        for(ProjectResourceEntity projectResource : projectResources){
            if(resourceQuantities.containsKey(projectResource.getResource().getName())){
                resourceQuantities.put(projectResource.getResource().getName(),resourceQuantities.get(projectResource.getResource().getName()) + projectResource.getQuantity());
            }else{
                resourceQuantities.put(projectResource.getResource().getName(),projectResource.getQuantity());
            }
        }
        return resourceQuantities;
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
        projectStatistics.setMostUsedResourceType(projectDao.findMostUsedResource().getType().name());
        projectStatistics.setMostUsedSkill(projectDao.findMostUsedSkill().getName());
        projectStatistics.setMostUsedInterest(projectDao.findMostUsedInterest().getName());
        projectStatistics.setMostUsedSkill(projectDao.findMostUsedSkill().getName());
        projectStatistics.setMostUsedInterest(projectDao.findMostUsedInterest().getName());
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
        ProjectLogDto projectLogDto = new ProjectLogDto(userBean.findUserById(task.getCreatedBy().getId()), project, "Task " + task.getTitle() + " added to project");
        projectLogDto.setType("CREATE_TASK");
        projectLogDto.setTaskId(task.getId());
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
        if(newStatus == 0 && (user.getRole().getValue() > 1  )){
            if(!projectUser.getApprovalStatus().equals(ProjectUserEntity.ApprovalStatus.CREATOR)){
                return false;
            }
        }

        if(newStatus == 400 && (!projectUser.isProjectManager() || project.getStatus().getValue() != 300)){
            return false;
        }
        if(newStatus == 500 && (!projectUser.isProjectManager() || project.getStatus().getValue() != 400)){
            return false;
        }
        if(newStatus == 100 && (user.getRole().getValue() > 1 || project.getStatus().getValue() > 200)){
            return false;
        }

        project.setStatus(ProjectEntity.Status.fromValue(newStatus));
        ProjectLogDto projectLogDto = new ProjectLogDto(user, project, "Status changed to " + status);
        projectLogDto.setType("UPDATE_PROJECT_STATUS");
        projectLogDto.setStatus(status);
        projectLogBean.createProjectLog(projectLogDto);
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
        UserEntity userToRemove = UserDao.findUserById(userId);
        List <TaskEntity> tasks = taskDao.findTasksByResponsibleUser(userToRemove);
        if(tasks != null) {
            for (TaskEntity task : tasks) {
                ProjectEntity projectTask = projectDao.findProjectByTask(task);
                UserEntity projectTaskCreator = projectUserDao.findProjectCreator(projectTask).getUser();
                task.setResponsibleUser(projectUserDao.findProjectCreator(project).getUser());
                taskDao.persist(task);
                notificationBean.sendNotification(new NotificationDto("TASK_ASSIGN", projectTaskCreator.getId(), projectTask.getName(), false, LocalDateTime.now()));
                ProjectLogDto projectLogDto = new ProjectLogDto(projectTaskCreator, projectTask, "User " + userToRemove.getFirstName() + " left project, task reassigned to project creator");
                projectLogDto.setType("UPDATE_TASK");
                projectLogDto.setTaskId(task.getId());
                projectLogDto.setOtherUserId(userToRemove.getId());
                projectLogBean.createProjectLog(projectLogDto);

            }
        }

        ProjectLogDto projectLogDto = new ProjectLogDto(user, project, "User " + userBean.findUserById(userId) + " removed from project");
        projectLogDto.setType("REMOVE_USER");
        projectLogDto.setOtherUserId(userId);
        projectLogBean.createProjectLog(projectLogDto);
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
    public ProjectEntity findProjectById(int id){
        return projectDao.findProjectById(id);
    }
    public List<ProjectLogDto> getProjectLogs(String projectName){
        ProjectEntity project = projectDao.findProjectByName(projectName);
        if (project == null) {
            return null;
        }
       return projectLogBean.getProjectLogs(project.getId());
    }
    public ProjectLogDto addProjectLog(ProjectLogDto dto){

        if( projectLogBean.createProjectLog(dto)){
            return dto;
        }else {
            return null;
        }


    }
    public void checkMaxMembers(int maxMembers){

        List<ProjectEntity> exceeded= projectDao.findProjectsByMaxMembers(maxMembers);
        if(!exceeded.isEmpty()){
            for(ProjectEntity project : exceeded){
                project.setMaxMembers(systemVariablesBean.getMaxUsers());
                projectDao.persist(project);
                NotificationDto notificationDto = new NotificationDto("PROJECT_FULL",projectUserDao.findProjectCreator(project).getUser().getId(), project.getName(), false, LocalDateTime.now());
                if(notificationBean.createNotification(notificationDto)){
                    notificationBean.sendNotification(notificationDto);
                }
        }
    }

    }
}