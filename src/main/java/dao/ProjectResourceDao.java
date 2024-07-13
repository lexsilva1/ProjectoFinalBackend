package dao;

import entities.ProjectEntity;
import entities.ResourceEntity;
import jakarta.ejb.Stateless;
import entities.ProjectResourceEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
/**
 * The DAO class for the project resource.
 */
@Stateless
public class ProjectResourceDao extends AbstractDao<ProjectResourceEntity> {
    @PersistenceContext
    private EntityManager em;
    private static final long serialVersionUID = 1L;

    public ProjectResourceDao() {
        super(ProjectResourceEntity.class);
    }
/**
     * Find all project resources.
     *
     * @return the list of project resources
     */
    public List<ProjectResourceEntity> findProjectResources(int projectId) {
        return em.createNamedQuery("ProjectResourceEntity.findProjectResourcesByProjectId", ProjectResourceEntity.class)
                .setParameter("projectId", projectId)
                .getResultList();
    }

    public ProjectResourceEntity findMostUsedResource() {
        return em.createNamedQuery("ProjectResourceEntity.findMostUsedResource", ProjectResourceEntity.class)
                .getSingleResult();
    }
    /**
     * Find a project resource by project and resource.
     * @param projectId
     * @param resourceId
     * @return
     */
    public ProjectResourceEntity findByProjectAndResource(ProjectEntity projectId, ResourceEntity resourceId) {
        try{
        return em.createQuery("SELECT pr FROM ProjectResourceEntity pr WHERE pr.project = :projectId AND pr.resource = :resourceId", ProjectResourceEntity.class)
                .setParameter("projectId", projectId)
                .setParameter("resourceId", resourceId)
                .getSingleResult();
        } catch (Exception e) {
            return null;

        }
    }
/**
     * Find all project resources.
     *
     * @return the list of project resources
     */
    public List<ProjectResourceEntity> findAllProjectResources() {
        return em.createQuery("SELECT pr FROM ProjectResourceEntity pr", ProjectResourceEntity.class)
                .getResultList();
    }


}
