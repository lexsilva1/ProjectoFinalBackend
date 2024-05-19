package entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "resources")
public class ResourceEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;
    @Column(name = "name", nullable = false, unique = true, updatable = false)
    private String name;
    @Column(name = "description", nullable = false, unique = true, updatable = false)
    private String description;
    @Column (name = "type", nullable = false, unique = false)
    private ResourceType type;
    @Column (name = "identifier", nullable = false, unique = true)
    private String identifier;
    @Column (name = " supplier", nullable = false, unique = false)
    private String supplier;
    @Column (name = "supplierContact", nullable = false, unique = false)
    private String supplierContact;
    @Column (name = " stock", nullable = false, unique = false)
    private int stock;
    @Column (name = "observations", nullable = false, unique = false)
    private String observations;
    public enum ResourceType {
        COMPONENT,
        RESOURCE
    }
}
