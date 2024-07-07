package entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "project_resources")
@NamedQuery(name = "ProjectResourceEntity.findProjectResourcesByProjectId", query = "SELECT pr FROM ProjectResourceEntity pr WHERE pr.project.id = :projectId")
@NamedQuery(name = "ProjectResourceEntity.findMostUsedResource", query = "SELECT pr FROM ProjectResourceEntity pr WHERE pr.quantity = (SELECT MAX(pr.quantity) FROM ProjectResourceEntity pr)")
public class ProjectResourceEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id", nullable = false, updatable = false)
    private ResourceEntity resource;

    @Column(name = "quantity", nullable = false, columnDefinition = "int default 0", updatable = true)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false, updatable = false)
    private ProjectEntity project;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ResourceEntity getResource() {
        return resource;
    }

    public void setResource(ResourceEntity resource) {
        this.resource = resource;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public ProjectResourceEntity() {
    }
}
