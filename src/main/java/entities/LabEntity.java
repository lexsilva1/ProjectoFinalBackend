package entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "Labs")
@NamedQuery(name = "LabEntity.findLabByLocation", query = "SELECT l FROM LabEntity l WHERE l.location = :location")
@NamedQuery(name = "LabEntity.findAllLabs", query = "SELECT l FROM LabEntity l")
public class LabEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;
    @Column(name = "location", nullable = false, unique = true)
    private Lab location;


    public LabEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Lab getLocation() {
        return location;
    }

    public void setLocation(Lab location) {
        this.location = location;
    }



    public enum Lab{
        Lisboa,
        Coimbra,
        Porto,
        Tomar,
        Viseu,
        Vila_Real
    }


}
