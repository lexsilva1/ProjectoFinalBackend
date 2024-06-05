package entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;
import java.io.Serializable;

@Entity
@Table(name = "tasks")
/**
 * TaskEntity class
 */
@NamedQuery(name = "TaskEntity.findTaskByProject", query = "SELECT t FROM TaskEntity t WHERE t.project = :project")
@NamedQuery(name = "TaskEntity.findTaskByUser", query = "SELECT t FROM TaskEntity t WHERE t.responsibleUser = :user")
@NamedQuery(name = "TaskEntity.findTaskByStatus", query = "SELECT t FROM TaskEntity t WHERE t.status = :status")
@NamedQuery(name = "TaskEntity.findTaskByDate", query = "SELECT t FROM TaskEntity t WHERE t.startDate = :date")
@NamedQuery(name = "TaskEntity.findTaskByTitle", query = "SELECT t FROM TaskEntity t WHERE t.title = :title")

public class TaskEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column( name = "id", nullable = false, unique = true )
    int id;

    @Column (name = "title", nullable = false, unique = false)
    String title;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private TaskEntity task;

    @ManyToOne
    @JoinColumn(name = "parent_task_id")
    private TaskEntity parentTask;

    @OneToMany(mappedBy = "parentTask")
    private Set<TaskEntity> dependencies;
    @Column (name = "description", nullable = false, unique = false)
    String description;
    @Column (name = "status", nullable = false, unique = false)
    String status;
    @Column (name = "Start_date", nullable = false, unique = false)
    LocalDate startDate;
    @Column (name = "End_date", nullable = false, unique = false)
    LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserEntity createdBy;
    @ManyToOne
    @JoinColumn(name = "responsible_user_id")
    private UserEntity responsibleUser;

    @Column (name = "external_executors", nullable = true, unique = false)
    String externalExecutors;
    @ManyToMany
    @JoinTable(
            name = "task_project_users",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
            private Set<UserEntity> projectUsers;

        public UserEntity getResponsibleUser() {
            return responsibleUser;
        }

        public void setResponsibleUser(UserEntity responsibleUser) {
            this.responsibleUser = responsibleUser;
        }
}



