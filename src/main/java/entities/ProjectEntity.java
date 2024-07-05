package entities;

import dto.ProjectDto;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "Projects")
@NamedQuery(name = "ProjectEntity.findProjectByName", query = "SELECT p FROM ProjectEntity p WHERE p.name = :name")
@NamedQuery(name = "ProjectEntity.findProjectsByLab", query = "SELECT p FROM ProjectEntity p WHERE p.lab = :lab")
@NamedQuery(name = "ProjectEntity.findProjectBySkill", query = "SELECT p FROM ProjectEntity p WHERE p.skills = :skills")
@NamedQuery(name = "ProjectEntity.findProjectByInterest", query = "SELECT p FROM ProjectEntity p WHERE p.interests = :interests")
@NamedQuery(name = "ProjectEntity.findProjectByStatus", query = "SELECT p FROM ProjectEntity p WHERE p.status = :status")
@NamedQuery(name = "ProjectEntity.findProjectTasks", query = "SELECT p.tasks FROM ProjectEntity p WHERE p = :project")
@NamedQuery(name = "ProjectEntity.findAllProjects", query = "SELECT p FROM ProjectEntity p")
@NamedQuery(name = "ProjectEntity.findProjectSkills", query = "SELECT p.skills FROM ProjectEntity p WHERE p = :project")
@NamedQuery(name = "ProjectEntity.findProjectById", query = "SELECT p FROM ProjectEntity p WHERE p.id = :id")
@NamedQuery(name = "ProjectEntity.findMostUsedResource", query = "SELECT p.resources FROM ProjectEntity p WHERE p = :project")
@NamedQuery(name = "ProjectEntity.findProjectByTask", query = "SELECT p FROM ProjectEntity p WHERE :task MEMBER OF p.tasks")
@NamedQuery(name = "ProjectEntity.findProjectsByMaxMembers", query = "SELECT p FROM ProjectEntity p WHERE p.maxMembers > :maxMembers")
public class ProjectEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;
    @Column(name = "name", nullable = false, unique = true, updatable = false)
    private String name;
    @Column(name = "description", nullable = false, unique = false, updatable = true)
    private String description;
    @OneToMany(mappedBy = "project" , fetch = FetchType.EAGER)
    private Set<ProjectUserEntity> projectUsers;
    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_skills",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<SkillEntity> skills;
    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_interests",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id")
    )

    private Set<InterestEntity> interests;

    @Enumerated(EnumType.STRING)
    @Column( name = "status", nullable = false, unique = false, updatable = true)
    Status status;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "lab_id", nullable = false, unique = false, updatable = false)
    private LabEntity lab;
    @Column (name="maxMembers", nullable = false, unique = false, updatable = true )
    int maxMembers;
    @Column (name = "image", nullable = true, unique = false, updatable = true)
    private String image;
    @Column (name = "created_at", nullable = false, unique = false, updatable = false)
    private LocalDateTime createdAt;
    @Column (name = "start_date", nullable = false, unique = false, updatable = true)
    private LocalDateTime startDate;
    @Column ( name = "in_progress_date", nullable = true, unique = false, updatable = true)
    private LocalDateTime inProgressDate;
    @Column (name = "end_date", nullable = false, unique = false, updatable = true)
    private LocalDateTime endDate;
    @Column (name = "concluson_date", nullable = true, unique = false, updatable = true)
    private LocalDateTime conclusionDate;
    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_resources",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id")
    )
    private Set<ResourceEntity> resources;
    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_tasks",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private Set<TaskEntity> tasks;

    public Set<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(Set<TaskEntity> tasks) {
        this.tasks = tasks;
    }

    public enum Status {
        Planning(100),
        Ready(200),
        Approved(300),
        In_Progress(400),
        Finished(500),
        Cancelled(0);

        public final int value;

        Status(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
        public static Status fromValue(int value) {
            for (Status status : Status.values()) {
                if (status.getValue() == value) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Invalid status value: " + value);
        }


    }
    public ProjectEntity() {
    }

    public ProjectEntity(ProjectDto projectDto, UserEntity creator) {
        this.name = projectDto.getName();
        this.description = projectDto.getDescription();
        this.image = projectDto.getImage();
        this.status = Status.valueOf(projectDto.getStatus());
        this.maxMembers = projectDto.getMaxTeamMembers();
        this.startDate = projectDto.startDate;
        this.endDate = projectDto.endDate;
        this.createdAt = LocalDateTime.now();



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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getInProgressDate() {
        return inProgressDate;
    }

    public void setInProgressDate(LocalDateTime inProgressDate) {
        this.inProgressDate = inProgressDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getConclusionDate() {
        return conclusionDate;
    }

    public void setConclusionDate(LocalDateTime conclusionDate) {
        this.conclusionDate = conclusionDate;
    }

    public Set<ResourceEntity> getResources() {
        return resources;
    }

    public void setResources(Set<ResourceEntity> resources) {
        this.resources = resources;
    }
}
