package dto;

import entities.ProjectEntity;
import entities.ProjectLogEntity;
import entities.UserEntity;

import java.time.LocalDateTime;

public class ProjectLogDto {
    private int id;
    private String log;
    private LocalDateTime time;
    private String type;
    private String project;
    private int userId;
    private int otherUserId;
    private int taskId;
    private int ResourceId;
    private String Status;
    public ProjectLogDto() {
    }
    public ProjectLogDto(UserEntity user, ProjectEntity project,String log) {
        this.userId = user.getId();
        this.project = project.getName();
        this.time = LocalDateTime.now();
        this.log = log;
        this.type = "OTHER";

    }

    public int getId() {
        return id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(int otherUserId) {
        this.otherUserId = otherUserId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getResourceId() {
        return ResourceId;
    }

    public void setResourceId(int resourceId) {
        ResourceId = resourceId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
