package dto;

import java.time.LocalDateTime;

public class MessageDto {
    private String message;
    private MessageUserDto sender;
    private MessageUserDto receiver;
    private LocalDateTime time;
    private boolean isRead;

    public MessageDto() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageUserDto getSender() {
        return sender;
    }

    public void setSender(MessageUserDto sender) {
        this.sender = sender;
    }

    public MessageUserDto getReceiver() {
        return receiver;
    }

    public void setReceiver(MessageUserDto receiver) {
        this.receiver = receiver;
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

    public void setIsRead(boolean isRead) {
        isRead = isRead;
    }
}
