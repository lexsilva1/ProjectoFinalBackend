package bean;

import dao.*;
import dto.*;
import entities.*;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.*;
/**
 * The bean class for the project.
 */
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
    private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(ProjectBean.class);

/**
     * Create default projects
     */
    public void createDefaultProjects() {
        logger.info("Creating default projects");
        if(projectDao.findProjectByName("System") == null){
            ProjectEntity defaultProject = new ProjectEntity();
            defaultProject.setName("System");
            defaultProject.setImage("http://localhost:8080/ProjectoFinalImages/System.jpg?t=1720546993380");
            defaultProject.setLab(labDao.findLabByLocation(LabEntity.Lab.Coimbra));
            List<SkillEntity> skills = skillDao.findSkillByType(SkillEntity.SkillType.HARDWARE);
            if (skills != null) {
                defaultProject.setSkills(new LinkedHashSet<>(skills));
            }
            defaultProject.setStatus((ProjectEntity.Status.Finished));
            defaultProject.setDescription("System is a project that aims to create a new hardware that will revolutionize the way we interact with technology.");
            defaultProject.setMaxMembers(4);
            List<InterestEntity> interests = interestDao.findInterestByType(InterestEntity.InterestType.CAUSES);
            if (interests != null) {
                defaultProject.setInterests(new LinkedHashSet<>(interests));
            }

            ProjectUserEntity defaultProjectUser = new ProjectUserEntity();
            defaultProjectUser.setProject(defaultProject);
            defaultProjectUser.setUser(userBean.findUserByEmail("admin@admin.com"));
            defaultProjectUser.setProjectManager(true);
            defaultProjectUser.setApprovalStatus(ProjectUserEntity.ApprovalStatus.CREATOR);
            defaultProject.setProjectUsers(new LinkedHashSet<>(List.of(defaultProjectUser)));
            defaultProject.setStartDate(LocalDateTime.now().minusDays(5));
            defaultProject.setEndDate(LocalDateTime.now().plusDays(30));
            defaultProject.setCreatedAt(LocalDateTime.now());
            defaultProject.setMaxMembers(4);

            projectDao.persist(defaultProject);
            projectUserDao.persist(defaultProjectUser);
        }
        if (projectDao.findProjectByName("Forge X") == null) {
            ProjectEntity defaultProject = new ProjectEntity();
            defaultProject.setName("Forge X");
            defaultProject.setImage("http://localhost:8080/ProjectoFinalImages/Forge X.jpg?t=1720546993380");
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
            defaultProjectUser.setApprovalStatus(ProjectUserEntity.ApprovalStatus.CREATOR);

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
            defaultProject.setImage("http://localhost:8080/ProjectoFinalImages/UserInterface.jpg?t=1720546993380");
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
            defaultProject.setImage("http://localhost:8080/ProjectoFinalImages/Project X.jpg?t=1720546993380");
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
            defaultProject.setImage("http://localhost:8080/ProjectoFinalImages/Build Deathstar.jpg?t=1720546993380");
            defaultProject.setLab(labDao.findLabByLocation(LabEntity.Lab.Coimbra));
            List<SkillEntity> skills = skillDao.findSkillByType(SkillEntity.SkillType.TOOLS);
            if (skills != null) {
                defaultProject.setSkills(new LinkedHashSet<>(skills));
            }
            defaultProject.setStatus((ProjectEntity.Status.Planning));
            defaultProject.setDescription("The Death Star, whose concept had been explored even before the Clone Wars, was the first in a long series of superweapons developed to execute the Tarkin Doctrine");
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
            ProjectUserEntity defaultProjectUser3 = new ProjectUserEntity();
            defaultProjectUser3.setProject(defaultProject);
            defaultProjectUser3.setUser(userBean.findUserByEmail("luke@gmail.com"));
            defaultProjectUser3.setProjectManager(false);
            defaultProjectUser3.setApprovalStatus(ProjectUserEntity.ApprovalStatus.INVITED);
            defaultProject.setProjectUsers(new LinkedHashSet<>(List.of(defaultProjectUser, defaultProjectUser2, defaultProjectUser3)));
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
            projectUserDao.persist(defaultProjectUser3);
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
            defaultProject.setImage("http://localhost:8080/ProjectoFinalImages/Yavin 4.jpeg?t=1720546993380");
            defaultProject.setName("Yavin 4");
            defaultProject.setLab(labDao.findLabByLocation(LabEntity.Lab.Coimbra));
            List<SkillEntity> skills = skillDao.findSkillByType(SkillEntity.SkillType.TOOLS);
            if (skills != null) {
                defaultProject.setSkills(new LinkedHashSet<>(skills));
            }
            defaultProject.setStatus((ProjectEntity.Status.Approved));
            defaultProject.setDescription("One of a number of moons orbiting the gas giant Yavin in the galaxy’s Outer Rim, Yavin 4 is a steamy world covered in jungle and forest. It was the location of the principal rebel base early in the Galactic Civil War.");
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
            ProjectUserEntity defaultProjectUser4 = new ProjectUserEntity();
            defaultProjectUser4.setProject(defaultProject);
            defaultProjectUser4.setUser(userBean.findUserByEmail("hansolo@gmail.com"));
            defaultProjectUser4.setProjectManager(false);
            defaultProjectUser4.setApprovalStatus(ProjectUserEntity.ApprovalStatus.INVITED);
            defaultProject.setProjectUsers(new LinkedHashSet<>(List.of(defaultProjectUser, defaultProjectUser2, defaultProjectUser3,defaultProjectUser4)));
            defaultProject.setCreatedAt(LocalDateTime.now());
            defaultProject.setStartDate(LocalDateTime.now().minusDays(1));
            defaultProject.setEndDate(LocalDateTime.now().plusDays(40));
            projectDao.persist(defaultProject);
            projectUserDao.persist(defaultProjectUser);
            projectUserDao.persist(defaultProjectUser2);
            projectUserDao.persist(defaultProjectUser3);
            projectUserDao.persist(defaultProjectUser4);
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
            defaultProject.setImage("http://localhost:8080/ProjectoFinalImages/Kessel Run.jpeg?t=1720546993380");
            defaultProject.setLab(labDao.findLabByLocation(LabEntity.Lab.Coimbra));
            List<SkillEntity> skills = skillDao.findSkillByType(SkillEntity.SkillType.TOOLS);
            if (skills != null) {
                defaultProject.setSkills(new LinkedHashSet<>(skills));
            }
            defaultProject.setStatus((ProjectEntity.Status.Finished));
            defaultProject.setDescription("The Kessel Run was a 20-parsec hyperspace route within the Akkadese Maelstrom used by smugglers and unscrupulous freighter captains to move spice from the spice mines of Kessel at the behest of the Pyke Syndicate.");
            defaultProject.setMaxMembers(3);
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
            task.setResponsibleUser(userBean.findUserByEmail("lando@gmail.com"));
            task.setStatus(TaskEntity.Status.COMPLETED);
            task.setStartDate(LocalDateTime.now().minusDays(3));
            task.setEndDate(LocalDateTime.now().plusDays(9));
            task.setCreationDate(LocalDateTime.now());
            taskDao.persist(task);
            TaskEntity finaltask = new TaskEntity();
            finaltask.setTitle("Final Presentation");
            finaltask.setDescription("Final presentation of the finalized project");
            finaltask.setResponsibleUser(userBean.findUserByEmail("hansolo@gmail.com"));
            finaltask.setStatus(TaskEntity.Status.COMPLETED);
            finaltask.setStartDate(defaultProject.getEndDate().minusDays(1));
            finaltask.setEndDate(defaultProject.getEndDate());
            finaltask.setCreationDate(LocalDateTime.now());
            finaltask.setCreatedBy(userBean.findUserById(defaultProjectUser.getUser().getId()));
            taskDao.persist(finaltask);
            TaskEntity task2 = new TaskEntity();
            task2.setTitle("Avoid the Empire");
            task2.setDescription("Avoid the Empire while smuggling the spice");
            task2.setResponsibleUser(userBean.findUserByEmail("chewbacca@gmail.com"));
            task2.setStatus(TaskEntity.Status.COMPLETED);
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

    /**
     * find project by name
     * @param name
     * @return ProjectEntity
     */
    public ProjectEntity findProjectByName(String name) {

        return projectDao.findProjectByName(name);
    }

    /**
     * converts a ProjectEntity to a dto
     * @param project
     * @return
     */
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

    /**
     * find project users
     * @param project
     * @return
     */
    public List<UserDto> findProjectUsers(ProjectEntity project) {

        List<UserEntity> projectUsers = projectUserDao.findAllProjectUsers(project);
        List<UserDto> users = new ArrayList<>();
        for (UserEntity user : projectUsers) {
            users.add(userBean.convertToDto(user));
        }

        return users;
    }
    /**
     * find project users and converts them to dto
     * @param project
     * @return a list of ProjectUserDto
     */
    public List<ProjectUserDto> findProjectUsersByProject(ProjectEntity project) {
       List<ProjectUserEntity> projectUsers = projectUserDao.findTeamMembers(project);
        List<ProjectUserDto> users = new ArrayList<>();
        for (ProjectUserEntity projectUser : projectUsers) {
            users.add(userBean.convertToProjectUserDto(projectUser));
        }
        return users;
    }
    /**
     * find project teamMembers... resturn ProjectUser dto
     *
     * @param project
     * @return
     */
    public List<ProjectUserDto> findTeamMembers(ProjectEntity project) {

        List<ProjectUserEntity> projectUsers = projectUserDao.findTeamMembers(project);
        List<ProjectUserDto> users = new ArrayList<>();
        for (ProjectUserEntity projectUser : projectUsers) {
            users.add(userBean.convertToProjectUserDto(projectUser));
        }

        return users;
    }
/**
     * find project's skills
     * @param project
     * @return a List of skillDto objects
     */
    public List<SkillDto> findProjectSkills(ProjectEntity project) {

        List<SkillEntity> projectSkills = projectDao.findProjectSkills(project);
        List<SkillDto> skills = new ArrayList<>();
        for (SkillEntity skill : projectSkills) {
            skills.add(convertToSkillDto(skill));
        }

        return skills;
    }
/**
     * conversts a skill entity to a dto
 * @param skill
 * @return a skillDto object
     */
    public SkillDto convertToSkillDto(SkillEntity skill) {

        SkillDto skillDto = new SkillDto();
        skillDto.setName(skill.getName());
        skillDto.setSkillType(skill.getSkillType().getDeclaringClass().getTypeName());
        skillDto.setId(skill.getId()); // Correct usage
        ;
        return skillDto;
    }
/**
     * find projects
 * @param projectName
 * @param projectLab
 * @param projectSkill
 * @param projectInterest
 * @param projectStatus
 * @param projectUser
 * @param token
 * @return a list of ProjectDto objects
     */
    public List<ProjectDto> findProjects(String projectName, String projectLab, String projectSkill, String projectInterest, int projectStatus, int projectUser, String token) {
        logger.info("Finding projects for user with token {}", token);
        List<ProjectEntity> projectEntities = projectDao.findProjects(projectName, projectLab, projectSkill, projectInterest, projectStatus, projectUser);

        List<ProjectDto> projectDtos = new ArrayList<>();
        for (ProjectEntity projectEntity : projectEntities) {
            if (!projectEntity.getName().equals("System")) {
                projectDtos.add(convertToDto(projectEntity));
            }
        }
        logger.info("Projects found");
        return projectDtos;
    }

    /**
     * create project method
     * @param projectDto
     * @param token
     * @return
     */
    public boolean createProject(CreateProjectDto projectDto, String token) {
        logger.info("Creating project");
        if (projectDao.findProjectByName(projectDto.getName()) != null) {
            logger.error("Project already exists");
            return false;
        }

        ProjectEntity project = new ProjectEntity();
        if(projectDto.getName() == null || projectDto.getName().isEmpty()){
            logger.error("Project name is empty");
            return false;
        }
        project.setName(projectDto.getName());
        if(projectDto.getDescription() == null || projectDto.getDescription().isEmpty()){
            logger.error("Project description is empty");
            return false;
        }
        project.setDescription(projectDto.getDescription());
        project.setImage(projectDto.getImage());
        project.setStatus(ProjectEntity.Status.Planning);
        if(projectDto.getLab() == null || projectDto.getLab().isEmpty()){
            logger.error("Project lab is empty");
            UserEntity user = userBean.findUserByToken(token);
            projectDto.setLab(user.getLocation().toString());
            logger.error("Project lab set to user{} location {}", user.getEmail(),user.getLocation().toString());
        }
        project.setLab(labDao.findLabByLocation(LabEntity.Lab.valueOf(projectDto.getLab())));
        TaskEntity lastTask = taskBean.createLastTask(token, projectDto, userBean.findUserByToken(token), List.of(userBean.findUserByToken(token).getId()));

        if(lastTask == null){
            logger.error("Last task not created");
            return false;
        }
        project.setTasks(new LinkedHashSet<>(List.of(lastTask)));

        if(projectDto.getSlots() <= 0){
            logger.error("Project slots are invalid and set to the default value");
            projectDto.setSlots(4);
        }
        project.setMaxMembers(projectDto.getSlots());
        project.setCreatedAt(LocalDateTime.now());
        if (projectDto.getStartDate() == null ) {
            logger.error("Project start date is null and set to the current date");
            project.setStartDate(LocalDateTime.now());
        }else {
            project.setStartDate(projectDto.getStartDate());
        }
        if(projectDto.getEndDate() == null ) { // Correct usage
            logger.error("Project end date is null and set to the start date plus 30 days");
            project.setEndDate(project.getStartDate().plusDays(30));
        }else if(projectDto.getEndDate().isBefore(projectDto.getStartDate())){
            logger.error("Project end date is before the start date");
            return false;
        }else{
            project.setEndDate(projectDto.getEndDate());
        }
        project.setSkills(skillBean.listDtoToEntity(new HashSet<>((projectDto.getSkills()))));
        if(projectDto.getInterests() == null){
            logger.error("Project interests are null");
            return false;
        }
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
                logger.info("Project user {} created", projectUser.getUser().getEmail());
                if(projectUserDto.getApprovalStatus().equals("INVITED")) {
                    NotificationDto notificationDto = new NotificationDto();
                    notificationDto.setProjectName(project.getName());
                    notificationDto.setUserId(projectUserDto.getUserId());
                    notificationDto.setType("INVITE");
                    notificationDto.setRead(false);
                    notificationBean.sendNotification(notificationDto);
                    logger.info("Notification sent to user {}", projectUser.getUser().getEmail());
                    }
                }
            }else{
                ProjectUserEntity projectUser = new ProjectUserEntity();
                projectUser.setProject(project);
                projectUser.setUser(userBean.findUserByToken(token));
                projectUser.setProjectManager(true);
                projectUser.setApprovalStatus(ProjectUserEntity.ApprovalStatus.CREATOR);
                projectUserDao.persist(projectUser);
                logger.info("Project user {} created", projectUser.getUser().getEmail());
        }
        if(projectDto.getBillOfMaterials() != null) {
            for (ResourceDto resourceDto : projectDto.getBillOfMaterials()) {
                ResourceEntity resource = resourceDao.findResourceByIdentifier(resourceDto.getIdentifier());
                ProjectResourceEntity projectResource = new ProjectResourceEntity();
                projectResource.setProject(project);
                projectResource.setResource(resource);
                projectResource.setQuantity(resourceDto.getQuantity());
                projectResourceDao.persist(projectResource);
                logger.info("Project resource {} created", resource.getName());
            }
        }
        projectDao.merge(project);
        ProjectLogDto projectLogDto = new ProjectLogDto(userBean.findUserByToken(token), project, "Project created");
        projectLogDto.setType("PROJECT_CREATED");
        projectLogBean.createProjectLog(projectLogDto);
        if(project.getMaxMembers()> systemVariablesBean.getMaxUsers()){
            logger.error("Project users exceeded the system limit");
            project.setMaxMembers(systemVariablesBean.getMaxUsers());
            logger.error("Project slots set to the system limit");
            projectDao.persist(project);
            NotificationDto notificationDto = new NotificationDto("PROJECT_USERS_EXCEEDED", userBean.findUserByToken(token).getId(), project.getName(), false, LocalDateTime.now());
            if(notificationBean.createNotification(notificationDto)){
                logger.info("Notification sent to user {}", userBean.findUserByToken(token).getEmail());
                notificationBean.sendNotification(notificationDto);
            }
        }
        logger.info("Project created");
        return true;
    }

    /**
     * update projects lab and description
     * @param projectDto
     * @param projectName
     * @return true or false, depending on the update
     */
    public boolean updateProject(UpdateProjectDto projectDto,String projectName){
        logger.info("Updating project {}", projectName);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        if(project == null){
            logger.error("Project not found");
            return false;
        }
        if(projectDto.getLab()!= null){
            project.setLab(labDao.findLabByLocation(LabEntity.Lab.valueOf(projectDto.getLab())));
            logger.info("Project lab updated to {}", projectDto.getLab());
        }
        if(projectDto.getDescription() != null){
            project.setDescription(projectDto.getDescription());
            logger.info("Project description updated to {}", projectDto.getDescription());
        }
        projectDao.persist(project);
        logger.info("Project {} updated", projectName);
        return true;
    }
