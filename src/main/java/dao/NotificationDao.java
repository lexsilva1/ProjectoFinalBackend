package dao;

import entities.NotificationEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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

}
