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
    @Column(name = "quantity", nullable = false, unique = false)
    private int quantity;
    @JoinColumn(name = "project_id", nullable = false, unique = true, updatable = false)
    private int project_id;
}
