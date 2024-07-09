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
            log1.setType(ProjectLogEntity.LogType.PROJECT_CREATED);
            log1.setDate(LocalDateTime.now());
            projectLogDao.persist(log1);
        }
        if(projectLogDao.findProjectLogByProjectId(2) == null) {
            ProjectLogEntity log2 = new ProjectLogEntity();
            log2.setProject_id(2);
            log2.setLog("Project created");
            log2.setUser_id(1);
            log2.setType(ProjectLogEntity.LogType.PROJECT_CREATED);
            log2.setDate(LocalDateTime.now());
            projectLogDao.persist(log2);
        }
        if(projectLogDao.findProjectLogByProjectId(3) == null) {
            ProjectLogEntity log3 = new ProjectLogEntity();
            log3.setProject_id(3);
            log3.setLog("Project created");
            log3.setUser_id(1);
            log3.setType(ProjectLogEntity.LogType.PROJECT_CREATED);
            log3.setDate(LocalDateTime.now());
            projectLogDao.persist(log3);
        }
        if(projectLogDao.findProjectLogByProjectId(4) == null) {
            ProjectLogEntity log4 = new ProjectLogEntity();
            log4.setProject_id(4);
            log4.setLog("Project created");
            log4.setUser_id(1);
            log4.setType(ProjectLogEntity.LogType.PROJECT_CREATED);
            log4.setDate(LocalDateTime.now());
            projectLogDao.persist(log4);
        }
        if(projectLogDao.findProjectLogByProjectId(5) == null) {
            ProjectLogEntity log5 = new ProjectLogEntity();
            log5.setProject_id(5);
            log5.setLog("Project created");
            log5.setUser_id(1);
            log5.setType(ProjectLogEntity.LogType.PROJECT_CREATED);
            log5.setDate(LocalDateTime.now());
            projectLogDao.persist(log5);
        }
        if(projectLogDao.findProjectLogByProjectId(6) == null) {
            ProjectLogEntity log6 = new ProjectLogEntity();
            log6.setProject_id(6);
            log6.setLog("Project created");
            log6.setUser_id(1);
            log6.setType(ProjectLogEntity.LogType.PROJECT_CREATED);
            log6.setDate(LocalDateTime.now());
            projectLogDao.persist(log6);
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
        projectLogDto.setStatus(projectLogEntity.getStatus());
        return projectLogDto;
    }
    public boolean createProjectLog(ProjectLogDto projectLogDto) {
        ProjectLogEntity projectLogEntity = new ProjectLogEntity();
        projectLogEntity.setProject_id(projectBean.findProjectByName(projectLogDto.getProject()).getId());
        projectLogEntity.setLog(projectLogDto.getLog());
        projectLogEntity.setUser_id(projectLogDto.getUserId());
        projectLogEntity.setOther_user_id(projectLogDto.getOtherUserId());
        projectLogEntity.setTask_id(projectLogDto.getTaskId());
        projectLogEntity.setProject_resource_id(projectLogDto.getResourceId());
        projectLogEntity.setDate(LocalDateTime.now());
        projectLogEntity.setType(ProjectLogEntity.LogType.valueOf(projectLogDto.getType()));
        projectLogEntity.setStatus(projectLogDto.getStatus());
        return projectLogDao.create(projectLogEntity);

    }
}
