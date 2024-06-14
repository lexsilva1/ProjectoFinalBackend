package dto;

import java.time.LocalDateTime;

public class LastMessageDto {
    private MessageUserDto sender;
    private String message;
    private LocalDateTime time;
    private boolean isRead;


    public LastMessageDto() {
    }


    public MessageUserDto getSender() {
        return sender;
    }

    public void setSender(MessageUserDto sender) {
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

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
