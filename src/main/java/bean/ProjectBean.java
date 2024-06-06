package bean;

import dao.*;
import dto.*;
import entities.*;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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



    public void createDefaultProjects() {
        if(projectDao.findProjectByName("Forge X") == null) {
            ProjectEntity defaultProject = new ProjectEntity();
            defaultProject.setName("Forge X");
            defaultProject.setLab(labDao.findLabByLocation(LabEntity.Lab.COIMBRA));
            List<SkillEntity> skills = skillDao.findSkillByType(SkillEntity.SkillType.SOFTWARE);
            if (skills != null) {
                defaultProject.setSkills(new LinkedHashSet<>(skills));
            }
            defaultProject.setStatus((ProjectEntity.Status.READY));
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
            defaultProject.setStartDate(java.time.LocalDate.now().minusDays(5));
            defaultProject.setEndDate(java.time.LocalDate.now().plusDays(30));
            defaultProject.setCreatedAt(java.time.LocalDate.now());
            TaskEntity task = new TaskEntity();
            task.setTitle("Create the database");
            task.setDescription("Create the database for the project");
            task.setProject(defaultProject);
            task.setResponsibleUser(userBean.findUserByEmail("admin@admin.com"));
            task.setStatus(TaskEntity.Status.NOT_STARTED);
            task.setStartDate(java.time.LocalDate.now().minusDays(5));
            task.setEndDate(java.time.LocalDate.now().plusDays(30));
            task.setCreationDate(java.time.LocalDate.now());
            taskDao.persist(task);
            Set <ResourceEntity> resources = new LinkedHashSet<>();
            ResourceEntity resource = resourceDao.findResourceByName("CPU");
            resources.add(resource);
            defaultProject.setResources(resources);
            projectDao.persist(defaultProject);
            ProjectTaskEntity projectTask = new ProjectTaskEntity();
            projectTask.setProject_id(defaultProject.getId());
            projectTask.setTask_id(task.getId());
            projectTaskDao.persist(projectTask);
            projectUserDao.persist(defaultProjectUser);

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
        projectDto.setLab(project.getLab().getLocation().ordinal());
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
    public void createProject(ProjectDto projectDto, String token) {

        ProjectEntity project = new ProjectEntity();
        project.setName(projectDto.getName());
        project.setDescription(projectDto.getDescription());
        project.setImage(projectDto.getImage());
        project.setStatus(ProjectEntity.Status.PLANNING);
        project.setLab(labDao.findLabByLocation(LabEntity.Lab.values()[projectDto.getLab()]));
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
        project.setCreator(userBean.findUserByToken(token));
        project.setMaxMembers(projectDto.getTeamMembers().size());
        projectDao.persist(project);
        for(int i = 0; i < project.getMaxMembers(); i++) {
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



       }
       public List<String> findAllStatus(){
        List<String> status = new ArrayList<>();
        for(ProjectEntity.Status s : ProjectEntity.Status.values()){
            status.add(s.name());
        }
        return status;
       }

}
