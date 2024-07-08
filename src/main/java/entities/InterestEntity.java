package entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "interests")
@NamedQuery(name = "InterestEntity.findInterestByName", query = "SELECT i FROM InterestEntity i WHERE i.name = :name")
@NamedQuery(name = "InterestEntity.findInterestByType", query = "SELECT i FROM InterestEntity i WHERE i.interestType = :interestType")
@NamedQuery(name = "InterestEntity.findAllInterests", query = "SELECT i FROM InterestEntity i")
@NamedQuery(name = "InterestEntity.findInterestsByName", query = "SELECT i FROM InterestEntity i WHERE i.name IN :names")
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
    @ManyToMany(mappedBy = "interests")
    private Set<ProjectEntity> projects;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    public void setInterestType(InterestType interestType) {
        this.interestType = interestType;
    }

    public Set<ProjectEntity> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectEntity> projects) {
        this.projects = projects;
    }
}
