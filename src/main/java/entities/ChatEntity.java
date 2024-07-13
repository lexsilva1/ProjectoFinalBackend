package entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The entity class for the chat table.

 */
@Entity
@Table(name = "chat")
@NamedQuery(name = "ChatEntity.getChatByProject", query = "SELECT c FROM ChatEntity c WHERE c.project = :project")
@NamedQuery(name = "ChatEntity.getAllChatsByProject", query = "SELECT c FROM ChatEntity c WHERE c.project = :project ORDER BY c.time ASC ")
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

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
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
}
