package dto;

import java.util.Set;
/**
 * The DTO class for the project tasks.
 */
public class ProjectTasksDto {
    private String projectName;
    private Set<TaskDto> tasks;


    public ProjectTasksDto() {
    }

    public ProjectTasksDto(String projectName, Set<TaskDto> tasks) {
        this.projectName = projectName;
        this.tasks = tasks;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Set<TaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(Set<TaskDto> tasks) {
        this.tasks = tasks;
    }
}
