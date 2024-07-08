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

    public List<ProjectResourceEntity> findAllProjectResources() {
        return em.createQuery("SELECT pr FROM ProjectResourceEntity pr", ProjectResourceEntity.class)
                .getResultList();
    }


}
