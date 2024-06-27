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
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false, unique = true, updatable = false)
    private TaskEntity task;
    @ManyToOne
    @JoinColumn(name = "projectName", nullable = false, unique = true, updatable = false)
    private ProjectEntity project;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaskEntity getTask() {
        return task;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }
}
