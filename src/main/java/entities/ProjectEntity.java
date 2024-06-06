package entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "Projects")
@NamedQuery(name = "ProjectEntity.findProjectByName", query = "SELECT p FROM ProjectEntity p WHERE p.name = :name")
@NamedQuery(name = "ProjectEntity.findProjectsByLab", query = "SELECT p FROM ProjectEntity p WHERE p.lab = :lab")
@NamedQuery(name = "ProjectEntity.findProjectBySkill", query = "SELECT p FROM ProjectEntity p WHERE p.skills = :skills")
@NamedQuery(name = "ProjectEntity.findProjectByInterest", query = "SELECT p FROM ProjectEntity p WHERE p.interests = :interests")
@NamedQuery(name = "ProjectEntity.findProjectByStatus", query = "SELECT p FROM ProjectEntity p WHERE p.status = :status")
@NamedQuery(name = "ProjectEntity.findProjectByCreator", query = "SELECT p FROM ProjectEntity p WHERE p.creator = :creator")
@NamedQuery(name = "ProjectEntity.findAllProjects", query = "SELECT p FROM ProjectEntity p")
@NamedQuery(name = "ProjectEntity.findProjectSkills", query = "SELECT p.skills FROM ProjectEntity p WHERE p = :project")
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
    @Column (name="maxMembers", nullable = false, unique = false, updatable = true )
    int maxMembers;
    @OneToOne
    @PrimaryKeyJoinColumn (name = "creator_id", referencedColumnName = "id")
    UserEntity creator;
    @Column (name = "image", nullable = true, unique = false, updatable = true)
    private String image;
    @Column (name = "created_at", nullable = false, unique = false, updatable = false)
    private LocalDate createdAt;
    @Column (name = "start_date", nullable = true, unique = false, updatable = true)
    private LocalDate startDate;
    @Column ( name = "in_progress_date", nullable = true, unique = false, updatable = true)
    private LocalDate inProgressDate;
    @Column (name = "end_date", nullable = true, unique = false, updatable = true)
    private LocalDate endDate;
    @Column (name = "concluson_date", nullable = true, unique = false, updatable = true)
    private LocalDate conclusionDate;
    @ManyToMany
    @JoinTable(
            name = "project_resources",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id")
    )
    private Set<ResourceEntity> resources;




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

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ProjectUserEntity> getProjectUsers() {
        return projectUsers;
    }

    public void setProjectUsers(Set<ProjectUserEntity> projectUsers) {
        this.projectUsers = projectUsers;
    }

    public Set<SkillEntity> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillEntity> skills) {
        this.skills = skills;
    }

    public Set<InterestEntity> getInterests() {
        return interests;
    }

    public void setInterests(Set<InterestEntity> interests) {
        this.interests = interests;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LabEntity getLab() {
        return lab;
    }

    public void setLab(LabEntity lab) {
        this.lab = lab;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
    }

    public UserEntity getCreator() {
        return creator;
    }

    public void setCreator(UserEntity creator) {
        this.creator = creator;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getInProgressDate() {
        return inProgressDate;
    }

    public void setInProgressDate(LocalDate inProgressDate) {
        this.inProgressDate = inProgressDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getConclusionDate() {
        return conclusionDate;
    }

    public void setConclusionDate(LocalDate conclusionDate) {
        this.conclusionDate = conclusionDate;
    }

    public Set<ResourceEntity> getResources() {
        return resources;
    }

    public void setResources(Set<ResourceEntity> resources) {
        this.resources = resources;
    }
}
