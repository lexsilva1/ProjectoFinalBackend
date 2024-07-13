package dto;

import java.time.LocalDateTime;
/**
 * The DTO class for the notification.
 */
public class NotificationDto {

    private String type;
    private int userId;
    private String projectName;
    private boolean isRead;
    private int notificationId;
    private LocalDateTime time;
    private int otherUserId;
    private int taskId;
    private String taskName;
    private String status;
    private boolean seen;

    public NotificationDto() {
    }

    public NotificationDto(String type, int userId, String projectName, boolean isRead, LocalDateTime time) {
        this.type = type;
        this.userId = userId;
        this.projectName = projectName;
        this.isRead = isRead;
        this.time = time;
        this.seen = false;
    }
    public NotificationDto(String type,int userId, String projectName, boolean isRead, String status){
        this.type = type;
        this.userId = userId;
        this.projectName = projectName;
        this.isRead = isRead;
        this.status = status;
        this.seen = false;
        this.time = LocalDateTime.now();
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(int otherUserId) {
        this.otherUserId = otherUserId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
