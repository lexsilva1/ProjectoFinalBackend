package dao;

import entities.ProjectEntity;
import entities.ResourceEntity;
import jakarta.ejb.Stateless;
import entities.ProjectResourceEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class ProjectResourceDao extends AbstractDao<ProjectResourceEntity> {
    @PersistenceContext
    private EntityManager em;
    private static final long serialVersionUID = 1L;

    public ProjectResourceDao() {
        super(ProjectResourceEntity.class);
    }

    public List<ProjectResourceEntity> findProjectResources(int projectId) {
        return em.createNamedQuery("ProjectResourceEntity.findProjectResourcesByProjectId", ProjectResourceEntity.class)
                .setParameter("projectId", projectId)
                .getResultList();
    }

    public ProjectResourceEntity findMostUsedResource() {
        return em.createNamedQuery("ProjectResourceEntity.findMostUsedResource", ProjectResourceEntity.class)
                .getSingleResult();
    }
    public ProjectResourceEntity findByProjectAndResource(int projectId, int resourceId) {
        return em.createQuery("SELECT pr FROM ProjectResourceEntity pr WHERE pr.project_id = :projectId AND pr.resource_id = :resourceId", ProjectResourceEntity.class)
                .setParameter("projectId", projectId)
                .setParameter("resourceId", resourceId)
                .getSingleResult();
    }
    public List<ProjectResourceEntity> findProjectResourceByResource(int resourceId) {
        return em.createQuery("SELECT pr FROM ProjectResourceEntity pr WHERE pr.resource_id = :resourceId order by quantity", ProjectResourceEntity.class)
                .setParameter("resourceId", resourceId)
                .getResultList();
    }
    public List<ProjectResourceEntity> findProjectResourceByProject(int projectId) {
        return em.createQuery("SELECT pr FROM ProjectResourceEntity pr WHERE pr.project_id = :projectId order by  quantity", ProjectResourceEntity.class)
                .setParameter("projectId", projectId)
                .getResultList();
    }


}
