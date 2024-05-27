package dao;

import entities.InterestEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Set;

@Stateless
public class InterestDao extends AbstractDao<InterestEntity> {
    @PersistenceContext
    private EntityManager em;
    private static final long serialVersionUID = 1L;
    public InterestDao() {
        super(InterestEntity.class);
    }
    public InterestEntity findInterestByName(String name) {
        try {
            return (InterestEntity) em.createNamedQuery("InterestEntity.findInterestByName").setParameter("name", name)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
    public List<InterestEntity> findInterestByType(InterestEntity.InterestType type) {
        try {
            return em.createNamedQuery("InterestEntity.findInterestByType").setParameter("interestType", type)
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
