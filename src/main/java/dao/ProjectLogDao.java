package dao;

import entities.ProjectLogEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class ProjectLogDao extends AbstractDao{
    @PersistenceContext
    private EntityManager em;
    private static final long serialVersionUID = 1L;
    public ProjectLogDao() { super(ProjectLogEntity.class);}

    public ProjectLogEntity findProjectLogByProjectId(int projectId) {
        try {
            return (ProjectLogEntity) em.createNamedQuery("ProjectLogEntity.getLogsByProject").setParameter("project_id", projectId)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }public List<ProjectLogEntity> findProjectLogsByProjectId(int projectId) {
        try {
            return em.createNamedQuery("ProjectLogEntity.getLogsByProject").setParameter("project_id", projectId)
                    .getResultList();

        } catch (Exception e) {
            return null;
        }
    }
}
