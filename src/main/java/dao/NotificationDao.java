package dao;

import entities.NotificationEntity;
import entities.ProjectEntity;
import entities.UserEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.query.criteria.spi.CriteriaBuilderExtension;

import java.util.List;

@Stateless
public class NotificationDao extends AbstractDao<NotificationEntity>{
    @PersistenceContext
    private EntityManager em;

    public NotificationDao() {
        super(NotificationEntity.class);
    }
    private static final long serialVersionUID = 1L;

    public NotificationEntity findNotificationById(int id) {
        return em.find(NotificationEntity.class, id);
    }
    public List<NotificationEntity> findNotificationByUser(UserEntity user) {
        return em.createNamedQuery("NotificationEntity.findNotificationByUser").setParameter("user", user)
                .getResultList();
    }
    public List<NotificationEntity> findNotificationByProject(ProjectEntity project) {
        return em.createNamedQuery("NotificationEntity.findNotificationByProject").setParameter("project", project)
                .getResultList();
    }
    public List<NotificationEntity> findNotifications(String projectName, int userId, Boolean isRead) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<NotificationEntity> cq = cb.createQuery(NotificationEntity.class);
        Root<NotificationEntity> root = cq.from(NotificationEntity.class);
        cq.select(root);
        if(projectName != null) {
            cq.where(cb.equal(root.get("project"), projectName));
        }
        if(userId != 0) {
            cq.where(cb.equal(root.get("user"), userId));
        }
        if(isRead != null) {
            cq.where(cb.equal(root.get("isRead"), isRead));
        }

        return em.createQuery(cq).getResultList();
    }

}
