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
    @JoinColumn(name = "project_id", nullable = false, unique = true, updatable = false)
    private int project_id;

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

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }
}