/**
     * finds all project status
 * @return a list of strings
     */
    public List<String> findAllStatus() {
        logger.info("Finding all project status");
        List<String> status = new ArrayList<>();
        for (ProjectEntity.Status s : ProjectEntity.Status.values()) {
            status.add(s.name());
        }
        logger.info("Project status found");
        return status;
    }

    /**
     * find a user by token and project
     * @param projectName
     * @param token
     * @return projectUser entity
     */
    public ProjectUserEntity findUserByTokenAndProject(String projectName, String token) {
        logger.info("Finding user by token {} and project {}", token, projectName);
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        if (user == null || project == null) {
            logger.error("User or project not found");
            return null;
        }
        logger.info("User found");
        return projectUserDao.findProjectUserByProjectAndUser(project, user);
    }
/**
     * Apply toa  project
 * @param token
 * @param projectName
 * @return true or false, depending on the application
 *
     */
    public boolean applyToProject(String token, String projectName) {
        logger.info("Applying to project {}", projectName);
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        ProjectUserEntity projectUser = projectUserDao.findProjectUserByProjectAndUser(project, user);
        if (user == null || project == null) {
            logger.error("User or project not found");
            return false;
        }
        if (projectUser != null) {
            logger.error("User already applied to project");
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
        logger.info("User {} applied to project {}", user.getEmail(), projectName);
        return true;
    }

    /**
     * invite use rto a project
     * @param token
     * @param projectName
     * @param userId
     * @return true if the project user entity is created
     */
    public boolean inviteToProject(String token, String projectName, int userId) {
        logger.info("Inviting user {} to project {}", userId, projectName);
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        UserEntity invitedUser = UserDao.findUserById(userId);
        ProjectUserEntity projectUser = projectUserDao.findProjectUserByProjectAndUser(project, invitedUser);
        if (user == null || project == null || invitedUser == null) {
            logger.error("User, project or invited user not found");
            return false;
        }
        if (projectUser != null) {
            logger.error("User already belongs to project");
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
        logger.info("User {} invited to project {}", invitedUser.getEmail(), projectName);
        return true;
    }

    public enum OperationType {
        ACCEPT_INVITATION,
        ACCEPT_APPLICATION
    }
/**
     * accept request
     * @param token
     * @param projectName
     * @param userId
     * @param operationType
     * @return true or false, depending on the acceptance
     */
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
        Set<ProjectUserEntity> projectMembers = project.getProjectUsers();
        int count = 0;
        for (ProjectUserEntity projectMember : projectMembers) {
            if (projectMember.getApprovalStatus() == ProjectUserEntity.ApprovalStatus.MEMBER) {
                count++;
            }
        }
        logger.info("verifying project members");
        if(count >= project.getMaxMembers()){
            logger.error("Project users exceeded the limit");
            return false;
        }

        projectUser.setApprovalStatus(ProjectUserEntity.ApprovalStatus.MEMBER);
        logger.info("User {} accepted to project {}", targetUser.getEmail(), project.getName());
        projectUserDao.persist(projectUser);
        projectUserDao.findProjectManagers(project).forEach(projectManager -> {
            logger.info("Sending notification to project manager {}", projectManager.getUser().getEmail());
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
        logger.info("Project log created");
        return true;
    }

    /**
     * reject request
     * @param token
     * @param projectName
     * @param userId
     * @param operationType
     * @return
     */
    public boolean rejectRequest(String token, String projectName, Integer userId, OperationType operationType) {
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        UserEntity targetUser = operationType == OperationType.ACCEPT_INVITATION ? user : UserDao.findUserById(userId);
            logger.info("Rejecting request");
        ProjectUserEntity projectUser = projectUserDao.findProjectUserByProjectAndUser(project, targetUser);
        if (user == null || project == null || targetUser == null || projectUser == null) {
            logger.error("User, project or target user not found");
            return false;
        }

        if (operationType == OperationType.ACCEPT_INVITATION) {
            if (projectUser.getApprovalStatus() != ProjectUserEntity.ApprovalStatus.INVITED) {
                logger.error("User not invited to project");
                return false;
            }
        } else if (operationType == OperationType.ACCEPT_APPLICATION) {
            logger.info("User applied to project");
            if (projectUser.getApprovalStatus() != ProjectUserEntity.ApprovalStatus.APPLIED) {
                return false;
            }
        }

        projectUserDao.remove(projectUser);
        logger.info("User {} rejected from project {}", targetUser.getEmail(), project.getName());
        projectUserDao.findProjectManagers(project).forEach(projectManager -> {
            logger.info("Sending notification to project manager {}", projectManager.getUser().getEmail());
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
        logger.info("Project log created");
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

    /**
     * leave project
     * @param token
     * @param projectName
     * @return
     */
    public boolean leaveProject(String token, String projectName) {
        logger.info("Leaving project {}", projectName);
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        ProjectUserEntity projectUser = projectUserDao.findProjectUserByProjectAndUser(project, user);
        if (user == null || project == null || projectUser == null) {
            logger.error("User, project or project user not found");
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
            logger.info("Reassigning tasks");
            for (TaskEntity task : filteredTasks) {
                ProjectEntity projectTask = projectDao.findProjectByTask(task);
                UserEntity projectTaskCreator = projectUserDao.findProjectCreator(projectTask).getUser();
                task.setResponsibleUser(projectUserDao.findProjectCreator(project).getUser());
                logger.info("Task {} reassigned to project creator", task.getTitle());
                taskDao.persist(task);
                notificationBean.sendNotification(new NotificationDto("TASK_ASSIGN", projectTaskCreator.getId(), projectTask.getName(), false, LocalDateTime.now()));
                ProjectLogDto projectLogDto = new ProjectLogDto(projectTaskCreator, projectTask, "User " + user.getFirstName() + " left project, task reassigned to project creator");
                projectLogDto.setType("UPDATE_TASK");
                projectLogDto.setTaskId(task.getId());
                projectLogDto.setOtherUserId(user.getId());
                projectLogBean.createProjectLog(projectLogDto);
                logger.info("Project log created");
            }
        }
        projectUserDao.remove(projectUser);
        logger.info("User {} left project {}", user.getEmail(), projectName);
        ProjectLogDto projectLogDto = new ProjectLogDto(user, project, "User " + user.getFirstName() + " left project");
        projectLogDto.setType("USER_LEFT");
        projectLogBean.createProjectLog(projectLogDto);
        notificationBean.sendNotification(new NotificationDto("USER_LEFT", projectUserDao.findProjectCreator(project).getUser().getId(), projectName, false, LocalDateTime.now()));
        logger.info("Notification sent to project creator");
        return true;
    }

    /**
     * find project creator by a task
     * @param task
     * @return User Dto
     */
 public UserDto findProjectCreatorByTask(TaskEntity task) {
        logger.info("Finding project creator by task");
        ProjectEntity project = projectDao.findProjectByTask(task);
        ProjectUserEntity projectUser = projectUserDao.findProjectCreator(project);
        logger.info("Project creator found");
        return userBean.convertToDto(projectUser.getUser());

    }

    /**
     * promote  a user to Project Manager
     * @param token
     * @param projectName
     * @param userId
     * @return
     */
    public boolean promoteUserToProjectManager(String token, String projectName, int userId) {
        logger.info("Promoting user {} to project manager", userId);
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        UserEntity promotedUser = UserDao.findUserById(userId);
        ProjectUserEntity projectUser = projectUserDao.findProjectUserByProjectAndUser(project, promotedUser);
        if (user == null || project == null || promotedUser == null || projectUser == null) {
            logger.error("User, project, promoted user or project user not found");
            return false;
        }
        projectUser.setProjectManager(true); logger.info("User {} promoted to project manager", promotedUser.getEmail());
        ProjectLogDto projectLogDto = new ProjectLogDto(userBean.findUserById(userId), project, "User " + userBean.findUserById(userId).getFirstName() + " promoted to project manager");
        projectLogDto.setType("PROMOTE_USER");
        projectLogDto.setOtherUserId(userId);
        projectLogBean.createProjectLog(projectLogDto);
        projectUserDao.merge(projectUser);
        logger.info("Project user updated");
        return true;
    }
/**
     * demote user from project manager
     * @param token
     * @param projectName
     * @param userId
     * @return
     */
    public boolean demoteUserFromProjectManager(String token, String projectName, int userId) {
        logger.info("Demoting user {} from project manager", userId);
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        UserEntity demotedUser = UserDao.findUserById(userId);
        ProjectUserEntity projectUser = projectUserDao.findProjectUserByProjectAndUser(project, demotedUser);
        if (user == null || project == null || demotedUser == null || projectUser == null) {
            logger.error("User, project, demoted user or project user not found");
            return false;
        }
        projectUser.setProjectManager(false);logger.info("User {} demoted from project manager", demotedUser.getEmail());
        ProjectLogDto projectLogDto = new ProjectLogDto(userBean.findUserById(userId), project, "User " + userBean.findUserById(userId).getFirstName() + " demoted from project manager");
        projectLogDto.setType("DEMOTE_USER");
        projectLogDto.setOtherUserId(userId);
        projectLogBean.createProjectLog(projectLogDto);
        projectUserDao.persist(projectUser);
        logger.info("Project user updated");
        return true;
    }

    /**
     * add resource to project
     * @param token
     * @param projectName
     * @param resourceId
     * @param quantity
     * @return true if the projectResource entity is created
     */
    public boolean addResourceToProject(String token, String projectName, int resourceId, int quantity) {
        logger.info("Adding resource to project");
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        ResourceEntity resource = resourceDao.findResourceById(resourceId);
        if (user == null || project == null || resource == null) {
            logger.error("User, project or resource not found");
            return false;
        }
        ProjectResourceEntity projectResource = new ProjectResourceEntity();
        projectResource.setProject(project);
        projectResource.setResource(resource);
        projectResource.setQuantity(quantity);
        projectResourceDao.persist(projectResource);
        logger.info("Resource added to project");
        return true;
    }
    /**
     * remove resource from project
     * @param token
     * @param projectName
     * @param resourceId
     * @return true if the projectResource entity is removed
     */
    public boolean removeResourceFromProject(String token, String projectName, int resourceId) {
        logger.info("Removing resource from project");
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        ResourceEntity resource = resourceDao.findResourceById(resourceId);
        if (user == null || project == null || resource == null) {
            logger.error("User, project or resource not found");
            return false;
        }
        ProjectResourceEntity projectResource = projectResourceDao.findByProjectAndResource(project, resource);
        projectResourceDao.remove(projectResource);
        logger.info("Resource removed from project");
        return true;
    }

    /**
     * updates a resources quantity in a project
     * @param token
     * @param projectName
     * @param resourceId
     * @param quantity
     * @return
     */
    public boolean updateResourceQuantity(String token, String projectName, int resourceId, int quantity) {
        logger.info("Updating resource quantity");
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        ResourceEntity resource = resourceDao.findResourceById(resourceId);
        if (user == null || project == null || resource == null || quantity <= 0) {
            logger.error("User, project, resource not found or quantity invalid");
            return false;
        }
        ProjectResourceEntity projectResource = projectResourceDao.findByProjectAndResource(project, resource);
        projectResource.setQuantity(quantity);
        projectResourceDao.persist(projectResource);
        logger.info("Resource quantity updated");
        return true;
    }

    /**
     * decodes the projects name
     * @param projectName
     * @return
     */
    public String decodeProjectName(String projectName) {
        logger.info("Decoding project name");
        try {
            logger.info("Project name decoded");
            return java.net.URLDecoder.decode(projectName, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            logger.error("Error decoding project name");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * adds  skill toa  project
     * @param token
     * @param projectName
     * @param skill
     * @return
     */
    public boolean addSkillToProject(String token, String projectName, SkillEntity skill) {
        logger.info("Adding skill {} to project {}", skill.getName(), projectName);
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        if (user == null || project == null || skill == null) {
            logger.error("User, project or skill not found");
            return false;
        }

        project.getSkills().add(skill);
        skill.getProjects().add(project);
        skillDao.merge(skill);
        projectDao.merge(project);
        logger.info("Skill added to project");
        return true;
    }

    /**
     * removes a skill from a project
     * @param token
     * @param projectName
     * @param skill
     * @return
     */
    public boolean removeSkillFromProject(String token, String projectName, SkillEntity skill) {
        logger.info("Removing skill {} from project {}", skill.getName(), projectName);
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        if (user == null || project == null || skill == null) {
            logger.error("User, project or skill not found");
            return false;
        }
        skill.getProjects().remove(project);
        project.getSkills().remove(skill);
        skillDao.merge(skill);
        projectDao.merge(project);
        logger.info("Skill removed from project");
        return true;
        }

    /**
     * add a keyword to a project
     * @param token
     * @param projectName
     * @param interest
     * @return
     */
    public boolean addInterestToProject(String token, String projectName, InterestEntity interest) {
        logger.info("Adding interest to project");
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        if (user == null || project == null || interest == null) {
            logger.error("User, project or interest not found");
            return false;
        }
        project.getInterests().add(interest);
        interest.getProjects().add(project);
        interestDao.merge(interest);
        projectDao.merge(project);
        logger.info("Interest added to project");
        return true;
    }

    /**
     * removes a skill from a project
     * @param token
     * @param projectName
     * @param interest
     * @return
     */
    public boolean removeInterestFromProject(String token, String projectName, InterestEntity interest) {
        logger.info("Removing interest from project");
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        if (user == null || project == null || interest == null) {
            logger.error("User, project or interest not found");
            return false;
        }
        project.getInterests().remove(interest);
        interest.getProjects().remove(project);
        interestDao.merge(interest);
        projectDao.merge(project);
        logger.info("Interest removed from project");
        return true;
    }

    /**
     * gets resources by lab statistics
     * @return  a hashmap of string and hashmap of string and integer
     */
    public HashMap<String,HashMap<String,Integer>> getResourceQuantitiesByLab(){
        logger.info("Finding resource quantities by lab");
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
        logger.info("Resource quantities by lab found");
        return resources;
    }

    /**
     * gets resource quantities by project
     * @return a hashmap of string and hashmap of string and integer
     */
    public HashMap<String,HashMap<String,Integer>> resourceQuantitiesByProject(){
        logger.info("Finding resource quantities by project");
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
        logger.info("Resource quantities by project found");
        return resourcesPerProject;
    }

    /**
     * finds resource quantities
     * @return a hashmap of string and integer
     */
    public HashMap<String,Integer> findResourceQuantities(){
        logger.info("Finding resource quantities");
        List<ProjectResourceEntity> projectResources = projectResourceDao.findAllProjectResources();
        HashMap<String,Integer> resourceQuantities = new HashMap<>();
        for(ProjectResourceEntity projectResource : projectResources){
            if(resourceQuantities.containsKey(projectResource.getResource().getName())){
                resourceQuantities.put(projectResource.getResource().getName(),resourceQuantities.get(projectResource.getResource().getName()) + projectResource.getQuantity());
            }else{
                resourceQuantities.put(projectResource.getResource().getName(),projectResource.getQuantity());
            }
        }
        logger.info("Resource quantities found");
        return resourceQuantities;
    }

    /**
     * gets project statistics
     * @return a project statistics object
     */
    public ProjectStatistics getProjectStatistics() {
        logger.info("Getting project statistics");
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
        logger.info("Project statistics found");
        return projectStatistics;
    }

    /**
     * finds a project's task
     * @param projectName
     * @return return a project task Dto
     */
    public ProjectTasksDto findProjectTasks(String projectName) {
        logger.info("Finding project {} tasks", projectName);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        if (project == null) {
            logger.error("Project not found");
            return null;
        }
        ProjectTasksDto projectTasksDto = new ProjectTasksDto();
        projectTasksDto.setProjectName(project.getName());
        Set<TaskDto> tasks = new HashSet<>();

        for (TaskEntity task : project.getTasks()) {
            tasks.add(taskBean.toTasktoDto(task));
        }
        projectTasksDto.setTasks(tasks);
        logger.info("Project tasks found");
        return projectTasksDto;
    }

    /**
     * adds a task toa  project
     * @param projectName
     * @param task
     */
    public void addTaskToProject( String projectName, TaskEntity task) {
        logger.info("Adding task to project");
        ProjectEntity project = projectDao.findProjectByName(projectName);
        if (project == null) {
            logger.error("Project not found");
            return;
        }
        project.getTasks().add(task);
        ProjectLogDto projectLogDto = new ProjectLogDto(userBean.findUserById(task.getCreatedBy().getId()), project, "Task " + task.getTitle() + " added to project");
        projectLogDto.setType("CREATE_TASK");
        projectLogDto.setTaskId(task.getId());
        projectDao.persist(project);
        logger.info("Task added to project");
    }

    /**
     * status converter
     * @param status
     * @return returns the integers associated with the status enums
     */
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
    public String convertStatusToString(int status){
        String newStatus;
        switch (status){
            case 100:
                newStatus = "Planning";
                break;
            case 200:
                newStatus = "Ready";
                break;
            case 300:
                newStatus = "Approved";
                break;
            case 400:
                newStatus = "In_Progress";
                break;
            case 500:
                newStatus = "Finished";
                break;
            case 0:
                newStatus = "Cancelled";
                break;
            default:
                newStatus = "Invalid";
        }
        return newStatus;
    }

    /**
     * converts from entity enum status
     * @param status
     * @return String
     */
    public String convertStatusToEntityStatus(int status){
        String newStatus;
        switch (status){
            case 100:
                newStatus = "PROJECT_REJECTED";
                break;
            case 200:
                newStatus = "PROJECT_READY";
                break;
            case 300:
                newStatus = "PROJECT_APPROVED";
                break;
            case 400:
                newStatus = "PROJECT_DOING";
                break;
            case 500:
                newStatus = "PROJECT_COMPLETE";
                break;
            case 0:
                newStatus = "PROJECT_CANCEL";
                break;
            default:
                newStatus = null;
        }
        return newStatus;
    }

    /**
     * updates the status of a project
     * @param token
     * @param projectName
     * @param status
     * @returntrue is the update is successful
     */
    public boolean updateProjectStatus(String token, String projectName, String status) {
        logger.info("Updating project {} status to {}", projectName, status);
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        ProjectUserEntity projectUser = projectUserDao.findProjectUserByProjectAndUser(projectDao.findProjectByName(projectName), user);
        int newStatus = convertStatus(status);
        if(newStatus == -1){
            logger.error("Invalid status");
            return false;
        }
        if(newStatus == 300 && (user.getRole().getValue() > 1 || project.getStatus().getValue() != 200)){
            logger.error("User not authorized to approve project or project not ready");
            return false;
        }
        if (newStatus == 200 && (!projectUser.isProjectManager() || project.getStatus().getValue() != 100)) {
            logger.error("User not authorized to ready project or project not planned");
            return false;
        }
        if(newStatus == 0 && (user.getRole().getValue() > 1  )){
            if(!projectUser.getApprovalStatus().equals(ProjectUserEntity.ApprovalStatus.CREATOR)){
                logger.error("User not authorized to cancel project");
                return false;
            }
        }

        if(newStatus == 400 && (!projectUser.isProjectManager() || project.getStatus().getValue() != 300)){
            logger.error("User not authorized to start project or project not approved");
            return false;
        }
        if(newStatus == 500 && (!projectUser.isProjectManager() || project.getStatus().getValue() != 400)){
            logger.error("User not authorized to finish project or project not in progress");
            return false;
        }
        if(newStatus == 100 && (user.getRole().getValue() > 1 || project.getStatus().getValue() > 200)){
            logger.error("User not authorized to plan project or project not in planning");
            return false;
        }

        project.setStatus(ProjectEntity.Status.fromValue(newStatus));
        ProjectLogDto projectLogDto = new ProjectLogDto(user, project, "Status changed to " + status);
        projectLogDto.setType("UPDATE_PROJECT_STATUS");
        projectLogDto.setStatus(status);
        projectLogBean.createProjectLog(projectLogDto);
        projectDao.persist(project);
        NotificationDto notificationDto = new NotificationDto(convertStatusToEntityStatus(newStatus), projectUserDao.findProjectCreator(project).getUser().getId(), project.getName(), false, project.getStatus().toString());
        project.getProjectUsers().forEach(projectUserEntity -> {
            notificationDto.setUserId(projectUserEntity.getUser().getId());
            notificationBean.sendNotification(notificationDto);
        });
        logger.info("Project status updated");
        return true;
    }

    /**
     * removes a projectUSer from teh project
     * @param token
     * @param projectName
     * @param userId
     * @return true if the user is removed
     */
    public boolean removeProjectUser(String token, String projectName, int userId) {
        logger.info("Removing user {} from project {}", userId, projectName);
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        ProjectUserEntity projectUser = projectUserDao.findProjectUserByProjectAndUser(project, UserDao.findUserById(userId));
        if (user == null || project == null || projectUser == null) {
            logger.error("User, project or project user not found");
            return false;
        }
        UserEntity userToRemove = UserDao.findUserById(userId);
        List <TaskEntity> tasks = taskDao.findTasksByResponsibleUser(userToRemove);
        if(tasks != null) {
            logger.info("Reassigning tasks");
            for (TaskEntity task : tasks) {
                ProjectEntity projectTask = projectDao.findProjectByTask(task);
                UserEntity projectTaskCreator = projectUserDao.findProjectCreator(projectTask).getUser();
                task.setResponsibleUser(projectUserDao.findProjectCreator(project).getUser());
                taskDao.persist(task);
                logger.info("Task {} reassigned to project creator", task.getTitle());
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
        logger.info("User {} removed from project {} by {}", userToRemove.getEmail(), projectName, user.getEmail());
        return true;
    }

    /**
     * checks if a user is a project manager
     * @param token
     * @param projectName
     * @return
     */
    public boolean isProjectManager(String token, String projectName){
        logger.info("Checking if user is project manager");
        UserEntity user = userBean.findUserByToken(token);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        ProjectUserEntity projectUser = projectUserDao.findProjectUserByProjectAndUser(project, user);
        if (user == null || project == null || projectUser == null) {
            logger.error("User, project or project user not found");
            return false;
        }
        logger.info("User is project manager");
        return projectUser.isProjectManager();
    }
    /**
     * gets project resources
     * @param projectName
     * @return a list of project resources
     */
    public List<ProjectResourceEntity> getProjectResources(String projectName){
        logger.info("Finding project {} resources", projectName);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        if (project == null) {
            logger.error("Project not found");
            return null;
        }
        logger.info("Project {} resources found", projectName);
        return projectResourceDao.findProjectResources(project.getId());
    }

    /**
     * finda a project by id
     * @param id
     * @return a project Entity
     */
    public ProjectEntity findProjectById(int id){
        logger.info("Finding project by id");
        return projectDao.findProjectById(id);
    }

    /**
     * gets project logs
     * @param projectName
     * @return
     */
    public List<ProjectLogDto> getProjectLogs(String projectName){
        logger.info("Finding project {} logs", projectName);
        ProjectEntity project = projectDao.findProjectByName(projectName);
        if (project == null) {
            logger.error("Project not found");
            return null;
        }
        logger.info("Project {} logs found", projectName);
       return projectLogBean.getProjectLogs(project.getId());
    }

    /**
     *
     * adds a project log to a project
     * @param dto
     * @return
     */
    public ProjectLogDto addProjectLog(ProjectLogDto dto){
        logger.info("Adding a log to project {}", dto.getProject());
        if( projectLogBean.createProjectLog(dto)){
            logger.info("Project log added");
            return dto;
        }else {
            logger.error("Project log not added");
            return null;
        }


    }

    /**
     * Checks teh maxMembers of a project
     * @param maxMembers
     */
    public void checkMaxMembers(int maxMembers){
        logger.info("Checking project max members");
        List<ProjectEntity> exceeded= projectDao.findProjectsByMaxMembers(maxMembers);
        if(!exceeded.isEmpty()){
            logger.error("Project users exceeded the system limit");
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