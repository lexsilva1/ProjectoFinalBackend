package entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.io.Serializable;

@Entity
@Table(name = "tasks")
/**
 * TaskEntity class
 */
@NamedQuery(name = "TaskEntity.findTasksByUser", query = "SELECT t FROM TaskEntity t WHERE t.responsibleUser = :user")
@NamedQuery(name = "TaskEntity.findTasksByStatus", query = "SELECT t FROM TaskEntity t WHERE t.status = :status")
@NamedQuery(name = "TaskEntity.findTaskByDate", query = "SELECT t FROM TaskEntity t WHERE t.startDate = :date")
@NamedQuery(name = "TaskEntity.findTaskByTitle", query = "SELECT t FROM TaskEntity t WHERE t.title = :title")
@NamedQuery(name = "TaskEntity.findTaskByProject", query = "SELECT t FROM TaskEntity t WHERE t.task = :task")
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
    @Column (name = "dependencies", nullable = true, unique = false)
    private Set<TaskEntity> dependencies;
    @Column (name = "description", nullable = false, unique = false)
    String description;
    @Column (name = "status", nullable = false, unique = false)
    Status status;
    @Column (name = "Start_date", nullable = false, unique = false)
    LocalDateTime startDate;
    @Column (name = "End_date", nullable = false, unique = false)
    LocalDateTime endDate;

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
            private Set<UserEntity> taskUsers;
    @Column (name = "creation_date", nullable = false, unique = false)
    LocalDateTime creationDate;

        public UserEntity getResponsibleUser() {
            return responsibleUser;
        }

        public void setResponsibleUser(UserEntity responsibleUser) {
            this.responsibleUser = responsibleUser;
        }
        public enum Status {
            NOT_STARTED,
            IN_PROGRESS,
            COMPLETED,
            CANCELLED
        }
        public TaskEntity() {
        }
        public Status getStatus() {
            return status;
        }
        public void setStatus(Status status) {
            this.status = status;
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

    public TaskEntity getTask() {
        return task;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }

    public TaskEntity getParentTask() {
        return parentTask;
    }

    public void setParentTask(TaskEntity parentTask) {
        this.parentTask = parentTask;
    }

    public Set<TaskEntity> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Set<TaskEntity> dependencies) {
        this.dependencies = dependencies;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    public String getExternalExecutors() {
        return externalExecutors;
    }

    public void setExternalExecutors(String externalExecutors) {
        this.externalExecutors = externalExecutors;
    }

    public Set<UserEntity> getTaskUsers() {
        return taskUsers;
    }

    public void setTaskUsers(Set<UserEntity> projectUsers) {
        this.taskUsers = projectUsers;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}



