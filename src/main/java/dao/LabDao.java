package dao;

import entities.LabEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class LabDao extends AbstractDao<LabEntity>{
    @PersistenceContext
    private EntityManager em;
    private static final long serialVersionUID = 1L;
    public LabDao() {
        super(LabEntity.class);
    }


    /**
     * Find a lab by location
     * @param location
     * @return
     */
    public LabEntity findLabByLocation(LabEntity.Lab location) {
        try {
            return (LabEntity) em.createNamedQuery("LabEntity.findLabByLocation").setParameter("location", location).getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }
}
