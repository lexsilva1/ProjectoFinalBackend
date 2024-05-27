package bean;

import dao.*;
import entities.*;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import dto.ProjectDto;
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
    public List<ProjectEntity> findAllProjects() {
        return projectDao.findAllProjects();
    }
    public ProjectDto convertToDto(ProjectEntity project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setName(project.getName());
        projectDto.setDescription(project.getDescription());
        projectDto.setImage(project.getImage());
        projectDto.setStatus(project.getStatus().ordinal());
        projectDto.setLab(project.getLab().getLocation().ordinal());
        int[] skills = new int[project.getSkills().size()];
        int i = 0;
        for (SkillEntity skill : project.getSkills()) {
            skills[i] = skill.getType().ordinal();
            i++;
        }
        projectDto.setSkills(skills);
        int[] interests = new int[project.getInterests().size()];
        i = 0;
        for (InterestEntity interest : project.getInterests()) {
            interests[i] = interest.getType().ordinal();
            i++;
        }
        projectDto.setInterests(interests);
        return projectDto;
    }
}
