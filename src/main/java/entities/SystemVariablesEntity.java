package entities;

import jakarta.persistence.*;

import java.io.Serializable;
/**
 * The entity class for the system_variables table.
 */
@Entity
@Table(name = "system_variables")
public class SystemVariablesEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;
    @Column(name = "timeout", nullable = false, unique = true)
    private int timeout;
    @Column(name = "max_users", nullable = false, unique = true)
    private int maxUsers;



    public SystemVariablesEntity() {
    }
    public SystemVariablesEntity(int timeout, int maxUsers) {
        this.timeout = timeout;
        this.maxUsers = maxUsers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }
}
