package dao;
import entities.ProjectUserEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;

@Stateless
public class ProjectUserDao extends AbstractDao<ProjectUserEntity>{
    @PersistenceContext
    private EntityManager em;
    private static final long serialVersionUID = 1L;
    public ProjectUserDao() {
        super(ProjectUserEntity.class);
    }
    public ProjectUserEntity findProjectUserByProject(ProjectUserEntity project) {
        try {
            return (ProjectUserEntity) em.createNamedQuery("ProjectUserEntity.findProjectUserByProject").setParameter("project", project)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
    public ProjectUserEntity findProjectUserByUser(ProjectUserEntity user) {
        try {
            return (ProjectUserEntity) em.createNamedQuery("ProjectUserEntity.findProjectUserByUser").setParameter("user", user)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

}
