package dto;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate creationDate;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getExternalExecutors() {
        return externalExecutors;
    }

    public void setExternalExecutors(String externalExecutors) {
        this.externalExecutors = externalExecutors;
    }
}
