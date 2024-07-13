package entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;
/**
 * The entity class for the Skills table.
 */
@Entity
@Table(name = "Skills")
@NamedQuery(name = "SkillEntity.findSkillByName", query = "SELECT s FROM SkillEntity s WHERE s.name = :name")
@NamedQuery(name = "SkillEntity.findSkillByType", query = "SELECT s FROM SkillEntity s WHERE s.skillType = :skillType")
@NamedQuery(name = "SkillEntity.findAllSkills", query = "SELECT s FROM SkillEntity s")
@NamedQuery(name = "SkillEntity.findSkillsByName", query = "SELECT s FROM SkillEntity s WHERE s.name IN :names")
public class SkillEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;
    @Column
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "skill_type")
    private SkillType skillType;
    @ManyToMany(mappedBy = "skills")
    private Set<UserEntity> users;
    @ManyToMany(mappedBy = "skills", fetch = FetchType.EAGER)
    private Set<ProjectEntity> projects;


    public enum SkillType {
        KNOWLEDGE,
        SOFTWARE,
        HARDWARE,
        TOOLS,
    }
    public SkillType getSkillType() {
        return skillType;
    }
    public SkillEntity() {
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

    public void setSkillType(SkillType skillType) {
        this.skillType = skillType;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    public Set<ProjectEntity> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectEntity> projects) {
        this.projects = projects;
    }
}
