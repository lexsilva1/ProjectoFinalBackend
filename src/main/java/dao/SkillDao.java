package dao;

import jakarta.ejb.Stateless;
import entities.SkillEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

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
}

