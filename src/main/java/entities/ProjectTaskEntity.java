package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "project_tasks")
public class ProjectTaskEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;
    @JoinColumn(name = "task_id", nullable = false, unique = true, updatable = false)
    private int task_id;
    @JoinColumn(name = "project_id", nullable = false, unique = true, updatable = false)
    private int project_id;

}
