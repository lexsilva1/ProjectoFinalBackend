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
    @Column (name = "other_user_id", nullable = true, unique = false)
    private int other_user_id;
    @Column (name = "task_id", nullable = true, unique = false)
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
        OTHER
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getOther_user_id() {
        return other_user_id;
    }

    public void setOther_user_id(int other_user_id) {
        this.other_user_id = other_user_id;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public LogType getType() {
        return type;
    }

    public void setType(LogType type) {
        this.type = type;
    }
}
