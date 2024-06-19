package dao;

import jakarta.ejb.Stateless;
import entities.SkillEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Stateless
public class SkillDao extends AbstractDao<SkillEntity>
{
    @PersistenceContext
    private EntityManager em;
    public SkillDao() {
        super(SkillEntity.class);
    }
    public SkillEntity findSkillByName(String name) {
        try {
            return (SkillEntity) em.createNamedQuery("SkillEntity.findSkillByName").setParameter("name", name)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
    public List<SkillEntity> findSkillByType(SkillEntity.SkillType type) {
        try {
            return em.createNamedQuery("SkillEntity.findSkillByType").setParameter("skillType", type)
                    .getResultList();

        } catch (Exception e) {
            return null;
        }
    }
    public List<SkillEntity> findAllSkills() {
        try {
            return em.createNamedQuery("SkillEntity.findAllSkills").getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    public Set<SkillEntity> findSkillsByName(Set<String> names) {
        try {
            List<SkillEntity> skillList = em.createQuery(
                            "SELECT s FROM SkillEntity s LEFT JOIN FETCH s.users WHERE s.name IN :names",
                            SkillEntity.class)
                    .setParameter("names", names)
                    .getResultList();
            return new HashSet<>(skillList);
        } catch (Exception e) {
            System.out.println("exception in findSkillsByName"+    e.getMessage());
            return null;
        }
    }

}

