package entities;

import dao.UserDao;
import dto.MessageDto;
import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@NamedQuery(name = "MessageEntity.findMessageByUser", query = "SELECT m FROM MessageEntity m WHERE m.sender = :sender and m.receiver = :receiver")
public class MessageEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserEntity sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private UserEntity receiver;
    @Column(name = "message", nullable = false, unique = false)
    private String message;
    @Column(name = "time", nullable = false, unique = false)
    private LocalDateTime time;
    @Column(name = "is_read", nullable = false, unique = false)
    private boolean isRead;

    public MessageEntity() {
    }
    public MessageEntity(MessageDto messageDto, UserEntity sender, UserEntity receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = messageDto.getMessage();
        this.time = messageDto.getTime();
        this.isRead = messageDto.isRead();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    public UserEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(UserEntity receiver) {
        this.receiver = receiver;
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
