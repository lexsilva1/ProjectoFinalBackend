package bean;

import dao.ProjectLogDao;
import dto.ProjectLogDto;
import entities.ProjectEntity;
import entities.ProjectLogEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ProjectLogBean {
    @Inject
    ProjectLogDao projectLogDao;
    @EJB
    ResourceBean ResourceBean;
    @EJB
    ProjectBean projectBean;

    public ProjectLogBean() {
    }
    public void createDefaultLogs() {
        if(projectLogDao.findProjectLogByProjectId(1) == null) {
            ProjectLogEntity log1 = new ProjectLogEntity();
            log1.setProject_id(1);
            log1.setLog("Project created");
            log1.setUser_id(1);
            log1.setType(ProjectLogEntity.LogType.UPDATE_PROJECT_STATUS);
            log1.setDate(LocalDateTime.now());
            projectLogDao.persist(log1);
        }
        if(projectLogDao.findProjectLogByProjectId(2) == null) {
            ProjectLogEntity log2 = new ProjectLogEntity();
            log2.setProject_id(2);
            log2.setLog("Project created");
            log2.setUser_id(1);
            log2.setType(ProjectLogEntity.LogType.UPDATE_PROJECT_STATUS);
            log2.setDate(LocalDateTime.now());
            projectLogDao.persist(log2);
        }
        if(projectLogDao.findProjectLogByProjectId(3) == null) {
            ProjectLogEntity log3 = new ProjectLogEntity();
            log3.setProject_id(3);
            log3.setLog("Project created");
            log3.setUser_id(1);
            log3.setType(ProjectLogEntity.LogType.UPDATE_PROJECT_STATUS);
            log3.setDate(LocalDateTime.now());
            projectLogDao.persist(log3);
        }
    }

    public List<ProjectLogDto> getProjectLogs(int projectId) {
        List<ProjectLogEntity> logEntities = projectLogDao.findProjectLogsByProjectId(projectId);
        List<ProjectLogDto> logDtos = new ArrayList<>();
        for (ProjectLogEntity logEntity : logEntities) {
            logDtos.add(convertToDto(logEntity));
        }
        return logDtos;
    }

    public ProjectLogDto convertToDto(ProjectLogEntity projectLogEntity) {
        ProjectEntity project = projectBean.findProjectById(projectLogEntity.getProject_id());
        ProjectLogDto projectLogDto = new ProjectLogDto();
        projectLogDto.setId(projectLogEntity.getId());
        projectLogDto.setProject(project.getName());
        projectLogDto.setTime(projectLogEntity.getDate());
        projectLogDto.setLog(projectLogEntity.getLog());
        projectLogDto.setUserId(projectLogEntity.getUser_id());
        projectLogDto.setOtherUserId(projectLogEntity.getOther_user_id());
        projectLogDto.setTaskId(projectLogEntity.getTask_id());
        projectLogDto.setResourceId(projectLogEntity.getProject_resource_id());
        projectLogDto.setType(projectLogEntity.getType().name());
        return projectLogDto;
    }
}
