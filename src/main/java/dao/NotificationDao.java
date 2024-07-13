package dao;

import entities.NotificationEntity;
import entities.ProjectEntity;
import entities.UserEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.query.criteria.spi.CriteriaBuilderExtension;

import java.util.ArrayList;
import java.util.List;
/**
 * The DAO class for the notification.
 */
@Stateless
public class NotificationDao extends AbstractDao<NotificationEntity>{
    @PersistenceContext
    private EntityManager em;

    public NotificationDao() {
        super(NotificationEntity.class);
    }
    private static final long serialVersionUID = 1L;
/**
     * Find a notification by id.
     *
     * @return the notification
     */
    public NotificationEntity findNotificationById(int id) {
        return em.find(NotificationEntity.class, id);
    }
    /**
     * Find all notifications.
     *
     * @return the list of notifications
     */
    public List<NotificationEntity> findNotificationByUser(UserEntity user) {
        return em.createNamedQuery("NotificationEntity.findNotificationByUser").setParameter("user", user)
                .getResultList();
    }
    public List<NotificationEntity> findNotificationByProject(ProjectEntity project) {
        return em.createNamedQuery("NotificationEntity.findNotificationByProject").setParameter("project", project)
                .getResultList();
    }
    /**
     * Find all notifications.
     *
     * @return the list of notifications
     */
    public List<NotificationEntity> findNotifications(ProjectEntity project, UserEntity user, Boolean isRead) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<NotificationEntity> cq = cb.createQuery(NotificationEntity.class);
        Root<NotificationEntity> root = cq.from(NotificationEntity.class);
        cq.select(root);
        List<Predicate> predicates = new ArrayList<>();
        if(project != null) {
            predicates.add(cb.equal(root.get("project"), project));
        }
        if(user != null) {
            predicates.add(cb.equal(root.get("user"), user));
        }
        if(isRead != null) {
            predicates.add(cb.equal(root.get("isRead"), isRead));
        }
        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        cq.orderBy(cb.desc(root.get("time"))); // Order by time in descending order
        return em.createQuery(cq).getResultList();
    }
    public int findLastNotificationIdByUser(UserEntity user) {
        return em.createNamedQuery("NotificationEntity.findLastNotificationIdByUser", Integer.class).getSingleResult();
    }

}
