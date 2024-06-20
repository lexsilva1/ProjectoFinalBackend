package dto;

import java.time.LocalDateTime;

public class NotificationDto {

    private String type;
    private int userId;
    private String projectName;
    private boolean isRead;
    private int notificationId;
    private LocalDateTime time;
    private int otherUserId;

    public NotificationDto() {
    }

    public NotificationDto(String type, int userId, String projectName, boolean isRead, LocalDateTime time) {
        this.type = type;
        this.userId = userId;
        this.projectName = projectName;
        this.isRead = isRead;
        this.time = time;
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
}
