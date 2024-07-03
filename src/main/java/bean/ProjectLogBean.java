package bean;

import dao.ProjectLogDao;
import dto.ProjectLogDto;
import entities.ProjectEntity;
import entities.ProjectLogEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

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
