package entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "project_resources")
public class ProjectResourceEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;
    @JoinColumn(name = "resource_id", nullable = false, unique = true, updatable = false)
    private int resource_id;
    @Column(name = "quantity", nullable = false, unique = false, columnDefinition = "int default 0", updatable = true)
    private int quantity;
    @JoinColumn(name = "project_id", nullable = false, unique = true, updatable = false)
    private int project_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResource_id() {
        return resource_id;
    }

    public void setResource_id(int resource_id) {
        this.resource_id = resource_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public ProjectResourceEntity() {
    }




}
