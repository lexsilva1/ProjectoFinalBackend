package dao;

import entities.ProjectEntity;
import entities.ProjectUserEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class ProjectDao extends AbstractDao<ProjectEntity> {
    @PersistenceContext
    private EntityManager em;
    private static final long serialVersionUID = 1L;
    ProjectDao() {
        super(ProjectEntity.class);
    }
    public ProjectEntity findProjectByName(String name) {
        try {
            return (ProjectEntity) em.createNamedQuery("ProjectEntity.findProjectByName").setParameter("name", name)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
    public List<ProjectEntity> findProjectsByLab(ProjectEntity lab) {
        try {
            return em.createNamedQuery("ProjectEntity.findProjectByLab").setParameter("lab", lab)
                    .getResultList();

        } catch (Exception e) {
            return null;
        }
    }
    public ProjectEntity findProjectBySkill(ProjectEntity skills) {
        try {
            return (ProjectEntity) em.createNamedQuery("ProjectEntity.findProjectBySkill").setParameter("skills", skills)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
    public ProjectEntity findProjectByInterest(ProjectEntity interests) {
        try {
            return (ProjectEntity) em.createNamedQuery("ProjectEntity.findProjectByInterest").setParameter("interests", interests)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
    public ProjectEntity findProjectByStatus(ProjectEntity status) {
        try {
            return (ProjectEntity) em.createNamedQuery("ProjectEntity.findProjectByStatus").setParameter("status", status)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
    public List<ProjectEntity> findAllProjects() {
        try {
            return em.createNamedQuery("ProjectEntity.findAllProjects")
                    .getResultList();

        } catch (Exception e) {
            return null;
        }
    }
}
