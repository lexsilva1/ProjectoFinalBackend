package entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Tokens")
@NamedQuery(name = "TokenEntity.findTokenByToken", query = "SELECT t FROM TokenEntity t WHERE t.token = :token")
@NamedQuery(name = "TokenEntity.findUSerByToken" , query = "SELECT t.user FROM TokenEntity t WHERE t.token = :token")
@NamedQuery(name = "TokenEntity.findTimedOutTokens", query = "SELECT t FROM TokenEntity t WHERE t.creationDate < :time AND t.isUsed = false")
@NamedQuery(name = "TokenEntity.findTokenByUserAndType", query = "SELECT t FROM TokenEntity t WHERE t.user = :user AND t.type = :type")
@NamedQuery(name = "TokenEntity.findTokensByUser", query = "SELECT t FROM TokenEntity t WHERE t.user = :user")
@NamedQuery(name = "TokenEntity.findUserByToken", query = "SELECT t.user FROM TokenEntity t WHERE t.token = :token")
@NamedQuery(name = "TokenEntity.findActiveTokensByUser", query = "SELECT t FROM TokenEntity t WHERE t.user = :user AND t.isUsed = false")
public class TokenEntity implements Serializable {
    @Id
    @Column(name = "token", nullable = false, unique = true, updatable = false)
    private String token;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, unique = false, updatable = false)
    private UserEntity user;
    @Column(name = "creationDate", nullable = false, unique = false)
    private LocalDateTime creationDate;
    @Column(name = "type", nullable = false, unique = false)
    private TokenType type;
    @Column(name = "isUsed", nullable = false, unique = false)
    private boolean isUsed;
    @Column(name = "lastActivity", nullable =true, unique = false)
    private LocalDateTime lastActivity;

    public enum TokenType {
        LOGIN,
        PASSWORD_RESET,
        REGISTER
    }
    public TokenEntity() {
    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(LocalDateTime lastActivity) {
        this.lastActivity = lastActivity;
    }
}

