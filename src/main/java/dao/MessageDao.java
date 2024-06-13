package dao;
import entities.MessageEntity;
import entities.UserEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class MessageDao  extends AbstractDao<MessageEntity>{
@PersistenceContext
private EntityManager em;
private static final long serialVersionUID = 1L;
    public MessageDao() {
        super(MessageEntity.class);
    }

    public List<MessageEntity> findMessagesByUser(int id, int id2) {
        UserEntity user = em.find(UserEntity.class, id);
        UserEntity user2 = em.find(UserEntity.class, id2);
        return em.createQuery("SELECT m FROM MessageEntity m WHERE " +
                        "(m.sender = :user1 AND m.receiver = :user2) OR " +
                        "(m.sender = :user2 AND m.receiver = :user1) ORDER BY m.time DESC", MessageEntity.class)
                .setParameter("user1", user)
                .setParameter("user2", user2)
                .getResultList();
    }
    public List<MessageEntity> findLastMessagesByUser(int id) {
        return em.createQuery(
                        "SELECT m FROM MessageEntity m " +
                                "WHERE m.time = " +
                                "(SELECT MAX(m2.time) FROM MessageEntity m2 " +
                                "WHERE ((m2.sender.id = :userId AND m2.receiver.id = m.receiver.id) OR " +
                                "(m2.receiver.id = :userId AND m2.sender.id = m.sender.id))) " +
                                "AND (m.sender.id = :userId OR m.receiver.id = :userId) order by m.time desc",
                        MessageEntity.class)
                .setParameter("userId", id)
                .getResultList();
    }
    public MessageEntity findMessageByProject(int id) {
        return em.find(MessageEntity.class, id);
    }
    public void createMessage(MessageEntity message) {
       em.persist(message);
    }


}
