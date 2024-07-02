package dao;

import jakarta.ejb.Stateless;
import entities.ProjectResourceEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class ProjectResourceDao extends AbstractDao<ProjectResourceEntity>{
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


}
