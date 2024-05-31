package entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "resources")
@NamedQuery(name = "Resource.findResourceById", query = "SELECT r FROM ResourceEntity r WHERE r.id = :id")
@NamedQuery(name = "Resource.findResourceByName", query = "SELECT r FROM ResourceEntity r WHERE r.name = :name")
@NamedQuery(name = "Resource.findResourceByType", query = "SELECT r FROM ResourceEntity r WHERE r.type = :type")
@NamedQuery(name = "Resource.findResourceBySupplier", query = "SELECT r FROM ResourceEntity r WHERE r.supplier = :supplier")

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
    public ResourceEntity() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(String supplierContact) {
        this.supplierContact = supplierContact;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}
