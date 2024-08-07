package dao;
import entities.ProjectEntity;
import entities.ProjectUserEntity;
import entities.UserEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;

import java.util.List;
/**
 * The DAO class for the project user.
 */
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
    public ProjectUserEntity findProjectUserByUser(UserEntity user) {
        try {
            return (ProjectUserEntity) em.createNamedQuery("ProjectUserEntity.findProjectUserByUser").setParameter("user", user)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
    /**
     * Find all project users.
     *
     * @return the list of project users
     */
    public List<ProjectUserEntity> findAllProjectUsers(ProjectEntity project) {
        try {
            return em.createNamedQuery("ProjectUserEntity.findAllProjectUsers").setParameter("project", project)
                    .getResultList();

        } catch (Exception e) {
            return null;
        }
    }
    /**
     * Find all team members.
     *
     * @return the list of team members
     */
    public List<ProjectUserEntity> findTeamMembers(ProjectEntity project) {
        try {
            return em.createNamedQuery("ProjectUserEntity.findProjectUserByProject").setParameter("project", project)
                    .getResultList();

        } catch (Exception e) {
            return null;
        }
    }
    /**
     * Find all project users.
     *
     * @return the list of project users
     */
    public ProjectUserEntity findProjectUserByProjectAndUser(ProjectEntity project, UserEntity user) {
        try {
            return (ProjectUserEntity) em.createNamedQuery("ProjectUserEntity.findProjectUserByUserAndProject").setParameter("project", project)
                    .setParameter("user", user).getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
    /**
     * Find all project users.
     *
     * @return the list of project users
     */
    public List<ProjectUserEntity> findProjectManagers(ProjectEntity project) {
        try {
            return em.createNamedQuery("ProjectUserEntity.findProjectManagers").setParameter("project", project)
                    .getResultList();

        } catch (Exception e) {
            return null;
        }
    }
    /**
     * Find all project users.
     *
     * @return the list of project users
     */
    public ProjectUserEntity findProjectCreator(ProjectEntity project) {
        try {
            return (ProjectUserEntity) em.createNamedQuery("ProjectUserEntity.findProjectCreator").setParameter("project", project)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

}
