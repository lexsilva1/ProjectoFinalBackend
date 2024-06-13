package entities;

import dao.UserDao;
import dto.MessageDto;
import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")

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

}
