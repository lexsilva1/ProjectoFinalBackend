package entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "interests")
public class LabEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;
    @Column
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
        LISBOA,
        COIMBRA,
        PORTO,
        TOMAR,
        VISEU,
        VILA_REAL
    }
}
