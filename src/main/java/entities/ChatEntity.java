package entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "chat")
@NamedQuery(name = "ChatEntity.getChatByProject", query = "SELECT c FROM ChatEntity c WHERE c.project = :project")
@NamedQuery(name = "ChatEntity.getAllChatsByProject", query = "SELECT c FROM ChatEntity c WHERE c.project = :project ORDER BY c.time DESC")
public class ChatEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id", nullable = false, unique = true, updatable = false)
    private int chatId;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserEntity sender;
    @ManyToOne
    @JoinColumn (name = "project_id")
    private ProjectEntity project;
    @Column(name = "message", nullable = false, unique = false)
    private String message;
    @Column(name = "time", nullable = false, unique = false)
    private LocalDateTime time;
    @Column(name = "is_read", nullable = false, unique = false)
    private boolean isRead;



}
