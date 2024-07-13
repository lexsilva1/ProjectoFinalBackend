package dao;

import entities.ProjectLogEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
@Stateless
public class ProjectLogDao extends AbstractDao{
    @PersistenceContext
    private EntityManager em;
    private static final long serialVersionUID = 1L;
    public ProjectLogDao() { super(ProjectLogEntity.class);}
/**
     * Find all project logs from a specific project.
     *
     * @return the list of project logs
     */
    public ProjectLogEntity findProjectLogByProjectId(int projectId) {
        try {
            return (ProjectLogEntity) em.createNamedQuery("ProjectLogEntity.getLogsByProject").setParameter("project_id", projectId)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }

    }
    /**
     * Find projects logs by project id.
     *
     * @return the list of project logs
     */
    public List<ProjectLogEntity> findProjectLogsByProjectId(int projectId) {
        try {
            return em.createNamedQuery("ProjectLogEntity.getLogsByProject").setParameter("project_id", projectId)
                    .getResultList();

        } catch (Exception e) {
            return null;
        }
    }
    /**
     *Create a project log.
     * @param projectLogEntity
     * @return
     */
    public boolean create(ProjectLogEntity projectLogEntity) {
        try {
            em.persist(projectLogEntity);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
