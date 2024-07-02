package dao;

import entities.ChatEntity;
import entities.ProjectEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class GroupChatDao extends AbstractDao<ChatEntity> {
    @PersistenceContext
    private EntityManager em;
    private static final long serialVersionUID = 1L;
    public GroupChatDao() {
        super(ChatEntity.class);
    }

    public List<ChatEntity> getAllChatByProject(ProjectEntity project) {
        try {
            return em.createNamedQuery("ChatEntity.getAllChatsByProject").setParameter("project", project)
                    .getResultList();

        } catch (Exception e) {
            return null;
        }
    }
}
