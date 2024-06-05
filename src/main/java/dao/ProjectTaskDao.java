package dao;

import entities.ProjectTaskEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class ProjectTaskDao extends AbstractDao<ProjectTaskEntity> {
@PersistenceContext
private EntityManager em;
private static final long serialVersionUID = 1L;

public ProjectTaskDao() {
    super(ProjectTaskEntity.class);


}

}
