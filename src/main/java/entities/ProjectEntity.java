package entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "Projects")
public class ProjectEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;
    @Column(name = "name", nullable = false, unique = true, updatable = false)
    private String name;
    @Column(name = "description", nullable = false, unique = true, updatable = false)
    private String description;
    @OneToMany(mappedBy = "project")
    private Set<ProjectUserEntity> projectUsers;
    @ManyToMany
    @JoinTable(
            name = "project_skills",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<SkillEntity> skills;
    @ManyToMany
    @JoinTable(
            name = "project_interests",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
    private Set<InterestEntity> interests;
    @Enumerated(EnumType.STRING)
    @Column( name = "status", nullable = false, unique = true, updatable = false)
    Status status;
    @ManyToOne
    @JoinColumn (name = "lab_id", nullable = false, unique = true, updatable = false)
    private LabEntity lab;

    public enum Status {
        PLANNING(100),
        READY(200),
        APPROVED(300),
        IN_PROGRESS(400),
        COMPLETED(500),
        CANCELLED(0);

        public final int value;

        Status(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }

    }
}
