package dto;

import java.time.LocalDateTime;

public class GroupChatDto {
    private String projectName;
    private String sender;
    private int senderId;
    private String message;
    private LocalDateTime time;

    public GroupChatDto() {
    }

    public GroupChatDto(String projectName, String sender,int senderId, String message, LocalDateTime time) {
        this.projectName = projectName;
        this.sender = sender;
        this.message = message;
        this.time = time;
        this.senderId = senderId;
    }

    public String getProjectName() {
        return projectName;
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
