package dto;

import java.time.LocalDateTime;
import java.util.Set;

public class TaskDto {

    private int id;
    private String title;
    private String description;
    private String projectName;
    private int CreatorId;
    private int ResponsibleId;
    private Set<Integer> dependencies;
    private Set<Integer> users;
    private String Status;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime creationDate;
    private String externalExecutors;
    public TaskDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getCreatorId() {
        return CreatorId;
    }

    public void setCreatorId(int creatorId) {
        CreatorId = creatorId;
    }

    public int getResponsibleId() {
        return ResponsibleId;
    }

    public void setResponsibleId(int responsibleId) {
        ResponsibleId = responsibleId;
    }

    public Set<Integer> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Set<Integer> dependencies) {
        this.dependencies = dependencies;
    }

    public Set<Integer> getUsers() {
        return users;
    }

    public void setUsers(Set<Integer> users) {
        this.users = users;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getExternalExecutors() {
        return externalExecutors;
    }

    public void setExternalExecutors(String externalExecutors) {
        this.externalExecutors = externalExecutors;
    }
}
