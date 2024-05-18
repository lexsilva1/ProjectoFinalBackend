package entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "project_logs")
public class ProjectLogEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;
    @Column(name = "project_id", nullable = false, unique = true, updatable = false)
    private int project_id;
    @Column (name = "user_id", nullable = false, unique = false)
    private int user_id;
    @Column (name = "other_user_id", nullable = false, unique = false)
    private int other_user_id;
    @Column (name = "task_id", nullable = false, unique = false)
    private int task_id;
    @Column (name = "date", nullable = false, unique = false)
    LocalDateTime date;
    @Column (name = "log", nullable = false, unique = false)
    private String log;
    @Column (name = "type", nullable = false, unique = false)
    private LogType type;

    public enum LogType {
        CREATE_TASK,
        UPDATE_TASK_STATUS,
        DELETE_TASK,
        APPROVE_USER,
        REJECT_USER,
        INVITE_USER,
        UPDATE_USER_ROLE,
        UPDATE_PROJECT_STATUS,
        UPDATE_PROJECT_DETAILS,
        REQUEST_RESOURCE,
    }
}
