package dto;

import java.time.LocalDateTime;
/**
 * The DTO class for the last message.
 */
public class LastMessageDto {
    private MessageUserDto sender;
    private String message;
    private LocalDateTime time;
    private boolean isRead;
    private String type;


    public LastMessageDto() {
    }
    public LastMessageDto(MessageUserDto sender, String message) {
        this.sender = sender;
        this.message = message;
        this.time = LocalDateTime.now();
        this.type = "LAST_MESSAGE";
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
