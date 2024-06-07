package entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "project_tasks")
public class ProjectTaskEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;
    @JoinColumn(name = "task_id", nullable = false, unique = true, updatable = false)
    private int task_id;
    @JoinColumn(name = "projectName", nullable = false, unique = true, updatable = false)
    private String projectName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProject_id(String projectName) {
        this.projectName = projectName;
    }
}
