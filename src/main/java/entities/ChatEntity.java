package entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "chat")
@Entity
@NamedQuery(name = "ChatEntity.getChatByProject", query = "SELECT c FROM ChatEntity c WHERE c.project = :project")
@NamedQuery(name = "ChatEntity.getChatByReceiver", query = "SELECT c FROM ChatEntity c WHERE c.receiver = :receiver")
public class ChatEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id", nullable = false, unique = true, updatable = false)
    private int chatId;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserEntity sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private UserEntity receiver;
    @ManyToOne
    @JoinColumn (name = "project_id")
    private ProjectEntity project;
    @Column(name = "message", nullable = false, unique = false)
    private String message;
    @Column(name = "time", nullable = false, unique = false)
    private LocalDateTime time;
    @Column(name = "is_read", nullable = false, unique = false)
    private boolean isRead;


    public void setReceiver(UserEntity receiver) {
        if (this.project != null && receiver != null) {
            throw new IllegalArgumentException("Receiver and project cannot both be set at the same time");
        }
        this.receiver = receiver;
    }

    public void setProject(ProjectEntity project) {
        if (this.receiver != null && project != null) {
            throw new IllegalArgumentException("Receiver and project cannot both be set at the same time");
        }
        this.project = project;
    }
}
