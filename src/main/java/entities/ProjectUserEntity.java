package entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;
/**
 * The entity class for the project_user table.
 */
@Entity
@Table(name = "project_user")
@NamedQuery(name = "ProjectUserEntity.findProjectUserByUserAndProject", query = "SELECT pu FROM ProjectUserEntity pu WHERE pu.user = :user AND pu.project = :project")
@NamedQuery(name = "ProjectUserEntity.findProjectUserByProject", query = "SELECT pu FROM ProjectUserEntity pu WHERE pu.project = :project")
@NamedQuery(name = "ProjectUserEntity.findAllProjectUsers", query = "SELECT pu FROM ProjectUserEntity pu WHERE pu.project = :project")
@NamedQuery(name = "ProjectUserEntity.findProjectUserByUser", query = "SELECT pu FROM ProjectUserEntity pu WHERE pu.user = :user")
@NamedQuery(name = "ProjectUserEntity.findProjectManagers", query = "SELECT pu FROM ProjectUserEntity pu WHERE pu.project = :project AND pu.isProjectManager = true")
@NamedQuery(name = "ProjectUserEntity.findProjectCreator", query = "SELECT p FROM ProjectUserEntity p WHERE p.project = :project AND p.approvalStatus = ApprovalStatus.CREATOR")
public class ProjectUserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @Column(name = "is_project_manager")
    private boolean isProjectManager;
    @Column(name = "approval_status")
    private ApprovalStatus approvalStatus;

    public enum ApprovalStatus {
        INVITED,
        APPLIED,
        MEMBER,
        CREATOR
    }
    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }
    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public boolean isProjectManager() {
        return isProjectManager;
    }

    public void setProjectManager(boolean projectManager) {
        isProjectManager = projectManager;
    }
}
