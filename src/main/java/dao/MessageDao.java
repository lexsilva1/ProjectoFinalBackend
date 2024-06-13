package dao;
import entities.MessageEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class MessageDao  extends AbstractDao<MessageEntity>{
@PersistenceContext
private EntityManager em;
private static final long serialVersionUID = 1L;
    public MessageDao() {
        super(MessageEntity.class);
    }

    public MessageEntity findMessageByUser(int id) {
        return em.find(MessageEntity.class, id);
    }
    public MessageEntity findMessageByProject(int id) {
        return em.find(MessageEntity.class, id);
    }
    public void createMessage(MessageEntity message) {
       em.persist(message);
    }

}
