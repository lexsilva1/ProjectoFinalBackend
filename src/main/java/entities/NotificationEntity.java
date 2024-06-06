package entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@NamedQuery(name = "NotificationEntity.findNotificationByUser", query = "SELECT n FROM NotificationEntity n WHERE n.user = :user")
@NamedQuery(name = "NotificationEntity.findNotificationByProject", query = "SELECT n FROM NotificationEntity n WHERE n.project = :project")
@NamedQuery(name = "NotificationEntity.findUnreadNotificationsByUser", query = "SELECT n FROM NotificationEntity n WHERE n.user = :user AND n.isRead = false")
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
    private String message;
    @Column (name = "is_read", nullable = false, unique = false)
    private boolean isRead;
    @Column (name = "time", nullable = false, unique = false)
    private LocalDateTime time;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
}
