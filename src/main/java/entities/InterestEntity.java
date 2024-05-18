package entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "interests")
public class InterestEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;
    @Column(name = "name", nullable = false, unique = true, updatable = false)
    private String name;
    @ManyToMany(mappedBy = "interests")
    private Set<UserEntity> users;
    @Column (name = "interest_type", nullable = false, unique = false)
    private InterestType interestType;
    public enum InterestType {
        KNOWLEDGE,
        CAUSES,
        THEMES,
        OTHER,
    }
    public InterestType getInterestType() {
        return interestType;
    }
    public InterestEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
