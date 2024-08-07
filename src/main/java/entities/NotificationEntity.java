package entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * The entity class for the notifications table.
 */
@Entity
@Table(name = "notifications")
@NamedQuery(name = "NotificationEntity.findNotificationByUser", query = "SELECT n FROM NotificationEntity n WHERE n.user = :user")
@NamedQuery(name = "NotificationEntity.findNotificationByProject", query = "SELECT n FROM NotificationEntity n WHERE n.project = :project")
@NamedQuery(name = "NotificationEntity.findUnreadNotificationsByUser", query = "SELECT n FROM NotificationEntity n WHERE n.user = :user AND n.isRead = false")
@NamedQuery(name = "NotificationEntity.findNotificationById", query = "SELECT n FROM NotificationEntity n WHERE n.id = :id")
@NamedQuery(name = "NotificationEntity.findLastNotificationIdByUser", query = "SELECT n.id FROM NotificationEntity n WHERE n.user = :user ORDER BY n.id DESC")
@NamedQuery(name= "NotificationEntity.findNotificationByProjectAndType", query = "SELECT n FROM NotificationEntity n WHERE n.project = :project AND n.type = :type")
@NamedQuery(name = "NotificationEntity.findNotificationByProjectAndUserAndType", query = "SELECT n FROM NotificationEntity n WHERE n.project = :project AND n.user = :user AND n.type = :type")
@NamedQuery(name = "NotificationEntity.findLastNotificationByUserAndType", query = "SELECT n FROM NotificationEntity n WHERE n.user = :user AND n.type = :type ORDER BY n.id DESC")
public class NotificationEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @Column (name = "message", nullable = false, unique = false)
    private NotificationType type;
    @Column (name = "is_read", nullable = false, unique = false)
    private boolean isRead;
    @Column (name = "is_seen", nullable = false, unique = false)
    private boolean isSeen;
    @Column (name = "time", nullable = false, unique = false)
    private LocalDateTime time;
    @ManyToOne
    @JoinColumn (name = "other_user_id", nullable = true, unique = false)
    private UserEntity otherUser;
    @ManyToOne
    @JoinColumn (name = "task_id", nullable = true, unique = false)
    private TaskEntity task;


    public enum NotificationType {
        INVITE,
        APPLY,
        ACCEPT_APPLICATION,
        ACCEPTED,
        REJECT,
        REJECTED,
        EXCLUDE,
        PROMOTED,
        DEMOTED,
        USER_LEFT,
        TASK_ASSIGN,
        TASK_EXECUTOR,
        TASK_DOING,
        TASK_COMPLETE,
        PROJECT_COMPLETE,
        PROJECT_CANCEL,
        PROJECT_APPROVED,
        PROJECT_READY,
        PROJECT_DOING,
        PROJECT_REJECTED,
        PROJECT_INFO,
        PROJECT_FULL,
        PROJECT_USERS_EXCEEDED,
        CHAT,
        DEMOTED_ADMIN,
        PROMOTED_ADMIN,

    }

    public NotificationEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getOtherUser() {
        return otherUser;
    }

    public void setOtherUser(UserEntity otherUser) {
        this.otherUser = otherUser;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType message) {
        this.type = message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public TaskEntity getTask() {
        return task;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }
}
