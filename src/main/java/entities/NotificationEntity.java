package entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@NamedQuery(name = "NotificationEntity.findNotificationByUser", query = "SELECT n FROM NotificationEntity n WHERE n.user = :user")
@NamedQuery(name = "NotificationEntity.findNotificationByProject", query = "SELECT n FROM NotificationEntity n WHERE n.project = :project")

public class NotificationEntity {
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

}
