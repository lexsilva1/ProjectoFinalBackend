package bean;

import dao.*;
import dto.ProjectUserDto;
import dto.UserDto;
import entities.*;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import dto.ProjectDto;

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
            projectUserDao.persist(defaultProjectUser);
            projectDao.persist(defaultProject);
        }
    }
    public ProjectEntity findProjectByName(String name) {
        return projectDao.findProjectByName(name);
    }
    public List<ProjectEntity> findProjectsByLab(ProjectEntity lab) {
        return projectDao.findProjectsByLab(lab);
    }
    public List<ProjectDto> findAllProjects() {
        List<ProjectEntity> projects = projectDao.findAll();
        List<ProjectDto> projectDtos = new ArrayList<>();
        for (ProjectEntity project : projects) {
            projectDtos.add(convertToDto(project));
        }
        return projectDtos;
    }
    public ProjectDto convertToDto(ProjectEntity project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setName(project.getName());
        projectDto.setDescription(project.getDescription());
        projectDto.setImage(project.getImage());
        projectDto.setStatus(project.getStatus().ordinal());
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


}
