package dao;
import entities.TaskEntity;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
public class TaskDao extends AbstractDao<TaskEntity>{

    public TaskDao() {
        super(TaskEntity.class);
    }

    public TaskEntity findTaskByName(String title) {
        try {
            return (TaskEntity) em.createNamedQuery("TaskEntity.findTaskByTitle").setParameter("title", title)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
    public TaskEntity findTaskByProject(TaskEntity project) {
        try {
            return (TaskEntity) em.createNamedQuery("TaskEntity.findTaskByProject").setParameter("project", project)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
    public List<TaskEntity> findTaskByUser(TaskEntity user) {
        try {
            return em.createNamedQuery("TaskEntity.findTaskByUser").setParameter("user", user)
                    .getResultList();

        } catch (Exception e) {
            return null;
        }
    }




}
