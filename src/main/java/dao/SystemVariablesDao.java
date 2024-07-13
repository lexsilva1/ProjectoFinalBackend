package dao;

import entities.SystemVariablesEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class SystemVariablesDao extends AbstractDao<SystemVariablesEntity>{
    @PersistenceContext
    private EntityManager em;
    public SystemVariablesDao() {
        super(SystemVariablesEntity.class);
    }
    private static final long serialVersionUID = 1L;
/**
     * Find all system variables.
     *
     * @return the list of system variables
     */
    public SystemVariablesEntity findSystemVariableById(int id) {
        return em.find(SystemVariablesEntity.class, id);
    }
    /**
     * Create system variables.
     *
     */
    public void create(int timeout, int maxUsers) {
        em.persist(new SystemVariablesEntity(timeout, maxUsers));
    }

}
