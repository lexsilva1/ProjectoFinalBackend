package dao;

import entities.InterestEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.HashSet;
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
    public List<InterestEntity> findAllInterests() {
        try {
            return em.createNamedQuery("InterestEntity.findAllInterests").getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    public Set<InterestEntity> findInterestsByName(Set<String> names) {
        try {
            List<InterestEntity> skillList = em.createQuery(
                            "SELECT s FROM InterestEntity s LEFT JOIN FETCH s.users WHERE s.name IN :names",
                            InterestEntity.class)
                    .setParameter("names", names)
                    .getResultList();
            return new HashSet<>(skillList);
        } catch (Exception e) {
            System.out.println("exception in findSkillsByName"+    e.getMessage());
            return null;
        }
    }
}
