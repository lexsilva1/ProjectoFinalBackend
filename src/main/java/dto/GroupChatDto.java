package dto;

import java.time.LocalDateTime;
/**
 * The DTO class for the group chat.
 */
public class GroupChatDto {
    private String projectName;
    private String sender;
    private int senderId;
    private String message;
    private LocalDateTime time;
    private String userPhoto;

    public GroupChatDto() {
    }

    public GroupChatDto(String projectName, String sender,int senderId, String userPhoto, String message) {
        this.projectName = projectName;
        this.sender = sender;
        this.message = message;
        this.time = LocalDateTime.now();
        this.senderId = senderId;
        this.userPhoto = userPhoto;

    }

    public String getProjectName() {
        return projectName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }
}
